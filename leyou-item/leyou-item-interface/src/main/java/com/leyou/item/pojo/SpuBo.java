package com.leyou.item.pojo;

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
}
