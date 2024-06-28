package com.hfc.springboot.mapper;

import com.hfc.springboot.entity.Book;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by hfc on 2021/4/16.
 */
@Mapper
public interface BookMapper {

    Book queryById(Integer id);

    Book queryByTitle(String title);

}
