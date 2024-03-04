package tw.com.remecomic.money.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyOrdersDto {
    private Integer episodeId;

    private Date dueDate;

    private Integer price;
}
