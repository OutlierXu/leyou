package com.leyou.search.service.serviceImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.item.pojo.*;
import com.leyou.search.Repository.GoodsRepository;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecificationClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.service.IGoodsService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author XuHao
 * @Title: GoodsService2
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1021:04
 */
@Service
public class GoodsService implements IGoodsService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private GoodsRepository goodsRepository;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Goods buildGoods(Spu spu) throws IOException {
        Goods goods = new Goods();

        //1.获取spu的title，brand，各级分类=>字符串格式
        String title = spu.getTitle();
        String brandName = brandClient.queryBrandByBid(spu.getBrandId()).getName();
        List<String> categoryNames = categoryClient.queryCategoryNamesByCids(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));

        String all = title + brandName + categoryNames;

        //获取当前spu下的所有skuList
        List<Sku> skuList = goodsClient.querySkuListBySpuId(spu.getId());

        //2.获取所有sku的价格的集合 ==>List<Long>
        List<Long> prices = new ArrayList<>();

        //3.获取每个所有sku的关键字段的信息List<Map<"字段名"，"字段值">>
        List<Map<String,Object>> skuMsgList = new ArrayList<>();
        skuList.forEach(sku -> {
            Long price = sku.getPrice();
            prices.add(price);

            Map<String, Object> skuMsg = new HashMap<>();
            skuMsg.put("id", sku.getId());
            skuMsg.put("title", sku.getTitle());
            skuMsg.put("images", sku.getImages() == null ? "" : sku.getImages().split(",")[0]);
            skuMsg.put("price", sku.getPrice());
            skuMsgList.add(skuMsg);
        });
        //3.1将skuMsgList转为json格式
        String skus = MAPPER.writeValueAsString(skuMsgList);

        //4.封装可搜索的规格参数，格式==>Map<String,Object> key是参数名称，value是参数值（可能是字符串，数值，集合类型）
        List<SpecParam> specParamList = specificationClient.selectSpecParamByGid(null, spu.getCid3(), true, null);

        //4.1查询到的通用属性==>格式Map<"参数id"，参数值>
        Map<String, Object> GenericSpec = MAPPER.readValue(goodsClient.querySpuDetailById(spu.getId()).getGenericSpec(), new TypeReference<Map<String, Object>>() {});
        //4.2查询到的通用属性==>格式Map<"参数id"，参数值集合类型>
        Map<String, List<Object>> specialSpec = MAPPER.readValue(goodsClient.querySpuDetailById(spu.getId()).getSpecialSpec(), new TypeReference<Map<String, List<Object>>>() {
        });

        Map<String, Object> specParamMap = new HashMap<>();
        specParamList.forEach(specParam -> {

            String id = specParam.getId().toString();

            if (specParam.getGeneric()){
                String value = GenericSpec.get(id).toString();
                if(specParam.getNumeric()){
                    //通用数值型参数
                    value = chooseSegment(value, specParam);
                }
                specParamMap.put(specParam.getName(),value);
            }else {
                specParamMap.put(specParam.getName(), specialSpec.get(id));
            }
        });

        //封装进goods中
        BeanUtils.copyProperties(spu, goods);
        goods.setCreateDate(spu.getCreateTime());
        goods.setAll(all);
        goods.setPrice(prices);
        goods.setSkus(skus);
        goods.setSpecs(specParamMap);


        return goods;
    }

    @Override
    public void createIndex(Long id) throws IOException {

        //1.查询数据库
        Spu spu = this.goodsClient.querySpuById(id);

        //2.构建用于存到索引库的商品
        Goods goods = this.buildGoods(spu);

        //3.商品保存到索引库
        this.goodsRepository.save(goods);

    }

    @Override
    public void deleteIndex(Long id) {
        this.goodsRepository.deleteById(id);
    }

    /**
     * 获取数值所处的区间
     * @param value 数据
     * @param p 该数值的参数对象
     * @return
     */

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }


    @Override
    public SearchResult<Goods> search(SearchRequest request) {

        if(StringUtils.isBlank(request.getKey())){
            //如果关键词为空
            return null;
        }

        //1.自定义搜索条件构建器
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

        //1.1创建基本查询条件，（输入的key值：匹配索引库中all字段）

        MatchQueryBuilder basicQuery = QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND);


        //1.4遍历前台传入的过滤条件，构建带过滤条件的基本查询
        Map<String, String> filter = request.getFilter();

        if(!filter.isEmpty()){

            //不为空 构建带过滤条件的基本查询
            BoolQueryBuilder boolQueryBuilder = buildBasicQueryWithFilter(filter, basicQuery);
            searchQueryBuilder.withQuery(boolQueryBuilder);
        } else{
            //为空，执行基本查询
            searchQueryBuilder.withQuery(basicQuery);
        }


        //1.2添加分页条件
        searchQueryBuilder.withPageable(PageRequest.of(request.getPage() - 1, request.getSize()));

        //1.3添加过滤条件（保留主要字段）
        searchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"},null));


        //2.按cid和brandId聚合 添加聚合条件
        String categoryAggName = "category";
        String brandAggName = "brand";

        searchQueryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        searchQueryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        AggregatedPage<Goods> aggGoodsPage = (AggregatedPage<Goods>) this.goodsRepository.search(searchQueryBuilder.build());

        List<Category> categoryList = parsedCategoryAgg(aggGoodsPage,categoryAggName);
        List<Brand> brandList = parseBrandAgg(aggGoodsPage,brandAggName) ;

        //3.判断聚合的分类数量是不是1,以确定是不是需要规格聚合

        List<Map<String,Object>> specs = null;

        if (categoryList.size() == 1){

            specs = getSpecs(searchQueryBuilder,categoryList.get(0).getId());
        }


        SearchResult result = new SearchResult(aggGoodsPage.getTotalElements(), aggGoodsPage.getTotalPages(), aggGoodsPage.getContent(), categoryList, brandList,specs);

        return result;
    }

    /**
     * 构建带过滤条件的基本查询条件
     * @param filter 过滤条件的集合map
     * @param basicQuery 基本匹配查询条件
     * @return
     */
    private BoolQueryBuilder buildBasicQueryWithFilter(Map<String, String> filter, MatchQueryBuilder basicQuery) {

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //添加基本查询条件
        boolQueryBuilder.must(basicQuery);

        for (Map.Entry<String, String> entry : filter.entrySet()) {

            //遍历map，过滤
            String key = entry.getKey();
            String field= "";

            if(key.equals("分类")){
                field = "cid3";
            }else if(key.equals("品牌")){
                field = "brandId";
            }else {
                field = "specs."+key+".keyword";
            }

            boolQueryBuilder.filter(QueryBuilders.termQuery(field, entry.getValue()));

        }

        return boolQueryBuilder;

    }

    private List<Map<String,Object>> getSpecs(NativeSearchQueryBuilder searchQueryBuilder, Long cid3) {
        List<Map<String,Object>> specs = new ArrayList<>();

        List<SpecParam> specParamList = this.specificationClient.selectSpecParamByGid(null, cid3, true, null);

        //NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(basicQuery);

        // 不需要任何查询结果集
        searchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));


        specParamList.forEach(specParam -> {

            Map<String, Object> specParamMap = new HashMap<>();
            String name = specParam.getName();

            specParamMap.put("key", name);

            searchQueryBuilder.addAggregation(AggregationBuilders.terms(name).field("specs." + name + ".keyword"));

            AggregatedPage<Goods> search = (AggregatedPage<Goods>) this.goodsRepository.search(searchQueryBuilder.build());

            StringTerms aggregation = (StringTerms) search.getAggregation(name);

            List<String> options = aggregation.getBuckets().stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());

            specParamMap.put("options", options);

            specs.add(specParamMap);
        });

        return  specs;

    }


    /**
     * 解析brand聚合结果集 获取所有的brandId
     * @param aggGoodsPage
     * @return 返回brand集合
     */
    private List<Brand> parseBrandAgg(AggregatedPage<Goods> aggGoodsPage,String brandAggName) {

        //获取名称为brandAggName的聚合结果
        LongTerms aggregation = (LongTerms) aggGoodsPage.getAggregation(brandAggName);

        //获取以id为key的桶
        List<LongTerms.Bucket> buckets = aggregation.getBuckets();

        List<Long> bids = buckets.stream().map(bucket -> (Long)bucket.getKeyAsNumber()).collect(Collectors.toList());

        List<Brand> brandList = this.brandClient.queryBrandByBids(bids);


        return  brandList;
    }

    /**
     * 解析category聚合结果集，获取所有的cid3，根据cid3查询对应的category名称
     * @param aggGoodsPage
     * @param categoryAggName
     * @return category的list集合
     */
    private List<Category> parsedCategoryAgg(AggregatedPage<Goods> aggGoodsPage,String categoryAggName) {

        LongTerms aggregation = (LongTerms)aggGoodsPage.getAggregation(categoryAggName);

        List<LongTerms.Bucket> buckets = aggregation.getBuckets();

        List<Long> cids = buckets.stream().map(bucket -> (Long) bucket.getKeyAsNumber()).collect(Collectors.toList());

        List<String> cames = this.categoryClient.queryCategoryNamesByCids(cids);

        ArrayList<Category> categoryList = new ArrayList<>();

        for (int i = 0; i < cames.size(); i++) {
            Category category = new Category();
            category.setId(cids.get(i));
            category.setName(cames.get(i));

            categoryList.add(category);
        }
        return categoryList;
    }
}
