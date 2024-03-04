package tw.com.remecomic.helpCenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.helpCenter.model.bean.HelpQuestion;
import tw.com.remecomic.helpCenter.model.bean.HelpRating;
import tw.com.remecomic.helpCenter.model.dao.HelpRatingDao;

@Service
public class HelpRatingService {

	@Autowired
	private HelpRatingDao ratingDao;
	
	//新增
	public HelpRating addRating(HelpRating helpRating) {
		HelpRating save = ratingDao.save(helpRating);
		return save;
	}
	
	//ID查詢
	public Optional<HelpRating> findRatingById(Integer id) {
		return ratingDao.findById(id);
	}
	
	public List<HelpRating> findAll() {
		return ratingDao.findAll();
	}

	//ID刪除
	public boolean deleteById(Integer id) {
		Optional<HelpRating> findById = ratingDao.findById(id);
		if(findById.isPresent()) {
			HelpRating rating = findById.get();
			ratingDao.delete(rating);
			return true;
		}return false;
	}
	
}
