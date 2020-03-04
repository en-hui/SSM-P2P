package fun.enhui.dao;

import fun.enhui.model.SystemDictionaryItem;
import fun.enhui.query.SystemDictionaryQueryObject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemDictionaryItemDao {

    @Insert("insert into systemdictionaryitem(parentId,title,sequence) values(#{parentId},#{title},#{sequence})")
    int insert(SystemDictionaryItem record);

    @Select("select id,parentId,title,sequence from systemdictionaryitem where id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "parentId",property = "parentId"),
            @Result(column = "title",property = "title"),
            @Result(column = "sequence",property = "sequence")
    })
    SystemDictionaryItem selectByPrimaryKey(Long id);

    @Update("update systemdictionaryitem set parentId = #{parentId},title = #{title},sequence=#{sequence} where id=#{id}")
    int updateByPrimaryKey(SystemDictionaryItem record);

    /**
     * 两个分页相关的查询
     */
    @Select("<script>" +
            "select count(id) from systemdictionaryitem where 1=1" +
                "<if test='parentId!=null'>" +
                "   AND  parentId=#{parentId}" +
                "</if>" +
                "<if test='keyword!=null'>" +
                "   AND title like concat('%',#{keyword},'%')  "+
                "</if> "+
            "</script>")
    int queryForCount(SystemDictionaryQueryObject qo);

    @Select("<script>" +
                "select id,parentId,title,sequence from systemdictionaryitem where 1=1"+
            "<if test='parentId!=null'>" +
                "AND parentId=#{parentId}" +
            "</if>" +
            "<if test='keyword!=null'>" +
            "   AND title like concat('%',#{keyword},'%')" +
            "LIMIT #{start},#{pageSize}" +
            "</if>  "+
            "</script>")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "parentId",property = "parentId"),
            @Result(column = "title",property = "title"),
            @Result(column = "sequence",property = "sequence")
    })
    List<SystemDictionaryItem> query(SystemDictionaryQueryObject qo);


    /**
     * 根据数据字典分类sn查询明细
     */
    @Select("select item.id,item.parentId,item.title,item.sequence from systemdictionaryitem item join systemdictionary d on item.parentId=d.id " +
            "   where d.id=#{id} ORDER BY item.sequence ASC")
    @Results({
            @Result(id = true,column = "item.id",property = "id"),
            @Result(column = "item.parentId",property = "parentId"),
            @Result(column = "item.title",property = "title"),
            @Result(column = "item.sequence",property = "sequence")
    })
    List<SystemDictionaryItem> listByParentId(int id);
}
