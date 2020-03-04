package fun.enhui.dao;

import fun.enhui.model.Bid;
import fun.enhui.query.BidRequestQueryObject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidDao {

    @Insert("insert into bid(actualRate,availableAmount,bidRequest_id," +
            "bidUser_id,bidTime,bidRequestTitle) values(#{actualRate},#{availableAmount},#{bidRequestId}," +
            "#{bidUser.id},#{bidTime},#{bidRequestTitle})")
    int insert(Bid bid);

    @Select("select id,actualRate,availableAmount,bidRequest_id,bidUser_id,bidTime,bidRequestTitle from bid where id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "actualRate",property = "actualRate"),
            @Result(column = "availableAmount",property = "availableAmount"),
            @Result(column = "bidRequest_id",property = "bidRequestId"),
            @Result(column = "bidTime",property = "bidTime"),
            @Result(column = "bidRequestTitle",property = "bidRequestTitle"),

            @Result(property = "bidUser",column = "bidUser_id",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById"))

    })
    Bid selectByPrimaryKey(Long id);


    @Select("select id,actualRate,availableAmount,bidRequest_id,bidUser_id,bidTime,bidRequestTitle from bid where bidRequest_id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "actualRate",property = "actualRate"),
            @Result(column = "availableAmount",property = "availableAmount"),
            @Result(column = "bidRequest_id",property = "bidRequestId"),
            @Result(column = "bidTime",property = "bidTime"),
            @Result(column = "bidRequestTitle",property = "bidRequestTitle"),

            @Result(property = "bidUser",column = "bidUser_id",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById"))

    })
    List<Bid> selectByBidRequest(Long bidRequestId);


    /**
     * 分页   ---  查询自己的投资
     * @param qo
     * @return
     */
    @Select("<script>" +
            "select count(b.id) from bid b left join bidrequest br on b.bidRequest_id=br.id  where bidUser_id = #{userId} " +
            "<if test='bidRequestState > -1'>" +
            "   AND br.bidRequestState=#{bidRequestState}" +
            "</if>" +
            "</script>")
    int queryMyBidForCount(BidRequestQueryObject qo);

    @Select("<script>" +
            "select * from bid b left join bidrequest br on b.bidRequest_id=br.id  where bidUser_id = #{userId} " +
            "<if test='bidRequestState > -1'>" +
            "   AND br.bidRequestState=#{bidRequestState}" +
            "</if>"+
            "<if test='orderBy!=null'>" +
            "    ORDER BY ${orderBy} ${orderType}" +
            "</if>"+
            "    Limit #{start},#{pageSize}" +
            "</script>")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "actualRate",property = "actualRate"),
            @Result(column = "availableAmount",property = "availableAmount"),
            @Result(column = "bidRequest_id",property = "bidRequestId"),
            @Result(column = "bidTime",property = "bidTime"),
            @Result(column = "bidRequestTitle",property = "bidRequestTitle"),
            @Result(column = "bidRequestState",property = "bidRequestState"),

            @Result(property = "bidUser",column = "bidUser_id",
                    one = @One(select = "fun.enhui.dao.LogininfoDao.getLogininfoById"))

    })
    List<Bid> queryMyBid(BidRequestQueryObject qo);
}
