package tw.com.remecomic.money.controller;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import ecpay.payment.integration.domain.AioCheckOutATM;
import ecpay.payment.integration.domain.AioCheckOutApplePay;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tw.com.remecomic.money.model.bean.MoneyOrders;
import tw.com.remecomic.money.model.bean.MoneyRecharge;
import tw.com.remecomic.money.model.dto.MoneyRechargeDto;
import tw.com.remecomic.money.service.MoneyRechargeService;

import java.util.List;

@RestController
public class MoneyRechargeController {

    @Autowired
    private MoneyRechargeService moneyRechargeService;

    @GetMapping("/money/recharge/findAll")
    public ResponseEntity<List<MoneyRecharge>> findAllRechargeRecord() {
        List<MoneyRecharge> rechargeRecords = moneyRechargeService.findAllRechargeRecord();

        if (!rechargeRecords.isEmpty()) {
            return new ResponseEntity<>(rechargeRecords, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/money/recharge/findAllByPage")
    public ResponseEntity<Page<MoneyRecharge>> findOrdersByPage(@RequestParam(name = "p", defaultValue = "1") Integer pageNum) {
        Page<MoneyRecharge> rechargeRecordsPage = moneyRechargeService.findRechargeRecordByPage(pageNum);

        if (rechargeRecordsPage.hasContent()) {
            return new ResponseEntity<>(rechargeRecordsPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/money/recharge/findUserByPage")
    public ResponseEntity<Page<MoneyRecharge>> findUserRechargeRecordByPage(
            @RequestParam(name = "p", defaultValue = "1") Integer pageNum,
            @RequestParam("userId") Integer userId) {
        Page<MoneyRecharge> userRechargeRecordsPage = moneyRechargeService.findUserRechargeRecordByPage(userId, pageNum);

        if (userRechargeRecordsPage.hasContent()) {
            return new ResponseEntity<>(userRechargeRecordsPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/money/recharge")
    public ResponseEntity<String> rechargeAccount(@RequestParam("userId") Integer userId
            , @RequestParam("rechargeAmount") Integer rechargeAmount) {

        try {
            moneyRechargeService.rechargingAccount(userId
                    , rechargeAmount
                    , true
                    , true);

            String ecPay = moneyRechargeService.rechargeAccountByEcPay(rechargeAmount);

            return new ResponseEntity<>(ecPay, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Still not pay for bill.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Recharge failed. Error: " + e.getMessage());
        }
    }

    @PostMapping("/money/rechargeByCoupon")
    public ResponseEntity<String> rechargeAccountByCoupon(
            @RequestParam("userId") Integer userId
            , @RequestBody MoneyRechargeDto rechargeDto) {

        try {
            moneyRechargeService.rechargingAccount(userId
                    , rechargeDto.getRechargeAmount()
                    , true
                    , false);

            return ResponseEntity.ok("Recharge successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Still not pay for bill.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Recharge failed. Error: " + e.getMessage());
        }
    }

//    @GetMapping("/money/recharge/byEcpay")
//    public ResponseEntity<String> rechargeAccountByEcPay() {
//        String ecPay = moneyRechargeService.rechargeAccountByEcPay();
//
//        if (ecPay != null) {
//            return new ResponseEntity<>(ecPay, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @PostMapping("/money/test")
//    public String testRecharge() {
//        return "ok";
//    }

//    @PostMapping("/money/recharge")
//    public ResponseEntity<String> rechargeAccount(@RequestParam("userId") Integer userId
//            , @RequestBody MoneyRechargeDto rechargeDto) {
//
//        try {
//            moneyRechargeService.rechargingAccount(userId
//                    , rechargeDto.getRechargeAmount()
//                    , rechargeDto.getStatus()
//                    , rechargeDto.getCoinType());
//
//            return ResponseEntity.ok("Recharge successful");
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Still not pay for bill.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Recharge failed. Error: " + e.getMessage());
//        }
//    }

    //    @GetMapping("/money/recharge/byEcpay")
//    @ResponseBody
//    public String rechargeAccountByEcPay() {
//        AllInOne all = new AllInOne();
//
//        AioCheckOutOneTime obj = new AioCheckOutOneTime();
//
//        obj.setMerchantID("3002607");
//        obj.setMerchantTradeNo("RemeComic1236789aqwerrshjdg");
//        obj.setMerchantTradeDate("2017/01/01 08:05:23");
//        obj.setPaymentType("aio");
//        obj.setTotalAmount("50");
//        obj.setTradeDesc("test Description");
//        obj.setItemName("TestItem");
//        obj.setReturnURL("http://localhost:8080/remecomic/money/test");
//        obj.setChoosePayment("Credit");
//
//        obj.setNeedExtraPaidInfo("N");
//
//        String form = all.aioCheckOut(obj, null);
//        return form;
//    }

}
