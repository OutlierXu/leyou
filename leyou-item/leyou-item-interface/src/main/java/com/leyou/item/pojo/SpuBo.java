package com.leyou.item.pojo;

import java.util.List;

/**
 * @author XuHao
 * @Title: SpuBo
 * @ProjectName leyou
 * @Description: 继承SPU对象，用于页面展示的临时对象
 * @date 2018/9/621:22
 */
public class SpuBo extends Spu {

    //商品分类名称
    private String cname;

    //品牌名称
    private String bname;

    //商品詳情
    private SpuDetail spuDetail;

    //sku列表
    private List<Sku> skus;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    @Override
    public String toString() {
        return "SpuBo{" +
                "cname='" + cname + '\'' +
                ", bname='" + bname + '\'' +
                ", spuDetail=" + spuDetail +
                ", skus=" + skus +
                '}';
    }
}
