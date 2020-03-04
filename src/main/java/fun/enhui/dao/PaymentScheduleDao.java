package fun.enhui.dao;

import fun.enhui.model.PaymentSchedule;
import fun.enhui.query.PaymentScheduleQueryObject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentScheduleDao {

    @Insert("insert into paymentschedule(id,deadLine,payDate,totalAmount," +
            "principal,interest,monthIndex,state,bidRequestType,returnType," +
            "bidrequest_id,borrowUser_id,bidRequestTitle,investUser_id)" +
            "   values(#{id},#{deadLine},#{payDate},#{totalAmount},#{principal}," +
            "#{interest},#{monthIndex},#{state},#{bidRequestType},#{returnType}," +
            "#{bidRequestId},#{borrowUser.id},#{bidRequestTitle},#{investUser.id})")
    public void insert(PaymentSchedule paymentSchedule);


    /**
     * 还款分页相关查询
     * @param qo
     * @return
     */

    @Select("<script>" +
            "select count(id) from paymentschedule where borrowUser_id=#{userId}"+
            "<if test='beginDate != null'>AND deadLine &gt; = #{beginDate}</if>"+
            "<if test='endDate != null'>AND deadLine &lt; = #{endDate}</if>"+
            "<if test='state > -1'>AND state = #{state}</if>"+
            "</script>")
    int queryReturnForCount(PaymentScheduleQueryObject qo);

    @Select("<script>" +
            "select * from paymentschedule where borrowUser_id=#{userId}"+
            "<if test='beginDate != null'>AND deadLine &gt; = #{beginDate}</if>"+
            "<if test='endDate != null'>AND deadLine &lt; = #{endDate}</if>"+
            "<if test='state > -1'>AND state = #{state}</if>"+
            "</script>")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "deadLine",property = "deadLine"),
            @Result(column = "payDate",property = "payDate"),
            @Result(column = "totalAmount",property = "totalAmount"),
            @Result(column = "principal",property = "principal"),
            @Result(column = "interest",property = "interest"),
            @Result(column = "monthIndex",property = "monthIndex"),
            @Result(column = "state",property = "state"),
            @Result(column = "bidRequestType",property = "bidRequestType"),
            @Result(column = "returnType",property = "returnType"),
            @Result(column = "bidrequest_id",property = "bidRequestId"),
            @Result(column = "bidRequestTitle",property = "bidRequestTitle"),

            @Result( column = "borrowUser_id",property = "borrowUser",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById")),
            @Result( column = "investUser_id",property = "investUser",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById"))
    })
    List<PaymentSchedule> queryReturn(PaymentScheduleQueryObject qo);

    /**
     * 收款分页相关查询
     */
    @Select("<script>" +
            "select count(id) from paymentschedule where investUser_id=#{userId}"+
            "<if test='beginDate != null'>AND deadLine &gt; = #{beginDate}</if>"+
            "<if test='endDate != null'>AND deadLine &lt; = #{endDate}</if>"+
            "<if test='state > -1'>AND state = #{state}</if>"+
            "</script>")
    int queryReceiveForCount(PaymentScheduleQueryObject qo);

    @Select("<script>" +
            "select * from paymentschedule where investUser_id=#{userId}"+
            "<if test='beginDate != null'>AND deadLine &gt; = #{beginDate}</if>"+
            "<if test='endDate != null'>AND deadLine &lt; = #{endDate}</if>"+
            "<if test='state > -1'>AND state = #{state}</if>"+
            "</script>")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "deadLine",property = "deadLine"),
            @Result(column = "payDate",property = "payDate"),
            @Result(column = "totalAmount",property = "totalAmount"),
            @Result(column = "principal",property = "principal"),
            @Result(column = "interest",property = "interest"),
            @Result(column = "monthIndex",property = "monthIndex"),
            @Result(column = "state",property = "state"),
            @Result(column = "bidRequestType",property = "bidRequestType"),
            @Result(column = "returnType",property = "returnType"),
            @Result(column = "bidrequest_id",property = "bidRequestId"),
            @Result(column = "bidRequestTitle",property = "bidRequestTitle"),

            @Result( column = "borrowUser_id",property = "borrowUser",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById")),
            @Result( column = "investUser_id",property = "investUser",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById"))
    })
    List<PaymentSchedule> queryReceive(PaymentScheduleQueryObject qo);


    @Select("select * from paymentschedule where id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "deadLine",property = "deadLine"),
            @Result(column = "payDate",property = "payDate"),
            @Result(column = "totalAmount",property = "totalAmount"),
            @Result(column = "principal",property = "principal"),
            @Result(column = "interest",property = "interest"),
            @Result(column = "monthIndex",property = "monthIndex"),
            @Result(column = "state",property = "state"),
            @Result(column = "bidRequestType",property = "bidRequestType"),
            @Result(column = "returnType",property = "returnType"),
            @Result(column = "bidrequest_id",property = "bidRequestId"),
            @Result(column = "bidRequestTitle",property = "bidRequestTitle"),

            @Result( column = "borrowUser_id",property = "borrowUser",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById")),
            @Result( column = "investUser_id",property = "investUser",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById"))
    })
    PaymentSchedule getById(Long id);

    @Update("update paymentschedule set deadLine=#{deadLine},payDate=#{payDate}," +
            "totalAmount=#{totalAmount},principal=#{principal},interest=#{interest}," +
            "monthIndex=#{monthIndex},state=#{state},bidRequestType=#{bidRequestType}," +
            "returnType=#{returnType},bidrequest_id=#{bidRequestId},borrowUser_id=#{borrowUser.id}," +
            "bidRequestTitle=#{bidRequestTitle},investUser_id=#{investUser.id} where id=#{id}")
    void update(PaymentSchedule paymentSchedule);




    @Update("update paymentschedule set state=2 where deadLine > now() and (state = 0  or state=3)")
    void update2Overdue();
}
