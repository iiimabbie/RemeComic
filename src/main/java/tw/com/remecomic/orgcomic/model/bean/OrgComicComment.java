package tw.com.remecomic.orgcomic.model.bean;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@Entity
@Table(name = "OrgComicComment")
@NoArgsConstructor
@AllArgsConstructor
//@IdClass(OrgComicCommentPK.class)
public class OrgComicComment {

	@Id
	@Column(name="comicCommentId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer comicCommentId;
	

	private Integer comicId;
	
//	@JsonBackReference(value="comic-orgcomic")
//	@ManyToOne
//	@JoinColumn(name="ComicId",insertable = false,updatable = false)
//	private OrgComic commentcomic;
//	
	

	private Integer userId;
	
	
//	@JsonBackReference(value="user-userA")
//	@ManyToOne
//	@JoinColumn(name="userId",insertable = false,updatable = false)
//	private UserA user;
//	

	
	
	@Column(name="commentContent")
	private String commentContent;	//只能輸入200字


public OrgComicComment(Integer comicId, Integer userId, String commentContent) {
	this.comicId = comicId;
	this.userId = userId;
	this.commentContent = commentContent;
}
	
	
	
	
}
