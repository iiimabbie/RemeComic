package tw.com.remecomic.comic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tw.com.remecomic.comic.model.bean.ComicDRatingComic;
import tw.com.remecomic.comic.model.bean.ComicDRatingComicPK;
import tw.com.remecomic.comic.model.dao.ComicDRatingComicDao;
import tw.com.remecomic.comic.model.dto.ComicDRatingComicDto;

@Service
public class ComicDRatingComicService {
	@Autowired
	ComicDRatingComicDao comicDRatingComicDao;
	
	private ComicDRatingComicPK pk;
	
	public Optional<ComicDRatingComic> findById(Integer userId, Integer comicId) {
		pk = new ComicDRatingComicPK(userId, comicId);
		return comicDRatingComicDao.findById(pk);

	}

	public List<ComicDRatingComic> findByUserId(Integer userId) {
		return comicDRatingComicDao.findComicDRatingComicByUserId(userId);

	}

	public Page<ComicDRatingComic> findUserComicByPage(Integer userId, Integer pageNum) {
		PageRequest pageRequest = PageRequest.of((pageNum - 1), 3
				, Sort.Direction.DESC, "ratingDate");

		ComicDRatingComic ratingComic = new ComicDRatingComic();
		ratingComic.setUserId(userId);

		Example<ComicDRatingComic> example = Example.of(ratingComic);

		return comicDRatingComicDao.findAll(example, pageRequest);
	}

	public ComicDRatingComicDto findSimpleComicRatingById(Integer userId, Integer comicId) {
		pk = new ComicDRatingComicPK(userId, comicId);
		Optional<ComicDRatingComic> ratingComic= comicDRatingComicDao.findById(pk);
		if (ratingComic.isPresent()) {
			return ComicDRatingComicDto.toDto(ratingComic.get());
		}
		return null;
	}
	

	public List<ComicDRatingComic> findAll() {
		return comicDRatingComicDao.findAll();
	}
	
	public List<ComicDRatingComicDto> findSimpleComicRatingAll() {
		 List<ComicDRatingComicDto> simpleComicRatingList = new ArrayList<>();
		 List<ComicDRatingComic> comicRatings = comicDRatingComicDao.findAll();
		 if(comicRatings!= null && !comicRatings.isEmpty()) {
			 for (ComicDRatingComic ratedComic : comicRatings) {
				 simpleComicRatingList.add(ComicDRatingComicDto.toDto(ratedComic));
			 } return simpleComicRatingList;
		 }return null;		
		 
	}	

	
	
	public ComicDRatingComic save(ComicDRatingComic ratingComic) {
		try {
			return comicDRatingComicDao.save(ratingComic);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
    }
	

	
	public void deleteById(Integer userId, Integer episodeId) {
		pk = new ComicDRatingComicPK(userId, episodeId);
		comicDRatingComicDao.deleteById(pk);
    }

}
