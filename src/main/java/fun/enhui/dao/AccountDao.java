package fun.enhui.dao;

import fun.enhui.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao {

    @Insert("insert into account(id,version,tradePassword,usableAmount,freezedAmount,borrowLimit," +
            "unReceiveInterest,unReceivePrincipal,unReturnAmount,remainBorrowLimit) values(" +
            "#{id},0,#{tradePassword},#{usableAmount},#{freezedAmount},#{borrowLimit}," +
            "#{unReceiveInterest},#{unReceivePrincipal},#{unReturnAmount},#{remainBorrowLimit})")
    int insert(Account record);

    @Select("select id,tradePassword,usableAmount,freezedAmount,borrowLimit," +
            "version,unReceiveInterest,unReceivePrincipal,unReturnAmount,remainBorrowLimit" +
            " from account where id=#{id}")
    Account selectByPrymaryKey(Long id);

    @Update("update account set " +
             "version=version+1,tradePassword=#{tradePassword},usableAmount=#{usableAmount}," +
            "freezedAmount=#{freezedAmount},borrowLimit=#{borrowLimit},unReceiveInterest=#{unReceiveInterest}," +
            "unReceivePrincipal=#{unReceivePrincipal},unReturnAmount=#{unReturnAmount}," +
            "remainBorrowLimit=#{remainBorrowLimit} where id=#{id} AND version=#{version}")
    int updateByPrimaryKey(Account record);


}
