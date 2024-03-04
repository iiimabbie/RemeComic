package tw.com.remecomic.forum.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tw.com.remecomic.forum.model.bean.ForumTag;
import tw.com.remecomic.forum.model.dao.ForumTagDao;
import tw.com.remecomic.forum.model.dto.ForumTagDto;
import tw.com.remecomic.forum.model.dto.ForumTagDto2;

@Service
@Transactional
public class ForumTagService {

	@Autowired
	private ForumTagDao forumTagDao;

////////////////////////////show(hot) ///////////////////////////////

	//////////////// today ( show(hot) ) /////////////////
	//////////////// week ( show(hot) ) /////////////////
	//////////////// month ( show(hot) ) /////////////////
	public List<ForumTagDto2> getHotTagsInTimePeriod(Integer periodNumber) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime past = now.minusDays(periodNumber);
		Date dateNow = Date.from(now.atZone(java.time.ZoneId.systemDefault()).toInstant());
		Date datePast = Date.from(past.atZone(java.time.ZoneId.systemDefault()).toInstant());
		List<ForumTagDto2> tagList = forumTagDao.findHotTagsInTimePeriod(datePast, dateNow);
		return tagList;
	}
	////////////////show(search+hot) /////////////////
	public List<ForumTagDto2> getTagsBySearchSortByHot(String searchString) {
		List<ForumTagDto2> tagList = forumTagDao.findTagsBySearchSortByHot(searchString);
		return tagList;
	}
	
	
	//////////////// all ( show(hot) ) /////////////////

//	public List<ForumTag> getAllTags1() {
//		List<ForumTag> allList = forumTagDao.findAll();
//		return allList;
//	}
	public List<ForumTagDto> getAllTags() {
		List<ForumTag> allList = forumTagDao.findAll();
		return allList.stream().map(ForumTagDto::sendToFrontend).toList();
	}

	public ForumTag addOneTag(ForumTag oneTag) {
		ForumTag tag = forumTagDao.save(oneTag);
		return tag;
	}
//	public ForumTag addOneIntoPostTag(ForumTag oneTag) {
//		ForumTag tag = forumTagDao.save(oneTag);
//		return tag;
//	}

}
