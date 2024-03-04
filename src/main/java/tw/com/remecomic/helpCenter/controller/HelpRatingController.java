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

import tw.com.remecomic.helpCenter.model.bean.HelpRating;
import tw.com.remecomic.helpCenter.service.HelpRatingService;

@RestController
public class HelpRatingController {

	@Autowired
	private HelpRatingService ratingService;
	
	@GetMapping("/helpcenter/rating")
	public ResponseEntity<List<HelpRating>> getAllRating(){
		List<HelpRating> rating = ratingService.findAll();
		if(rating != null) {
			return ResponseEntity.ok(rating);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/helpcenter/rating/{categoryId}")
	public ResponseEntity<HelpRating> getRatingById(@PathVariable Integer ratingId) {
		Optional<HelpRating> rating = ratingService.findRatingById(ratingId);
		if (rating.isPresent()) {
			return ResponseEntity.ok(rating.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/helpcenter/rating/add")
	public ResponseEntity<HelpRating> addRating(@RequestBody HelpRating helpRating) {
		HelpRating add = ratingService.addRating(helpRating);
		if (add != null) {
			return ResponseEntity.ok(add);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/helpcenter/rating/delete")
	public ResponseEntity<HelpRating> deleteRating(@RequestParam Integer id) {
		boolean delete = ratingService.deleteById(id);
		if (delete) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
