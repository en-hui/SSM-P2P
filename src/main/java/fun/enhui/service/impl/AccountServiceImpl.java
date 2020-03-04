package fun.enhui.service.impl;

import fun.enhui.dao.AccountDao;
import fun.enhui.model.Account;
import fun.enhui.service.AccountService;
import fun.enhui.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;
    @Override
    public void update(Account account) {
        int ret = this.accountDao.updateByPrimaryKey(account);
        if(ret == 0){
            throw new RuntimeException("乐观锁失败，Account:"+account.getId());
        }
    }

    @Override
    public void add(Account account) {
        this.accountDao.insert(account);
    }

    @Override
    public Account get(Long id) {
        return this.accountDao.selectByPrymaryKey(id);
    }

    @Override
    public Account getCurrent() {
        return this.get(UserContext.getCurrent().getId());
    }
}
