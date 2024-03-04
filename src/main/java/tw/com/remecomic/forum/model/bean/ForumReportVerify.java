package tw.com.remecomic.forum.model.bean;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="ForumReportVerify")
public class ForumReportVerify {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="reportVerifyId")
	private Integer reportVerifyId;
	
	@Column(name="status")
	private String status;
	
	@OneToMany(mappedBy = "reportVerify",cascade = CascadeType.ALL,orphanRemoval = true)
//	@JsonIgnoreProperties({"reportVerify"})
	@JsonIgnore
	private Collection<ForumPostReport> posts; //正在該審核狀態中的檢舉

	public ForumReportVerify(Integer reportVerifyId) {
		this.reportVerifyId = reportVerifyId;
	}
	
}
