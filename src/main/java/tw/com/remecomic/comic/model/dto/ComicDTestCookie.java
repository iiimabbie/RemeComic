package tw.com.remecomic.comic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDTestCookie {
	private String sessionId;
	private Integer userId;
	private String userName;
	
	public ComicDTestCookie(String sessionId, String userName) {
		super();
		this.sessionId = sessionId;
		this.userName = userName;
	}
	
	
	
}
