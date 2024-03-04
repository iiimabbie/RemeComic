package tw.com.remecomic.forum.model.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@NoArgsConstructor
@Entity
@Table(name="ForumPostReport")
public class ForumPostReport {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="reportId")
	private Integer reportId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="postId")
//	@JsonIgnoreProperties({"reports"})
	@JsonIgnore
	private ForumPost post; //多對一fk 被檢舉的貼文
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
//	@JsonIgnoreProperties({"badges","birthDate","gender",
//			"registerDate","userPhoto"})
	@JsonIgnore
	private ForumUser user; //多對一fk 檢舉者 
						//這次 我沒有在 UserA 裡寫 @OneToMany
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="reasonId")
//	@JsonIgnoreProperties({"reports"})
	@JsonIgnore
	private ForumReason reason; //一對一fk 檢舉理由
	
	@Column(name="reportTime")
	@JsonIgnore
	private Date reportTime; //檢舉時間
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="reportVerifyId")
//	@JsonIgnoreProperties({"posts"})
	@JsonIgnore
	private ForumReportVerify reportVerify; //一對一fk 檢舉的審核狀態
	
	@PrePersist
	protected void onCreate() {
		reportTime = new Date();
	}
	
}
