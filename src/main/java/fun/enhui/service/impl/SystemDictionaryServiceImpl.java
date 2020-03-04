package fun.enhui.service.impl;


import fun.enhui.dao.SystemDictionaryDao;
import fun.enhui.dao.SystemDictionaryItemDao;
import fun.enhui.model.SystemDictionary;
import fun.enhui.model.SystemDictionaryItem;
import fun.enhui.query.PageResult;
import fun.enhui.query.SystemDictionaryQueryObject;
import fun.enhui.service.SystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemDictionaryServiceImpl implements SystemDictionaryService {

    @Autowired
    private SystemDictionaryDao systemDictionaryDao;

    @Autowired
    private SystemDictionaryItemDao systemDictionaryItemDao;

    @Override
    public PageResult queryDics(SystemDictionaryQueryObject qo) {
        int count = this.systemDictionaryDao.queryForCount(qo);
        if(count>0){
            List<SystemDictionary> list = this.systemDictionaryDao.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void saveOrUpdateDic(SystemDictionary systemDictionary) {
        if(systemDictionary.getId()!=null){
            this.systemDictionaryDao.updateByPrimaryKey(systemDictionary);
        }else{
            this.systemDictionaryDao.insert(systemDictionary);
        }
    }

    @Override
    public PageResult queryItems(SystemDictionaryQueryObject qo) {
        int count = this.systemDictionaryItemDao.queryForCount(qo);
        if(count>0){
            List<SystemDictionaryItem> list = this.systemDictionaryItemDao.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public List<SystemDictionary> listAllDics() {
        return this.systemDictionaryDao.selectAll();
    }

    @Override
    public void saveOrUpdateItem(SystemDictionaryItem systemDictionaryItem) {
        if(systemDictionaryItem.getId()!=null){
            this.systemDictionaryItemDao.updateByPrimaryKey(systemDictionaryItem);
        }else{
            this.systemDictionaryItemDao.insert(systemDictionaryItem);
        }
    }

    @Override
    public List<SystemDictionaryItem> listByParentId(int id) {
        return this.systemDictionaryItemDao.listByParentId(id);
    }
}
