package tw.com.remecomic.comic.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.comic.model.bean.ComicDEpisodeBody;

@Repository
public interface ComicDEpisodeBodyDao extends JpaRepository<ComicDEpisodeBody, Integer>  {
	
	
	
	String queryEpisodeWithComments="""
			Select ceb.pageId, pageNum, pagePhoto, cr.isBookMarked, episodeId, cc.commentId, cc.commentContent, cc.commentDate 
			,u.userId , u.name as userName, u.gender as userGender, u.userPhoto as userPhoto
			FROM ComicDEpisodeBody ceb
			LEFT JOIN ComicDComment cc ON cc.pageId = ceb.pageId
			LEFT JOIN UserA u ON u.userId = cc.userId
			LEFT JOIN ComicDRead cr ON cr.pageId = ceb.pageId AND cr.userId = :userId
            where ceb.episodeId = :episodeId
			""";
	@Query(value=queryEpisodeWithComments, nativeQuery=true)
	List<Map<String,Object>> findBodyWithCommentsByEpisodeId(Integer episodeId, Integer userId);

}
