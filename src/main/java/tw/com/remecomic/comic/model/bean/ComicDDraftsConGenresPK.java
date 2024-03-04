package tw.com.remecomic.comic.model.bean;

import java.io.Serializable;
import java.util.Objects;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDDraftsConGenresPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonBackReference(value="draft-comicGenres")
	//@JsonIgnoreProperties({"comic"})
	private Integer draftId;
	
	@JsonBackReference(value="genre-draftConGenres")
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
		ComicDDraftsConGenresPK pk = (ComicDDraftsConGenresPK) o;
		return Objects.equals(genreId, pk.genreId) &&
				Objects.equals(draftId, pk.draftId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(genreId, draftId);
	}


}
