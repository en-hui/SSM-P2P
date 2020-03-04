package fun.enhui.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 一次投标对象
 */
@Getter
@Setter
public class Bid{
    private Long id;
    private BigDecimal actualRate;   //年化利率（等同于bidrequest上的currentRate）
    private BigDecimal availableAmount;  //这次投标金额
    private Long bidRequestId;   //关联借款
    private String bidRequestTitle;    //冗余数据，等同于借款标题
    private Logininfo bidUser;    //投标人
    private Date bidTime;    //投标时间
    private int bidRequestState;   //冗余数据，等同于借款的状态


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
