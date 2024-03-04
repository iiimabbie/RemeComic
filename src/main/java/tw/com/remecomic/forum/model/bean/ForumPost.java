package tw.com.remecomic.forum.model.bean;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name="ForumPost")
public class ForumPost {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="postId")
	private Integer postId;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	@JsonIgnore
	private ForumUser postUser; //多對一fk
	
	@Column(name="postContent")
	private String postContent;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="groupId")
	@JsonIgnore
	private ForumGroup group;
	
	@Column(name="postTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date postTime;
	
	@Column(name="publicStatus")
	private String publicStatus;
	
	@Column(name="verifyHidden")
	private Integer verifyHidden;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	@JsonIgnoreProperties({"postTagId","post"})
	@JsonIgnore
	private Collection<ForumPostTag> tags; //該貼文包含的tag
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
//	@JsonIgnoreProperties({"postPictureId","post"})
	@JsonIgnore
	private Collection<ForumPostPhoto> photos; //該貼文包含的photo
	
	@OneToMany(mappedBy = "likedPost",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
//	@JsonIgnoreProperties({"likedPost","likeFromUser","likeTime"})
	@JsonIgnore
	private Collection<ForumPostLike> likes; //該貼文得到的喜歡
	
	@OneToMany(mappedBy = "forwardedPost",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonIgnore
	private Collection<ForumPostForward> forwards; //該貼文得到的轉發
	
	@OneToMany(mappedBy = "collectedPost",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JsonIgnore
	private Collection<ForumPostCollect> collects; //該貼文得到的轉發
	
	@OneToMany(mappedBy = "parentPost",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	@JsonIgnoreProperties({"comment"})
	@JsonIgnore
	private Collection<ForumPostCommentedPost> comments; //該貼文得到的評論
	
	@OneToOne(mappedBy = "childPost",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private ForumPostCommentedPost toCommentPost; //該貼文(評論)回應的貼文

	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
//	@JsonIgnoreProperties({"post"})
	@JsonIgnore
	private Collection<ForumPostReport> reports; //該貼文得到的檢舉
	
	@PrePersist
	protected void onCreate() {
		postTime = new Date();
		verifyHidden = 0;
	}


	
}
