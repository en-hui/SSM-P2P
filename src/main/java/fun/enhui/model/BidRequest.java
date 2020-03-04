package fun.enhui.model;

import fun.enhui.util.BidConst;
import fun.enhui.util.BitStatesUtils;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * 借款对象
 */
@Setter
@Getter
public class BidRequest{
    private Long id;
    private int version;  //版本号
    private int returnType;  //还款类型（等额本息）
    private int bidRequestType;  //借款类型（信用标）
    private int bidRequestState;  //借款状态
    private BigDecimal bidRequestAmount; //借款总金额
    private BigDecimal currentRate;   //年化利率
    private BigDecimal minBidAmount;  //最小借款金额
    private int monthes2Return;  //还款月数
    private int bidCount=0;   //已投标次数（冗余）
    private BigDecimal totalRewardAmount;   //总回报金额（总利息）
    private BigDecimal currentSum= BidConst.ZERO;  //当前已投标总金额
    private String title;  //借款标题
    private String description;  //借款描述
    private long bidState = 0;   //交易状态码
    private Date disableDate;  //招标截止日期
    private int disableDays;   //招标天数
    private Logininfo createUser;  //借款人
    private List<Bid> bids;   //针对该借款的投标
    private Date applyTime;  //申请时间
    private Date publishTime;   //发标时间


    public boolean getIsBorrowDo(){   //借款人确认
        return BitStatesUtils.hasState(bidState,BitStatesUtils.BID_BORROW_DO);
    }

    public boolean getIsInvestDo(){    //投资人确认
        return BitStatesUtils.hasState(bidState,BitStatesUtils.BID_INVEST_DO);
    }

    /**
     * 计算招标截至日期
     * @return
     */
    /*public Date getDisDate(){
        return new Date(this.publishTime.getTime()+this.disableDays*24*60*60*1000);
    }*/

    //还款类型
    public String getReturnTypeDisplay(){
        return returnType==0?"等额本息":"";
    }
    //计算借款进度   百分比
    public BigDecimal getPersent(){
        return currentSum.divide(bidRequestAmount,BidConst.DISPLAY_SCALE,
                RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }
    //计算借款剩余投资钱数
    public BigDecimal getRemainAmount(){
        return bidRequestAmount.subtract(currentSum);
    }

    public String getBidRequestStateDisplay(){
        switch (this.bidRequestState){
            case 0:
                return "待确认";
            case 1:
                return "招标中";
            case 2:
                return "借款取消";
            case 3:
                return "超时";
            case 4:
                return "投资取消";
            case 7:
                return "还款中";
            case 8:
                return "已还清";
            case 9:
                return "逾期";
        }
        return "";
    }
}
