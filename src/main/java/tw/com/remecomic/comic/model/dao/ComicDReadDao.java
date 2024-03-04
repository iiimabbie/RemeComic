package tw.com.remecomic.comic.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.comic.model.bean.ComicDRead;
import tw.com.remecomic.comic.model.bean.ComicDReadPK;


@Repository
public interface ComicDReadDao extends JpaRepository<ComicDRead, ComicDReadPK>  {
	String queryPageByUser =  """
			SELECT cr.pageId, ceb.pageNum, cr.isBookMarked, ceb.episodeId, cr.userId, ceu.comicId, epMax.totalPage 
			FROM ComicDRead cr
			JOIN ComicDEpisodeBody ceb ON cr.pageId = ceb.pageId
			JOIN ComicDEpisodeUpdate ceu ON ceu.episodeId = ceb.episodeId
			LEFT JOIN (
			    SELECT episodeId, MAX(pageNum) AS totalPage
			    FROM ComicDEpisodeBody
			    GROUP BY episodeId
			) epMax ON ceb.episodeId = epMax.episodeId
			WHERE ceu.comicId = :comicId AND cr.userId = :userId
			""";
	@Query(value=queryPageByUser, nativeQuery=true)
	List<Map<String,Object>> findReadPageByUserId(Integer comicId, Integer userId);
	
	String insertReadPageByUser =  """
			INSERT INTO  ComicDRead (pageId, userId)
			values(:pageId, :userId)
			""";
	@Query(value=insertReadPageByUser, nativeQuery=true)
	List<Map<String,Object>> saveRead(Integer pageId, Integer userId);
	

	String insertBookMarkedByUser =  """
			INSERT INTO  ComicDRead (pageId, userId, isBookMarked)
			values(:pageId, :userId, :isBookMarked)
			""";
	@Query(value=insertBookMarkedByUser, nativeQuery=true)
	void saveBookMarked(Integer pageId, Integer userId, Integer isBookMarked);
	
	
	String findByUserIdAndPageIds =  """
			SELECT * from ComicDRead
			WHERE userId = :userId AND pageId IN :pageIds
			""";
	@Query(value=findByUserIdAndPageIds, nativeQuery=true)
	List<ComicDRead> findByUserIdAndPageIds(Integer userId, List<Integer> pageIds);
	
	String findByUserIdAndPageId =  """
			SELECT * from ComicDRead
			WHERE userId = :userId AND pageId = :pageId
			""";
	@Query(value=findByUserIdAndPageId, nativeQuery=true)
	ComicDRead findByUserIdAndPageId(Integer userId, Integer pageId);
	
}
