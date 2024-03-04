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
public class ComicDCommentPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonBackReference
	//@JsonIgnoreProperties({"page"})
	private Integer pageId;
	
	@JsonBackReference
	//@JsonIgnoreProperties({"user"})
	private Integer userId;


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ComicDCommentPK pk = (ComicDCommentPK) o;
		return Objects.equals(pageId, pk.pageId) &&
				Objects.equals(userId, pk.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pageId, userId);
	}


}
