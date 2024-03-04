package tw.com.remecomic.orgcomic.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgComicBodyDto {

	private Integer comicId;
	private Integer comicEpisode;
	private String comicBodyPhoto;
}
