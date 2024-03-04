package tw.com.remecomic.comic.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.comic.service.ComicDUserPreferencesService;

@RestController
public class ComicDUserPreferencesController {

	@Autowired
	ComicDUserPreferencesService comicDUserPreferencesService;
	
	@GetMapping(value="/comicD/comic/userPreference") //花費時間：
	public void updateData(){
		comicDUserPreferencesService.findDataForMyPerference();
	}
	
	@GetMapping(value="/comicD/comic/userPreference/cluster") //花費時間：
	public void getCluster() throws Exception{
		comicDUserPreferencesService.kmeanCalculateCluster();
		
	}
	
	@GetMapping(value="/comicD/comic/userPreference/getRecommandation") //花費時間：
	public void getRecommandation() throws Exception{
		comicDUserPreferencesService.getRecommandation();
		
	}
	
	@GetMapping(value="/comicD/comic/userPreference/user") //花費時間：
	public List<Map<String,Object>>  getRecommandation(@RequestParam Integer userId) throws Exception{
		comicDUserPreferencesService.kmeanCalculateCluster();
		return comicDUserPreferencesService.getNewUserCluster(userId);
		
	}
}
	
	