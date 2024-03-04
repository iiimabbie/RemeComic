package tw.com.remecomic.forum.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.forum.model.bean.ForumGroup;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.dto.ForumGroupDto;
import tw.com.remecomic.forum.model.dto.ForumUserDto;
import tw.com.remecomic.forum.model.dto.ForumUserForVerifyDto;
import tw.com.remecomic.forum.model.dto.ForumUserProfileDto;
import tw.com.remecomic.forum.service.ForumUserService;

@RestController
//@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600) // 指定允许跨域的域名和缓存时间
public class ForumUserController {
	@Autowired
	private ForumUserService forumUserService;

	@GetMapping("/forum/user")
	public ResponseEntity<List<ForumUser>> userAll(){
		List<ForumUser> findUserAll = forumUserService.findUserAll();
		return ResponseEntity.ok(findUserAll);
	}
	
////////////////////////////users (info for profile) ///////////////////////////////
	
	@GetMapping("/user/profile")
	public ResponseEntity<ForumUserProfileDto> userProfile(@RequestParam Integer userId,
														@RequestParam Integer loginUserId){
		System.out.println("主頁對象user："+userId);
		System.out.println("當前登錄者loginUser："+loginUserId);
		Optional<ForumUser> result = forumUserService.findById(userId);
		Optional<ForumUser> loginUserResult = forumUserService.findById(loginUserId);
		if(result.isPresent()&&loginUserResult.isPresent()) {
			ForumUser user = result.get();
			ForumUser logingUser = loginUserResult.get();
			ForumUserProfileDto userProfileDto = ForumUserProfileDto.sendToFrontend(user,logingUser);
			return ResponseEntity.ok(userProfileDto);
		}
		return ResponseEntity.notFound().build();
	}
	
//////////////////////////// users (info for verify) ///////////////////////////////
	
	//////////////// show : user's deletedPostsCount >= 2 (users) ( for verify ) /////////////////	
//	@GetMapping("/users/deletedCount") //read follow
//	public ResponseEntity<List<ForumUserForVerifyDto>> usersShowDeletedCount(@RequestParam List<Integer> userIdList) {
//		List<ForumUserForVerifyDto> usersList = forumUserService.getUsersInIdList(userIdList);
//		if(usersList!=null) {
//			return ResponseEntity.ok(usersList);
//		}
//		return ResponseEntity.notFound().build();
//	}
	//////////////// show : user's report has reject times >= 2 (users) ( for verify )/////////////////	
	@GetMapping("/users/reportRejectCount") //read follow
	public ResponseEntity<List<Map<String, Object[]>>> usersShowReportRejectCount(@RequestParam List<Integer> userIdList) {
		System.out.println("有傳到controller userIdList :"+userIdList);
		List<Map<String, Object[]>> usersList = forumUserService.getUsersReportReject(userIdList);
		if(usersList!=null) {
			return ResponseEntity.ok(usersList);
		}
		return ResponseEntity.notFound().build();
	}
	//////////////// update : user's deletedPostsCount + banPostExpire (users) ( for verify ) /////////////////
	@PutMapping("/users/update/toBanPost") 
	public ResponseEntity<List<ForumUserForVerifyDto>> usersUpdateToBanPost(@RequestBody Map<String,String> request) {
		
		List<Integer> userIdList = new ArrayList<>();
		 String userIdListString = request.get("userIdList");
		 if(userIdListString!=null) {
			 String[] userIdListStringArray = userIdListString.split(",");
			 
			 for(String ele : userIdListStringArray) {
				 userIdList.add(Integer.valueOf(ele));
			 }
		 }
		
		List<ForumUserForVerifyDto> usersList = forumUserService.updateUsersAboutDeleteColumn(userIdList);
		if(usersList!=null) {
			return ResponseEntity.ok(usersList);
		}
		return ResponseEntity.notFound().build();
	}
	//////////////// update : user's banReportExpire (users) ( for verify ) /////////////////
	@PutMapping("/users/update/toBanReport") 
	public ResponseEntity<List<ForumUserForVerifyDto>> usersUpdateToBanReport(@RequestBody Map<String,String> request) {
		List<Integer> userIdList = new ArrayList<>();
		 String userIdListString = request.get("userIdList");
		 if(userIdListString!=null) {
			 String[] userIdListStringArray = userIdListString.split(",");
			 
			 for(String ele : userIdListStringArray) {
				 userIdList.add(Integer.valueOf(ele));
			 }
		 }
		
		List<ForumUserForVerifyDto> usersList = forumUserService.updateUsersAboutBanReportRight(userIdList);
		if(usersList!=null) {
			return ResponseEntity.ok(usersList);
		}
		return ResponseEntity.notFound().build();
	}
	
//////////////////////////// following ///////////////////////////////
	
