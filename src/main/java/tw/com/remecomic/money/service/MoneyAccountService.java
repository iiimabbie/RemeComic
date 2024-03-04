package tw.com.remecomic.money.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.remecomic.money.model.bean.MoneyAccount;
import tw.com.remecomic.money.model.bean.MoneyOrders;
import tw.com.remecomic.money.model.bean.MoneyRecharge;
import tw.com.remecomic.money.model.dao.MoneyAccountDao;
import tw.com.remecomic.userA.model.bean.UserA;
import tw.com.remecomic.userA.model.dto.UserSimpleDto;

import java.util.Collection;
import java.util.Optional;

@Service
public class MoneyAccountService {

    @Autowired
    private MoneyAccountDao moneyAccountDao;

    /**
     * 創造使用者的金錢帳戶，如果該使用者的帳戶不存在。
     *
     * @param userId 要創造帳戶的使用者
     */
    public void createAccount(Integer userId) {
//        Integer userId = userSimpleDto.getUserId();

        if (!accountExist(userId)) {
            MoneyAccount moneyAccount = new MoneyAccount();
            moneyAccount.setUserId(userId);
            moneyAccount.setFreeCoin(0);
            moneyAccount.setPayCoin(0);

            moneyAccountDao.save(moneyAccount);
        }
    }

    /**
     * 根據帳戶ID找金錢帳戶。
     *
     * @param accountId 要找的帳戶ID
     */
    public MoneyAccount findById(Integer accountId) {
        Optional<MoneyAccount> optional = moneyAccountDao.findById(accountId);

        return optional.orElse(null);
    }

    /**
     * 檢查特定用戶的帳戶是否存在。
     *
     * @param userId 要檢查的用戶ID
     * @return 如果帳戶存在則為true，否則為false
     */
    public boolean accountExist(Integer userId) {
        return moneyAccountDao.findMoneyAccountByUserId(userId) != null;
    }

    /**
     * 根據用戶ID找金錢帳戶。
     *
     * @param userId 要找的用戶ID
     * @return 帳戶，如果不存在則為null
     */
    public MoneyAccount findMoneyAccountByUserId(Integer userId) {
        return moneyAccountDao.findMoneyAccountByUserId(userId);
    }

    /**
     * Retrieves the user ID associated with the specified account ID.
     *
     * @param accountId The identifier of the account for which to find the user ID.
     * @return The user ID associated with the given account ID, or null if not found.
     */
    public Integer findUserIdByAccountId(Integer accountId) {
        return moneyAccountDao.findUserIdByAccountId(accountId);
    }

    /**
     * 根據特定的使用者找所有的儲值記錄。
     *
     * @param userId 使用者ID，用於找相關的儲值記錄
     * @return 如果找到對應的金錢帳戶，則 return 該帳戶的所有儲值記錄；如果金錢帳戶不存在，則 return null。
     */
    public Collection<MoneyRecharge> findAllRechargeRecordByUser(Integer userId) {
//        Integer userId = userA.getUserId();

        MoneyAccount account = moneyAccountDao.findMoneyAccountByUserId(userId);

        if (account != null) {
            return account.getRechargesByAccountId();
        }

        return null;
    }

    /**
     * 根據給特定的使用者ID查找所有的訂單記錄。
     *
     * @param userId 使用者ID，用於找相關的訂單記錄
     * @return 如果找到對應的金錢帳戶，則返回該帳戶的所有訂單記錄；如果金錢帳戶不存在，則返回null。
     */
    public Collection<MoneyOrders> findAllOrdersRecordByUser(Integer userId) {
//        Integer userId = userA.getUserId();

        MoneyAccount account = moneyAccountDao.findMoneyAccountByUserId(userId);

        if (account != null) {
            return account.getOrdersByAccountId();
        }

        return null;
    }

    /**
     * 查詢指定使用者的總金幣數量。
     *
     * @param userId 使用者ID
     * @return 使用者的總金幣數量，若使用者不存在則返回 null
     */
    public Integer findTotalCoinByUser(Integer userId) {
        MoneyAccount account = moneyAccountDao.findMoneyAccountByUserId(userId);

        if (account != null) {
            Integer freeCoin = account.getFreeCoin();
            Integer payCoin = account.getPayCoin();

            return freeCoin + payCoin;
        }

        return null;
    }
}
