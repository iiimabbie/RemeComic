package tw.com.remecomic.comic.model.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(ComicDUserClusteredComicsPK.class)
@Table(name = "ComicDUserClusteredComics")
public class ComicDUserClusteredComics {
	@Id
	private Integer clusterGroupId;
	@Id
	private Integer comicId;


}