	//////////////// show all (following & followers) /////////////////	
	
	@GetMapping("/following/show") //read follow
	public ResponseEntity<List<ForumUserDto>> followingsShowAll(@RequestParam Integer userId,
																@RequestParam Integer loginUserId) {
		Optional<ForumUser> findResult = forumUserService.findById(userId);
		Optional<ForumUser> loginUserResult = forumUserService.findById(loginUserId);
		if(findResult.isPresent()&&loginUserResult.isPresent()) {
			List<ForumUserDto> allFollow = forumUserService.getAllFollowing(findResult.get(),loginUserResult.get());
			return ResponseEntity.ok(allFollow);
		}
		return ResponseEntity.notFound().build();
	}
	@GetMapping("/follower/show") //read follow
	public ResponseEntity<List<ForumUserDto>> followersShowAll(@RequestParam Integer userId,
															@RequestParam Integer loginUserId) {
		Optional<ForumUser> findResult = forumUserService.findById(userId);
		Optional<ForumUser> loginUserResult = forumUserService.findById(loginUserId);
		if(findResult.isPresent()&&loginUserResult.isPresent()) {
			List<ForumUserDto> allFollow = forumUserService.getAllFollower(findResult.get(),loginUserResult.get());
			return ResponseEntity.ok(allFollow);
		}
		return ResponseEntity.notFound().build();
	}
	@GetMapping("/following/show/detail") //read follow
	public ResponseEntity<List<ForumUserDto>> followShowAllWithFollow(@RequestParam Integer userId) {
		Optional<ForumUser> findResult = forumUserService.findById(userId);
		if(findResult.isPresent()) {
			List<ForumUserDto> allFollow = forumUserService.getAllFollowingWithFollow(findResult.get());
			return ResponseEntity.ok(allFollow);
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// show filter by search (following) /////////////////

	
	//////////////// show recommended to follow (following) /////////////////	
	
	
	//////////////// add+delete (following) /////////////////
	
	@PostMapping("/follow/change") //必要參數： userId, followedUserId
	public ResponseEntity<ForumUserDto> followChange(@RequestBody Map<String,String> request){
		
		Integer userId = Integer.valueOf(request.get("userId"));
		Integer followedUserId = Integer.valueOf(request.get("followedUserId"));
		String connString = request.get("connString");
		
		if(!(connString.equals("follow")||connString.equals("black"))) {
			return ResponseEntity.badRequest().build();
		}
		
		System.out.println("進入到changeFollow;userId="+userId+",followedUserId="+followedUserId+";connString="+connString);
		
		Optional<ForumUser> findUserResult = forumUserService.findById(userId);
		Optional<ForumUser> findFollowedUserResult = forumUserService.findById(followedUserId);
		if((findUserResult.isPresent())&&(findFollowedUserResult.isPresent())) {
			ForumUserDto follow = forumUserService.changeFollow(findUserResult.get(), findFollowedUserResult.get(),connString);
			
			if(follow!=null) { // 成功跟隨該user
				return ResponseEntity.ok(follow);
			}else {   // 成功取消該user
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}	
	
//////////////////////////// blocking ///////////////////////////////
	
	//////////////// show all (blocking) /////////////////
	@GetMapping("/block/show") //read follow
	public ResponseEntity<List<ForumUserDto>> blockShowAll(@RequestParam Integer userId) {
		Optional<ForumUser> findResult = forumUserService.findById(userId);
		if(findResult.isPresent()) {
			List<ForumUserDto> allFollow = forumUserService.getAllBlocking(findResult.get());
			return ResponseEntity.ok(allFollow);
		}
		return ResponseEntity.notFound().build();
	}
	
//////////////////////////// in group ///////////////////////////////
	
	//////////////// show (in group) /////////////////
	// 顯示該使用者的所有group
	@GetMapping("/group/show")
	public ResponseEntity<List<ForumGroupDto>> groupShowAll(@RequestParam Integer userId) {
		Optional<ForumUser> findResult = forumUserService.findById(userId);
		if(findResult.isPresent()) {
			List<ForumGroupDto> allFollow = forumUserService.getAllGroup(findResult.get());
			return ResponseEntity.ok(allFollow);
		}
		return ResponseEntity.notFound().build();
	}
	// 顯示該group中的所有user
	@GetMapping("/group/user/show")
	public ResponseEntity<List<ForumUserDto>> userShowInGroup(@RequestParam Integer groupId) {
		Optional<ForumGroup> findResult = forumUserService.findGroupById(groupId);
		if(findResult.isPresent()) {
			List<ForumUserDto> allFollow = forumUserService.getUsersInGroup(findResult.get());
			return ResponseEntity.ok(allFollow);
		}
		return ResponseEntity.notFound().build();
	}
	//////////////// update manager (in group) /////////////////
	@PutMapping("/group/manager/change")
	public ResponseEntity<ForumGroupDto> changeGroupManager(@RequestParam Integer userId,
														@RequestParam Integer groupId) {
		Optional<ForumUser> findResult = forumUserService.findById(userId);
		Optional<ForumGroup> findGroupResult = forumUserService.findGroupById(groupId);
		if(findResult.isPresent() && findGroupResult.isPresent()) {
			ForumGroupDto group = forumUserService.changeGroupManager(findResult.get(),findGroupResult.get());
			return ResponseEntity.ok(group);
		}
		return ResponseEntity.notFound().build();
	}
	//////////////// add (in group) /////////////////
	// 新建group
	@PostMapping("/group/add")
	public ResponseEntity<ForumGroupDto> buildGroup(@RequestParam Integer userId,
												@RequestParam String groupName) {
		Optional<ForumUser> findResult = forumUserService.findById(userId);
		if(findResult.isPresent()) {
			ForumGroupDto group = forumUserService.buildGroup(findResult.get(),groupName);
			return ResponseEntity.ok(group);
		}
		return ResponseEntity.notFound().build();
	}
	// 新增user進入group
	@PostMapping("/group/user/add")
	public ResponseEntity<ForumGroupDto> addUserInGroup(@RequestParam List<Integer> userIdList,
													@RequestParam Integer groupId) {
		Optional<ForumGroup> findResult = forumUserService.findGroupById(groupId);
		if(findResult.isPresent()) {
			ForumGroupDto group = forumUserService.addUserInGroup(findResult.get(),userIdList);
			return ResponseEntity.ok(group);
		}
		return ResponseEntity.notFound().build();
	}
	//////////////// delete (by manager) (in group) /////////////////
	// 刪除group
	@DeleteMapping("/group/delete")
	public ResponseEntity<ForumGroupDto> deleteGroup(@RequestParam Integer groupId) {
		Optional<ForumGroup> findResult = forumUserService.findGroupById(groupId);
		if(findResult.isPresent()) {
			boolean hasDelete = forumUserService.deleteGroup(findResult.get());
			return hasDelete ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}
	// 把user從group內刪除
	@DeleteMapping("/group/user/delete")
	public ResponseEntity<ForumGroupDto> deleteUserInGroup(@RequestParam Integer groupId,
														@RequestParam Integer userId) {
		Optional<ForumGroup> findResult = forumUserService.findGroupById(groupId);
		Optional<ForumUser> findUserResult = forumUserService.findById(userId);
		if(findResult.isPresent() && findUserResult.isPresent()) {
			boolean hasDelete = forumUserService.deleteUserInGroup(findResult.get(),findUserResult.get());
			return hasDelete ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}
//////////////////////////// filter by search ///////////////////////////////



}
