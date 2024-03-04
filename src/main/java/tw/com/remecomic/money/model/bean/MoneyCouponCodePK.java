package tw.com.remecomic.money.model.bean;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public class MoneyCouponCodePK implements Serializable {

//    @Column(name = "couponId")
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer couponId;
//
//    @Column(name = "userId")
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer userId;

    @Column(name = "couponId")
    private Integer couponId;

    @Column(name = "userId")
    private Integer userId;

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyCouponCodePK that = (MoneyCouponCodePK) o;
        return Objects.equals(couponId, that.couponId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponId, userId);
    }
}
