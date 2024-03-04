package tw.com.remecomic.comic.model.bean;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDDraftsConComicsPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	//@JsonBackReference(value="draft-comicGenres")
	private Integer draftId;
	
	@JsonBackReference(value="comic-draftConComics")
	private Integer comicId;


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ComicDDraftsConComicsPK pk = (ComicDDraftsConComicsPK) o;
		return Objects.equals(comicId, pk.comicId) &&
				Objects.equals(draftId, pk.draftId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(comicId, draftId);
	}


}
