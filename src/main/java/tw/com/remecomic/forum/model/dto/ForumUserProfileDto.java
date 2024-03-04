package tw.com.remecomic.forum.model.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.bean.ForumUserConnection;

@Data
@NoArgsConstructor
@Component
public class ForumUserProfileDto {
	
	private Integer userId;
	private String userName;
	private String userPhoto;
	private List<ForumUserDto> followersList; // 正在跟隨該使用者的人
	private List<ForumUserDto> followingList; // 該登入者正在追隨的人
	
	private String info;
	private boolean loginUserFollowing;
	
	private String backgroundPhoto;
	private ForumPost topPost;
	private ForumUserDto connectMap;
	
	//個人介面的呈現
	public static ForumUserProfileDto sendToFrontend(ForumUser user,ForumUser loginUser){
		ForumUserProfileDto userDto = new ForumUserProfileDto();
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getName());
		userDto.setUserPhoto(user.getUserPhoto());
		List<ForumUserDto> followersList = user.getFollowersOrBlockers().stream()
				.filter(follow -> follow.getConnectionType().equals("follow"))
				.map(ForumUserConnection::getUser)
				.map(ForumUserDto::sendToFrontendNoFollow).toList();
		List<ForumUserDto> followingList = user.getFollowingOrBlocking().stream()
				.filter(follow -> follow.getConnectionType().equals("follow"))
				.map(ForumUserConnection::getPassiveUser)
				.map(ForumUserDto::sendToFrontendNoFollow).toList();
		userDto.setFollowersList(followersList); //需要篩選 此清單中有跟目前登錄者同樣follow的人有誰
		userDto.setFollowingList(followingList);
		
		userDto.setBackgroundPhoto(user.getBackgroundPhoto());
		userDto.setInfo(user.getInfo());
		userDto.setTopPost(user.getTopPost());
		
		List<ForumUser> loginUserFollowingList = loginUser.getFollowingOrBlocking().stream()
				.filter(follow -> follow.getConnectionType().equals("follow"))
				.map(ForumUserConnection::getPassiveUser).toList();
		for(ForumUser userInList:loginUserFollowingList) {
			System.out.println(userInList.getName());
		}
		System.out.println("主頁對象user："+user.getName());
		System.out.println("當前登錄者loginUser："+loginUser.getName());
		System.out.println("登錄者是否跟隨該user?"+loginUserFollowingList.contains(user));
		userDto.setLoginUserFollowing(loginUserFollowingList.contains(user)?true:false);
		
		return userDto;
	}
//	public static ForumUserProfileDto sendToFrontendNoFollow(ForumUser user){
//		ForumUserProfileDto userDto = new ForumUserProfileDto();
//		userDto.setUserId(user.getUserId());
//		userDto.setUserName(user.getName());
//		userDto.setUserPhoto(user.getUserPhoto());
//		return userDto;
//	}
	
}
