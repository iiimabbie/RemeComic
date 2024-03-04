package tw.com.remecomic.orgcomic.model.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "OrgApprovalStatus")
public class OrgApprovalStatus {

	
	@Id
	@Column(name = "orgApproveId")
	private Integer orgApproveId;
	
	@Column(name = "orgApproveName")
	private String orgApproveName;	//只能輸入50字
	
	
}
