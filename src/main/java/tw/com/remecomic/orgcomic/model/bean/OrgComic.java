package tw.com.remecomic.orgcomic.model.bean;

import java.sql.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@Entity
@NoArgsConstructor
@Table(name = "OrgComic")
public class OrgComic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ComicId")
	private Integer ComicId;
		
	@JsonManagedReference(value="comic-like")
	@OneToMany(mappedBy = "comic",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrgComicLike> comicToLike;

	
	@ManyToOne
	@JoinColumn(name = "userId",insertable = false,updatable = false)
	private UserA orgUser;
	
	@Column(name = "userId")
	private Integer userId;
		
	
	@Column(name = "genreId")
	private Integer genreId;
	
	@Column(name = "verify")
	private Integer verify;
	
	@Column(name = "comicName")
	private String comicName;	//只能輸入100字
	
	
	@Column(name = "orgComicCover")
	private String orgComicCover;
	
	
	@Column(name = "orgPublishDate")
	private Date orgPublishDate;
	
	
	@Column(name = "introduction")
	private String introduction;	//只能輸入500字
	
//	@ManyToOne
//	@JoinColumn(name = "genreId")
//	private Integer genreId;
	
public OrgComic(Integer verify, String comicName, String orgComicCover, Date orgPublishDate, String introduction, Integer userId, Integer genreId) {
	 	this.verify = verify;
	    this.comicName = comicName;
	    this.orgComicCover = orgComicCover;
	    this.orgPublishDate = orgPublishDate;
	    this.introduction = introduction;
	    this.userId = userId;
	    this.genreId = genreId;
	}


//	public OrgComic(Integer verify, String comicName, String orgComicCover, Date orgPublishDate, String introduction,Integer userId) {
//		this.verify = verify;
//		this.comicName = comicName;
//		this.orgComicCover = orgComicCover;
//		this.orgPublishDate = orgPublishDate;
//		this.introduction = introduction;
//		this.userId = userId;
//	}
	
//	@JsonManagedReference(value="comic-body")
//	@OneToMany(mappedBy = "comic",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
//	private List<OrgComicLike> comicTobody;
	
	
//	@JsonManagedReference(value="comic-comment")
//	@OneToMany(mappedBy = "commentcomic",cascade = CascadeType.ALL)
//	private List<OrgComicComment> comicToComment;
	

	
}
