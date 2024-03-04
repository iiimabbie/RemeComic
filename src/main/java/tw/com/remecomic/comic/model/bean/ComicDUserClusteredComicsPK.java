package tw.com.remecomic.comic.model.bean;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDUserClusteredComicsPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private Integer comicId;
	
	@JsonIgnore
	private Integer clusterGroupId;
	

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ComicDUserClusteredComicsPK pk = (ComicDUserClusteredComicsPK) o;
		return Objects.equals(comicId, pk.comicId) &&
				Objects.equals(clusterGroupId, pk.clusterGroupId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(comicId, clusterGroupId);
	}
	
	
	

}
