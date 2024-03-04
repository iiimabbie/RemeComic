package tw.com.remecomic.helpCenter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.helpCenter.model.bean.HelpQuestion;
import tw.com.remecomic.helpCenter.service.HelpQuestionService;

@RestController
public class HelpQuestionController {

	@Autowired
	private HelpQuestionService questionService;

	@GetMapping("/helpcenter/question/{helpId}")
	public ResponseEntity<HelpQuestion> getQuestionById(@PathVariable Integer helpId) {
		Optional<HelpQuestion> findQuestionById = questionService.findQuestionById(helpId);
		if (findQuestionById.isPresent()) {
			return ResponseEntity.ok(findQuestionById.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/helpcenter/question/category/{categoryId}")
	public ResponseEntity<List<HelpQuestion>> getQuestionByCategory(@PathVariable Integer categoryId) {
		List<HelpQuestion> findQuestionByCategory = questionService.findQuestionByCategory(categoryId);
		if (findQuestionByCategory != null && !findQuestionByCategory.isEmpty()) {
			return ResponseEntity.ok(findQuestionByCategory);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/helpcenter/allQuestion")
	public ResponseEntity<List<HelpQuestion>> getAllQuestion() {
		List<HelpQuestion> question = questionService.findAllQuestion();
		if (question != null) {
			return ResponseEntity.ok(question);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/helpcenter/question")
	public ResponseEntity<List<HelpQuestion>> getQuestionByWord(@RequestParam("q")String question){
		List<HelpQuestion> helpQuestion = questionService.findQuestionByWord(question);
		if(helpQuestion != null) {
			return ResponseEntity.ok(helpQuestion);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/helpcenter/question/add")
	public ResponseEntity<HelpQuestion> addQuestion(@RequestBody HelpQuestion helpQuestion) {
		HelpQuestion question = questionService.addQuestion(helpQuestion);
		if (question != null) {
			return ResponseEntity.ok(question);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/helpcenter/question/delete")
	public ResponseEntity<HelpQuestion> deleteQuestion(@RequestParam Integer id) {
		boolean delete = questionService.deleteQuestionById(id);
		if (delete) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/helpcenter/question/update")
	public ResponseEntity<HelpQuestion> updateQuestion(
			@RequestParam("id")Integer id,
			@RequestParam("category")Integer category,
			@RequestParam("question")String question,
			@RequestParam("answer")String answer) {
		HelpQuestion helpQuestion = new HelpQuestion(id, category, question, answer);
		HelpQuestion update = questionService.updateQuestion(id, helpQuestion);
		if(update != null) {
			return ResponseEntity.ok(update);
		}
		return ResponseEntity.notFound().build();
	}
}
