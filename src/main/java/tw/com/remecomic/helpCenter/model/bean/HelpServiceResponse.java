package tw.com.remecomic.helpCenter.model.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@Entity
@NoArgsConstructor
@Table(name = "HelpServiceResponse")
public class HelpServiceResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "responseId")
	private Integer responseId;

	@Column(name = "responseContent")
	private String responseContent;

	@Column(name = "responseDate")
	private Date responseDate;

	@ManyToOne
	@JoinColumn(name = "userId")
//	@JsonBackReference(value = "response-user")
	private UserA responseToUser;

	@ManyToOne
	@JoinColumn(name = "serverUserId")
//	@JsonBackReference(value = "response-servUser")
	private UserA responseToServUser;

	@OneToOne
	@JoinColumn(name = "reportId")
	@JsonBackReference(value = "response-report")
	private HelpReport responseToReport;

}
