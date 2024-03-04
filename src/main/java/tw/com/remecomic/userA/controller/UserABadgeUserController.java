package tw.com.remecomic.userA.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.userA.model.bean.UserABadgeUser;
import tw.com.remecomic.userA.model.dto.UserDto;
import tw.com.remecomic.userA.service.UserABadgeUserService;

@RestController
public class UserABadgeUserController {

	@Autowired
	private UserABadgeUserService ubService;

//	@PutMapping("/{userId}/badges")
//	public UserDto updateUserBadges(@PathVariable int userId, @RequestBody List<UserABadge> updatedBadges) {
//		return ubService.updateUserBadges(userId, updatedBadges);
//	}

	@DeleteMapping("/user/delUserBadge")
	public void deleteBadge(@RequestParam("userId") Integer userId) {
		ubService.deleteUserBadge(userId);
	}

	@GetMapping("/user/findUserBadge")
	public List<UserABadgeUser> finduser(@Param("userId") Integer userId) {

		return ubService.findUserBadge(userId);
	}

	@PutMapping("/user/updateBadges")
	public UserDto test(@RequestBody Map<String, Object> userBadgeMap) {
		return ubService.test(userBadgeMap);

	}

}
