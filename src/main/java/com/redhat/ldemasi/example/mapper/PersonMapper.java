package com.redhat.ldemasi.example.mapper;

import com.redhat.ldemasi.example.model.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PersonMapper {
    @Select("SELECT * FROM People WHERE id = #{id}")
    List<Person> getPerson(@Param("id") Long id);

    @Insert("Insert into People values (#{id}, #{name}, #{age})")
    int setPerson(Person person);

    @Update("Update People set name = #{name} where id = #{id}")
    Person updatePerson(Person person);

    @Select("SELECT * FROM People")
    Person getAllPeople();
}
