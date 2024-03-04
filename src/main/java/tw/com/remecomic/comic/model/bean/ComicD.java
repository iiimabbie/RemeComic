package tw.com.remecomic.comic.model.bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.money.model.bean.MoneyPromotion;

@Data
@Entity
@NoArgsConstructor
@Table(name="ComicD")
public class ComicD {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer comicId; 
	private String comicTitle;
	private String creator;
	private String comicDescription;
	private String comicCover;
	private String updateDay;
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishDate;
	private Integer editorChoice;
	private Integer purchasePrice;
	private Integer rentalPrice;
	private Integer isPublished;
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyTime;

	@PrePersist
    protected void createDefaultDate() {
//		publishDate = new Date();
		modifyTime = new Date();
    }
	
	
	
		
	public ComicD(String comicTitle, String creator, String comicDescription, String comicCover, String updateDay,
			Date publishDate, Integer editorChoice, Integer purchasePrice, Integer rentalPrice) {
		super();
		this.comicTitle = comicTitle;
		this.creator = creator;
		this.comicDescription = comicDescription;
		this.comicCover = comicCover;
		this.updateDay = updateDay;
		this.publishDate = publishDate;
		this.editorChoice = editorChoice;
		this.purchasePrice = purchasePrice;
		this.rentalPrice = rentalPrice;
	}



	// 一個comic有很多章節 // 漫畫拿掉章節也一起拿掉
	@JsonManagedReference(value="comic-episodes")
	@OneToMany(mappedBy = "comic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ComicDEpisodeUpdate> episodes;
	
	// 一個comic評論 // 漫畫拿掉評論也一起拿掉
	@JsonManagedReference(value="comic-comicRatings")
	@OneToMany(mappedBy = "comic", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval=true)
	private List<ComicDRatingComic> comicRatings;
	
	@JsonManagedReference(value="comic-comicGenres")
	@OneToMany(mappedBy = "comic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ComicDComicConGenres> comicGenres;

	// 一個comic有很多促銷
	@JsonManagedReference(value="comic-money")
	@OneToMany(mappedBy = "comicDByComicId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<MoneyPromotion> promotionsByComicId;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ComicD [comicId=");
		builder.append(comicId);
		builder.append(", comicTitle=");
		builder.append(comicTitle);
		builder.append(", creator=");
		builder.append(creator);
		builder.append(", comicDescription=");
		builder.append(comicDescription);
		builder.append(", comicCover=");
		builder.append(comicCover);
		builder.append(", updateDay=");
		builder.append(updateDay);
		builder.append(", publishDate=");
		builder.append(publishDate);
		builder.append(", editorChoice=");
		builder.append(editorChoice);
		builder.append(", purchasePrice=");
		builder.append(purchasePrice);
		builder.append(", rentalPrice=");
		builder.append(rentalPrice);
		builder.append(", isPublished=");
		builder.append(isPublished);
		builder.append(", modifyTime=");
		builder.append(modifyTime);
		builder.append(", episodes=");
		builder.append(episodes);
		builder.append(", comicRatings=");
		builder.append(comicRatings);
		builder.append(", comicGenres=");
		builder.append(comicGenres);
		builder.append(", promotionsByComicId=");
		builder.append(promotionsByComicId);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

