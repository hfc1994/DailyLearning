package com.hfc.springboot.mapper;

import com.hfc.springboot.entity.ItemList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by hfc on 2021/4/16.
 */
@Mapper
public interface ItemListMapper {

    @Select("select * from item_list where id = ${id}")
    ItemList queryById(Integer id);

}
