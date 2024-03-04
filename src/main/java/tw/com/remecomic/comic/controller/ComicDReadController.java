package tw.com.remecomic.comic.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.comic.model.bean.ComicDRead;
import tw.com.remecomic.comic.service.ComicDReadService;

@RestController
public class ComicDReadController {

	@Autowired
	ComicDReadService comicDReadService;
	
	@PostMapping(value="/comicD/comic/read/save") //花費時間：
	public void save(@RequestBody Map<String,Object> mapRead){
		Integer userId = Integer.parseInt(mapRead.get("userId").toString());
		List<Integer> pageIds = (List<Integer>)mapRead.get("pageIds");
		List<ComicDRead> existReads = comicDReadService.findByUserIdAndPageIds(userId,pageIds);
		List<ComicDRead> reads = 
				pageIds.stream()
				.filter(pageId -> existReads.stream().noneMatch(read -> read.getPageId().equals(pageId)))
				.map(pageId -> new ComicDRead(userId,pageId,0))
				.collect(Collectors.toList());
		comicDReadService.saveAll(reads);
	}
	
	@PostMapping(value="/comicD/episode/read/bookmark") //花費時間：
	public ResponseEntity<ComicDRead> saveBookMark(@RequestBody Map<String,Object> mapRead){
		Integer userId = Integer.parseInt(mapRead.get("userId").toString());
		Integer pageId = Integer.parseInt(mapRead.get("pageId").toString());
		Integer isBookMarked = Integer.parseInt(mapRead.get("isBookMarked").toString());
		ComicDRead existRead =  comicDReadService.findByUserIdAndPageId(userId,pageId);
		ComicDRead resRead = null;
		if(existRead != null) {
			existRead.setIsBookMarked(isBookMarked);
			resRead = comicDReadService.save(existRead);
		}else {
		 ComicDRead newRead = new ComicDRead(userId,pageId,isBookMarked);
		 resRead = comicDReadService.save(newRead);
		}
		return ResponseEntity.ok(resRead);
	}

}
	