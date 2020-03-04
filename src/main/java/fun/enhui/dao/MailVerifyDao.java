package fun.enhui.dao;

import fun.enhui.model.MailVerify;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface MailVerifyDao {

    @Insert("insert into mailVerify(userinfo_id,email,uuid,sendDate) values(#{userinfoId},#{email},#{uuid},#{sendDate})")
    int insert(MailVerify mailVerify);

    /**
     * 根据uuid 查询对应的邮箱验证对象
     */
    @Select("select id,userinfo_id,sendDate,uuid,email from mailVerify where uuid = #{uuid}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(column ="userinfo_id" ,property = "userinfoId"),
            @Result(column ="email" ,property = "email"),
            @Result(column ="uuid" ,property = "uuid"),
            @Result(column ="sendDate" ,property = "sendDate"),
    })
    MailVerify selectByUUID(String uuid);

}
