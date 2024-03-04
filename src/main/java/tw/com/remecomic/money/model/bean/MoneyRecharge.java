package tw.com.remecomic.money.model.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "MoneyRecharge")
public class MoneyRecharge {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rechargeId")
    private Integer rechargeId;

    @Column(name = "accountId")
    private Integer accountId;

    @Column(name = "rechargeAmount")
    private Integer rechargeAmount;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "rechargeDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rechargeDate;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "coinType")
    private Boolean coinType;

    // 透過自身的 FK(accountId)取得對應的 MoneyAccount
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "accountId", insertable = false, updatable = false)
    private MoneyAccount moneyAccountByAccountId;

    @PrePersist
    public void onCreate() {
        if (rechargeDate == null) {
            rechargeDate = new Date();
        }
    }
}
