package tw.com.remecomic.comic.model.bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.money.model.bean.MoneyOrders;


@Data
@Entity
@NoArgsConstructor
@Table(name="ComicDEpisodeUpdate")
public class ComicDEpisodeUpdate {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer episodeId;
	private Integer comicId;
	private Integer episodeLikes;
	private Integer episodeNum;	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;	
	private String episodeCover;	
	private Integer episodeViews;
	
	@PrePersist
    protected void createDefaultDate() {
		updateDate = new Date();
    }
	
	
	
	
	public ComicDEpisodeUpdate(Integer episodeId, Integer episodeLikes, Integer episodeViews, Integer episodeNum, String episodeCover
			) {
		super();
		this.episodeId = episodeId;
		this.episodeLikes = episodeLikes;
		this.episodeViews = episodeViews;
		this.episodeNum = episodeNum;
		this.episodeCover = episodeCover;
		
	}




	// 一個漫畫有很多章節  // 拿到集數相對的漫畫集數
	@JsonBackReference(value="comic-episodes")
	@ManyToOne(targetEntity = ComicD.class, fetch = FetchType.LAZY)
	@JoinColumn(name="comicId", insertable=false, updatable=false)
	private ComicD comic;
	
	// 一個章節有很多like // episode拿掉rating也一起拿掉
	@JsonManagedReference(value="episode-ratingEpisodes")
	//@JsonIgnoreProperties({"episode"})
	@OneToMany(fetch = FetchType.LAZY, mappedBy="episode", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<ComicDRatingEpisode> ratingEpisodes;
	
	// 一個章節有很多page // episode拿掉page也一起拿掉
	//@JsonManagedReference(value="episode-bodies")
	@JsonIgnoreProperties({"episode"})
	@OneToMany(fetch = FetchType.LAZY, mappedBy="episode", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<ComicDEpisodeBody> episodeBodies;

	// 一個章節有很多訂單
	@JsonManagedReference
	@OneToMany(mappedBy = "comicDEpisodeUpdateByEpisodeId")
	private Collection<MoneyOrders> ordersByEpisodeId;
}
