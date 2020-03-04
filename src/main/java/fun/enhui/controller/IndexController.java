package fun.enhui.controller;

import fun.enhui.query.BidRequestQueryObject;
import fun.enhui.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 */
@Controller
public class IndexController {

    @Autowired
    private BidRequestService bidRequestService;

    @RequestMapping("index")
    public String index(Model model){
        model.addAttribute("bidRequests",this.bidRequestService.listIndex());
        return "main";
    }


    /**
     * 投资列表的边框
     * 我要投资页面   由边框和表格内容组成，表格内容由ajax控制
     */
    @RequestMapping("invest")
    public String investIndex(){
        return "invest";
    }

    /**
     * 投资列表明细
     */
    @RequestMapping("invest_list")
    public String investList(BidRequestQueryObject qo,Model model){
        model.addAttribute("pageResult",this.bidRequestService.query(qo));
        return "invest_list";
    }
}
