package tw.com.remecomic.comic.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.comic.model.bean.ComicDComment;
import tw.com.remecomic.comic.model.dao.ComicDCommentDao;

@Service
public class ComicDCommentService {
	@Autowired
	ComicDCommentDao comicDCommentDao;
	
	public Map<String,Object> save(Integer userId, Integer pageId, String commentContent, Date commentDate, Integer toUser, Integer toCommentId) {
		ComicDComment comment = new ComicDComment(userId, pageId, commentContent, commentDate, toUser, toCommentId);
		ComicDComment resComment = comicDCommentDao.save(comment);
		return comicDCommentDao.selectNewComment(resComment.getCommentId());

	}

}
