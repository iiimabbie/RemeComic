package tw.com.remecomic.forum.model.bean;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumPostCollectPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private Integer collectedPost;
	
//	@JsonIgnoreProperties({"gender","birthDate","registerDate",
//		"userPhoto","badges","following","followers","posts","haveLikedPosts"})
	@JsonIgnore
	private Integer collectFromUser;

	
	
}
