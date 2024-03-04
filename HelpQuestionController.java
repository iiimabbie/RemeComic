package tw.com.remecomic.helpCenter.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.helpCenter.model.bean.HelpQuestion;
import tw.com.remecomic.helpCenter.service.HelpQuestionService;

@RestController
public class HelpQuestionController {

	@Autowired
	private HelpQuestionService questionService;
	
	@GetMapping("/helpcenter/question")
	public ResponseEntity<HelpQuestion> getQuestionById(@RequestParam Integer helpId){
		Optional<HelpQuestion> findQuestionById = questionService.findQuestionById(helpId);
		if(findQuestionById.isPresent()) {
			return ResponseEntity.ok(findQuestionById.get());
		}return ResponseEntity.notFound().build();
	}
	
}
