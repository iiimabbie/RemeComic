package tw.com.remecomic.money.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.com.remecomic.money.model.bean.MoneyAccount;
import tw.com.remecomic.userA.model.bean.UserA;

public interface MoneyAccountDao extends JpaRepository<MoneyAccount, Integer> {

    @Query("SELECT ma FROM MoneyAccount ma JOIN ma.userAByUserId u WHERE u.userId = :userId")
    MoneyAccount findMoneyAccountByUserId(@Param("userId") Integer userId);

    @Query("SELECT ma FROM MoneyAccount ma JOIN ma.userAByUserId u WHERE u.name = :userName")
    MoneyAccount findMoneyAccountByUserName(@Param("userName") String userName);

    @Query("select userId from MoneyAccount where accountId = :accountId")
    Integer findUserIdByAccountId(@Param("accountId") Integer accountId);
}
