package tw.com.remecomic.money.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import tw.com.remecomic.money.exception.InsufficientBalanceException;
import tw.com.remecomic.money.model.bean.MoneyAccount;
import tw.com.remecomic.money.model.bean.MoneyOrders;
import tw.com.remecomic.money.model.dao.MoneyAccountDao;
import tw.com.remecomic.money.model.dao.MoneyOrdersDao;

import java.util.*;

@Service
public class MoneyOrdersService {

    @Autowired
    private MoneyAccountDao moneyAccountDao;

    @Autowired
    private MoneyOrdersDao moneyOrdersDao;

    /**
     * 獲得所有訂單記錄的方法。
     *
     * @return 包含所有訂單記錄的清單列表
     */
    public List<MoneyOrders> findAllOrdersRecord() {
        return moneyOrdersDao.findAll();
    }

    /**
     * 分頁查詢訂單記錄的方法。
     *
     * @param pageNum 頁碼
     * @return 訂單記錄的分頁結果
     */
    public Page<MoneyOrders> findOrdersByPage(Integer pageNum) {
        PageRequest pageRequest = PageRequest.of((pageNum - 1), 3
                , Sort.Direction.DESC, "orderDate");

        return moneyOrdersDao.findAll(pageRequest);
    }

    /**
     * 分頁查詢指定使用者的訂單記錄。
     *
     * @param userId  使用者ID
     * @param pageNum 頁碼，從1開始
     * @return 包含指定使用者訂單記錄的分頁結果
     */
    public Page<MoneyOrders> findUserOrdersByPage(Integer userId, Integer pageNum) {
        PageRequest pageRequest = PageRequest.of((pageNum - 1), 3
                , Sort.Direction.DESC, "orderDate");

        MoneyAccount account = moneyAccountDao.findMoneyAccountByUserId(userId);

        MoneyOrders exampleOrder = new MoneyOrders();
        exampleOrder.setAccountId(account.getAccountId());

        Example<MoneyOrders> example = Example.of(exampleOrder);

        return moneyOrdersDao.findAll(example, pageRequest);
    }

    /**
     * 創建訂單的方法。
     *
     * @param userId    使用者ID
     * @param episodeId 漫畫集數ID
     * @param dueDate   到期日期
     * @param price     價格
     * @throws InsufficientBalanceException 如果使用者餘額不足以支付訂單，則拋出此異常
     */
    public void createOrders(@Param("userId") Integer userId
            , @Param("episodeId") Integer episodeId
            , @Param("dueDate") Date dueDate
            , @Param("price") Integer price) {
        MoneyAccount account = moneyAccountDao.findMoneyAccountByUserId(userId);

        Integer freeCoin = account.getFreeCoin();
        Integer payCoin = account.getPayCoin();

        if (isSufficient(price, freeCoin, payCoin)) {
            MoneyOrders order = new MoneyOrders();

            order.setAccountId(account.getAccountId());
            order.setEpisodeId(episodeId);
            order.setDueDate(dueDate);
            order.setPrice(price);

            moneyOrdersDao.save(order);

            if (freeCoin - price < 0) {
                account.setFreeCoin(0);
                account.setPayCoin(payCoin - (price - freeCoin));
                moneyAccountDao.save(account);
            }
            if (freeCoin - price >= 0) {
                account.setFreeCoin(freeCoin - price);
                moneyAccountDao.save(account);
            }
        }else {
            throw new InsufficientBalanceException("Insufficient balance to create the order.");
        }

    }

    /**
     * Retrieves all order dates and prices, and calculates the total amount for each date.
     *
     * @return A map where keys are order dates and values are the corresponding total amounts.
     * @throws RuntimeException if the lists of dates and prices have different sizes.
     */
    public Map<Date, Integer> dateAndTotalCollection() {
        List<Date> dateList = moneyOrdersDao.findAllOrderDates();
        List<Integer> prices = moneyOrdersDao.findAllOrderPrices();

        Map<Date, Integer> dateAndTotal = new TreeMap<>();

        if (dateList.size() == prices.size()) {
            for (int i = 0; i < dateList.size(); i++) {
                Date currentDate = truncateTime(dateList.get(i));
                int currentPrice = prices.get(i);

                if (dateAndTotal.containsKey(currentDate)) {
                    int updatedTotal = dateAndTotal.get(currentDate) + currentPrice;

                    dateAndTotal.put(currentDate, updatedTotal);
                } else {
                    dateAndTotal.put(currentDate, currentPrice);
                }
            }

            return dateAndTotal;
        } else {
            throw new RuntimeException("Date and price lists must have the same size");
        }
    }

    private boolean isSufficient(Integer price, Integer freeCoin, Integer payCoin) {
        return freeCoin + payCoin >= price;
    }

    private Date truncateTime(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
