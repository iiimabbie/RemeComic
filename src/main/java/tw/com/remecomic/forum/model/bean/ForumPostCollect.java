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
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name="ForumPostCollect")
@IdClass(ForumPostCollectPK.class)
public class ForumPostCollect {
	
//	@JsonIgnoreProperties({"likes"})
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="postId")
	@JsonIgnore
	private ForumPost collectedPost; //多對多fk 被喜歡的貼文
	
	@JsonIgnore
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	private ForumUser collectFromUser; //多對多fk 喜歡該貼文的user
	
	@Column(name="collectTime")
	private Date collectTime;
	
	public ForumPostCollectPK getId() {
		return new ForumPostCollectPK(collectedPost.getPostId(),collectFromUser.getUserId());		
	}
	
	public void setId(ForumPostCollectPK id) {
		this.collectedPost.setPostId(id.getCollectedPost());
		this.collectFromUser.setUserId(id.getCollectFromUser());
	}
	@PrePersist
	protected void onCreate() {
		collectTime = new Date();
	}
	
}
