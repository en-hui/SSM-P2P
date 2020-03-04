package fun.enhui.controller;


import fun.enhui.model.SystemDictionary;
import fun.enhui.model.SystemDictionaryItem;
import fun.enhui.query.SystemDictionaryQueryObject;
import fun.enhui.service.SystemDictionaryService;
import fun.enhui.util.JSONResult;
import fun.enhui.util.mgrLoginAnnocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据字典相关
 */
@Controller
public class SystemDictionaryController {


    @Autowired
    private SystemDictionaryService systemDictionaryService;

    /**
     * 数据字典分类列表
     */
    @mgrLoginAnnocation
    @RequestMapping("systemDictionary_list")
    public String systemDictionaryList(
            @ModelAttribute("qo")SystemDictionaryQueryObject qo, Model model){
        model.addAttribute("pageResult",this.systemDictionaryService.queryDics(qo));
        return "mgrsystemdic/systemDictionary_list";
    }

    /**
     * 修改和添加  数据字典分类
     */
    @mgrLoginAnnocation
    @RequestMapping("systemDictionary_update")
    @ResponseBody
    public JSONResult systemDictionaryUpdate(SystemDictionary systemDictionary){

        this.systemDictionaryService.saveOrUpdateDic(systemDictionary);
        return new JSONResult();
    }


    /**
     *数据字典明细列表
     */
    @mgrLoginAnnocation
    @RequestMapping("systemDictionaryItem_list")
    String systemDictionaryItemList(
            @ModelAttribute("qo")SystemDictionaryQueryObject qo, Model model){
        model.addAttribute("pageResult",this.systemDictionaryService.queryItems(qo));
        model.addAttribute("systemDictionaryGroups",this.systemDictionaryService.listAllDics());
        return "mgrsystemdic/systemDictionaryItem_list";
    }

    /**
     *  添加和修改字典明细
     */
    @mgrLoginAnnocation
    @RequestMapping("systemDictionaryItem_update")
    @ResponseBody
    public JSONResult systemDictionaryItemUpdate(SystemDictionaryItem systemDictionaryItem){

        this.systemDictionaryService.saveOrUpdateItem(systemDictionaryItem);
        return new JSONResult();
    }

}
