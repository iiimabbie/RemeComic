package tw.com.remecomic.comic.controller;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import tw.com.remecomic.comic.model.bean.ComicD;
import tw.com.remecomic.comic.model.bean.ComicDComicConGenres;
import tw.com.remecomic.comic.model.bean.ComicDGenres;
import tw.com.remecomic.comic.model.dto.ComicDDto;
import tw.com.remecomic.comic.model.dto.ComicDEpisodeUpdateDto;
import tw.com.remecomic.comic.model.dto.ComicDEpisodesDto;
import tw.com.remecomic.comic.model.dto.ComicDSummariesDto;
import tw.com.remecomic.comic.service.ComicDComicConGenresService;
import tw.com.remecomic.comic.service.ComicDGenresService;
import tw.com.remecomic.comic.service.ComicDService;
import tw.com.remecomic.util.PageUtil;

@RestController
public class ComicDController {
	@Autowired
	ComicDService comicDService;
	
	@Autowired
	ComicDGenresService comicDGenresService;
	
	@Autowired
	ComicDComicConGenresService comicDComicConGenresService;
	
	
	@GetMapping("/comicD/comic") //花費時間:218m
	public ResponseEntity<ComicD> getComicById(@RequestParam Integer comicId) {
		Optional<ComicD> comicSearch = comicDService.findById(comicId);
		if(comicSearch.isPresent()) {
			return ResponseEntity.ok(comicSearch.get());
		}return ResponseEntity.notFound().build();		
		
	}
	
	@GetMapping("/comicD/simplecomic") //花費時間:88ms test
	public ResponseEntity<ComicDDto> getSimpleComicById(@RequestParam Integer comicId) {
		ComicDDto comicSearch = comicDService.findSimpleComicById(comicId);
		if(comicSearch != null) {
			return ResponseEntity.ok(comicSearch);
		}return ResponseEntity.notFound().build();		
	}
	
	
	@GetMapping("/comicD/comic/calculated") //花費時間:88ms
	public ResponseEntity<ComicDSummariesDto> getCalculatedDataByComicById(@RequestParam Integer comicId) {
		ComicDSummariesDto comicSearch = comicDService.findCalculatedComicById(comicId);
		if(comicSearch != null) {
			return ResponseEntity.ok(comicSearch);
		}return ResponseEntity.notFound().build();		
	}
	
	@GetMapping("/comicD/comics") //花費時間：47.88s
	public ResponseEntity<List<ComicD>> getAllComic() {
		return ResponseEntity.ok(comicDService.findAll());
	}
	
	@GetMapping("/comicD/simplecomics")  //花費時間：130ms優化
	public ResponseEntity<List<ComicDDto>> getSimpleComicAll() {
		return ResponseEntity.ok(comicDService.findSimpleComicAll());
	}
	
	@GetMapping("/comicD/calculatedcomics")  //花費時間：
	public ResponseEntity<List<ComicDDto>> getCalculatedComicAll() {
		return ResponseEntity.ok(comicDService.findCalculatedComicAll());
	}
	
	@GetMapping("/comicD/comics/episodes")  //花費時間：220ms
	public ResponseEntity <Map<String,List<?>>> findComicEpsiodeByPage(@RequestParam Integer p) {
		return ResponseEntity.ok(comicDService.findComicEpisodeByPage(p));
	}
	@PostMapping("/comicD/comics/episodes/search")  //花費時間：220ms
	public ResponseEntity <Map<String,List<?>>> findComicEpsiodeByPage(@RequestBody Map<String,Object> comicIdsPage) {
		return ResponseEntity.ok(comicDService.findComicEpisodeBySearch(comicIdsPage));
	}
	
	@GetMapping("/comicD/comics/episodes/expand")  //花費時間：207ms
	public ResponseEntity<List<ComicDEpisodeUpdateDto>> getComicEpisodeExpandByPage(@RequestParam Integer p) {
		return ResponseEntity.ok(comicDService.findComicEpsiodeExpandByPage(p));
	}
	
	
	@GetMapping("/comicD/calculatedcomics/fast")  //花費時間：170ms 優化
	public ResponseEntity<List<ComicDSummariesDto>> getCalculatedComicAllFast() {
		return ResponseEntity.ok(comicDService.findCalculatedComicAllFast());
	}
	
