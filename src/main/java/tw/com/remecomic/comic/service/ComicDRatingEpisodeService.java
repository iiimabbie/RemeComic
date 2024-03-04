package tw.com.remecomic.comic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.comic.model.bean.ComicDEpisodeUpdate;
import tw.com.remecomic.comic.model.bean.ComicDRatingEpisode;
import tw.com.remecomic.comic.model.bean.ComicDRatingEpisodePK;
import tw.com.remecomic.comic.model.dao.ComicDEpisodeUpdateDao;
import tw.com.remecomic.comic.model.dao.ComicDRatingEpisodeDao;
import tw.com.remecomic.comic.model.dto.ComicDRatingEpisodeDto;

@Service
public class ComicDRatingEpisodeService {
	@Autowired
	ComicDRatingEpisodeDao comicDRatingEpisodeDao;
	
	@Autowired
	ComicDEpisodeUpdateDao comicDEpisodeUpdateDao;

	private ComicDRatingEpisodePK pk;
	
	public Optional<ComicDRatingEpisode> findById(Integer userId, Integer episodeId) {
		pk = new ComicDRatingEpisodePK(userId, episodeId);
		return comicDRatingEpisodeDao.findById(pk);

	}
	
	public ComicDRatingEpisodeDto findSimpleEpisodeRatingById(Integer userId, Integer episodeId) {
		pk = new ComicDRatingEpisodePK(userId, episodeId);
		Optional<ComicDRatingEpisode> ratingEpisode= comicDRatingEpisodeDao.findById(pk);
		if (ratingEpisode.isPresent()) {
			return ComicDRatingEpisodeDto.toDto(ratingEpisode.get());
		}
		return null;
	}
	
	public List<ComicDRatingEpisode> findAll() {
		return comicDRatingEpisodeDao.findAll();
	}
	
	public List<ComicDRatingEpisodeDto> findSimpleEpisodeRatingAll() {
		 List<ComicDRatingEpisodeDto> simpleEpisodeRatingList = new ArrayList<>();
		 List<ComicDRatingEpisode> episodeRatings = comicDRatingEpisodeDao.findAll();
		 if(episodeRatings!= null && !episodeRatings.isEmpty()) {
			 for (ComicDRatingEpisode ratedEpisode : episodeRatings) {
				 simpleEpisodeRatingList.add(ComicDRatingEpisodeDto.toDto(ratedEpisode));
			 } return simpleEpisodeRatingList;
		 }return null;		
		 
	}
	
	public List<Integer> findRatingsByUserId(Integer comicId, Integer userId) {
		try {
			return comicDRatingEpisodeDao.findRatingByUserId(comicId, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
    }

	
	public ComicDRatingEpisode save( Map<String,Object> ratingMap) {
		try {
			Integer episodeId = (Integer)ratingMap.get("episodeId")	;	
			ComicDRatingEpisode ratingEp = new ComicDRatingEpisode(
					(Integer)ratingMap.get("userId"),episodeId);
			
			ComicDEpisodeUpdate ep = comicDEpisodeUpdateDao.findById(episodeId).get();
			Integer epLikes =  ep.getEpisodeLikes();
			ep.setEpisodeLikes(epLikes+1);
			comicDEpisodeUpdateDao.save(ep);
			return comicDRatingEpisodeDao.save(ratingEp);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
    }
	
	public void saveLike(Integer userId, Integer episodeId, Integer episodeLike) {
		comicDRatingEpisodeDao.saveLike(userId,episodeId,episodeLike);
        
    }
	

	
	public void deleteById(Integer userId, Integer episodeId) {
		pk = new ComicDRatingEpisodePK(userId, episodeId);
		comicDRatingEpisodeDao.deleteById(pk);
    }
	

}
