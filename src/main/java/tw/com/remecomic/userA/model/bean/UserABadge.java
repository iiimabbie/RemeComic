package tw.com.remecomic.userA.model.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "UserABadge")
public class UserABadge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer badgeId;

	private String badgeName;

	private String badgePhoto;

	private String badgeDetail;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "UserABadgeUser", // 中介表格名稱
			joinColumns = @JoinColumn(name = "badgeId"), // UserABadge 表格在中介表格中的外來鍵
			inverseJoinColumns = @JoinColumn(name = "userId") // UserA 表格在中介表格中的外來鍵
	)
	@JsonIgnoreProperties("badges")
	private List<UserA> users;

	@OneToMany(mappedBy = "badge", orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<UserABadgeUser> badgeUsers;
}
