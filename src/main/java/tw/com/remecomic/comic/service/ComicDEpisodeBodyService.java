package tw.com.remecomic.comic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.comic.model.dao.ComicDEpisodeBodyDao;
import tw.com.remecomic.comic.model.dto.ComicDEpisodeBodyDto;

@Service
public class ComicDEpisodeBodyService {
	@Autowired 
	ComicDEpisodeBodyDao comicDEpisodeBodyDao;
	
	public List<ComicDEpisodeBodyDto> findBodyWithCommentsByEpisodeId(Integer episodeId, Integer userId) {
		return ComicDEpisodeBodyDto.toDto(comicDEpisodeBodyDao.findBodyWithCommentsByEpisodeId(episodeId,userId ));
		
	}
	

}
