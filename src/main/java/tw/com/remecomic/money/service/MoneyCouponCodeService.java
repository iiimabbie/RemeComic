package tw.com.remecomic.money.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tw.com.remecomic.comic.model.bean.ComicD;
import tw.com.remecomic.money.exception.CouponException;
import tw.com.remecomic.money.model.bean.MoneyAccount;
import tw.com.remecomic.money.model.bean.MoneyCouponCode;
import tw.com.remecomic.money.model.bean.MoneyPromotion;
import tw.com.remecomic.money.model.dao.MoneyAccountDao;
import tw.com.remecomic.money.model.dao.MoneyCouponCodeDao;
import tw.com.remecomic.userA.service.UserAService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MoneyCouponCodeService {

    @Autowired
    private MoneyCouponCodeDao moneyCouponCodeDao;

    @Autowired
    private MoneyAccountDao moneyAccountDao;

    @Autowired
    private UserAService userAService;

    /**
     * 查詢所有優惠券代碼
     *
     * @return 包含所有優惠券代碼的列表
     */
    public List<MoneyCouponCode> findAllCouponCodes() {
        return moneyCouponCodeDao.findAll();
    }

    /**
     * 根據優惠券代碼ID查詢單個優惠券代碼
     *
     * @param couponId 優惠券代碼ID
     * @return 如果找到則返回對應的優惠券代碼，否則返回null
     */
    public MoneyCouponCode findCouponCodeById(Integer couponId) {
        return moneyCouponCodeDao.findById(couponId).orElse(null);
    }

    /**
     * 根據促銷名稱查詢相應的優惠券代碼
     *
     * @param promotionName 促銷名稱
     * @return 包含相應優惠券代碼的列表
     */
    public List<MoneyCouponCode> findCouponCodeByPromotionName(String promotionName) {
        String trimmed = promotionName.trim();

        return moneyCouponCodeDao.findMoneyCouponCodeByPromotionName(trimmed);
    }

    public List<MoneyCouponCode> findCouponCodeByPromotionId(Integer promotionId) {
        return moneyCouponCodeDao.findMoneyCouponCodeByPromotionId(promotionId);
    }

    /**
     * 分頁查詢所有優惠券代碼
     *
     * @param pageNum 頁碼
     * @return 包含分頁結果的Page對象
     */
    public Page<MoneyCouponCode> findCouponCodeByPage(Integer pageNum) {
        PageRequest pageRequest = PageRequest.of((pageNum - 1), 10
                , Sort.Direction.DESC, "start");

        return moneyCouponCodeDao.findAll(pageRequest);
    }

    /**
     * 分頁查詢指定促銷的優惠券代碼
     *
     * @param promotion 指定的促銷對象
     * @param pageNum    頁碼
     * @return 包含分頁結果的Page對象
     */
    public Page<MoneyCouponCode> findPromotionCouponCodeByPage(MoneyPromotion promotion, Integer pageNum) {
        PageRequest pageRequest = PageRequest.of((pageNum - 1), 3
                , Sort.Direction.DESC, "startDate");
        MoneyCouponCode exampleCouponCode = new MoneyCouponCode();
        exampleCouponCode.setPromotionId(promotion.getPromotionId());

        Example<MoneyCouponCode> example = Example.of(exampleCouponCode);

        return moneyCouponCodeDao.findAll(example, pageRequest);
    }

    /**
     * 創建優惠券代碼
     *
     * @param moneyCouponCode 優惠券代碼對象
     * @return 創建的優惠券代碼對象
     */
    public MoneyCouponCode createCouponCode(MoneyCouponCode moneyCouponCode) {
        return moneyCouponCodeDao.save(moneyCouponCode);
    }

    /**
     * 更新優惠券代碼
     *
     * @param couponId        要更新的優惠券代碼ID
     * @param updatedCouponCode 更新的優惠券代碼對象
     * @return 如果更新成功，返回更新後的優惠券代碼，否則返回null
     */
    public MoneyCouponCode updateCouponCode(Integer couponId, MoneyCouponCode updatedCouponCode) {
        Optional<MoneyCouponCode> existingCouponCode = moneyCouponCodeDao.findById(couponId);

        if (existingCouponCode.isPresent()) {
            updatedCouponCode.setCouponId(couponId);
            return moneyCouponCodeDao.save(updatedCouponCode);
        }
        return null;
    }

    /**
     * 刪除優惠券代碼
     *
     * @param couponId 要刪除的優惠券代碼ID
     */
    public void deleteCouponCode(Integer couponId) {
        moneyCouponCodeDao.deleteById(couponId);
    }



    /**
     * 在系統中為所有使用者創建具有相同屬性的優惠券代碼。
     * 對於每個使用者，將複製傳入的優惠券代碼屬性，並將其保存到數據庫中。
     *
     * @param moneyCouponCode 包含要複製的優惠券代碼屬性的對象
     */
    public void createCodeForAll(MoneyCouponCode moneyCouponCode) {
        List<Integer> allUserId = userAService.findAllUserID();
        List<MoneyCouponCode> mcList=new ArrayList<MoneyCouponCode>();
        for (Integer userId:
             allUserId) {
            MoneyCouponCode couponCode = new MoneyCouponCode();

            couponCode.setUserId(userId);
            couponCode.setPromotionId(moneyCouponCode.getPromotionId());
            couponCode.setCouponCode(moneyCouponCode.getCouponCode());
            couponCode.setCouponCoin(moneyCouponCode.getCouponCoin());
            couponCode.setStart(moneyCouponCode.getStart());
            couponCode.setExpired(moneyCouponCode.getExpired());
            couponCode.setIsUsed(false);
            mcList.add(couponCode);
        }
        moneyCouponCodeDao.saveAll(mcList);
    }

    /**
     * 使用優惠券代碼來更新使用者帳戶。
     *
     * @param couponCode 優惠券代碼
     * @param userId     使用者ID
     * @throws CouponException 如果優惠券代碼無效、已使用，或者使用者帳戶不存在時，拋出此異常
     */
    public void useCouponCode(String couponCode, Integer userId) {
        MoneyCouponCode specificCode = moneyCouponCodeDao.findSpecificCode(userId, couponCode);

        if (specificCode != null && !specificCode.getIsUsed()) {
            specificCode.setIsUsed(true);

            moneyCouponCodeDao.save(specificCode);

            MoneyAccount account = moneyAccountDao.findMoneyAccountByUserId(userId);

            if (account != null) {
                Integer freeCoin = account.getFreeCoin();
                Integer couponCoin = specificCode.getCouponCoin();

                account.setFreeCoin(freeCoin + couponCoin);

                moneyAccountDao.save(account);
            }else {
                System.out.println("User account not found for user ID: \" + userId");

                throw new CouponException("User account not found for user ID: " + userId);
            }
        } else {
            System.out.println("Invalid coupon code or already used. Coupon code: \" + couponCode");

            throw new CouponException("Invalid coupon code or already used. Coupon code: " + couponCode);
        }
    }

    private boolean isCouponValid(MoneyCouponCode couponCodeEntity) {
        Date currentDate = new Date();
        return currentDate.after(couponCodeEntity.getStart()) && currentDate.before(couponCodeEntity.getExpired());
    }

    public MoneyCouponCode test(Integer userId, String couponCode) {
        MoneyCouponCode specificCode = moneyCouponCodeDao.findSpecificCode(userId, couponCode);

        if (specificCode != null && !specificCode.getIsUsed()) {
            specificCode.setIsUsed(true);

            System.out.println("1111111111111");

            System.out.println(specificCode.getIsUsed());
            return moneyCouponCodeDao.save(specificCode);
        }

        return null;
    }
}
