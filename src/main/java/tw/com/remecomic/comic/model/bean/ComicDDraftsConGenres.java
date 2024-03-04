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
@Table(name="ComicDDraftsConGenres")
@IdClass(ComicDDraftsConGenresPK.class)
public class ComicDDraftsConGenres {
	@Column(unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer draftConGenreId ;
	
	@Id
	@Column(name = "draftId")
	private Integer draftId;
	
	@Id
	@Column(name = "genreId")
	private Integer genreId;
	
	@Id
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JoinColumn(name="genreId", insertable = false, updatable = false)
	private ComicDGenres genre;
	
	@Id
	//@JsonIgnore
	@JsonBackReference(value="comic-draftConGenres")
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JoinColumn(name="draftId", insertable = false, updatable = false)
	private ComicDDrafts draft;
	

	public ComicDDraftsConGenres(Integer draftId, Integer genreId) {
		super();
		this.draftId = draftId;
		this.genreId = genreId;
	}
	
	

}
