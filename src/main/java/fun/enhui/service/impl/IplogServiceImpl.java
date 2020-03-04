package fun.enhui.service.impl;

import fun.enhui.dao.IplogDao;
import fun.enhui.model.Iplog;
import fun.enhui.query.IplogQueryObject;
import fun.enhui.query.PageResult;
import fun.enhui.service.IplogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IplogServiceImpl implements IplogService {

    @Autowired
    private IplogDao iplogDao;

    @Override
    public PageResult query(IplogQueryObject qo) {
        int count = this.iplogDao.queryForCount(qo);
        if(count>0){
            List<Iplog> list = this.iplogDao.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }
}
