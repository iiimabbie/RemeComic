package tw.com.remecomic.money.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyCouponCodeDto {
    private Integer promotionId;

    private String couponCode;

    private Integer couponCoin;

    private Date start;

    private Date expired;
}
