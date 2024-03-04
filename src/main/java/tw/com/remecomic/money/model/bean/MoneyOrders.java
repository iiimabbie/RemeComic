package tw.com.remecomic.money.model.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tw.com.remecomic.comic.model.bean.ComicDEpisodeUpdate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "MoneyOrders")
public class MoneyOrders {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "orderId")
    private Integer orderId;

    @Column(name = "accountId")
    private Integer accountId;

    @Column(name = "episodeId")
    private Integer episodeId;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "orderDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "dueDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @Column(name = "price")
    private Integer price;

    // 透過自身的 FK(accountId)取得對應的 MoneyAccount
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "accountId", insertable=false, updatable=false)
    private MoneyAccount moneyAccountByAccountId;

    // 透過自身的 FK(episodeId)取得對應的 ComicDEpisodeUpdate
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "episodeId", insertable=false, updatable=false)
    private ComicDEpisodeUpdate comicDEpisodeUpdateByEpisodeId;

    @PrePersist
    public void onCreate() {
        if (orderDate == null) {
            orderDate = new Date();
        }
    }
}
