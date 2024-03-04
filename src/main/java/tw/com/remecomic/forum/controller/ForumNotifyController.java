package tw.com.remecomic.forum.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.dto.ForumNotifyDto;
import tw.com.remecomic.forum.service.ForumNotifyService;
import tw.com.remecomic.forum.service.ForumUserService;

@RestController
public class ForumNotifyController {
	
	@Autowired
	private ForumUserService userAService;
	@Autowired
	private ForumNotifyService notifyService;
	
//////////////////////////// show notify ///////////////////////////////
	
	//////////////// all (show notify) /////////////////
	@GetMapping("/notify/show") //read follow
	public ResponseEntity<List<ForumNotifyDto>> notifyShowAll(@RequestParam Integer userId) {
		Optional<ForumUser> findResult = userAService.findById(userId);
		if(findResult.isPresent()) {
			List<ForumNotifyDto> all = notifyService.getAllNotify(findResult.get());
			return ResponseEntity.ok(all);
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// by not read (show notify) /////////////////
	// 在 前端 透過 js 做篩選，不另寫controller
	
	//////////////// 及時在 active 時就跳出通知 (show notify) /////////////////
	
//////////////////////////// add notify ///////////////////////////////
	
	//////////////// remind user get report (add notify) /////////////////
	

//////////////////////////// delete notify ///////////////////////////////
	
	//////////////// have read ones only left week (delete notify) /////////////////
	
	//////////////// reference table not exit id (delete notify) /////////////////

}
