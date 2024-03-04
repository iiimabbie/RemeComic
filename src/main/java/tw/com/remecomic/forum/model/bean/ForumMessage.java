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
@Table(name="ForumMessage")
public class ForumMessage {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="messageId")
	private Integer messageId;
	
	@ManyToOne
	@JoinColumn(name="userId")
	@JsonIgnore
	private ForumUser user;
	
	@ManyToOne
	@JoinColumn(name="acceptedUserId")
	@JsonIgnore
	private ForumUser acceptedUser;
	
	@ManyToOne
	@JoinColumn(name="acceptedGroupId")
	@JsonIgnore
	private ForumGroup group;
	
	@Column(name="messageContent")
	private String messageContent;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
	@Column(name="sendTime")
	private Date sendTime;
	
	@Column(name="messageStatus")
	private Integer messageStatus;
	
	@PrePersist
	protected void onCreate() {
		sendTime = new Date();
	}
	
}
