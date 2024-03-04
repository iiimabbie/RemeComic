package tw.com.remecomic.comic.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDSummariesDto {
	
	private Object comicId; 
	private String comicTitle;
	private String creator;
	private String comicDescription;
	private String comicCover;
	private String updateDay;
	private Date publishDate;
	private Boolean editorChoice;
	private Integer purchasePrice;
	private Integer rentalPrice;
	private List<String> genreName = new ArrayList<>();	
	private BigDecimal comicLikesEP;
	private BigDecimal comicViews;
	private Long comicLikes;
	private Long comments;
	private Boolean comicPromotion;
	private Integer latestEpisodeLikes;
	private Integer latestEpisodeViews;
	private Date latestEpisodeUpdateDate;
	private String horizontalPhoto;
	private String video;
	
	public ComicDSummariesDto(Integer comicId, String comicTitle, String comicDescription, String updateDay,
			Date publishDate, Boolean editorChoice, Integer purchasePrice, Integer rentalPrice, 
			BigDecimal comicLikesEP, BigDecimal comicViews, Long comicLikes) {
		super();
		this.comicId = comicId;
		this.comicTitle = comicTitle;
		this.comicDescription = comicDescription;
		this.updateDay = updateDay;
		this.publishDate = publishDate;
		this.editorChoice = editorChoice;
		this.purchasePrice = purchasePrice;
		this.rentalPrice = rentalPrice;
		this.comicLikesEP = comicLikesEP;
		this.comicViews = comicViews;
		this.comicLikes = comicLikes;

	}
	
	public ComicDSummariesDto(Integer comicId, String comicTitle, String comicDescription, String updateDay,
			Date publishDate, Boolean editorChoice, Integer purchasePrice, Integer rentalPrice) {
		super();
		this.comicId = comicId;
		this.comicTitle = comicTitle;
		this.comicDescription = comicDescription;
		this.updateDay = updateDay;
		this.publishDate = publishDate;
		this.editorChoice = editorChoice;
		this.purchasePrice = purchasePrice;
		this.rentalPrice = rentalPrice;
	}
	
	
	//getting comicCover in this constructor
	public ComicDSummariesDto(Object comicId, String comicTitle, String creator, String comicCover, String comicDescription, String updateDay,
			Date publishDate, Boolean editorChoice, Integer purchasePrice, Integer rentalPrice, BigDecimal comicLikesEP, Long comicLikes, BigDecimal comicViews,
			Long comments, Integer latestEpisodeLikes, Date latestEpisodeUpdateDate) {
		super();
		this.comicId = comicId;
		this.comicTitle = comicTitle;
		this.creator = creator;
		this.comicCover = comicCover;
		this.comicDescription = comicDescription;
		this.updateDay = updateDay;
		this.publishDate = publishDate;
		this.editorChoice = editorChoice;
		this.purchasePrice = purchasePrice;
		this.rentalPrice = rentalPrice;
		this.comicLikesEP = comicLikesEP;
		this.comicLikes = comicLikes;
		this.comicViews = comicViews;
		this.comments = comments;
		this.latestEpisodeLikes = latestEpisodeLikes;
		this.latestEpisodeUpdateDate = latestEpisodeUpdateDate;
	
	}
}