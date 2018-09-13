package com.leyou.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author XuHao
 * @Title: Goods
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/920:18
 */
@Document(indexName = "goods",type = "docs",shards = 1,replicas = 0)
public class Goods {

    @Id
    private Long id;

    //private String title;
    //private String brand;
    //private String category;
    // 所有需要被搜索的信息，包含标题，分类，品牌
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String all;
    //副标题不分词，也不索引
    @Field(type = FieldType.Keyword,index = false)
    private String subTitle;

    private Long cid1;
    private Long cid2;
    private Long cid3;
    private Long brandId;
    private Date createDate;
    //当前spu下的所有sku的price集合
    private List<Long> price;

    //skuList集合（json字符串格式）
    @Field(type = FieldType.Keyword,index = false)
    private String skus;

    //可搜索的规格参数，key是参数名，值是object类型的参数值
    private Map<String,Object> specs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Long getCid1() {
        return cid1;
    }

    public void setCid1(Long cid1) {
        this.cid1 = cid1;
    }

    public Long getCid2() {
        return cid2;
    }

    public void setCid2(Long cid2) {
        this.cid2 = cid2;
    }

    public Long getCid3() {
        return cid3;
    }

    public void setCid3(Long cid3) {
        this.cid3 = cid3;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Long> getPrice() {
        return price;
    }

    public void setPrice(List<Long> price) {
        this.price = price;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    public Map<String, Object> getSpecs() {
        return specs;
    }

    public void setSpecs(Map<String, Object> specs) {
        this.specs = specs;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", all='" + all + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", cid1=" + cid1 +
                ", cid2=" + cid2 +
                ", cid3=" + cid3 +
                ", brandId=" + brandId +
                ", createDate=" + createDate +
                ", price=" + price +
                ", skus='" + skus + '\'' +
                ", specs=" + specs +
                '}';
    }
}











