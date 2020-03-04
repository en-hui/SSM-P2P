package fun.enhui.dao;

import fun.enhui.model.Iplog;
import fun.enhui.query.IplogQueryObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IplogDao {

    @Insert("insert into iplog (ip,state,userName,loginTime,userType) " +
            "values(#{ip},#{state},#{userName},#{loginTime},#{userType}) ")
    int insert(Iplog record);

    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column ="ip" ,property = "ip"),
            @Result(column ="state" ,property = "state"),
            @Result(column ="userName" ,property = "userName"),
            @Result(column ="loginTime" ,property = "loginTime"),
            @Result(column ="userType" ,property = "userType")
    })
    @Select("select id,ip,state,userName,loginTime,userType from iplog")
    List<Iplog> selectAll();

    /**
     * 高级查询总数
     */
   @Select("<script>" +
            "select count(id) from iplog where 1=1"+
            "<if test='beginDate != null'>AND loginTime &gt; = #{beginDate}</if>"+
            "<if test='endDate != null'>AND loginTime &lt; = #{endDate}</if>"+
            "<if test='state > -1'>AND state = #{state}</if>"+
            "<if test='username!=null'>AND username = #{username}</if>"
           + "<if test='userType!=null'>AND userType =#{userType}</if>"+
            "</script>")
    int queryForCount(IplogQueryObject qo);

    /**
     * 查询当前页数据
     */
    @Select("<script>"+
            "select id,ip,state,userName,loginTime,userType from iplog where 1=1"+
            "<if test='beginDate != null'>AND loginTime &gt;=#{beginDate}</if>"+
            "<if test='endDate != null'>AND loginTime &lt;=#{endDate}</if>"+
            "<if test='state > -1'>AND state =#{state}</if>"+
            "<if test='username!=null'>AND username =#{username}</if>"
            +"<if test='userType!=null'>AND userType =#{userType}</if>"+
            "ORDER BY loginTime DESC LIMIT #{start},#{pageSize}"+
            "</script>")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column ="ip" ,property = "ip"),
            @Result(column ="state" ,property = "state"),
            @Result(column ="userName" ,property = "userName"),
            @Result(column ="loginTime" ,property = "loginTime"),
            @Result(column ="userType" ,property = "userType")
    })
    List<Iplog> query(IplogQueryObject qo);
}
