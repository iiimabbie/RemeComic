package tw.com.remecomic.comic.model.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name="ComicDComicConGenres")
@IdClass(ComicDComicConGenresPK.class)
public class ComicDComicConGenres {
	@Column(unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer comicConGenreId ;
	
	@Id
	@Column(name = "comicId")
	private Integer comicId;
	
	@Id
	@Column(name = "genreId")
	private Integer genreId;	
	
	@JsonBackReference(value="genre-comicConGenres")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="genreId", insertable = false, updatable = false)
	private ComicDGenres genre;
	
	@JsonBackReference(value="comic-comicGenres")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="comicId", insertable = false, updatable = false)
	private ComicD comic;

	public ComicDComicConGenres(Integer comicId, Integer genreId) {
		super();
		this.comicId = comicId;
		this.genreId = genreId;
	}
	
	

}
