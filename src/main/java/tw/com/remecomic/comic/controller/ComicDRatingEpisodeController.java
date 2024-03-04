package tw.com.remecomic.comic.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.comic.model.bean.ComicDRatingEpisode;
import tw.com.remecomic.comic.model.dto.ComicDRatingEpisodeDto;
import tw.com.remecomic.comic.service.ComicDRatingEpisodeService;

@RestController
public class ComicDRatingEpisodeController {
	@Autowired
	ComicDRatingEpisodeService comicDRatingEpisodeService;
	
	@GetMapping("/comicD/episode/rating") //花費時間: ms
	public ResponseEntity<ComicDRatingEpisode> getEpisodeRatingById(@RequestParam Integer userId, @RequestParam Integer episodeId) {
		Optional<ComicDRatingEpisode> ratedComic = comicDRatingEpisodeService.findById(userId, episodeId);
		if(ratedComic.isPresent()) {
			return ResponseEntity.ok(ratedComic.get());
		}return ResponseEntity.notFound().build();		
		
	}	
	
	@GetMapping("/comicD/episode/simplerating") //花費時間: ms 
	public ResponseEntity<ComicDRatingEpisodeDto> getSimpleEpisodeRatingById(@RequestParam Integer userId, @RequestParam Integer episodeId) {
		ComicDRatingEpisodeDto ratedSimpleComic = comicDRatingEpisodeService.findSimpleEpisodeRatingById(userId, episodeId);
		if(ratedSimpleComic != null) {
			return ResponseEntity.ok(ratedSimpleComic);
		}return ResponseEntity.notFound().build();		
	}
	
	@GetMapping("/comicD/episode/ratings") //花費時間：ms
	public List<ComicDRatingEpisode> getEpisodeRatingAll() {
		return comicDRatingEpisodeService.findAll();
	}
	
	@GetMapping("/comicD/episode/simpleratings")  //花費時間：ms 優化
	public ResponseEntity<List<ComicDRatingEpisodeDto>> getSimpleComicRatingAll() {
		return ResponseEntity.ok(comicDRatingEpisodeService.findSimpleEpisodeRatingAll());
	}
	
//	@PostMapping("/comicD/episode/rating/save") //花費時間：ms 
//	public ResponseEntity<ComicDRatingEpisode> addEpisodeRating(@RequestBody Map<String,Object> ratingMap) {
//		return ResponseEntity.ok(comicDRatingEpisodeService.save(ratingMap));		
//	}
	
	@PostMapping("/comicD/episode/rating/like") //花費時間：ms 
	public ResponseEntity<?> saveLike(@RequestBody Map<String,Object> mapEpLike) {
		Integer userId = Integer.parseInt(mapEpLike.get("userId").toString());
		Integer episodeId = Integer.parseInt(mapEpLike.get("episodeId").toString());
		Integer episodeLike = Integer.parseInt(mapEpLike.get("episodeLike").toString());
		comicDRatingEpisodeService.saveLike(userId,episodeId,episodeLike);
		return ResponseEntity.ok().build();		
	}
	
	
	@DeleteMapping("/comicD/episode/rating/delete") 
	public void delete(Integer userId, Integer comicId) {
		comicDRatingEpisodeService.deleteById(userId, comicId);
	}


}
