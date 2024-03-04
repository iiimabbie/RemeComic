package tw.com.remecomic.orgcomic.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgComicDto {


	private Integer verify;
	private String comicName;
	private String orgComicCover;
	private Date orgPublishDate;
	private String introduction;
	private Integer userId;
	private Integer comicId;
	private Integer genreid;
		
	
}
