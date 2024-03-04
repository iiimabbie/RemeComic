package tw.com.remecomic.money.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tw.com.remecomic.comic.model.dto.ComicDDto;
import tw.com.remecomic.comic.model.dto.ComicDEpisodeUpdateDto;
import tw.com.remecomic.comic.service.ComicDEpisodeUpdateService;
import tw.com.remecomic.comic.service.ComicDService;
import tw.com.remecomic.money.exception.InsufficientBalanceException;
import tw.com.remecomic.money.model.bean.MoneyOrders;
import tw.com.remecomic.money.model.dto.MoneyOrdersDto;
import tw.com.remecomic.money.service.MoneyOrdersService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class MoneyOrdersController {

    @Autowired
    private MoneyOrdersService moneyOrdersService;

    @Autowired
    private ComicDEpisodeUpdateService comicDEpisodeUpdateService;

    @Autowired
    private ComicDService comicDService;

    @GetMapping("/money/orders/ComicTitle/findByEpisode")
    public ResponseEntity<ComicDDto> getSimpleEpisodeById(@RequestParam Integer episodeId) {
        ComicDEpisodeUpdateDto episodeSearch = comicDEpisodeUpdateService.findSimpleEpisodeById(episodeId);

        if (episodeSearch != null) {
            ComicDDto comicDDto = comicDService.findSimpleComicById(episodeSearch.getComicId());

            return ResponseEntity.ok(comicDDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/money/orders/findAll")
    public ResponseEntity<List<MoneyOrders>> findAllOrdersRecord() {
        List<MoneyOrders> ordersRecords = moneyOrdersService.findAllOrdersRecord();

        if (!ordersRecords.isEmpty()) {
            return ResponseEntity.ok(ordersRecords);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/money/orders/findAllByPage")
    public ResponseEntity<Page<MoneyOrders>> findOrdersByPage(@RequestParam(name = "p", defaultValue = "1") Integer pageNum) {
        Page<MoneyOrders> ordersRecordsPage = moneyOrdersService.findOrdersByPage(pageNum);

        if (ordersRecordsPage.hasContent()) {
            return ResponseEntity.ok(ordersRecordsPage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/money/orders/findUserByPage")
    public ResponseEntity<Page<MoneyOrders>> findUserOrdersByPage(
            @RequestParam(name = "p", defaultValue = "1") Integer pageNum,
            @RequestParam("userId") Integer userId) {
        Page<MoneyOrders> userOrdersRecordsPage = moneyOrdersService.findUserOrdersByPage(userId, pageNum);

        if (userOrdersRecordsPage.hasContent()) {
            return ResponseEntity.ok(userOrdersRecordsPage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/money/orders/create")
    public ResponseEntity<String> createOrders(@RequestParam("userId") Integer userId
            , @RequestBody MoneyOrdersDto ordersDto) {

        try {
            moneyOrdersService.createOrders(userId
                    , ordersDto.getEpisodeId()
                    , ordersDto.getDueDate()
                    , ordersDto.getPrice());

            return new ResponseEntity<>("Create successful", HttpStatus.OK);
        } catch (InsufficientBalanceException e) {
            return new ResponseEntity<>("Create order failed. Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Create failed. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/money/order/findDateAndTotal")
    public ResponseEntity<Map<Date, Integer>> findDateAndTotal() {
        Map<Date, Integer> dateIntegerMap = moneyOrdersService.dateAndTotalCollection();

        if (dateIntegerMap != null) {
            return new ResponseEntity<>(dateIntegerMap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @ResponseBody
//    @PostMapping("/money/orders/create")
//    public ResponseEntity<String> createOrders(@RequestParam("userId") Integer userId
//            , @RequestParam("episodeId") Integer episodeId
//            , @RequestParam("dueDate") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date dueDate
//            , @RequestParam("price") Integer price) {
//
//        try {
//            moneyOrdersService.createOrders(userId, episodeId, dueDate, price);
//
//            return new ResponseEntity<>("Create successful", HttpStatus.OK);
//        } catch (InsufficientBalanceException e) {
//            return new ResponseEntity<>("Create order failed. Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Create failed. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
