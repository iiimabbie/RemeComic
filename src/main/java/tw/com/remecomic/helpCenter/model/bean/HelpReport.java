package tw.com.remecomic.helpCenter.model.bean;

import java.util.Date;
import java.util.Base64;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@Entity
@NoArgsConstructor
@Table(name = "HelpReport")
public class HelpReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reportId")
	private Integer reportId;

	//自動生成,當前時間
	@Column(name = "reportCurrentDate")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime reportCurrentDate;

	//問題發生時間
	@Column(name = "reportDate")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
//	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private Date reportDate;

	@Column(name = "reportTitle")
	private String reportTitle;

	@Column(name = "reportContent")
	private String reportContent;

	@Lob
	@Column(name = "reportPhoto")
	private byte[] reportPhoto;

	@Column(name = "reportStatus")
	private Integer reportStatus;

	@Column(name = "userName")
	private String userName;
	
	@Column(name = "userId")
	private String userId;
	
	@Column(name = "category")
	private String category;
	
//	@ManyToOne
//	@JoinColumn(name = "userId")
//	@JsonBackReference(value = "report-user")
//	private UserA reportToUser;

	@ManyToOne
	@JoinColumn(name = "serverUserId")
	@JsonBackReference(value = "report-servUser")
	private UserA reportToServUser;

	@OneToOne(mappedBy = "responseToReport")
	@JsonManagedReference(value = "response-report")
	private HelpServiceResponse reportToResponse;

	@OneToOne(mappedBy = "ratingToReport")
	@JsonManagedReference(value = "rating-report")
	private HelpRating reportToRating;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "category")
//	@JsonBackReference(value = "category-report")
//	private HelpCategory reportToCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeId")
	@JsonBackReference(value = "type-report")
	private HelpType reportToType;

	@Transient
	private String reportBase64Pic;
	
	public String getHelpReportBase64Pic() {
		return this.reportBase64Pic;
	}
	
	public void setHelpReportBase64Pic(String reportBase64Pic) {
		this.reportBase64Pic = reportBase64Pic;
	}
}
