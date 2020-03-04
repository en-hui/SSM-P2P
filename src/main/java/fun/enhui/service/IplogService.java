package fun.enhui.service;

import fun.enhui.query.IplogQueryObject;
import fun.enhui.query.PageResult;

public interface IplogService {
    /**
     *分页查询
     */
    PageResult query(IplogQueryObject qo);
}
