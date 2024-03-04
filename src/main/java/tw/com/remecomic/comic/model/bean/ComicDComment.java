package tw.com.remecomic.comic.model.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Entity
@NoArgsConstructor
@Table(name = "ComicDComment")
//@IdClass(ComicDCommentPK.class)
public class ComicDComment {
    @Id
	@Column(unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;

//	@Column(name = "pageId")
	private Integer pageId;

//	@Column(name = "userId")
	private Integer userId;

	private String commentContent;

	@Temporal(TemporalType.TIMESTAMP)
	private Date commentDate;
	
	private Integer toUser;
	private Integer toCommentId;

	@PrePersist
	protected void createDefaultDate() {
		commentDate = new Date();
	}

	@JsonBackReference(value = "page-comments")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pageId", insertable = false, updatable = false)
	private ComicDEpisodeBody page;

//	@JsonBackReference(value = "user-comments")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private UserA user;

public ComicDComment( Integer userId, Integer pageId, String commentContent) {
	this.userId = userId;
	this.pageId = pageId;
	this.commentContent = commentContent;
}

public ComicDComment( Integer userId, Integer pageId, String commentContent, Date commentDate, Integer toUser, Integer toCommentId) {
	this.userId = userId;
	this.pageId = pageId;
	this.commentContent = commentContent;
	this.commentDate = commentDate;
	this.toUser = toUser;
	this.toCommentId = toCommentId;
}


	
	

}
