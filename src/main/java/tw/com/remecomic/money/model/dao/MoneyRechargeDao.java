package tw.com.remecomic.money.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.com.remecomic.money.model.bean.MoneyAccount;
import tw.com.remecomic.money.model.bean.MoneyRecharge;

public interface MoneyRechargeDao extends JpaRepository<MoneyRecharge, Integer> {

    @Query("SELECT mr FROM MoneyRecharge mr JOIN mr.moneyAccountByAccountId ma WHERE ma.accountId = :accountId")
    MoneyRecharge findMoneyRechargeByAccountId(@Param("accountId") Integer accountId);

}
