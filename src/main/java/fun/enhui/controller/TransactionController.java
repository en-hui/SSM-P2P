package fun.enhui.controller;

import fun.enhui.service.TransactionService;
import fun.enhui.util.JSONResult;
import fun.enhui.util.LoginAnnocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 交易相关
 */
@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * 借款---当面交易
     * @return
     */
    @LoginAnnocation
    @ResponseBody
    @RequestMapping("borrow_transaction")
    public JSONResult borrowTransaction(Long bidRequestId,Integer type){
        transactionService.borrowTransaction(bidRequestId,type);
        return new JSONResult();
    }


    /**
     * 还款 -- 交易
     * @param id
     * @param type   1还  2收
     * @return
     */
    @LoginAnnocation
    @ResponseBody
    @RequestMapping("returnMoneyTransaction")
    public JSONResult returnTransaction(Long id,Integer type){
        transactionService.returnTransaction(id,type);
        return new JSONResult();
    }
}
