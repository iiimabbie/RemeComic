package tw.com.remecomic.money.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tw.com.remecomic.money.model.bean.MoneyPromotion;

public interface MoneyPromotionDao extends JpaRepository<MoneyPromotion, Integer> {

    @Query("SELECT mp FROM MoneyPromotion mp JOIN mp.comicDByComicId c WHERE c.comicTitle like %:comicTitle%")
    List<MoneyPromotion> findMoneyPromotionByComicTitle(@Param("comicTitle") String comicTitle);
    
    
    @Query("SELECT comicId FROM MoneyPromotion WHERE endDate > ?1")
    List<Integer> findPromotionBeforeToday(Date today);

    @Query("Select promotionName from MoneyPromotion where promotionId = :promotionId")
    String findMoneyPromotionNameByPromotionId(@Param("promotionId") Integer promotionId);

    MoneyPromotion findByPromotionId(Integer promotionId);
}


