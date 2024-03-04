package tw.com.remecomic.comic.model.bean;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDRatingEpisodePK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonBackReference(value="user-ratingEpisodes")
	private Integer userId;
	
	@JsonBackReference(value="episode-ratingEpisodes")
	private Integer episodeId;
	

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ComicDRatingEpisodePK pk = (ComicDRatingEpisodePK) o;
		return Objects.equals(userId, pk.userId) &&
				Objects.equals(episodeId, pk.episodeId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, episodeId);
	}
	
	
	

}
