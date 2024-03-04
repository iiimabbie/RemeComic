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

import tw.com.remecomic.helpCenter.model.bean.HelpCategory;
import tw.com.remecomic.helpCenter.service.HelpCategoryService;

@RestController
public class HelpCategoryController {

	@Autowired
	private HelpCategoryService categoryService;
	
	@GetMapping("/helpcenter/category")
	public ResponseEntity<List<HelpCategory>> getAllCategory(){
		List<HelpCategory> category = categoryService.findAllCategory();
		if(category != null) {
			return ResponseEntity.ok(category);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/helpcenter/category/{categoryId}")
	public ResponseEntity<HelpCategory> getCategoryById(@PathVariable Integer categoryId) {
		Optional<HelpCategory> category = categoryService.findCategoryById(categoryId);
		if (category.isPresent()) {
			return ResponseEntity.ok(category.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/helpcenter/category/add")
	public ResponseEntity<HelpCategory> addCategory(@RequestBody HelpCategory helpCategory) {
		HelpCategory add = categoryService.addCategory(helpCategory);
		if (add != null) {
			return ResponseEntity.ok(add);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/helpcenter/category/delete")
	public ResponseEntity<HelpCategory> deleteCategory(@RequestParam Integer id) {
		boolean delete = categoryService.deleteCategoryById(id);
		if (delete) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
