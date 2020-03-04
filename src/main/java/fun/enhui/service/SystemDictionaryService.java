package fun.enhui.service;


import fun.enhui.model.SystemDictionary;
import fun.enhui.model.SystemDictionaryItem;
import fun.enhui.query.PageResult;
import fun.enhui.query.SystemDictionaryQueryObject;

import java.util.List;

/**
 * 数据字典相关服务
 */
public interface SystemDictionaryService {

    /**
     * 数据字典分类  分页查询
     */
    PageResult queryDics(SystemDictionaryQueryObject qo);

    /**
     * 修改或者添加数据字典分类
     */
    void saveOrUpdateDic(SystemDictionary systemDictionary);

    /**
     * 数据字典明细   分页查询
     */
    PageResult queryItems(SystemDictionaryQueryObject qo);

    /**
     * 查询全部数据字典分类
     */
    List<SystemDictionary> listAllDics();

    /**
     *修改或者添加数据字典明细
     */
    void saveOrUpdateItem(SystemDictionaryItem systemDictionaryItem);

    /**
     *根据数据字典分类sn查询明细
     */
    List<SystemDictionaryItem> listByParentId(int id);
}
