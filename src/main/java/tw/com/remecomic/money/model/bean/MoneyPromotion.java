package tw.com.remecomic.money.model.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tw.com.remecomic.comic.model.bean.ComicD;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "MoneyPromotion")
public class MoneyPromotion {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "promotionId")
    private Integer promotionId;

    @Column(name = "comicId")
    private Integer comicId;

    @Column(name = "promotionName")
    private String promotionName;

    @Column(name = "promotionDesc")
    private String promotionDesc;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    // 一個 Promotion 有很多 CouponCode
    @JsonManagedReference(value="promotion-coupon")
    @OneToMany(mappedBy = "moneyPromotionByPromotionId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval=true)
    private Collection<MoneyCouponCode> couponCodesByPromotionId;

    // 透過自身的 FK(comicId)取得對應的 ComicD
    @JsonBackReference(value="comic-money")
    @ManyToOne
    @JoinColumn(name = "comicId", insertable=false, updatable=false)
    private ComicD comicDByComicId;

    public MoneyPromotion(Integer comicId, String promotionName, String promotionDesc, Date startDate, Date endDate) {
        this.comicId = comicId;
        this.promotionName = promotionName;
        this.promotionDesc = promotionDesc;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
