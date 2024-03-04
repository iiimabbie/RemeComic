package tw.com.remecomic.money.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.com.remecomic.money.model.bean.MoneyAccount;
import tw.com.remecomic.money.model.bean.MoneyOrders;
import tw.com.remecomic.money.model.bean.MoneyRecharge;
import tw.com.remecomic.money.service.MoneyAccountService;
import tw.com.remecomic.userA.model.dto.UserDto;
import tw.com.remecomic.userA.service.UserAService;

import java.util.Collection;
import java.util.List;

@RestController
public class MoneyAccountController {

    @Autowired
    private MoneyAccountService moneyAccountService;

    @Autowired
    private UserAService userAService;

    public void createUserAccount(@RequestParam("name") String username) {

    }

    @GetMapping("/money/account/find")
    public ResponseEntity<MoneyAccount> findAccountByAccountId(@RequestParam("accountId") Integer accountId) {
        MoneyAccount moneyAccount = moneyAccountService.findById(accountId);

        if (moneyAccount != null) {
            return new ResponseEntity<>(moneyAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/money/account/find/User")
    public ResponseEntity<MoneyAccount> findAccountByUserId(@RequestParam("userId") Integer userId) {
        MoneyAccount moneyAccountByUserId = moneyAccountService.findMoneyAccountByUserId(userId);

        if (moneyAccountByUserId != null) {
            return ResponseEntity.ok(moneyAccountByUserId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/money/account/username/find/account")
    public ResponseEntity<UserDto> findUsernameByAccountId(
            @RequestParam("accountId") int accountId) {
        Integer userId = moneyAccountService.findUserIdByAccountId(accountId);

        UserDto userDtoById = userAService.findUserDtoById(userId);

        if (userDtoById != null) {
            return new ResponseEntity<>(userDtoById, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/money/rechargeRecord/find/User")
    public ResponseEntity<Collection<MoneyRecharge>> findAllRechargeRecordByUser(@RequestParam("userId") Integer userId) {
        Collection<MoneyRecharge> rechargeCollection = moneyAccountService.findAllRechargeRecordByUser(userId);

        if (rechargeCollection != null) {
            return ResponseEntity.ok(rechargeCollection);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/money/orderRecord/find/User")
    public ResponseEntity<Collection<MoneyOrders>> findAllOrdersRecordByUser(@RequestParam("userId") Integer userId) {
        Collection<MoneyOrders> ordersCollection = moneyAccountService.findAllOrdersRecordByUser(userId);

        if (ordersCollection != null) {
            return ResponseEntity.ok(ordersCollection);
        } else {
            return ResponseEntity.notFound().build();
        }

    }


}
