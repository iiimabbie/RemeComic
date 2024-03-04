package tw.com.remecomic.money.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.remecomic.comic.model.bean.ComicD;
import tw.com.remecomic.money.model.bean.MoneyCouponCode;
import tw.com.remecomic.money.model.bean.MoneyPromotion;
import tw.com.remecomic.money.model.dto.MoneyPromotionDto;
import tw.com.remecomic.money.service.MoneyPromotionService;

import java.util.Date;
import java.util.List;

@RestController
public class MoneyPromotionController {

    @Autowired
    private MoneyPromotionService moneyPromotionService;

    @GetMapping("/money/promotion/find")
    public ResponseEntity<MoneyPromotion> findPromotionByPromotionId(@RequestParam("promotionId") int promotionId) {
        MoneyPromotion moneyPromotion = moneyPromotionService.findById(promotionId);

        if (moneyPromotion != null) {
            return new ResponseEntity<>(moneyPromotion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/money/promotion/findName")
    public ResponseEntity<String> findMoneyPromotionNameByPromotionId(
            @RequestParam("promotionId") int promotionId) {
        String name = moneyPromotionService.findMoneyPromotionNameByPromotionId(promotionId);

        if (name != null) {
            return new ResponseEntity<>(name, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/money/promotion/findAll")
    public ResponseEntity<List<MoneyPromotion>> findAllPromotion() {
        List<MoneyPromotion> promotions = moneyPromotionService.findAll();

        if (!promotions.isEmpty()) {
            return new ResponseEntity<>(promotions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/money/promotion/findByComicTitle")
    public ResponseEntity<List<MoneyPromotion>> findPromotionByComicTitle(@RequestParam("comicTitle") String comicTitle) {
        List<MoneyPromotion> promotions = moneyPromotionService.findPromotionByComicTitle(comicTitle);

        if (!promotions.isEmpty()) {
            return new ResponseEntity<>(promotions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/money/promotion/findAllByPage")
    public ResponseEntity<Page<MoneyPromotion>> findPromotionByPage(@RequestParam(name = "p", defaultValue = "1") Integer pageNum) {
        Page<MoneyPromotion> page = moneyPromotionService.findPromotionByPage(pageNum);

        if (!page.isEmpty()) {
            return new ResponseEntity<>(page, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/money/promotion/findComicByPage")
    public ResponseEntity<Page<MoneyPromotion>> findComicPromotionByPage(
            @RequestParam(name = "p", defaultValue = "1") Integer pageNum
            , @RequestBody ComicD comicD) {
        Page<MoneyPromotion> page = moneyPromotionService.findComicPromotionByPage(comicD, pageNum);

        if (!page.isEmpty()) {
            return new ResponseEntity<>(page, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/money/promotion/create")
    public ResponseEntity<String> createPromotion(@RequestParam("comicId") Integer comicId
            , @RequestBody MoneyPromotionDto promotionDto) {
        MoneyPromotion promotion = new MoneyPromotion(comicId
                , promotionDto.getPromotionName()
                , promotionDto.getPromotionDesc()
                , promotionDto.getStartDate()
                , promotionDto.getEndDate());

        try {
            moneyPromotionService.createPromotion(promotion);

            return new ResponseEntity<>("Promotion created", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Create promotion failed. Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create promotion. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/money/promotion/delete")
    public ResponseEntity<Void> deletePromotion(
            @RequestParam("promotionId") Integer promotionId) {
        moneyPromotionService.delete(promotionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/money/promotion/update")
    public ResponseEntity<MoneyPromotion> updatePromotion(@RequestParam("promotionId") Integer promotionId
            , @RequestBody MoneyPromotionDto promotionDto) {
        MoneyPromotion updatedPromotion = moneyPromotionService.update(promotionId, promotionDto);

        System.out.println(promotionDto.getPromotionDesc());
        if (updatedPromotion != null) {
            return new ResponseEntity<>(updatedPromotion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/money/promotion/create")
//    public ResponseEntity<String> createPromotion(@RequestParam("comicId") Integer comicId
//            , @RequestParam("promotionName") String promotionName
//            , @RequestParam("promotionDesc") String promotionDesc
//            , @RequestParam("startDate") Date startDate
//            , @RequestParam("endDate") Date endDate) {
//        MoneyPromotion promotion = new MoneyPromotion(comicId, promotionName, promotionDesc, startDate, endDate);
//
//        try {
//            moneyPromotionService.createPromotion(promotion);
//
//            return new ResponseEntity<>("Promotion created", HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>("Create promotion failed. Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to create promotion. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
}
