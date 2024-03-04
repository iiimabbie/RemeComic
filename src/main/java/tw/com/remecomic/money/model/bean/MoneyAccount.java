package tw.com.remecomic.money.model.bean;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@Entity
@Table(name = "MoneyAccount")
public class MoneyAccount {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "accountId")
	private Integer accountId;

//	@Column(name = "userId", insertable = false, updatable = false)
	@Column(name = "userId")
//    @Transient
	private Integer userId;

	@Column(name = "freeCoin")
	private Integer freeCoin;

	@Column(name = "payCoin")
	private Integer payCoin;

	@OneToOne(cascade = CascadeType.REMOVE)
//    @JsonBackReference(value = "user-account")
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private UserA userAByUserId;

	// 一個 Account 有很多 儲值單
	@JsonManagedReference
	@OneToMany(mappedBy = "moneyAccountByAccountId")
	private Collection<MoneyRecharge> rechargesByAccountId;

	// 一個 Account 有很多 Orders
	@JsonManagedReference
	@OneToMany(mappedBy = "moneyAccountByAccountId")
	private Collection<MoneyOrders> ordersByAccountId;

}
