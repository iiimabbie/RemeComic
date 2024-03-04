package tw.com.remecomic.money.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.remecomic.money.exception.CouponException;
import tw.com.remecomic.money.model.bean.MoneyCouponCode;
import tw.com.remecomic.money.model.bean.MoneyPromotion;
import tw.com.remecomic.money.model.dto.MoneyCouponCodeDto;
import tw.com.remecomic.money.model.dto.MoneyCouponCodeOnlyDto;
import tw.com.remecomic.money.service.MoneyCouponCodeService;

import java.util.List;

@RestController
public class MoneyCouponCodeController {

    @Autowired
    private MoneyCouponCodeService moneyCouponCodeService;

    @GetMapping("/money/couponCodes/findAll")
    public ResponseEntity<List<MoneyCouponCode>> findAllCouponCodes() {
        List<MoneyCouponCode> couponCodes = moneyCouponCodeService.findAllCouponCodes();
        return new ResponseEntity<>(couponCodes, HttpStatus.OK);
    }

    @GetMapping("/money/couponCodes/{couponId}")
    public ResponseEntity<MoneyCouponCode> findCouponCodeById(@PathVariable Integer couponId) {
        MoneyCouponCode couponCode = moneyCouponCodeService.findCouponCodeById(couponId);

        if (couponCode != null) {
            return new ResponseEntity<>(couponCode, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/money/couponCodes/findByPromotionName")
    public ResponseEntity<List<MoneyCouponCode>> findCouponCodeByPromotionName(@RequestParam String promotionName) {
        List<MoneyCouponCode> couponCodes = moneyCouponCodeService.findCouponCodeByPromotionName(promotionName);
        return new ResponseEntity<>(couponCodes, HttpStatus.OK);
    }

    @GetMapping("/money/couponCodes/findAllByPage")
    public ResponseEntity<Page<MoneyCouponCode>> findCouponCodeByPage(
            @RequestParam(name = "p", defaultValue = "1") Integer pageNum) {
        Page<MoneyCouponCode> page = moneyCouponCodeService.findCouponCodeByPage(pageNum);

        if (!page.isEmpty()) {
            return new ResponseEntity<>(page, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/money/couponCodes/findPromotionByPage")
    public ResponseEntity<Page<MoneyCouponCode>> getPromotionCouponCodeByPage(@RequestParam Integer pageNum
            , @RequestParam Integer promotionId) {
        MoneyPromotion promotion = new MoneyPromotion();
        promotion.setPromotionId(promotionId);
        Page<MoneyCouponCode> couponCodesPage = moneyCouponCodeService.findPromotionCouponCodeByPage(promotion, pageNum);
        return new ResponseEntity<>(couponCodesPage, HttpStatus.OK);
    }

    @PostMapping("/money/couponCodes/create")
    public ResponseEntity<MoneyCouponCode> createCouponCode(@RequestBody MoneyCouponCode moneyCouponCode) {
        MoneyCouponCode createdCouponCode = moneyCouponCodeService.createCouponCode(moneyCouponCode);
        return new ResponseEntity<>(createdCouponCode, HttpStatus.CREATED);
    }

    @PostMapping("/money/couponCodes/createForAll")
    public ResponseEntity<String> createCodeForAll(@RequestBody MoneyCouponCodeDto codeDto) {
        MoneyCouponCode code = new MoneyCouponCode(codeDto.getPromotionId()
                , codeDto.getCouponCode()
                , codeDto.getCouponCoin()
                , codeDto.getStart()
                , codeDto.getExpired());

        try {
            moneyCouponCodeService.createCodeForAll(code);

            return new ResponseEntity<>("Code created", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("Failed to create code. Error: "
                    + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/money/couponCodes/useCoupon")
    public ResponseEntity<String> useCoupon(
            @RequestParam("userId") Integer userId
            , @RequestParam("couponCode") String couponCode
            ) {

        try {
            moneyCouponCodeService.useCouponCode(couponCode, userId);
            return ResponseEntity.ok("Coupon code successfully used");
        } catch (CouponException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error using coupon code: " + e.getMessage());
        }
    }

    @PutMapping("/money/couponCodes/update/{couponId}")
    public ResponseEntity<MoneyCouponCode> updateCouponCode(@PathVariable Integer couponId
            , @RequestBody MoneyCouponCode updatedCouponCode) {
        MoneyCouponCode updatedCode = moneyCouponCodeService.updateCouponCode(couponId, updatedCouponCode);

        if (updatedCode != null) {
            return new ResponseEntity<>(updatedCode, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/money/couponCodes/delete/{couponId}")
    public ResponseEntity<Void> deleteCouponCode(@PathVariable Integer couponId) {
        moneyCouponCodeService.deleteCouponCode(couponId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/test/moneyCouponCode")
    public ResponseEntity<MoneyCouponCode> test(@RequestParam("userId") Integer userId
            , @RequestParam("couponCode") String couponCode) {
        MoneyCouponCode test = moneyCouponCodeService.test(userId, couponCode);

        if (test != null) {
            return new ResponseEntity<>(test, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
