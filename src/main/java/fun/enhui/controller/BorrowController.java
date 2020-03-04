package fun.enhui.controller;

import fun.enhui.model.Bid;
import fun.enhui.model.BidRequest;
import fun.enhui.model.Logininfo;
import fun.enhui.model.Userinfo;
import fun.enhui.query.BidRequestQueryObject;
import fun.enhui.service.AccountService;
import fun.enhui.service.BidRequestService;
import fun.enhui.service.RealAuthService;
import fun.enhui.service.UserinfoService;
import fun.enhui.util.BidConst;
import fun.enhui.util.LoginAnnocation;
import fun.enhui.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 借款申请相关的
 */
@Controller
public class BorrowController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private BidRequestService bidRequestService;

    @Autowired
    private RealAuthService realAuthService;

    /**
     * 跳转至 我要借款 首页
     */
    @LoginAnnocation
    @RequestMapping("borrow")
    public String borrowIndex(Model model){
        Logininfo current = UserContext.getCurrent();
        //如果没有登录，返回html静态页面
        if(current == null){
            return "redirect:borrow.html";
        }else{
            model.addAttribute("account",this.accountService.getCurrent());
            model.addAttribute("userinfo",this.userinfoService.getCurrent());
            return "borrow";
        }

    }

    /**
     * 导向到借款申请页面
     */
    @LoginAnnocation
    @RequestMapping("borrowInfo")
    public String borrowInfo(Model model){
        Long id = UserContext.getCurrent().getId();
        if(bidRequestService.canApplyBidRequeset(id)){
            //能借款
            model.addAttribute("minBidRequestAmount",BidConst.SMALLEST_BIDREQUEST_AMOUNT);//最小借款金额
            model.addAttribute("minBidAmount",BidConst.SMALLEST_BID_AMOUNT);  //系统规定的最小投标金额
            model.addAttribute("account",this.accountService.getCurrent());
            return "borrow_apply";
        }else{
            //不能借款
            return "borrow_apply_result";
        }
    }

    /**
     * 借款申请
     */
    @LoginAnnocation
    @RequestMapping("borrow_apply")
    public String borrowApply(BidRequest bidRequest){
        this.bidRequestService.apply(bidRequest);
        return "redirect:/borrowInfo.do";
    }

    /**
     * 前端借款明细
     */
    @LoginAnnocation
    @RequestMapping("borrow_info")
    public String borrow_info(Long id,Model model){
        BidRequest bidRequest = this.bidRequestService.get(id);
        if(bidRequest != null) {
            //借款人
            Userinfo applier = this.userinfoService.get(bidRequest.getCreateUser().getId());
            //投资人
            Userinfo invest = null;
            if(bidRequest.getBids().size()>0){
                for(Bid bid:bidRequest.getBids()){  //由于本系统暂时只有一个投资人，所以不用list
                    invest = this.userinfoService.get(bid.getBidUser().getId());
                }

            }

            //实名信息
            model.addAttribute("realAuth", this.realAuthService.get(applier.getRealAuthId()));
            model.addAttribute("bidRequest",bidRequest);
            model.addAttribute("userInfo",applier);
            if(UserContext.getCurrent()!=null){
                //borrowself:当前用户是否是借款人自己
                if(UserContext.getCurrent().getId().equals(applier.getId())){
                    model.addAttribute("borrowself",true);
                }else{
                    model.addAttribute("borrowself",false);
                    model.addAttribute("account",this.accountService.getCurrent());
                }
                //investself:当前用户是投资人
                if(invest!=null){
                    if(UserContext.getCurrent().getId().equals(invest.getId())){
                        model.addAttribute("investself",true);
                    }else{
                        model.addAttribute("investself",false);
                    }
                }
            }else{
                model.addAttribute("borrowself",false);
                model.addAttribute("investself",false);
            }
        }

        return "borrow_info";
    }


    /**
     * 导向到借款项目列表
     * @return
     */
    @LoginAnnocation
    @RequestMapping("borrow_list")
    public String borrowList(@ModelAttribute("qo")BidRequestQueryObject qo, Model model){
        model.addAttribute("pageResult",this.bidRequestService.queryMyBorrow(qo));
        return "bidRequest_list";
    }







}
