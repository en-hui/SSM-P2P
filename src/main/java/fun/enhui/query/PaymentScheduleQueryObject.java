package fun.enhui.query;

import fun.enhui.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 查询还款
 */
@Getter
@Setter
public class PaymentScheduleQueryObject extends QueryObject {
    private int state = -1;

    private Date beginDate;
    private Date endDate;
    private Long userId;

    @DateTimeFormat(pattern="yyyy-MM-dd") //springMVC中比较简单的时间类型转换方式
    public void setBeginDate(Date beginDate){
        this.beginDate = beginDate;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd")
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    public Date getEndDate(){
        return endDate==null?null:DateUtil.endOfDay(endDate);
    }
}
