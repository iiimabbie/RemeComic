package tw.com.remecomic.comic.model.bean;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.comic.model.dao.ComicDDao;
import tw.com.remecomic.userA.model.bean.UserA;
import tw.com.remecomic.userA.model.dao.UserADao;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDRatingComicPK implements Serializable{
	private static final long serialVersionUID = 1L;	

	@JsonBackReference(value="user-comicRatings")
	private Integer userId;
	
	@JsonBackReference(value="comic-comicRatings")
	private Integer comicId;


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ComicDRatingComicPK pk = (ComicDRatingComicPK) o;
		return Objects.equals(userId, pk.userId) &&
				Objects.equals(comicId, pk.comicId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, comicId);
	}

}
