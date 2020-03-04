package fun.enhui.controller;

import fun.enhui.query.PaymentScheduleQueryObject;
import fun.enhui.service.PaymentScheduleService;
import fun.enhui.util.LoginAnnocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 还款相关
 */
@Controller
public class PaymentScheduleController {

    @Autowired
    private PaymentScheduleService paymentScheduleService;
    /**
     * 还款列表-------借款人看的
     * @return
     */
    @LoginAnnocation
    @RequestMapping("borrowBidReturn_list")
    public String returnlist(@ModelAttribute("qo")PaymentScheduleQueryObject qo, Model model){
        model.addAttribute("pageResult",this.paymentScheduleService.queryReturn(qo));

        return "returnmoney_list";
    }

    /**
     * 收款列表----投资人看的
     */
    @LoginAnnocation
    @RequestMapping("receivables_list")
    public String receivableslist(@ModelAttribute("qo")PaymentScheduleQueryObject qo, Model model){
        model.addAttribute("pageResult",this.paymentScheduleService.queryReceive(qo));

        return "receivables_list";
    }
}
