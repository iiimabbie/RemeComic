package tw.com.remecomic.forum.model.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.bean.ForumUserConnection;

@Data
@NoArgsConstructor
@Component
public class ForumUserDto {
	
	private Integer userId;
	private String userName;
	private String userPhoto;
	private List<ForumUserDto> followersList; // 正在跟隨該使用者的人
	private List<ForumUserDto> followingList; // 該登入者正在追隨的人
	
	private String info;
	private boolean loginUserFollowing;
	
	//彈跳小視窗介面
	public static ForumUserDto sendToFrontend(ForumUser user,ForumUser loginUser){
		ForumUserDto userDto = new ForumUserDto();
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
		
		userDto.setInfo(user.getInfo());
		
		List<ForumUser> loginUserFollowingList = loginUser.getFollowingOrBlocking().stream()
		.filter(follow -> follow.getConnectionType().equals("follow"))
		.map(ForumUserConnection::getPassiveUser).toList();
		userDto.setLoginUserFollowing(loginUserFollowingList.contains(user)?true:false);
		
		return userDto;
	}
	//
	public static ForumUserDto sendToFrontendNoFollow(ForumUser user){
		ForumUserDto userDto = new ForumUserDto();
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getName());
		userDto.setUserPhoto(user.getUserPhoto());
		return userDto;
	}
	
	//推薦跟隨/跟隨者/跟隨中清單
	public static ForumUserDto sendToFrontendNoFollowWithInfo(ForumUser user,ForumUser loginUser){
		ForumUserDto userDto = new ForumUserDto();
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getName());
		userDto.setUserPhoto(user.getUserPhoto());
		userDto.setInfo(user.getInfo());
		
		List<ForumUser> loginUserFollowingList = loginUser.getFollowingOrBlocking().stream()
				.filter(follow -> follow.getConnectionType().equals("follow"))
				.map(ForumUserConnection::getPassiveUser).toList();
		userDto.setLoginUserFollowing(loginUserFollowingList.contains(user)?true:false);
		return userDto;
	}
	
}
