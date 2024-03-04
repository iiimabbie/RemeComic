package tw.com.remecomic.comic.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import tw.com.remecomic.comic.model.bean.ComicD;
import tw.com.remecomic.comic.model.bean.ComicDDrafts;
import tw.com.remecomic.comic.model.dto.ComicDDraftsDto;
import tw.com.remecomic.comic.service.ComicDComicConGenresService;
import tw.com.remecomic.comic.service.ComicDDraftsService;
import tw.com.remecomic.comic.service.ComicDGenresService;
import tw.com.remecomic.comic.service.ComicDService;

@RestController
public class ComicDDraftsController {
	@Autowired
	ComicDService comicDService;
	
	@Autowired
	ComicDGenresService comicDGenresService;
	
	@Autowired
	ComicDComicConGenresService comicDComicConGenresService;
	
	@Autowired 
	ComicDDraftsService comicDDraftsService;
	
	@Value("${comicD.user.admin}")
	private String comicDAdminId;
	
	
	@PostMapping("/comicD/comic/drafts")
	public ResponseEntity<Map<String,Object>> getComicDraftsAll(@RequestParam Integer p) {
		Map<String,Object> comicDraft = comicDDraftsService.findAll(p);
			if(comicDraft != null) {
				return ResponseEntity.ok(comicDraft);
			}return ResponseEntity.notFound().build();			
	}
	
	@GetMapping("/comicD/comic/draft")
	public ResponseEntity<ComicDDraftsDto> getComicDraftByDraftId(@RequestParam Integer draftId) {
		ComicDDraftsDto draftDto = comicDDraftsService.findByIdWithGenreNames(draftId);
		if(draftDto != null) {
			return ResponseEntity.ok(draftDto);
		}return ResponseEntity.notFound().build();		
	}
	
	
//	@PostMapping("/comicD/comic/drafts/update")
//	public ResponseEntity<ComicDDrafts> updateComicDraft(@RequestBody ComicDDrafts comicDraft) {
//		return ResponseEntity.ok(comicDDraftsService.comicDraftUpdate(comicDraft));
//	}
	
	@PostMapping("/comicD/comic/drafts/update")
	public ResponseEntity<ComicDDraftsDto> updateComicGenreDraft(@RequestBody Map<String, ?> comicDraftMap, HttpSession httpSession) {
		System.out.println(httpSession.getAttribute("loginUser"));
		System.out.println(httpSession.getAttribute("loginUserWithUserId"));	
		System.out.println("Id------------"+httpSession.getId());			
		
		Integer userId = (Integer)httpSession.getAttribute("loginUserId");
		if(userId == Integer.valueOf(comicDAdminId)) {
			LinkedHashMap<String,Object> draftMap = (LinkedHashMap<String,Object>)comicDraftMap.get("draftData");
			ComicDDraftsDto comicDDraftsDto = new ComicDDraftsDto();
			ComicDDrafts comicDraft = comicDDraftsDto.convertMapToComicDDrafts(draftMap);
			ComicDDrafts updatedDraft = comicDDraftsService.comicDraftUpdate(comicDraft);
			Integer updatedDraftId = updatedDraft.getDraftId();
			List<Map<String,Object>> genreNameColors = (List<Map<String,Object>>)comicDraftMap.get("genreNameColors");
			ComicDDraftsDto draftDto = ComicDDraftsDto.toDto(updatedDraft);
			if(genreNameColors != null && !genreNameColors.isEmpty()) {				
				List <String> genreNames = comicDDraftsService.handleGenreUpdate(updatedDraftId,genreNameColors);
				draftDto.getGenres().addAll(genreNames);
				System.out.println("==========genreNames In draftDto============" + draftDto.getGenres());
			}return ResponseEntity.ok(draftDto);					
		}return ResponseEntity.status(HttpStatus.FORBIDDEN).build();	
	}
	
	@DeleteMapping("/comicD/comic/drafts/delete")
	public void updateComicGenreDraft(@RequestParam Integer draftId) {
		comicDDraftsService.deleteById(draftId);
		
	}
	
	@PostMapping("/comicD/comic/draft/publish")
	public ResponseEntity<ComicDDraftsDto> publishDraftToComic(@RequestBody LinkedHashMap<String,Object> map){
		ComicDDraftsDto comicRes = comicDDraftsService.publishDraftToComic(map);
		if( comicRes != null) {
			return ResponseEntity.ok(comicRes);
		}else {
			return ResponseEntity.badRequest().build();
		}
		
	}
	/*@PostMapping("/comicD/comic/drafts/publish")
	public ResponseEntity<List<ComicDDrafts>> publishDraftsToComics(@RequestBody List<LinkedHashMap<String,Object>> maps){
		return ResponseEntity.ok(comicDDraftsService.publishDraftsToComics(maps));
	}*/
	@GetMapping("/comicD/comic/drafts/unlock")
	public ResponseEntity<ComicDDraftsDto> unlockAndDuplicateDraft(@RequestParam Integer draftId){
		return ResponseEntity.ok(comicDDraftsService.unlockAndDuplicateDraft(draftId));
	}
	

}
	