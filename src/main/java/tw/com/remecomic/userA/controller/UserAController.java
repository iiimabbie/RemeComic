package tw.com.remecomic.userA.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import tw.com.remecomic.money.service.MoneyAccountService;
import tw.com.remecomic.userA.model.bean.UserA;
import tw.com.remecomic.userA.model.dto.UserDto;
import tw.com.remecomic.userA.service.UserAService;

@RestController
public class UserAController {

	@Autowired
	private UserAService uService;

	@Autowired
	private MoneyAccountService moneyAccountService;

	@GetMapping("/user/user/{id}")
	public ResponseEntity<UserA> findById(@PathVariable Integer id) {
		Optional<UserA> userOptional = uService.findById(id);
		if (userOptional.isPresent()) {
			return ResponseEntity.ok(userOptional.get());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/user/simpleuser/{id}")
	public ResponseEntity<UserDto> findSimpleUserById(@PathVariable Integer id) {
		UserDto user = uService.findUserDtoById(id);
		if (user != null) {
			return ResponseEntity.ok(user);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/user/users")
	public ResponseEntity<List<UserA>> findAll() {

		return ResponseEntity.ok(uService.findAll());
	}

	@GetMapping("/user/simpleusers")
	public ResponseEntity<List<UserDto>> findSimpleUserAll(HttpSession httpSession) {

		System.out.println("===========================================================");
		System.out.println(httpSession.getId());
		System.out.println(httpSession.getAttribute("loginUser"));
		System.out.println(httpSession.getAttribute("loginUser"));
		System.out.println(httpSession.getAttribute("loginUserWithUserId"));
		System.out.println(httpSession.getAttribute("loginUserId"));
		System.out.println(httpSession.getAttribute("loginUserEmail"));

		return ResponseEntity.ok(uService.findUserDtoAll());
	}

	@GetMapping("/user/usernamelike")
	public List<UserA> findUserByNameLike(@RequestParam String keyWord) {
		return uService.findUserByNameLike(keyWord);
	}

	@GetMapping("/user/badges")
	public List<UserA> findUsersWithBadges(@RequestParam List<Integer> bIds) {
		return uService.findUsersWithBadges(bIds);
	}

	// 新增用户
	@PostMapping("/user/add")
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userSimpleDto) {
		UserDto addedUser = uService.addUser(userSimpleDto);
		if (addedUser != null) {
			moneyAccountService.createAccount(addedUser.getUserId());

			return ResponseEntity.ok(addedUser);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 新增用户和帳號
	@PostMapping("/user/add2")
	public ResponseEntity<UserDto> addUser2(@RequestBody UserDto userSimpleDto) {
		UserDto addedUser = uService.addUser(userSimpleDto);

		if (addedUser != null) {
			moneyAccountService.createAccount(addedUser.getUserId());

			return ResponseEntity.ok(addedUser);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 更新用户
	@PutMapping("/user/update")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userSimpleDto) {
		UserDto updatedUser = uService.updateUserSimple(userSimpleDto);
		if (updatedUser != null) {
			return ResponseEntity.ok(updatedUser);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

//	被外鍵約束了目前刪不掉user o3o
	@DeleteMapping("/user/user/delete")
	public ResponseEntity<?> delete(Integer userId) {
		Optional<UserA> user = uService.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		uService.deleteById(userId);
		return ResponseEntity.noContent().build();
	}

}
