package tw.com.remecomic.comic.model.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@Entity
@NoArgsConstructor
@Table(name = "ComicDRatingComic")
@IdClass(ComicDRatingComicPK.class)
public class ComicDRatingComic {

	@Column(unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ratingComicId;

	@Id
	@Column(name = "userId")
	private Integer userId;

	@Id
	@Column(name = "comicId")
	private Integer comicId;

	@Column(name = "`like`")
	private Integer like;

	@Column(name = "ratingDate")
	private Date ratingDate;

	@PrePersist
	protected void createDefaultDate() {
		ratingDate = new Date();
	}

//	@JsonBackReference(value = "user-comicRatings")
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private UserA user;

	@JsonBackReference(value = "comic-comicRatings")
	@ManyToOne
	@JoinColumn(name = "comicId", insertable = false, updatable = false)
	private ComicD comic;

	public ComicDRatingComic(UserA user, ComicD comic) {
		this.user = user;
		this.comic = comic;
	}

	public ComicDRatingComic(UserA user, ComicD comic, Integer like, Date ratingDate) {
		this.user = user;
		this.comic = comic;
		this.like = like;
		this.ratingDate = ratingDate;
	}

	public ComicDRatingComic(Integer userId, Integer comicId, Integer like) {
		super();
		this.userId = userId;
		this.comicId = comicId;
		this.like = like;
	}

}
