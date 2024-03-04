package tw.com.remecomic.forum.model.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.forum.model.bean.ForumUser;

@Data
@NoArgsConstructor
@Component
public class ForumUserForVerifyDto {
	
	private Integer userId;
	private String userName;
	private String userPhoto;
	private Date reportBanExpire;
	private Date postBanExpire;
	private Integer deletedPostsCount;
	
	public static ForumUserForVerifyDto sendToFrontend(ForumUser user){
		ForumUserForVerifyDto userDto = new ForumUserForVerifyDto();
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getName());
		userDto.setUserPhoto(user.getUserPhoto());
		userDto.setDeletedPostsCount(user.getDeletedPostsCount());
		return userDto;
	}
	public static ForumUserForVerifyDto sendToFrontendWithExpire(ForumUser user){
		ForumUserForVerifyDto userDto = new ForumUserForVerifyDto();
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getName());
		userDto.setUserPhoto(user.getUserPhoto());
		userDto.setPostBanExpire(user.getPostBanExpire());
		userDto.setReportBanExpire(user.getReportBanExpire());
		userDto.setDeletedPostsCount(user.getDeletedPostsCount());
		return userDto;
	}
	
}
