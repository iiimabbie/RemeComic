package tw.com.remecomic.comic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.comic.model.dto.ComicDEpisodeBodyDto;
import tw.com.remecomic.comic.service.ComicDEpisodeBodyService;

@RestController
public class ComicDEpisodeBodyController {
	@Autowired
	ComicDEpisodeBodyService comicDEpisodeBodyService;
	
	@GetMapping("/comicD/episode/body")
	public List<ComicDEpisodeBodyDto> getBodyWithCoommentsByEpisodeId (@RequestParam Integer episodeId, Integer userId){
		return comicDEpisodeBodyService.findBodyWithCommentsByEpisodeId(episodeId, userId);
	}
	
	

}
