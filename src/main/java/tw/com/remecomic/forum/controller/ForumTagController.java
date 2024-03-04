package tw.com.remecomic.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.forum.model.bean.ForumTag;
import tw.com.remecomic.forum.model.dto.ForumTagDto;
import tw.com.remecomic.forum.model.dto.ForumTagDto2;
import tw.com.remecomic.forum.service.ForumTagService;

@RestController
public class ForumTagController {
	@Autowired
	private ForumTagService tagService;
	
//////////////////////////// show(hot) ///////////////////////////////
	
	//////////////// today ( show(hot) ) ->periodNumber=1 /////////////////
	
	//////////////// week ( show(hot) ) ->periodNumber=7 /////////////////
	
	//////////////// month ( show(hot) ) ->periodNumber=30 /////////////////
	@GetMapping("/tag/show/hot") //read tag
	public ResponseEntity<List<ForumTagDto2>> tagShowHotInTimePeriod(@RequestParam(defaultValue = "1") Integer periodNumber) {
		System.out.println("periodNumber="+periodNumber);
		List<ForumTagDto2> allTags = tagService.getHotTagsInTimePeriod(periodNumber);
		return allTags!=null?ResponseEntity.ok(allTags):ResponseEntity.notFound().build();
	}
	//////////////// show(search+hot) /////////////////
	@GetMapping("/tag/show/search/addInPost") //read tag
	public ResponseEntity<List<ForumTagDto2>> tagShowBySearch(@RequestParam(value="searchString") String searchString) {
		System.out.println("searchString="+searchString);
		List<ForumTagDto2> allTags = tagService.getTagsBySearchSortByHot(searchString);
		return allTags!=null?ResponseEntity.ok(allTags):ResponseEntity.notFound().build();
	}
	
	
	@GetMapping("/tag/show") //read tag
	public ResponseEntity<List<ForumTagDto>> tagShowAll() {
		List<ForumTagDto> allTags = tagService.getAllTags();
		if(allTags!=null) {
			return ResponseEntity.ok(allTags);
		}
		return ResponseEntity.notFound().build();
	}
	@PostMapping("/tag/add") //必要參數： tagName
	public ResponseEntity<ForumTag> tagAddOne(@RequestBody ForumTag oneTag){
		ForumTag saveTag = tagService.addOneTag(oneTag);
		if(saveTag!=null) {
			return ResponseEntity.ok(saveTag);			
		}
		return ResponseEntity.notFound().build();
	}
}
