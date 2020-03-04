package fun.enhui.controller;

import fun.enhui.query.BidRequestQueryObject;
import fun.enhui.service.BidRequestService;
import fun.enhui.service.TransactionService;
import fun.enhui.util.JSONResult;
import fun.enhui.util.LoginAnnocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 投资相关
 */
@Controller
public class BidController {

    @Autowired
    private BidRequestService bidRequestService;

    @Autowired
    private TransactionService transactionService;

    /**
     * 投资
     * @param bidRequestId
     * @param amount
     * @return
     */
    @LoginAnnocation
    @ResponseBody
    @RequestMapping("borrow_bid")
    public JSONResult bid(Long bidRequestId, BigDecimal amount){
        this.bidRequestService.bid(bidRequestId,amount);
        return new JSONResult();
    }


    /**
     * 查看本人投资列表
     */
    @LoginAnnocation
    @RequestMapping("bid_list")
    public String bidList(@ModelAttribute("qo")BidRequestQueryObject qo, Model model){
        model.addAttribute("pageResult",this.bidRequestService.queryMyBid(qo));
        return "bid_list";
    }


}
