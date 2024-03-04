package tw.com.remecomic.comic.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.remecomic.comic.model.bean.ComicDRatingEpisode;
import tw.com.remecomic.comic.model.bean.ComicDRatingEpisodePK;

@Repository
public interface ComicDRatingEpisodeDao extends JpaRepository<ComicDRatingEpisode, ComicDRatingEpisodePK>  {
	
	String saveLike="""
			INSERT INTO ComicDRatingEpisode (userId, episodeId, episodeLike)
			VALUES (:userId, :episodeId, :episodeLike)
			ON DUPLICATE KEY UPDATE episodeLike = :episodeLike
			""";
	@Query(value=saveLike, nativeQuery=true) 
	void saveLike(Integer userId, Integer episodeId, Integer episodeLike);
	
	 String queryRatingByUserId = """								
				SELECT cre.episodeId AS orderEpisodeId
				from ComicDRatingEpisode cre
				JOIN ComicDEpisodeUpdate ceu ON cre.episodeId = ceu.episodeId
				WHERE comicId = :comicId AND userId = :userId		
			""";		
	    @Query(value=queryRatingByUserId, nativeQuery=true)
	    List<Integer> findRatingByUserId(@RequestParam Integer comicId, @RequestParam Integer userId);

}
