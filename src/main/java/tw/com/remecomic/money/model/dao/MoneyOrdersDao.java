package tw.com.remecomic.money.model.dao;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.com.remecomic.money.model.bean.MoneyOrders;

import java.util.Date;
import java.util.List;

public interface MoneyOrdersDao extends JpaRepository<MoneyOrders, Integer> {

    @Query("SELECT mo FROM MoneyOrders mo JOIN mo.moneyAccountByAccountId ma WHERE ma.accountId = :accountId")
    List<MoneyOrders> findMoneyOrdersByAccountId(@Param("accountId") Integer accountId);

    @Query("select orderDate from MoneyOrders ")
    List<Date> findAllOrderDates();

    @Query("select price from MoneyOrders ")
    List<Integer> findAllOrderPrices();
}
