package com.redhat.ldemasi.example.mapper;

import com.redhat.ldemasi.example.model.Example;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExampleMapper {
    @Select("SELECT * FROM Examples WHERE id = #{id}")
    Example getExample(@Param("id") String id);
}
