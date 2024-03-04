package tw.com.remecomic.userA.model.bean;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.helpCenter.model.bean.HelpRating;
import tw.com.remecomic.helpCenter.model.bean.HelpReport;
import tw.com.remecomic.helpCenter.model.bean.HelpServiceResponse;
import tw.com.remecomic.orgcomic.model.bean.OrgComicLike;

@Entity
@Data
@NoArgsConstructor
@Table(name = "UserA")
public class UserA {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String name;
	private String gender;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
	private Date birthDate;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
	private Date registerDate;
	private String userPhoto;

	private String email;
	private Integer userAtt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "statusId")
	@JsonIgnoreProperties("users")
	private UserAStatus status;

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("users")
	private List<UserABadge> badges;

	@OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<UserABadgeUser> userBadges;

	@OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<UserAAttendance> userAttendance;

	@PrePersist
	public void prePersist() {
		if (this.status == null) {
			UserAStatus defaultStatus = getDefaultStatus(); // 獲取預設的 UserAStatus
			this.status = defaultStatus;
		}
		if (this.registerDate == null) {
			this.registerDate = new Date(); // 當前日期時間
		}
	}

	private UserAStatus getDefaultStatus() {
		UserAStatus defaultStatus = new UserAStatus();
		defaultStatus.setStatusId(2);
		return defaultStatus;
	}

//	///////////////////// comicD////////////////////////
////	@JsonManagedReference(value = "user-ratingEpisodes")
////  @JsonIgnoreProperties("user")
//	@JsonIgnore
//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//	private List<ComicDRatingEpisode> episodeRatings;
//
////	@JsonManagedReference(value = "user-comicRatings")
////  @JsonIgnoreProperties("user")
//	@JsonIgnore
//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//	private List<ComicDRatingComic> comicRatings;
//
////	@JsonManagedReference(value = "user-comments")
//	@JsonIgnore
////  @JsonIgnoreProperties("user")
//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//	private List<ComicDComment> comments;
//
////	@JsonManagedReference(value = "read-user")
//	@JsonIgnore
//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//	private List<ComicDRead> userRead;
//
//	///////////////////// money////////////////////////
//	@JsonManagedReference(value = "user-account")
//	@OneToOne(mappedBy = "userAByUserId", cascade = CascadeType.REMOVE)
//	@JsonIgnore
//	private MoneyAccount moneyAccountsByUserId;
//
	///////////////////// helpCenter////////////////////////
//	@OneToMany(mappedBy = "reportToUser", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 使用者回報問題
//	@JsonManagedReference(value = "report-user")
//	@JsonIgnore
//	private List<HelpReport> userToReport;

	@OneToMany(mappedBy = "reportToServUser", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 回報的客服
//	@JsonManagedReference(value = "report-servUser")
	@JsonIgnore
	private List<HelpReport> servUserToReport;

	@OneToMany(mappedBy = "responseToServUser", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 客服回覆
//	@JsonManagedReference(value = "response-servUser")
	@JsonIgnore
	private List<HelpServiceResponse> servUserToResponse;

	@OneToMany(mappedBy = "responseToUser", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 接收的使用者
//	@JsonManagedReference(value = "response-user")
	@JsonIgnore
	private List<HelpServiceResponse> userToResponse;

//	@OneToMany(mappedBy = "ratingToUser", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 使用者評分
////	@JsonManagedReference(value = "rating-user")
//	@JsonIgnore
//	private List<HelpRating> userToRating;

	@OneToMany(mappedBy = "ratingToServUser", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 接收評分的客服
//	@JsonManagedReference(value = "rating-servUser")
	@JsonIgnore
	private List<HelpRating> servUserToRating;

//	@JsonManagedReference(value = "user-like")
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrgComicLike> userLike;

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		UserA other = (UserA) obj;
//		return Objects.equals(userId, other.userId);
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(userId);
//	}

}
