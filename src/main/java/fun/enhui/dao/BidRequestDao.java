package fun.enhui.dao;

import fun.enhui.model.BidRequest;
import fun.enhui.query.BidRequestQueryObject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRequestDao {

    @Insert("insert into bidrequest (version,bidRequestType,bidRequestState,bidRequestAmount,currentRate," +
            "monthes2Return,bidCount,totalRewardAmount,currentSum,title,description,bidState,disableDate," +
            "createUser_id,disableDays,minBidAmount,applyTime,publishTime,returnType) " +
            "values(0,#{bidRequestType},#{bidRequestState},#{bidRequestAmount},#{currentRate},#{monthes2Return}," +
            "#{bidCount},#{totalRewardAmount},#{currentSum},#{title},#{description},#{bidState},#{disableDate},#{createUser.id}," +
            "#{disableDays},#{minBidAmount},#{applyTime},#{publishTime},#{returnType})")
    int insert(BidRequest bidRequest);


    @Select("select id,version,bidRequestType,bidRequestState,bidRequestAmount,currentRate,\n" +
            "            monthes2Return,bidCount,totalRewardAmount,currentSum,title,description,bidState,disableDate,"+
            "            createUser_id,disableDays,minBidAmount,applyTime,publishTime,returnType from bidrequest where id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "version",property = "version"),
            @Result(column = "bidRequestType",property = "bidRequestType"),
            @Result(column = "bidRequestState",property = "bidRequestState"),
            @Result(column = "bidRequestAmount",property = "bidRequestAmount"),
            @Result(column = "currentRate",property = "currentRate"),
            @Result(column = "monthes2Return",property = "monthes2Return"),
            @Result(column = "bidCount",property = "bidCount"),
            @Result(column = "totalRewardAmount",property = "totalRewardAmount"),
            @Result(column = "currentSum",property = "currentSum"),
            @Result(column = "title",property = "title"),
            @Result(column = "description",property = "description"),
            @Result(column = "bidState",property = "bidState"),
            @Result(column = "disableDate",property = "disableDate"),
            @Result(column = "disableDays",property = "disableDays"),
            @Result(column = "minBidAmount",property = "minBidAmount"),
            @Result(column = "applyTime",property = "applyTime"),
            @Result(column = "publishTime",property = "publishTime"),
            @Result(column = "returnType",property = "returnType"),

            @Result(property = "createUser",column = "createUser_id",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById")),
            @Result(property = "bids",column = "id",many = @Many(select = "fun.enhui.dao.BidDao.selectByBidRequest"))

    })
    BidRequest selectByPrimaryKey(Long id);

    @Update("update bidrequest set version=version+1,bidRequestType=#{bidRequestType},bidRequestState=#{bidRequestState}," +
            "bidRequestAmount=#{bidRequestAmount},currentRate=#{currentRate},monthes2Return=#{monthes2Return},bidCount=#{bidCount}," +
            "totalRewardAmount=#{totalRewardAmount},currentSum=#{currentSum},title=#{title},description=#{description},bidState=#{bidState}," +
            "disableDate=#{disableDate},createUser_id=#{createUser.id},disableDays=#{disableDays},minBidAmount=#{minBidAmount}," +
            "applyTime=#{applyTime},publishTime=#{publishTime},returnType=#{returnType} where id=#{id} and version=#{version}")
    int updateByPrimaryKey(BidRequest bidRequest);


    /**
     * 查询全部借款  --- 分页
     * @param qo
     * @return
     */

    @Select("<script>" +
            "select * from bidrequest where 1=1" +
            "<if test='bidRequestState > -1'>" +
            "   AND bidRequestState=#{bidRequestState}" +
            "</if>"+
            "<if test='orderBy!=null'>" +
            "    ORDER BY ${orderBy} ${orderType}" +
            "</if>"+
            "    Limit #{start},#{pageSize}" +
            "</script>")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "version",property = "version"),
            @Result(column = "bidRequestType",property = "bidRequestType"),
            @Result(column = "bidRequestState",property = "bidRequestState"),
            @Result(column = "bidRequestAmount",property = "bidRequestAmount"),
            @Result(column = "currentRate",property = "currentRate"),
            @Result(column = "monthes2Return",property = "monthes2Return"),
            @Result(column = "bidCount",property = "bidCount"),
            @Result(column = "totalRewardAmount",property = "totalRewardAmount"),
            @Result(column = "currentSum",property = "currentSum"),
            @Result(column = "title",property = "title"),
            @Result(column = "description",property = "description"),
            @Result(column = "bidState",property = "bidState"),
            @Result(column = "disableDate",property = "disableDate"),
            @Result(column = "disableDays",property = "disableDays"),
            @Result(column = "minBidAmount",property = "minBidAmount"),
            @Result(column = "applyTime",property = "applyTime"),
            @Result(column = "publishTime",property = "publishTime"),
            @Result(column = "returnType",property = "returnType"),

            @Result(property = "createUser",column = "createUser_id",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById")),
            @Result(property = "bids",column = "id",many = @Many(select = "fun.enhui.dao.BidDao.selectByBidRequest"))

    })
    List<BidRequest> query(BidRequestQueryObject qo);

    @Select("<script>" +
            "select count(id) from bidrequest where 1=1 " +
                "<if test='bidRequestState > -1'>" +
                "   AND bidRequestState=#{bidRequestState}" +
                "</if>" +
            "</script>")
    int queryForCount(BidRequestQueryObject qo);


    /**
     * 查询自己的借款  -----分页
     * @param qo
     * @return
     */
    @Select("<script>" +
            "select count(id) from bidrequest where createUser_id = #{userId} " +
            "<if test='bidRequestState > -1'>" +
            "   AND bidRequestState=#{bidRequestState}" +
            "</if>" +
            "</script>")
    int queryMyBorrowForCount(BidRequestQueryObject qo);

    @Select("<script>" +
            "select * from bidrequest where createUser_id = #{userId}" +
            "<if test='bidRequestState > -1'>" +
            "   AND bidRequestState=#{bidRequestState}" +
            "</if>"+
            "<if test='orderBy!=null'>" +
            "    ORDER BY ${orderBy} ${orderType}" +
            "</if>"+
            "    Limit #{start},#{pageSize}" +
            "</script>")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "version",property = "version"),
            @Result(column = "bidRequestType",property = "bidRequestType"),
            @Result(column = "bidRequestState",property = "bidRequestState"),
            @Result(column = "bidRequestAmount",property = "bidRequestAmount"),
            @Result(column = "currentRate",property = "currentRate"),
            @Result(column = "monthes2Return",property = "monthes2Return"),
            @Result(column = "bidCount",property = "bidCount"),
            @Result(column = "totalRewardAmount",property = "totalRewardAmount"),
            @Result(column = "currentSum",property = "currentSum"),
            @Result(column = "title",property = "title"),
            @Result(column = "description",property = "description"),
            @Result(column = "bidState",property = "bidState"),
            @Result(column = "disableDate",property = "disableDate"),
            @Result(column = "disableDays",property = "disableDays"),
            @Result(column = "minBidAmount",property = "minBidAmount"),
            @Result(column = "applyTime",property = "applyTime"),
            @Result(column = "publishTime",property = "publishTime"),
            @Result(column = "returnType",property = "returnType"),

            @Result(property = "createUser",column = "createUser_id",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById")),
            @Result(property = "bids",column = "id",many = @Many(select = "fun.enhui.dao.BidDao.selectByBidRequest"))

    })
    List<BidRequest> queryMyBorrow(BidRequestQueryObject qo);


    /**
     * 定时任务--借款超时改变状态
     */
    @Update("update bidrequest set bidrequeststate = 3  where disableDate > now() and (bidRequestState = 1 or bidRequestState = 0)")
    void updateState2Overdue();
}