	@PostMapping("/comicD/calculatedcomics/search")  //花費時間：
	public ResponseEntity<Map<String,Object>> findCalculatedComicAllFastCrossColumn(@RequestBody Map<String,Object> search) {
		String searchTerm = (String)search.get("searchTerm");
		Map<String,Object>result = null;
		String cleanSearchTerm = searchTerm.trim();
		if(cleanSearchTerm.length()>=2 && "=>".equals(cleanSearchTerm.substring(0,2))) {
			if(">".equals(cleanSearchTerm.trim().charAt(0))) {
				
			}else if("<".equals(cleanSearchTerm.trim().charAt(0))) {
				
			}else {
				
			}
		}else {
			
			result = comicDService.findCalculatedComicCrossColumn(search);
		}	
		
		if(result != null) {
			return ResponseEntity.ok(result);
		}return ResponseEntity.notFound().build();
		
	}
	
	
	//如果資料完全一樣（包含null)會進行修改，如果有一個資料不一樣會自動新增
	@PostMapping(value="/comicD/comic/save") //花費時間：274ms
	public ResponseEntity<?> addOrUpdate(@RequestBody ComicDDto comicDDto){
		ComicD comicD = new ComicD(
				comicDDto.getComicTitle(),
				comicDDto.getCreator(),
				comicDDto.getComicDescription(),
				comicDDto.getComicCover(),
				comicDDto.getUpdateDay(),
				comicDDto.getPublishDate(),
				comicDDto.getEditorChoice(),
				comicDDto.getPurchasePrice(),
				comicDDto.getRentalPrice()
				);
		ComicD saveComic = comicDService.save(comicD);
		Integer comicId = saveComic.getComicId();
		URI addedComicUri = URI.create("http://localhost:8080/remecomic/comicD/comics?comicId=" + comicId);
		return ResponseEntity.created(addedComicUri).body(saveComic);
		
	}
	
	//單獨新增，後端防呆，一定要拿comicId才可以新增
	@PutMapping(value="/comicD/comic/update") //花費時間：274ms
	public ResponseEntity<ComicD> update(@RequestParam Integer comicId, @RequestBody ComicD comicD) {
		Optional<ComicD> comic = comicDService.findById(comicId);
		if(!comic.isPresent()) {
			return ResponseEntity.notFound().build();
		}	
		comicD.setComicId(comicId);
		return ResponseEntity.ok(comicDService.save(comicD));
	}
	
	
	@PutMapping(value="/comicD/comic/update/multiple") //花費時間：274ms
	public ResponseEntity<List<Map<String,Object>>> update(@RequestBody List<Map<String, Object>> updateComics) throws ParseException {
		if(updateComics!= null && !updateComics.isEmpty()) {		
			return ResponseEntity.ok(comicDService.updateComicDByColumnName(updateComics));
		}return ResponseEntity.badRequest().build();	
	}
		
	@DeleteMapping(value="/comicD/comic/delete") //花費時間: 267ms
	public ResponseEntity<?> delete(Integer comicId) {
		Optional<ComicD> comic = comicDService.findById(comicId);
		if(!comic.isPresent()) {
			return ResponseEntity.notFound().build();
		}		
		comicDService.deleteById(comicId);
		return ResponseEntity.noContent().build();
	}
	
	//由comicId得到genreId,genreName,list of associatedComics with the same genre
	@GetMapping("/comicD/comic/genre") //花費時間: 580ms
	public ResponseEntity<List<ComicDGenres>> test(Integer comicId) {
		List<ComicDGenres> genreList = new ArrayList<>();
		for (ComicDComicConGenres item : comicDGenresService.findByComicId(comicId)) {
			genreList.add(item.getGenre());
		}return ResponseEntity.ok(genreList);			
	}
	
//	@GetMapping("/setSession")
//	public ResponseEntity<?> setSession(HttpSession httpSession) {
//	    httpSession.setAttribute("loginUser", "FanTuan");
//	    return ResponseEntity.ok(httpSession.getId());
//	}
	
	@GetMapping("/validateSession")
	public ResponseEntity<?> validateSession(HttpSession httpSession) {
		String loginUser = (String) httpSession.getAttribute("loginUser");
		if(loginUser == null){
	           System.out.println("session attribute 空的");
	           return new ResponseEntity<String>(httpSession.getId(), HttpStatus.UNAUTHORIZED); // 401
	       }
		return new ResponseEntity<String>("checkCookie", HttpStatus.OK); 
	}

}
	