package tw.com.remecomic.comic.model.bean;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDReadPK {
private static final long serialVersionUID = 1L;
	
	@JsonBackReference(value="read-user")
	private Integer userId;
	
	@JsonBackReference(value="read-page")
	private Integer pageId;


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ComicDReadPK pk = (ComicDReadPK) o;
		return Objects.equals(userId, pk.userId) &&
				Objects.equals(pageId, pk.pageId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, pageId);
	}

	

}
