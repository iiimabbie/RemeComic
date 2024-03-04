package tw.com.remecomic.forum.model.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="ForumUserConnection")
public class ForumUserConnection {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="connectionId")
	private Integer connectionId;
	
	@ManyToOne
	@JoinColumn(name="userId")
	@JsonIgnore
	private ForumUser user; //多對多fk  追蹤者
	
	@ManyToOne
	@JoinColumn(name="passiveUserId")
	@JsonIgnore
	private ForumUser passiveUser; //多對多fk  被追蹤者
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
	@Column(name="passiveTime")
	private Date passiveTime;
	
	@Column(name="connectionType")
	private String connectionType; //follow/ban
	
	@PrePersist
	protected void onCreate() {
		passiveTime = new Date();
	}
	
}
