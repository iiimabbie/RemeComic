package tw.com.remecomic.comic.model.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="ComicDGenres")
public class ComicDGenres {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer genreId;
	
	private String genreName;
	
	@JsonManagedReference(value="genre-comicConGenres")
	//@JsonIgnoreProperties({"genre"})
	@OneToMany(mappedBy="genre", cascade = CascadeType.REMOVE)
	private List<ComicDComicConGenres> comicGenres;
	
	@JsonManagedReference(value="genre-draftConGenres")
	//@JsonIgnoreProperties({"genre"})
	@OneToMany(mappedBy="genre",cascade = CascadeType.REMOVE)
	private List<ComicDDraftsConGenres> draftsGenres;
	

}
