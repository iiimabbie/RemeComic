package tw.com.remecomic.forum.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.forum.model.bean.ForumGroup;
import tw.com.remecomic.forum.model.bean.ForumMessage;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.dto.ForumMessageDto;
import tw.com.remecomic.forum.service.ForumMessageService;
import tw.com.remecomic.forum.service.ForumUserService;

@RestController
public class ForumMessageController {
	
	@Autowired
	private ForumMessageService msgService;
	@Autowired
	private ForumUserService forumUserService;
	@Autowired
	private ForumUserService userAService;
	
//////////////////////////// show message ///////////////////////////////
	
	//////////////// in group (show message) /////////////////
	@GetMapping("/msg/group/show") //read follow
	public ResponseEntity<PageImpl<ForumMessageDto>> messageShowGroup(@RequestParam Integer groupId,
												@RequestParam(defaultValue = "1",required = false) Integer pageNumber) {
		Optional<ForumGroup> findResult = forumUserService.findGroupById(groupId);
		if(findResult.isPresent()) {
			PageImpl<ForumMessageDto> pageImpl = msgService.getMsgInGroupByPage(findResult.get(),pageNumber);
			return pageImpl!=null ? ResponseEntity.ok(pageImpl) : ResponseEntity.notFound().build();				
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// one by one (show message) /////////////////
	@GetMapping("/msg/individual/show") //read follow
	public ResponseEntity<PageImpl<ForumMessageDto>> messageShowIndividual(@RequestParam Integer logUserId,
																		@RequestParam Integer chatWithUserId,
												@RequestParam(defaultValue = "1",required = false) Integer pageNumber) {
		Optional<ForumUser> findLogResult = userAService.findById(logUserId);
		Optional<ForumUser> findChatWithResult = userAService.findById(chatWithUserId);
		if(findLogResult.isPresent() && findChatWithResult.isPresent()) {
			PageImpl<ForumMessageDto> pageImpl = msgService.getMsgIndividualByPage(findLogResult.get(),findChatWithResult.get(),pageNumber);
			return pageImpl!=null ? ResponseEntity.ok(pageImpl) : ResponseEntity.notFound().build();				
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// show 每個聊天窗口 (one+one/group) 的最新內容 (show message) /////////////////
	
//////////////////////////// add message ///////////////////////////////
	
	//////////////// in group (add message) /////////////////
	@PostMapping("/msg/group/add") //read follow
	public ResponseEntity<ForumMessageDto> messageAddGroup(@RequestParam Integer userId,
							@RequestParam Integer groupId,@RequestParam String content) {
		Optional<ForumUser> findLogResult = userAService.findById(userId);
		Optional<ForumGroup> findResult = forumUserService.findGroupById(groupId);
		if(findLogResult.isPresent() && findResult.isPresent()) {
			ForumMessageDto save = msgService.addMsgInGroup(findLogResult.get(),findResult.get(),content);
			return save!=null ? ResponseEntity.ok(save) : ResponseEntity.notFound().build();				
		}
		return ResponseEntity.notFound().build();
	}
	
	//////////////// one by one (add message) /////////////////
	@PostMapping("/msg/individual/add") //read follow
	public ResponseEntity<ForumMessageDto> messageAddIndividual(@RequestParam Integer userId,
							@RequestParam Integer acceptUserId,@RequestParam String content) {
		Optional<ForumUser> findLogResult = userAService.findById(userId);
		Optional<ForumUser> findAcceptResult = userAService.findById(acceptUserId);
		if(findLogResult.isPresent() && findAcceptResult.isPresent()) {
			ForumMessageDto save = msgService.addMsgIndividual(findLogResult.get(),findAcceptResult.get(),content);
			return save!=null ? ResponseEntity.ok(save) : ResponseEntity.notFound().build();				
		}
		return ResponseEntity.notFound().build();
	}
	
//////////////////////////// delete message ///////////////////////////////
	
	//////////////// in 24 hours (delete message) /////////////////
	@DeleteMapping("/msg/delete") //read follow
	public ResponseEntity<PageImpl<ForumMessageDto>> messageDelete(@RequestParam Integer msgId){
		Optional<ForumMessage> findLogResult = msgService.findById(msgId);
		if(findLogResult.isPresent()) {
			boolean hasDelete = msgService.deleteMsgById(findLogResult.get());
			return hasDelete ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();				
		}
		return ResponseEntity.notFound().build();
	}
	
}
