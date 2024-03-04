package tw.com.remecomic.money.service;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import tw.com.remecomic.money.model.bean.MoneyAccount;
import tw.com.remecomic.money.model.bean.MoneyRecharge;
import tw.com.remecomic.money.model.dao.MoneyAccountDao;
import tw.com.remecomic.money.model.dao.MoneyRechargeDao;
import tw.com.remecomic.money.util.GiveYouEverything;


import java.util.List;


@Service
public class MoneyRechargeService {

    @Autowired
    private MoneyAccountDao moneyAccountDao;

    @Autowired
    private MoneyRechargeDao moneyRechargeDao;

    /**
     * 儲值金錢帳戶的方法。
     *
     * @param userId 使用者ID，用於找對應的金錢帳戶
     * @param rechargeAmount 儲值金額
     * @param status 儲值狀態，true 表示成功，false 表示失敗
     * @param coinType 儲值幣種，true 表示付費金幣，false 表示免費金幣
     * @throws RuntimeException 如果 status 為 false
     */
    public void rechargingAccount(Integer userId
            , Integer rechargeAmount
            , Boolean status
            , Boolean coinType){
        MoneyAccount account = moneyAccountDao.findMoneyAccountByUserId(userId);

        Integer payCoin = account.getPayCoin();
        Integer freeCoin = account.getFreeCoin();

        MoneyRecharge moneyRecharge = new MoneyRecharge();

        moneyRecharge.setAccountId(account.getAccountId());
        moneyRecharge.setRechargeAmount(rechargeAmount);
        moneyRecharge.setStatus(status);
        moneyRecharge.setCoinType(coinType);

        moneyRechargeDao.save(moneyRecharge);

        if (status) {
            if (coinType) {
                account.setPayCoin(payCoin + rechargeAmount);
            }else {
                account.setFreeCoin(freeCoin + rechargeAmount);
            }

            moneyAccountDao.save(account);
        }else {
            throw new RuntimeException();
        }
    }

    // 這先不用
//    public MoneyRecharge findRechargeRecordByRechargeId(Integer rechargeId) {
//        Optional<MoneyRecharge> optional = moneyRechargeDao.findById(rechargeId);
//
//        return optional.orElse(null);
//    }

    /**
     * 獲得所有的儲值記錄。
     *
     * @return 包含所有儲值記錄的清單列表，如果沒有儲值記錄，則返回空的清單列表
     */
    public List<MoneyRecharge> findAllRechargeRecord() {
        return moneyRechargeDao.findAll();
    }

    public Page<MoneyRecharge> findRechargeRecordByPage(Integer pageNum) {
        PageRequest pageRequest = PageRequest.of((pageNum - 1), 3
                , Sort.Direction.DESC, "rechargeDate");

        return moneyRechargeDao.findAll(pageRequest);
    }

    public Page<MoneyRecharge> findUserRechargeRecordByPage(Integer userId, Integer pageNum) {
        PageRequest pageRequest = PageRequest.of((pageNum - 1), 3
                , Sort.Direction.DESC, "rechargeDate");
        MoneyAccount account = moneyAccountDao.findMoneyAccountByUserId(userId);

        MoneyRecharge exampleOrder = new MoneyRecharge();
        exampleOrder.setAccountId(account.getAccountId());

        Example<MoneyRecharge> example = Example.of(exampleOrder);

        return moneyRechargeDao.findAll(example, pageRequest);
    }

    public String rechargeAccountByEcPay(Integer rechargeAmount) {

        AllInOne all = new AllInOne();

        AioCheckOutOneTime obj = new AioCheckOutOneTime();

        obj.setMerchantID("3002607");
        obj.setMerchantTradeNo("RemeComic" + GiveYouEverything.generateRandomNumber());
        obj.setMerchantTradeDate(GiveYouEverything.generateDateAndTime());
        obj.setPaymentType("aio");
        obj.setTotalAmount(String.valueOf(rechargeAmount));
        obj.setTradeDesc("Recharge Description");
        obj.setItemName("Deposit");
        obj.setReturnURL("http://localhost:8080/remecomic/money/test");
        obj.setClientBackURL("http://localhost:5173/frontstage/home");
        obj.setChoosePayment("Credit");

        obj.setNeedExtraPaidInfo("N");

        String form = all.aioCheckOut(obj, null);
        return form;
    }
//    /**
//     * 儲值金錢帳戶的方法。
//     *
//     * @param userId 使用者ID，用於找對應的金錢帳戶
//     * @param rechargeAmount 儲值金額
//     * @param status 儲值狀態，true 表示成功，false 表示失敗
//     * @param coinType 儲值幣種，true 表示付費金幣，false 表示免費金幣
//     * @throws RuntimeException 如果 status 為 false
//     */
//    public void rechargingAccount(@Param("userId") Integer userId
//            , @Param("rechargeAmount") Integer rechargeAmount
//            , @Param("status") Boolean status
//            , @Param("coinType") Boolean coinType){
//        MoneyAccount account = moneyAccountDao.findMoneyAccountByUserId(userId);
//
//        Integer payCoin = account.getPayCoin();
//        Integer freeCoin = account.getFreeCoin();
//
//        MoneyRecharge moneyRecharge = new MoneyRecharge();
//
//        moneyRecharge.setAccountId(account.getAccountId());
//        moneyRecharge.setRechargeAmount(rechargeAmount);
//        moneyRecharge.setStatus(status);
//        moneyRecharge.setCoinType(coinType);
//
//        moneyRechargeDao.save(moneyRecharge);
//
//        if (status) {
//            if (coinType) {
//                account.setPayCoin(payCoin + rechargeAmount);
//            }else {
//                account.setFreeCoin(freeCoin + rechargeAmount);
//            }
//
//            moneyAccountDao.save(account);
//        }else {
//            throw new RuntimeException();
//        }
//    }
}
