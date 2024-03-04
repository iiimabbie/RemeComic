package tw.com.remecomic.money.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tw.com.remecomic.comic.model.bean.ComicD;
import tw.com.remecomic.money.model.bean.MoneyPromotion;
import tw.com.remecomic.money.model.dao.MoneyPromotionDao;
import tw.com.remecomic.money.model.dto.MoneyPromotionDto;

import java.util.Date;
import java.util.List;

@Service
public class MoneyPromotionService {

    @Autowired
    private MoneyPromotionDao moneyPromotionDao;

    /**
     * 根據活動的ID找對應的活動。
     *
     * @param promotionId 要找的活動的ID
     * @return 如果找到符合的活動，則返回該活動，否則返回 null
     */
    public MoneyPromotion findById(Integer promotionId) {
        return moneyPromotionDao.findById(promotionId).orElse(null);
    }

    /**
     * Finds and returns a String representation of a MoneyPromotion
     * identified by the specified promotionId.
     *
     * @param promotionId The identifier of the MoneyPromotion to retrieve.
     * @return A String representation of the MoneyPromotion, or null if not found.
     */
    public String findMoneyPromotionNameByPromotionId(Integer promotionId) {
        return moneyPromotionDao.findMoneyPromotionNameByPromotionId(promotionId);
    }

    /**
     * 取得所有活動的列表。
     *
     * @return 包含所有活動的清單列表
     */
    public List<MoneyPromotion> findAll() {
        return moneyPromotionDao.findAll();
    }

    /**
     * 根據漫畫標題模糊搜尋相關的活動。
     *
     * @param comicTitle 要搜尋的漫畫標題
     * @return 符合漫畫標題的活動列表
     */
    public List<MoneyPromotion> findPromotionByComicTitle(String comicTitle) {
        String trimmed = comicTitle.trim();

        return moneyPromotionDao.findMoneyPromotionByComicTitle(trimmed);
    }

    /**
     * 根據開始日期降序分頁找活動。
     *
     * @param pageNum 要找的頁碼（從1開始）
     * @return 符合條件的活動分頁結果
     */
    public Page<MoneyPromotion> findPromotionByPage(Integer pageNum) {
        PageRequest pageRequest = PageRequest.of((pageNum - 1), 3
                , Sort.Direction.DESC, "startDate");

        return moneyPromotionDao.findAll(pageRequest);
    }

    /**
     * 根據漫畫和開始日期降序分頁找相關的活動。
     *
     * @param comicD  目標漫畫
     * @param pageNum 要找的頁碼（從1開始）
     * @return 符合條件的活動分頁結果
     */
    public Page<MoneyPromotion> findComicPromotionByPage(ComicD comicD, Integer pageNum) {
        PageRequest pageRequest = PageRequest.of((pageNum - 1), 3
                , Sort.Direction.DESC, "startDate");
        MoneyPromotion examplePromotion = new MoneyPromotion();
        examplePromotion.setComicId(comicD.getComicId());

        Example<MoneyPromotion> example = Example.of(examplePromotion);

        return moneyPromotionDao.findAll(example, pageRequest);
    }

    /**
     * 創建活動並保存至資料庫。
     *
     * @param moneyPromotion 要創建和保存的活動
     * @throws IllegalArgumentException 如果結束日期在開始日期之前，拋出此異常
     */
    public void createPromotion(MoneyPromotion moneyPromotion) {
        Date endDate = moneyPromotion.getEndDate();
        Date startDate = moneyPromotion.getStartDate();

        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        moneyPromotionDao.save(moneyPromotion);
    }

    /**
     * 根據提供的 promotionId 和 MoneyPromotionDto 更新 MoneyPromotion。
     *
     * @param promotionId  要更新的活動的ID。
     * @param promotionDto 包含促動新值的數據。
     * @return 如果具有給定ID的活動存在，則返回更新後的 MoneyPromotion，否則返回 null。
     */
    public MoneyPromotion update(Integer promotionId, MoneyPromotionDto promotionDto) {
        System.out.println(" ============" + promotionId);

        MoneyPromotion existingPromotion = moneyPromotionDao.findById(promotionId).orElse(null);

        if (existingPromotion != null) {
            System.out.println("=================@===================");
            System.out.println(" ============" + promotionDto.getPromotionDesc());
            System.out.println(" ============" + promotionDto.getPromotionName());
            System.out.println("=================@===================");

            existingPromotion.setPromotionName(promotionDto.getPromotionName());
            existingPromotion.setPromotionDesc(promotionDto.getPromotionDesc());

            return moneyPromotionDao.save(existingPromotion);
        }

        return null;
    }

    public void delete(Integer promotionId) {
        moneyPromotionDao.deleteById(promotionId);
    }


}
