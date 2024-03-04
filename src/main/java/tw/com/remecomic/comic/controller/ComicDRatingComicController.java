package tw.com.remecomic.comic.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.comic.model.bean.ComicD;
import tw.com.remecomic.comic.model.bean.ComicDEpisodeUpdate;
import tw.com.remecomic.comic.model.bean.ComicDRatingComic;
import tw.com.remecomic.comic.model.dto.ComicDRatingComicDto;
import tw.com.remecomic.comic.service.ComicDRatingComicService;
import tw.com.remecomic.money.model.bean.MoneyOrders;

@RestController
public class ComicDRatingComicController {
	@Autowired
	ComicDRatingComicService comicDRatingComicService;
	
	@GetMapping("/comicD/comic/rating") //花費時間: 58ms
	public ResponseEntity<ComicDRatingComic> getComicRatingById(@RequestParam Integer userId, @RequestParam Integer comicId) {
		Optional<ComicDRatingComic> ratedComic = comicDRatingComicService.findById(userId, comicId);
		if(ratedComic.isPresent()) {
			return ResponseEntity.ok(ratedComic.get());
		}return ResponseEntity.notFound().build();		
		
	}

	@GetMapping("/comicD/comicRating/findByUser")
	public ResponseEntity<List<ComicDRatingComic>> getComicRatingByUserId(@RequestParam Integer userId) {
		List<ComicDRatingComic> ratingComicList = comicDRatingComicService.findByUserId(userId);

		if(ratingComicList != null) {
			return ResponseEntity.ok(ratingComicList);
		}else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/comicD/comicRating/findByPage")
	public ResponseEntity<Page<ComicDRatingComic>> findUserComicByPage(
			@RequestParam(name = "p", defaultValue = "1") Integer pageNum,
			@RequestParam("userId") Integer userId) {
		Page<ComicDRatingComic> userComicByPage = comicDRatingComicService.findUserComicByPage(userId, pageNum);

		if (userComicByPage.hasContent()) {
			return ResponseEntity.ok(userComicByPage);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/comicD/comic/simplerating") //花費時間: 60ms //較後面的資料花較久的時間搜尋 
	public ResponseEntity<ComicDRatingComicDto> getSimpleComicRatingById(@RequestParam Integer userId, @RequestParam Integer comicId) {
		ComicDRatingComicDto ratedSimpleComic = comicDRatingComicService.findSimpleComicRatingById(userId, comicId);
		if(ratedSimpleComic != null) {
			return ResponseEntity.ok(ratedSimpleComic);
		}return ResponseEntity.notFound().build();		
	}
	
	@GetMapping("/comicD/comic/ratings") //花費時間：920ms
	public List<ComicDRatingComic> getComicRatingAll() {
		return comicDRatingComicService.findAll();
	}
	
	@GetMapping("/comicD/comic/simpleratings")  //花費時間：700ms 優化
	public ResponseEntity<List<ComicDRatingComicDto>> getSimpleComicRatingAll() {
		return ResponseEntity.ok(comicDRatingComicService.findSimpleComicRatingAll());
	}
	
	@PostMapping(value="/comicD/comic/rating/save", consumes = "application/json") //花費時間：222ms 
	public ResponseEntity<ComicDRatingComic> addComicRating(@RequestBody ComicDRatingComicDto ratingComic) {
		ComicDRatingComic ratingComicNew = new ComicDRatingComic(
				ratingComic.getUserId(),
				ratingComic.getComicId(),
				ratingComic.getLike()	
				);
		return ResponseEntity.ok(comicDRatingComicService.save(ratingComicNew));		
	}
	
	
	//或許不需要修改評論的功能
	@PutMapping("/comicD/comic/rating/update") 
	public ResponseEntity<ComicDRatingComic> updateComicRating(
			Integer userId, Integer comicId,
			@RequestBody ComicDRatingComic comicRating) {
		Optional<ComicDRatingComic> checkEpisode = comicDRatingComicService.findById(userId, comicId);
		if(!checkEpisode.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		comicRating.setUserId(userId);
		comicRating.setComicId(comicId);
		return ResponseEntity.ok(comicDRatingComicService.save(comicRating));
	}
	
	@DeleteMapping("/comicD/comic/rating/delete") 
	public void delete(Integer userId, Integer comicId) {
		comicDRatingComicService.deleteById(userId, comicId);
	}

}
