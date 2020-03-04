package fun.enhui.dao;

import fun.enhui.model.Logininfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface LogininfoDao {

    @Insert("insert into logininfo(username,password,state,userType) values(#{username},#{password},#{state},#{userType})")
    @Options(useGeneratedKeys = true)
    public int insert(Logininfo record);

    @Select("select count(id) from logininfo where username=#{username}")
    public int getCountByUsername(String username);


    @Select("select id,username,password,state,userType from logininfo where username=#{username} AND password=#{password} AND userType=#{userType}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "username",property = "username"),
            @Result(column = "password",property = "password"),
            @Result(column = "state",property = "state"),
            @Result(column = "userType",property = "userType")
    })
    public Logininfo login(@Param("username") String username, @Param("password") String password,@Param("userType")int userType);

    @Select("select count(id) from logininfo where userType = #{userType}")
    int countByUserType(int userType);

    /**
     * 根据id查询logininfo---查询后台审核人和申请人
     */
    @Select("select id,username from logininfo where id=#{id}")
    Logininfo getLogininfoById(int id);

    @Select("select count(stuid) from studentinfo where stuid=#{stuNumber} and stuname=#{realName}")
    int checkUser(@Param("stuNumber")String stuNumber, @Param("realName")String realName);
}
