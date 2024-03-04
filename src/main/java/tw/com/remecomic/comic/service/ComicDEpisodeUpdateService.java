package tw.com.remecomic.comic.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.comic.model.bean.ComicDEpisodeUpdate;
import tw.com.remecomic.comic.model.dao.ComicDDao;
import tw.com.remecomic.comic.model.dao.ComicDEpisodeBodyDao;
import tw.com.remecomic.comic.model.dao.ComicDEpisodeUpdateDao;
import tw.com.remecomic.comic.model.dao.ComicDRatingEpisodeDao;
import tw.com.remecomic.comic.model.dto.ComicDEpisodeUpdateDto;
import tw.com.remecomic.comic.model.dto.ComicDEpisodesDto;

@Service
public class ComicDEpisodeUpdateService {
	@Autowired
	ComicDEpisodeUpdateDao comicDEpisodeUpdateDao;
	
	@Autowired
	ComicDDao comicDDao;
	
	@Autowired
	ComicDRatingEpisodeDao comicDRatingEpisodeDao;
	
	@Autowired
	ComicDEpisodeBodyDao comicDEpisodeBodyDao;
	
	public Optional<ComicDEpisodeUpdate> findById(Integer episodeId) {
		return comicDEpisodeUpdateDao.findById(episodeId);
		
	}
	
	public ComicDEpisodeUpdateDto findSimpleEpisodeById(Integer episodeId) {
		Optional<ComicDEpisodeUpdate> episode = comicDEpisodeUpdateDao.findById(episodeId);
		if (episode.isPresent()) {
			return ComicDEpisodeUpdateDto.toDto(episode.get());
		}return null;

	}
	
	public List<ComicDEpisodeUpdate> findAll() {
		return comicDEpisodeUpdateDao.findAll();
	}
	
	public List<ComicDEpisodesDto> findSimpleEpisodeAll() {
		List<Map<String,Object>> episodesByComics= comicDEpisodeUpdateDao.findSimpleDataAll();
		List <ComicDEpisodesDto> result = episodesByComics.stream()
			.map(ComicDEpisodesDto::toDtoSimpleEpisode)
			.collect(Collectors.toList());
		return result;
	}
	
	
	public List<ComicDEpisodeUpdateDto> findBasicAndCommentByComicId(Integer comicId) {
		List<Map<String,Object>> comicEpisodes = comicDEpisodeUpdateDao.findBasicAndCommentByComicId(comicId);
		List<ComicDEpisodeUpdateDto> episodeDtos = ComicDEpisodeUpdateDto.toBasicAndCommentDto(comicEpisodes);
		return episodeDtos;
	}
	
	public List<Integer> findOrdersByUserId(Integer comicId, Integer userId) {
		try {
			return comicDEpisodeUpdateDao.findOrdersByUserId(comicId, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
    }
	
	
	
	public ComicDEpisodeUpdate save(ComicDEpisodeUpdate episode) {
		return comicDEpisodeUpdateDao.save(episode);						
	}
	
	public Integer updateEpisodeByColumnName(List<Map<String, Object>> updateEpisodes) throws ParseException {
		Integer rowUpdate = 0;
		for(Map<String, Object> mapComic : updateEpisodes) {
			String colName = mapComic.keySet().stream()
					.filter(keyname -> !keyname.equals("episodeId")).findFirst().get();
			Object episodeId = mapComic.get("episodeId");
			Object updateData = mapComic.get(colName);
			rowUpdate += comicDEpisodeUpdateDao.updateEpisodeByColumnName((String)colName, (Object)updateData, (Integer)episodeId);
		}return rowUpdate;							
	}

	
	public void deleteById(Integer episodeId) {
        comicDEpisodeUpdateDao.deleteById(episodeId);
    }
	

}
