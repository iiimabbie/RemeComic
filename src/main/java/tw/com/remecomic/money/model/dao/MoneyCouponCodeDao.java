package tw.com.remecomic.money.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.com.remecomic.money.model.bean.MoneyCouponCode;
import tw.com.remecomic.money.model.bean.MoneyPromotion;

import java.util.List;

public interface MoneyCouponCodeDao extends JpaRepository<MoneyCouponCode, Integer> {

    @Query("SELECT mcc FROM MoneyCouponCode mcc JOIN mcc.moneyPromotionByPromotionId mp WHERE mp.promotionName like %:promotionName%")
    List<MoneyCouponCode> findMoneyCouponCodeByPromotionName(@Param("promotionName") String promotionName);

//    MoneyCouponCode findMoneyCouponCodeByCouponCode(String couponCode);

    MoneyCouponCode findMoneyCouponCodeByCouponCodeAndUserId(String couponCode, Integer userId);

    List<MoneyCouponCode> findMoneyCouponCodeByPromotionId(Integer promotionId);

    @Query(value = "select * from MoneyCouponCode2 mcc where mcc.userId = :userId and mcc.couponCode = :couponCode", nativeQuery = true)
    MoneyCouponCode findSpecificCode(@Param("userId") Integer userId, @Param("couponCode") String couponCode);

    List<MoneyCouponCode> findMoneyCouponCodeByUserId(Integer userId);
}
