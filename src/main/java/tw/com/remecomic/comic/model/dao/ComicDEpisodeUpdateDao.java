package tw.com.remecomic.comic.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.remecomic.comic.model.bean.ComicDEpisodeUpdate;

@Repository
public interface ComicDEpisodeUpdateDao extends JpaRepository<ComicDEpisodeUpdate, Integer>, ComicDEpisodeUpdateRepositoryCustom  {

	@Query(value="SELECT Max(episodeNum), ceu.updateDate, ceu.episodeLikes FROM ComicDEpisodeUpdate ceu WHERE comicId = :comicId"
			, nativeQuery=true)
	Map<String, Object> findEpSimpleDataByComicId(Integer comicId);	
	
	String querySelectAll = "Select  ceu.comicId, comicTitle, comicCover, episodeId, episodeLikes, episodeNum, "
			+ "updateDate, episodeCover, episodeViews "
			+ "From ComicDEpisodeUpdate ceu "
			+ "Join ComicD cd ON cd.comicId = ceu.comicId ";
	@Query(value=querySelectAll, nativeQuery=true)
	List<Map<String,Object>> findSimpleDataAll();
	
	String querySingleByComicId = "Select  ceu.comicId, comicTitle, comicCover, episodeId, episodeLikes, episodeNum, updateDate, episodeCover, episodeViews "
			+ "From ComicDEpisodeUpdate ceu "
			+ "Join ComicD cd ON cd.comicId = ceu.comicId "
			+ "WHERE ceu.comicId = :comicId";
	@Query(value=querySingleByComicId, nativeQuery=true)
	List<Map<String,Object>> findSingleSimpleDataByComicId(Integer comicId);

	String querySingleByEpisodeId = "Select ceu.comicId, comicTitle, comicCover, episodeId, episodeLikes, episodeNum, updateDate, episodeCover, episodeViews "
	        + "From ComicDEpisodeUpdate ceu "
	        + "Join ComicD cd ON cd.comicId = ceu.comicId "
	        + "WHERE ceu.episodeId = :episodeId";
	@Query(value=querySingleByEpisodeId, nativeQuery=true)
	Map<String,Object> findSingleSimpleDataByEpisodeId(Integer episodeId);
	
	
	String queryBasicAndCommentByComicId = 
			"Select distinct ceu.comicId, comicTitle, comicCover, ceu.episodeId, episodeLikes, episodeNum, updateDate, "
			+"episodeCover, episodeViews, commentContent , cc.userId , u.name as userName, cc.pageId, rentalPrice, "
			+"purchasePrice, horizontalPhoto2 as  horizontalPhoto "
			+"From ComicDEpisodeUpdate ceu "
			+"Join ComicD cd ON cd.comicId = ceu.comicId "
			+"Join ComicDEpisodeBody ceb ON ceb.episodeId = ceu.episodeId "
			+"Left Join ComicDComment cc ON cc.pageId = ceb.pageId "
			+"Left Join UserA u ON u.userId = cc.userId "
			+"Left JOIN ComicDMedia cm on cm.comicId = cd.comicId "
			+"WHERE ceu.comicId = :comicId ";
	
	@Query(value=queryBasicAndCommentByComicId, nativeQuery=true)
	List<Map<String,Object>> findBasicAndCommentByComicId(Integer comicId);
		
	
	String queryOrdersByUserId = """								
			SELECT 
				mo.episodeId AS orderEpisodeId									
				FROM ComicDEpisodeUpdate ceu
				LEFT JOIN MoneyOrders mo  ON mo.episodeId = ceu.episodeId
				LEFT JOIN MoneyAccount ma  ON mo.accountId = ma.accountId
				WHERE ceu.comicId = :comicId AND userId = :userId		
		""";		
	@Query(value=queryOrdersByUserId, nativeQuery=true)
	List<Integer> findOrdersByUserId(@RequestParam Integer comicId, @RequestParam Integer userId);

	
	
}
