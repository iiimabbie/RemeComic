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
@Table(name="ForumPostForward")
@IdClass(ForumPostForwardPK.class)
public class ForumPostForward {
	
//	@JsonIgnoreProperties({"likes"})
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="postId")
	@JsonIgnore
	private ForumPost forwardedPost; //多對多fk 被喜歡的貼文
	
	@JsonIgnore
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	private ForumUser forwardFromUser; //多對多fk 喜歡該貼文的user
	
	@Column(name="forwardTime")
	private Date forwardTime;
	
	public ForumPostForwardPK getId() {
		return new ForumPostForwardPK(forwardedPost.getPostId(),forwardFromUser.getUserId());		
	}
	
	public void setId(ForumPostForwardPK id) {
		this.forwardedPost.setPostId(id.getForwardedPost());
		this.forwardFromUser.setUserId(id.getForwardFromUser());
	}
	@PrePersist
	protected void onCreate() {
		forwardTime = new Date();
	}
	
}
