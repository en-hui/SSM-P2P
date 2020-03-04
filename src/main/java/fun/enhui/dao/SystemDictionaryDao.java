package fun.enhui.dao;

import fun.enhui.model.SystemDictionary;
import fun.enhui.query.SystemDictionaryQueryObject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemDictionaryDao {

    @Insert("insert into systemdictionary(sn,title) values(#{sn},#{title})")
    int insert(SystemDictionary record);

    @Select("select id,sn,title from systemdictionary where id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "sn",property = "sn"),
            @Result(column = "title",property = "title")
    })
    SystemDictionary selectByPrimaryKey(Long id);

    @Select("select id,sn,title from systemdictionary")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "sn",property = "sn"),
            @Result(column = "title",property = "title")
    })
    List<SystemDictionary> selectAll();


    /**
     *两个分页的方法
     */
    @Select("<script>select count(id) from systemdictionary where 1=1" +
            "<if test='keyword != null'>" +
            "   AND ( sn like concat('%',#{keyword},'%')" +
                "OR title like concat('%',#{keyword},'%') )" +
            "</if>" +
            "</script>")
    int queryForCount(SystemDictionaryQueryObject qo);

    @Select("<script>select id,sn,title from systemdictionary where 1=1 " +
            "<if test='keyword != null'>" +
                "AND ( sn like concat('%',#{keyword},'%')"+
                "OR title like concat('%',#{keyword},'%') )"+
            "</if>" +
            "Limit #{start},#{pageSize}"+
            "</script>")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "sn",property = "sn"),
            @Result(column = "title",property = "title")
    })
    List<SystemDictionary> query(SystemDictionaryQueryObject qo);



    @Update("update systemdictionary set sn=#{sn},title=#{title} where id = #{id}")
    int updateByPrimaryKey(SystemDictionary record);

}
