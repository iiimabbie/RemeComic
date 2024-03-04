package tw.com.remecomic.comic.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.comic.model.bean.ComicDEpisodeUpdate;
import tw.com.remecomic.comic.model.dto.ComicDEpisodeUpdateDto;
import tw.com.remecomic.comic.model.dto.ComicDEpisodesDto;
import tw.com.remecomic.comic.model.dto.ComicDReadDto;
import tw.com.remecomic.comic.service.ComicDEpisodeUpdateService;
import tw.com.remecomic.comic.service.ComicDRatingEpisodeService;
import tw.com.remecomic.comic.service.ComicDReadService;
import tw.com.remecomic.comic.service.ComicDService;

@RestController
public class ComicDEpisodeUpdateController {
    @Autowired
    ComicDEpisodeUpdateService comicDEpisodeUpdateService;

    @Autowired
    ComicDService comicDService;
    
    @Autowired
    ComicDReadService comicDReadService;
    
    @Autowired
    ComicDRatingEpisodeService comicDRatingEpisodeService;

    @GetMapping("/comicD/episode") //花費時間: 200ms
    public ResponseEntity<ComicDEpisodeUpdate> getEpisodeById(@RequestParam Integer episodeId) {
        Optional<ComicDEpisodeUpdate> episodeSearch = comicDEpisodeUpdateService.findById(episodeId);
        if (episodeSearch.isPresent()) {
            return ResponseEntity.ok(episodeSearch.get());
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/comicD/simpleepisode")
    public ResponseEntity<ComicDEpisodeUpdateDto> getSimpleEpisodeById(@RequestParam Integer episodeId) {
        ComicDEpisodeUpdateDto episodeSearch = comicDEpisodeUpdateService.findSimpleEpisodeById(episodeId);
        if(episodeSearch != null) {
            return ResponseEntity.ok(episodeSearch);
        }return ResponseEntity.notFound().build();

    }
    
    @GetMapping("/comicD/comic/simpleEpisode")
    public ResponseEntity<List<ComicDEpisodeUpdateDto>> getBasicAndCommentByComicId(@RequestParam Integer comicId) {
    	List<ComicDEpisodeUpdateDto> episodeDtos = comicDEpisodeUpdateService.findBasicAndCommentByComicId(comicId);
        if(!episodeDtos.isEmpty()) {
            return ResponseEntity.ok(episodeDtos);
        }return ResponseEntity.notFound().build();

    }
    
    @GetMapping("/comicD/episode/user/data")
    public  ResponseEntity<Map<String,List<?>>> getRatingAndOrderAndReadByUserId(@RequestParam Integer comicId, Integer userId) {
    	System.out.println(comicId + userId );
    	Map<String,List<?>> userData = new HashMap<>();
    	
    	List<ComicDReadDto> readDtos = comicDReadService.findReadPageByUserId(comicId, userId);
    	userData.put("userReads", readDtos);
    	userData.put("userOrders", comicDEpisodeUpdateService.findOrdersByUserId(comicId, userId));
    	userData.put("userLikes", comicDRatingEpisodeService.findRatingsByUserId(comicId, userId));
    	return ResponseEntity.ok(userData);
//        if(episodeDtos != null) {
//            return ResponseEntity.ok(episodeDtos);
//        }return ResponseEntity.notFound().build();

    }
  
    

    @GetMapping("/comicD/episodes") //花費時間: > 35s
    public ResponseEntity<List<ComicDEpisodeUpdate>> getAllEpisode() {
        List<ComicDEpisodeUpdate> episodeSearchList = comicDEpisodeUpdateService.findAll();
        if (episodeSearchList != null && !episodeSearchList.isEmpty()) {
            return ResponseEntity.ok(episodeSearchList);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/comicD/simpleepisodes") //花費時間:425ms 優化
    public ResponseEntity<List<ComicDEpisodesDto>> getAllSimpleEpisdoes() {
        List<ComicDEpisodesDto> simpleEpisodeSearchList = comicDEpisodeUpdateService.findSimpleEpisodeAll();
        if (simpleEpisodeSearchList != null && !simpleEpisodeSearchList.isEmpty()) {
            return ResponseEntity.ok(simpleEpisodeSearchList);
        }
        return ResponseEntity.notFound().build();
    }


    //只能新增
    @PostMapping("/comicD/episode/save")
    public ResponseEntity<?> addEpisode(@RequestBody ComicDEpisodeUpdate episode) {
        System.out.println(episode.getComicId());
        if (episode.getComicId() != null) {
            ComicDEpisodeUpdate saveEpisode = comicDEpisodeUpdateService.save(episode);
            return ResponseEntity.ok().body(saveEpisode);
        }
        return ResponseEntity.badRequest().body("comicId cannot be null");
        //Integer episodeId = saveEpisode.getComicId();
        //URI addedEpisodeUri = URI.create("http://localhost:8080/remecomic/comicD/episode?episodeId=" + episodeId);

    }

    //單獨新增，後端防呆，一定要拿comicId才可以新增
    //需要加入episodeId與episode的epId驗證
    @PutMapping("/comicD/episode/update")
    public ResponseEntity<ComicDEpisodeUpdate> updateComicByComicD(@RequestParam Integer episodeId, @RequestBody ComicDEpisodeUpdate episode) {
        Optional<ComicDEpisodeUpdate> checkEpisode = comicDEpisodeUpdateService.findById(episodeId);
        if (!checkEpisode.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        episode.setEpisodeId(episodeId);
        return ResponseEntity.ok(comicDEpisodeUpdateService.save(episode));
    }

    @PutMapping("/comicD/episode/update/multiple")
    public ResponseEntity<Integer> update(@RequestBody List<Map<String, Object>> updateEpisodes) throws ParseException {
        if (updateEpisodes != null && !updateEpisodes.isEmpty()) {
            return ResponseEntity.ok(comicDEpisodeUpdateService.updateEpisodeByColumnName(updateEpisodes));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/comicD/episode/delete")
    public void deleteComicByComicId(Integer episodeId) {
        comicDEpisodeUpdateService.deleteById(episodeId);
    }


}
