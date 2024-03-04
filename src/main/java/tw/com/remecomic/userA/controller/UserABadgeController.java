package tw.com.remecomic.userA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.userA.model.dto.BadgeSimpleDto;
import tw.com.remecomic.userA.service.UserABadgeService;

@RestController
public class UserABadgeController {

	@Autowired
	private UserABadgeService bService;

	@GetMapping("/badge/simplebadges")
	public ResponseEntity<List<BadgeSimpleDto>> findOnlyBadgeAll() {

		return ResponseEntity.ok(bService.findOnlyBadgeAll());
	}

//	@GetMapping("/badge/badge")
//	public ResponseEntity<UserABadge> findById(@RequestParam Integer id) {
//		if (bService.findById(id).isPresent()) {
//			return ResponseEntity.ok(bService.findById(id).get());
//		}
//		return ResponseEntity.notFound().build();
//	}
//
//	@GetMapping("/badge/badge/{id}")
//	public ResponseEntity<UserABadge> findById(@PathVariable Integer id) {
//		Optional<UserABadge> badgeOptional = bService.findById(id);
//		if (badgeOptional.isPresent()) {
//			return ResponseEntity.ok(badgeOptional.get());
//		}
//		return ResponseEntity.notFound().build();
//	}
//
//	@GetMapping("/badge/simplebadge")
//	public ResponseEntity<UserABadgeDto> findSimpleById(@RequestParam Integer id) {
//		if (bService.findSimpleBadgeById(id) != null) {
//			return ResponseEntity.ok(bService.findSimpleBadgeById(id));
//		}
//		return ResponseEntity.notFound().build();
//	}
//
//	@GetMapping("/badge/simplebadge/{id}")
//	public ResponseEntity<UserABadgeDto> findSimpleById(@PathVariable Integer id) {
//		UserABadgeDto badge = bService.findSimpleBadgeById(id);
//		if (badge != null) {
//			return ResponseEntity.ok(badge);
//		}
//		return ResponseEntity.notFound().build();
//	}
//
//	@GetMapping("/badge/badges")
//	public List<UserABadge> findAll() {
//		return bService.findAll();
//	}
//
//	@PutMapping(value = "/badge/badge/save")
//	public ResponseEntity<?> addOrUpdate(@RequestBody UserABadge userABadge) {
//		UserABadge saveBadge = bService.save(userABadge);
//		Integer badgeId = saveBadge.getBadgeId();
//		URI addedUserUri = URI.create("http://localhost:8080/remecomic/badge/badge?id=" + badgeId);
//		return ResponseEntity.created(addedUserUri).body(saveBadge);
//	}
//
//	基本上來說不會有刪除badge的行為，就不測了到時候會刪掉
//	@DeleteMapping(value = "/badge/badge/delete")
//	public ResponseEntity<?> delete(Integer badgeId) {
//		Optional<UserABadge> badge = bService.findById(badgeId);
//		if (!badge.isPresent()) {
//			return ResponseEntity.notFound().build();
//		}
//		bService.deleteById(badgeId);
//		return ResponseEntity.noContent().build();
//	}

}
