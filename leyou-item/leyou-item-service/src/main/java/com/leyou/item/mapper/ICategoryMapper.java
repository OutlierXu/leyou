package com.leyou.item.mapper;


import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**继承通用mapper
 * @author XuHao
 */
@org.apache.ibatis.annotations.Mapper
public interface ICategoryMapper extends Mapper<Category>{

    List<Category> queryCategoryListByBid(@Param("bid") Long bid);
}
