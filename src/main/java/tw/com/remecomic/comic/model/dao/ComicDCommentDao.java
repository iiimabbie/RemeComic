package tw.com.remecomic.comic.model.dao;

import java.util.Date;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.comic.model.bean.ComicDComment;
import tw.com.remecomic.comic.model.bean.ComicDCommentPK;

@Repository
public interface ComicDCommentDao extends JpaRepository<ComicDComment,ComicDCommentPK >  {
	String insertNewComment="""
			INSERT INTO ComicDComment (userId, pageId, commentContent, commentDate, toUser, toCommentId)
			Values(:userId, :pageId, :commentContent , :commentDate, :toUser, :toCommentId)
			""";
	@Query(value=insertNewComment, nativeQuery=true)
	void insertNewComment(Integer userId, Integer pageId, String commentContent, 
			Date commentDate, Integer toUser, Integer toCommentId);
	
	String selectNewComment="""
			SELECT cc.commentContent, cc.pageId, cc.commentDate, cc.commentId, u.name as userName, u.gender as userGender,
			u.userPhoto as userPhoto
			FROM ComicDComment cc
			JOIN UserA u on cc.userId = u.userId
			WHERE commentId = :commentId
			""";
	@Query(value=selectNewComment, nativeQuery=true)
	Map<String,Object> selectNewComment(Integer commentId);

}
