package tw.com.remecomic.comic.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.comic.model.bean.ComicDGenres;
import tw.com.remecomic.comic.model.dto.ComicDDto;
import tw.com.remecomic.comic.model.dto.ComicDSummariesDto;
import tw.com.remecomic.comic.service.ComicDGenresService;
import tw.com.remecomic.comic.service.ComicDService;

@RestController
@CrossOrigin(origins = "https://localhost:5173") 
public class ComicDGenresController {
	@Autowired
	ComicDService comicDService;
	
	@Autowired
	ComicDGenresService comicDGenresService;

	//由genreName得到ListofComicsDtos
	@GetMapping("/comicD/genre/names") //花費時間: 580ms
	public ResponseEntity<List<ComicDDto>> getComicByGenresName(@RequestParam List<String> genreNames) {
		List<Integer> genreIds = comicDGenresService.findGenreIdByGenreNames(genreNames);
		List<Integer> comicIds = new ArrayList<>();
		if(genreIds != null && !genreIds.isEmpty()) {
			for(Integer genreId: genreIds) {
				comicIds.addAll(comicDGenresService.findComicIdByGenreId(genreId));			
			}			
			if(!comicIds.isEmpty()) {
				List<ComicDDto> comicDDtos = 
					comicIds.stream()
					.map(comicDService::findSimpleComicById)
					.collect(Collectors.toList());		
				return ResponseEntity.ok(comicDDtos);
			}return	ResponseEntity.notFound().build();	
		}return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/comicD/genre/ids") //花費時間: 580ms
	public ResponseEntity<List<ComicDSummariesDto>> getComicByGenresId(@RequestParam List<Integer> genreIds) {
		if(genreIds != null && !genreIds.isEmpty()) {
			List<Integer> comicIds = comicDGenresService.findComicIdByGenreIds(genreIds);	
			if(!comicIds.isEmpty()) {
				List<ComicDSummariesDto>  comicDSums =  comicDService.findCalculatedComicByIds(comicIds);
				return ResponseEntity.ok(comicDSums);
			}
		}return ResponseEntity.notFound().build();
	}
	
	
	@GetMapping("/comicD/genre/save") //花費時間: 235ms
	public ResponseEntity<ComicDGenres> addGenre(@RequestParam String genreName) {
		Integer genreId = comicDGenresService.findGenreIdByGenreName(genreName);
		if(genreId == null) {
			comicDGenresService.save(genreName);
			ComicDGenres result = comicDGenresService.findByGenreName(genreName).get();
			return ResponseEntity.ok(result);
		}return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/comicD/genres")
	public ResponseEntity<List<ComicDGenres>> findAll() {
		return ResponseEntity.ok(comicDGenresService.findAll());
	}
		
	@DeleteMapping("/comicD/genre/delete") //花費時間: 111ms
	public ResponseEntity<?> deleteGenreById(@RequestParam Integer genreId) {
		comicDGenresService.delete(genreId);
		return ResponseEntity.noContent().build();
	}
	
	
	@DeleteMapping("/comicD/genre/delete/name") //花費時間: 163ms
	public ResponseEntity<?> deleteGenreByName(@RequestParam String genreName) {
		Integer genreId = comicDGenresService.findGenreIdByGenreName(genreName);
		if(genreId != null) {
			comicDGenresService.deleteByGenreName(genreName);
			return ResponseEntity.noContent().build();
		}return ResponseEntity.badRequest().build();
	}

}
