package tw.com.remecomic.comic.model.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "ComicDRatingEpisode")
@IdClass(ComicDRatingEpisodePK.class)
public class ComicDRatingEpisode {
	@Column(unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ratingEpisodeId;

	@Id
	@Column(name = "userId")
	private Integer userId;

	@Id
	@Column(name = "episodeId")
	private Integer episodeId;

	private Integer episodeLike;

	@Temporal(TemporalType.TIMESTAMP)
	private Date episodeRatingDate;

	@PrePersist
	protected void createDefaultDate() {
		episodeRatingDate = new Date();
	}

	public ComicDRatingEpisode(Integer userId, Integer episodeId, Integer episodeLike, Date episodeRatingDate) {
		super();
		this.userId = userId;
		this.episodeId = episodeId;
		this.episodeLike = episodeLike;
		this.episodeRatingDate = episodeRatingDate;
	}

	public ComicDRatingEpisode(Integer userId, Integer episodeId) {
		super();
		this.userId = userId;
		this.episodeId = episodeId;
	}

//	@JsonBackReference(value = "user-ratingEpisodes")
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private UserA user;

	@JsonBackReference(value = "episode-ratingEpisodes")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "episodeId", insertable = false, updatable = false)
	private ComicDEpisodeUpdate episode;

}
