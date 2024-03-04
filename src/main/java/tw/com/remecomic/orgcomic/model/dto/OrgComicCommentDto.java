package tw.com.remecomic.orgcomic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgComicCommentDto {

	private Integer comicId;
	private Integer userId;
	private String commentContent;
	
	
		
	
}
