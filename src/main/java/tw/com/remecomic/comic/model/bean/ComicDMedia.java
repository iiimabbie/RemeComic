package tw.com.remecomic.comic.model.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="ComicDMedia")
public class ComicDMedia {
	@Id
	private Integer comicId;
	
	private String video;
	private String horizontalPhoto;
	private String fullSpaceBGPhoto;


}
