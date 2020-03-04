package fun.enhui.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询借款
 */
@Setter
@Getter
public class BidRequestQueryObject extends QueryObject{


    private int bidRequestState  = -1;  //借款状态

    private Long userId;  //查询个人用户时用到的userid
    private String orderBy; //按照什么列排序
    private String orderType;   //按照什么顺序排序
}
