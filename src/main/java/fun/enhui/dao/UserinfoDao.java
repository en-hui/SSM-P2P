package fun.enhui.dao;

import fun.enhui.model.Userinfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserinfoDao {

    @Insert("insert into userinfo(id,version,bitState,realName,idNumber,phoneNumber,email," +
            "department_id,profession_id,studyId,clazz,realAuthId) " +
            " values(#{id},0,#{bitState},#{realName},#{idNumber},#{phoneNumber},#{email}," +
            "#{department.id},#{profession.id},#{studyId},#{clazz},#{realAuthId})")
    int insert(Userinfo record);

    @Select("select id,version,bitState,realName,idNumber,phoneNumber,email," +
            "department_id,profession_id,studyId,clazz,realAuthId from userinfo where id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "version",property = "version"),
            @Result(column = "bitState",property = "bitState"),
            @Result(column = "realName",property = "realName"),
            @Result(column = "idNumber",property = "idNumber"),
            @Result(column = "phoneNumber",property = "phoneNumber"),
            @Result(column = "email",property = "email"),

            @Result(property = "department",column = "department_id",
                    one = @One(select = "fun.enhui.dao.SystemDictionaryDao.selectByPrimaryKey")),
            @Result(property = "profession",column = "profession_id",
                    one = @One(select = "fun.enhui.dao.SystemDictionaryItemDao.selectByPrimaryKey")),
            @Result(column = "studyId",property = "studyId"),
            @Result(column = "clazz",property = "clazz"),
            @Result(column = "realAuthId",property = "realAuthId")
    })
    Userinfo selectByPrimaryKey(Long id);

    @Update("update userinfo set " +
            "version=version+1,bitState=#{bitState},realName=#{realName},idNumber=#{idNumber}," +
            "phoneNumber=#{phoneNumber},email=#{email},department_id=#{department.id},profession_id=#{profession.id}," +
            "studyId=#{studyId}," +"clazz=#{clazz},realAuthId=#{realAuthId} where id=#{id} AND version=#{version}")
    int updateByPrimaryKey(Userinfo record);
}
