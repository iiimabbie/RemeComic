package tw.com.remecomic.comic.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.comic.model.bean.ComicDComment;
import tw.com.remecomic.comic.service.ComicDCommentService;

@RestController
public class ComicDCommentController {
	@Autowired
	ComicDCommentService comicDCommentService;
		
	@PostMapping("/comicD/page/comment") //花費時間：ms 
	public ResponseEntity<Map<String,Object>> save(@RequestBody Map<String,Object> mapPageComment) {
		Integer userId = Integer.parseInt(mapPageComment.get("userId").toString());
		Integer pageId = Integer.parseInt(mapPageComment.get("pageId").toString());
		String commentContent = (String)mapPageComment.get("commentContent");
		Date commentDate = new Date();
		Integer toUser = 
				mapPageComment.get("toUser") != null ?
				Integer.parseInt(mapPageComment.get("toUser").toString()) : 0;
		Integer toCommentId = 
				mapPageComment.get("toCommentId") != null ?
				Integer.parseInt(mapPageComment.get("toCommentId").toString()) : 0;
		Map<String,Object> comment = comicDCommentService.save(userId,pageId,commentContent,commentDate, toUser, toCommentId);
		return ResponseEntity.ok(comment);		
	}
	

}
