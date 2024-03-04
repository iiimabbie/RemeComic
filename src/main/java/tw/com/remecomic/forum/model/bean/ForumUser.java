package tw.com.remecomic.forum.model.bean;

import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@NoArgsConstructor
@Entity
@Table(name="ForumUser")
public class ForumUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userId")
	private Integer userId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	@MapsId
	@JsonIgnore
	private UserA user;
	
	@Column(name="forumUserName")
	private String name;
	
	@Column(name="photo")
	private String userPhoto;
	
	@Column(name="backgroundPhoto")
	private String backgroundPhoto;
	
	@Column(name="info")
	private String info;
		
	@Column(name="reportBanExpire")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private Date reportBanExpire;
	
	@Column(name="postBanExpire")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private Date postBanExpire;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="topPostId")
	private ForumPost topPost;
	
	@Column(name="deletedPostsCount")
	private Integer deletedPostsCount;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private Collection<ForumUserConnection> followingOrBlocking; // 該使用者追蹤/屏蔽的人

	@OneToMany(mappedBy = "passiveUser")
	@JsonIgnore
	private Collection<ForumUserConnection> followersOrBlockers; // 追蹤/屏蔽該使用者的人們

	@OneToMany(mappedBy = "postUser")
	@JsonIgnore
	private Collection<ForumPost> posts; // 該使用者發布的貼文

	@OneToMany(mappedBy = "likeFromUser")
	@JsonIgnore
	private Collection<ForumPostLike> haveLikedPosts; // 該使用者喜歡的貼文
	
    public ForumUser(UserA user) {
        this.user = user;
    }
}
