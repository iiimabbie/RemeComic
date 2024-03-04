package tw.com.remecomic.comic.model.bean;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDComicConGenresPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonBackReference(value="comic-comicGenres")
	//@JsonIgnoreProperties({"comic"})
	private Integer comicId;
	
	@JsonBackReference(value="genre-comicConGenres")
	//@JsonIgnoreProperties({"genre"})
	private Integer genreId;


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ComicDComicConGenresPK pk = (ComicDComicConGenresPK) o;
		return Objects.equals(genreId, pk.genreId) &&
				Objects.equals(comicId, pk.comicId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(genreId, comicId);
	}


}
