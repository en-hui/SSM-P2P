package fun.enhui.dao;

import fun.enhui.model.RealAuth;
import fun.enhui.query.RealAuthQueryObject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealAuthDao {

    @Insert("insert into realauth(realName,sex,idNumber,stuNumber,state,card_image1,card_image2,stu_image1,stu_image2," +
            "remark,auditTime,applyTime,auditor_id,applier_id) " +
            "values(#{realName},#{sex},#{idNumber},#{stuNumber},#{state},#{card_image1},#{card_image2},#{stu_image1}," +
            "#{stu_image2},#{remark},#{auditTime},#{applyTime},#{auditor.id},#{applier.id})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int insert(RealAuth realAuth);

    @Update("update realauth set  realName= #{realName},sex = #{sex}, idNumber= #{idNumber},stuNumber=#{stuNumber},state = #{state}," +
            " card_image1= #{card_image1}, card_image2= #{card_image2}, stu_image1= #{stu_image1}, stu_image2= #{stu_image2}," +
            " remark= #{remark},auditTime= #{auditTime}, applyTime= #{applyTime}, auditor_id= #{auditor.id}," +
            " applier_id= #{applier.id} where id=#{id}")
    int updateByPrimaryKey(RealAuth realAuth);

    /**
     * 前台用户查询
     * @param id
     * @return
     */
    @Select("select id,realName,sex,idNumber,stuNumber,state,card_image1,card_image2,stu_image1,stu_image2,remark,auditTime,applyTime,applier_id,auditor_id from realauth where id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "realName",property = "realName"),
            @Result(column = "sex",property = "sex"),
            @Result(column = "idNumber",property = "idNumber"),
            @Result(column = "stuNumber",property = "stuNumber"),
            @Result(column = "state",property = "state"),
            @Result(column = "card_image1",property = "card_image1"),
            @Result(column = "card_image2",property = "card_image2"),
            @Result(column = "stu_image1",property = "stu_image1"),
            @Result(column = "stu_image2",property = "stu_image2"),
            @Result(column = "remark",property = "remark"),
            @Result(column = "auditTime",property = "auditTime"),
            @Result(column = "applyTime",property = "applyTime"),

            @Result(property = "applier",column = "applier_id",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById")),
            @Result(property = "auditor",column = "auditor_id",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById"))
    })
    RealAuth selectByPrimaryKey(Long id);


    /**
     * 后台审核分页查询相关
     */
    @Select("<script>" +
            "select count(id) from realauth where 1=1 " +
            "<if test='beginDate!=null'>AND applyTime &gt;= #{beginDate}</if>" +
            "<if test='endDate!=null'>AND applyTime &lt;= #{endDate}</if>" +
            "<if test='state>-1'>AND state = #{state}</if>" +
            "</script>")
    int queryForCount(RealAuthQueryObject qo);


    @Select("<script>" +
              "select id,realName,sex,idNumber,stuNumber,state,card_image1,card_image2," +
                "stu_image1,stu_image2,remark,auditTime,applyTime," +
                "applier_id,auditor_id from realauth where  1=1" +
                "<if test='beginDate!=null'>AND applyTime &gt;= #{beginDate}</if>" +
                "<if test='endDate!=null'>AND applyTime &lt;= #{endDate}</if>" +
            "    <if test='state>-1'>AND state = #{state}</if> " +
            "        LIMIT #{start},#{pageSize}   "+
            "</script>")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "realName",property = "realName"),
            @Result(column = "sex",property = "sex"),
            @Result(column = "idNumber",property = "idNumber"),
            @Result(column = "stuNumber",property = "stuNumber"),
            @Result(column = "state",property = "state"),
            @Result(column = "card_image1",property = "card_image1"),
            @Result(column = "card_image2",property = "card_image2"),
            @Result(column = "stu_image1",property = "stu_image1"),
            @Result(column = "stu_image2",property = "stu_image2"),
            @Result(column = "remark",property = "remark"),
            @Result(column = "auditTime",property = "auditTime"),
            @Result(column = "applyTime",property = "applyTime"),

            @Result(property = "applier",column = "applier_id",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById")),
            @Result(property = "auditor",column = "auditor_id",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById"))
    })
    List<RealAuth> query(RealAuthQueryObject qo);



}
