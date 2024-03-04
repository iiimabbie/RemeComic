package tw.com.remecomic.forum.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.forum.model.bean.ForumTag;


@Component
public interface ForumTagDto2 {
	
	Integer getTagId();
	String getTagName();
	Integer getPostAmount();

}
