package tw.com.remecomic.comic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.comic.model.bean.ComicDComicConGenres;
import tw.com.remecomic.comic.model.bean.ComicDGenres;
import tw.com.remecomic.comic.model.dao.ComicDComicConGenresDao;
import tw.com.remecomic.comic.model.dao.ComicDGenresDao;

@Service
public class ComicDGenresService {
	@Autowired
	ComicDGenresDao comicDGenresDao;
	
	@Autowired
	ComicDComicConGenresDao comicDComicConGenresDao ;
	
	public List<Integer> findGenreIdByGenreNames(List<String> genreNames) {
		List<Integer> genreIds = new ArrayList<>();
		for (String name : genreNames) {
			String genreNameLower = name.toLowerCase();
			Integer genreId = comicDGenresDao.findGenreIdByGenreName(genreNameLower);	
			genreIds.add(genreId);	
		}return genreIds;		
	}
	
	public List<String> findGenreNameByGenreId(List<Integer> genreIds) {
		List<String> genreNames = new ArrayList<>();
		for (Integer id :genreIds) {
			genreNames.add(comicDGenresDao.findGenreNameByGenreId(id));
		}
		return genreNames;
	}
	
	
	public List<Integer> findComicIdByGenreIds(List<Integer> genreIds) {
		return comicDComicConGenresDao.findByGenreIds(genreIds);	
	}
	
	public List<Integer> findComicIdByGenreId(Integer genreId) {
		return comicDComicConGenresDao.findByGenreId(genreId);	
	}
	
	public List<ComicDComicConGenres> findByComicId(Integer comicId){
		return comicDComicConGenresDao.findByComicId(comicId);
	}
	
	public Integer findGenreIdByGenreName(String genreName) {
		String genreNameLower = genreName.toLowerCase();
		return comicDGenresDao.findGenreIdByGenreName(genreNameLower);	
	}
	
	public Optional<ComicDGenres> findByGenreName(String genreName) {
		String genreNameLower = genreName.toLowerCase();
		return comicDGenresDao.findByGenreName(genreNameLower);	
	}
	
	public void save(String genreName) {
		String genreNameLower = genreName.toLowerCase();
		comicDGenresDao.save(genreNameLower);	
	}
	
	public void delete(Integer genreId) {
		comicDGenresDao.deleteById(genreId);	
	}
	
	public void deleteByGenreName(String genreName) {
		String genreNameLower = genreName.toLowerCase();
		comicDGenresDao.deleteByGenreName(genreNameLower);	
	}

	public List<ComicDGenres> findAll() {
		return comicDGenresDao.findAll();
	}
	

	

}
