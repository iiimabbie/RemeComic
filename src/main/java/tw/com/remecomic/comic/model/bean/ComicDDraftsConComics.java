package tw.com.remecomic.comic.model.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="ComicDDraftsConComics")
@IdClass(ComicDDraftsConComicsPK.class)
public class ComicDDraftsConComics {
	@Column(unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer draftConComicsId ;
	
	@Id
	@Column(name = "draftId")
	private Integer draftId;
	
	@Id
	@Column(name = "comicId")
	private Integer comicId;
	
	@Id
	@JsonBackReference(value="comic-draftConComics")
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JoinColumn(name="comicId", insertable = false, updatable = false)
	private ComicD comic;
	
	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JoinColumn(name="draftId", insertable = false, updatable = false)
	private ComicDDrafts draft;
	

	public ComicDDraftsConComics(Integer draftId, Integer comicId) {
		super();
		this.draftId = draftId;
		this.comicId = comicId;
	}
	
	

}
