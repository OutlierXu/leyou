package com.leyou.item.mapper;


import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author XuHao
 */
public interface IBrandMapper extends Mapper<Brand> {


    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES(#{cid}, #{id})")
    void insertCategoryBrand(@Param("cid") Long cid, @Param("id") Long id);

    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{bid}")
    void deleteCategoryBrandByBid(@Param("bid")Long bid);


}
