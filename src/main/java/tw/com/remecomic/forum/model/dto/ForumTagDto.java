package tw.com.remecomic.forum.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.forum.model.bean.ForumTag;

@Data
@NoArgsConstructor
@Component
public class ForumTagDto {
	
	private Integer tagId;
	private String tagName;
//	private Integer postAmount;
	
	public static ForumTagDto sendToFrontend(ForumTag tag){
		ForumTagDto tagDto = new ForumTagDto();
		tagDto.setTagId(tag.getTagId());
		tagDto.setTagName(tag.getTagName());
//		tagDto.setPostAmount(tag.getPosts()!=null?tag.getPosts().size():0); //有時限性，近一周的熱門/近24小時的熱門，可能需要在service/dao篩選
		return tagDto;
	}

}
