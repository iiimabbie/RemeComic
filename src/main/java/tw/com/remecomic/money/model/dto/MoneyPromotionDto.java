package tw.com.remecomic.money.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyPromotionDto {
    private String promotionName;

    private String promotionDesc;

    private Date startDate;

    private Date endDate;
}
