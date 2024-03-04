package tw.com.remecomic.money.model.bean;

import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@Entity
@NoArgsConstructor
@Table(name = "MoneyCouponCode2")
public class MoneyCouponCode {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "couponId")
    private Integer couponId;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "promotionId")
//    @Transient
    private Integer promotionId;

    @Column(name = "couponCode")
    private String couponCode;

    @Column(name = "couponCoin")
    private Integer couponCoin;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "expired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expired;

    @Column(name = "isUsed")
    private Boolean isUsed;

    // 透過自身的 FK(promotionId)取得對應的 MoneyPromotion
    @JsonBackReference(value="promotion-coupon")
    @ManyToOne
    @JoinColumn(name = "promotionId", insertable = false, updatable = false)
    private MoneyPromotion moneyPromotionByPromotionId;

    // 透過MoneyUserCoupon這個中介表格來完成
//    @JsonManagedReference
//    @ManyToMany
//    @JoinTable(
//            name = "MoneyUserCoupon",
//            joinColumns = @JoinColumn(name = "couponId"),
//            inverseJoinColumns = @JoinColumn(name = "userId")
//    )
//    private Collection<UserA> users;

    public MoneyCouponCode(Integer promotionId, String couponCode, Integer couponCoin, Date start, Date expired) {
        this.promotionId = promotionId;
        this.couponCode = couponCode;
        this.couponCoin = couponCoin;
        this.start = start;
        this.expired = expired;
    }
}
//, referencedColumnName = "couponId" , referencedColumnName = "userId"