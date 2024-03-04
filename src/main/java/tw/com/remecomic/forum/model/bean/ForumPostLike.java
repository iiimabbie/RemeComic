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
@Table(name="ForumPostLike")
@IdClass(ForumPostLikePK.class)
public class ForumPostLike {
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="postId")
	@JsonIgnoreProperties({"likes"})
	private ForumPost likedPost; //多對多fk 被喜歡的貼文
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	@JsonIgnore
	private ForumUser likeFromUser; //多對多fk 喜歡該貼文的user
	
	@Column(name="likeTime")
	private Date likeTime;
	
	public ForumPostLikePK getId() {
		return new ForumPostLikePK(likedPost.getPostId(),likeFromUser.getUserId());		
	}
	
	public void setId(ForumPostLikePK id) {
		this.likedPost.setPostId(id.getLikedPost());
		this.likeFromUser.setUserId(id.getLikeFromUser());
	}
	@PrePersist
	protected void onCreate() {
		likeTime = new Date();
	}
	
}
