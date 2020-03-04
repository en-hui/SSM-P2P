package fun.enhui.query;

import fun.enhui.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 登录日志查询对象
 */
@Setter
@Getter
public class IplogQueryObject extends QueryObject {
    private Date beginDate;
    private Date endDate;
    private int state = -1;
    private String username;
    private int userType;

    /**
     * 参数都是直接让springmvc注入进来，所以要对时间格式进行转换
     * @param beginDate
     */
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

    public String getUsername(){
        return StringUtils.hasLength(username)?username:null;
    }











}
