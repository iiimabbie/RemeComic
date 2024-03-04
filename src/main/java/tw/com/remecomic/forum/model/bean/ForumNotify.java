package tw.com.remecomic.forum.model.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@NoArgsConstructor
@Entity
@Table(name="ForumNotifiy")
public class ForumNotify {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="notifyId")
	private Integer notifyId;
	
	@ManyToOne
	@JoinColumn(name="acceptedUserId")
	@JsonIgnore
	private ForumUser acceptedUser; //多對多fk  追蹤者
	
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name="notifyTypeId")
	@JsonIgnore
	private ForumNotifyType notifyType; //多對多fk  被追蹤者
	
	@Column(name="typeTableId")
	private Integer tableId;
	
	@ManyToOne
	@JoinColumn(name="activeUserId")
	@JsonIgnore
	private ForumUser activeUser; //多對多fk  追蹤者
	
	@Column(name="activeTime")
	private Date activeTime;
	
	@Column(name="notifyStatus")
	private Integer notifyStatus;
	
}
