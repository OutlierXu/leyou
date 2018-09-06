package com.leyou.item.mapper;


import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author XuHao
 */
public interface IBrandMapper extends Mapper<Brand> {


    /**
     * 插入brand_category中间表的记录
     * @param cid
     * @param id
     */
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES(#{cid}, #{id})")
    void insertCategoryBrand(@Param("cid") Long cid, @Param("id") Long id);

    /**
     * 删除brand_category中间表的记录
     * @param bid
     */
    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{bid}")
    void deleteCategoryBrandByBid(@Param("bid")Long bid);


    /**
     * 查询brand_category中间表的记录
     * @param bid
     */
    @Select("SELECT COUNT(*) FROM tb_category_brand WHERE brand_id = #{bid}")
    int selectCategoryBrand(@Param("bid")Long bid);
}
