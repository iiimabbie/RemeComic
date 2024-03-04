package tw.com.remecomic.comic.model.bean;

import java.util.Calendar;
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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="ComicDDrafts")
public class ComicDDrafts {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer draftId; 
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
	private Date modificationTime;
	private Date createdDate;
	private Integer isPublished;
	private Integer version;

	@PrePersist
    protected void createDefaultDate() {		
		modificationTime = new Date();
		createdDate = new Date();
    }
	
	@PreUpdate
    protected void createDefaultTime() {
		modificationTime = new Date();
    }
	
	@JsonManagedReference(value="comic-draftConGenres")
	@OneToMany(fetch = FetchType.LAZY, mappedBy="draft", cascade = CascadeType.REMOVE)
	private List<ComicDDraftsConGenres> genres;
	
		
	public ComicDDrafts(String comicTitle, String creator, String comicDescription, String comicCover, String updateDay,
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
	
	public ComicDDrafts(Integer draftId, String comicTitle, String creator, String comicDescription, String comicCover,
            List<ComicDDraftsConGenres> genres, Date publishDate, String updateDay, 
            Integer purchasePrice, Integer rentalPrice, Date modificationTime ) {
			this.draftId = draftId;
			this.comicTitle = comicTitle;
			this.creator = creator;
			this.comicDescription = comicDescription;
			this.comicCover = comicCover;
			this.genres = genres;
			this.publishDate = publishDate;
			this.updateDay = updateDay;
			this.purchasePrice = purchasePrice;
			this.rentalPrice = rentalPrice;
			this.modificationTime = modificationTime;
			
		}
	
	public ComicDDrafts(Integer draftId, String comicTitle, String creator, String comicDescription, String comicCover,
            List<ComicDDraftsConGenres> genres, Date publishDate, String updateDay, 
            Integer purchasePrice, Integer rentalPrice, Integer isPublished) {
			this.draftId = draftId;
			this.comicTitle = comicTitle;
			this.creator = creator;
			this.comicDescription = comicDescription;
			this.comicCover = comicCover;
			this.genres = genres;
			this.publishDate = publishDate;
			this.updateDay = updateDay;
			this.purchasePrice = purchasePrice;
			this.rentalPrice = rentalPrice;
			this.isPublished = isPublished;
			
		}


}

