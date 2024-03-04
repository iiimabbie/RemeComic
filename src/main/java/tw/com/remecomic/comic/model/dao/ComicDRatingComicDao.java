package tw.com.remecomic.comic.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.remecomic.comic.model.bean.ComicDRatingComic;
import tw.com.remecomic.comic.model.bean.ComicDRatingComicPK;
@Repository
public interface ComicDRatingComicDao extends JpaRepository<ComicDRatingComic, ComicDRatingComicPK>  {
	
	//public ComicDRatingComic save(ComicDRatingComicDto newlyRatedComic);

    List<ComicDRatingComic> findComicDRatingComicByUserId(Integer userId);
    
    
    
    @Query(value="SELECT comicId from ComicDRatingComic where userId = :userId")
    List<Integer> findComicIdByUserId(Integer userId);
    
    String queryMyPreference = """
		    SELECT crc.comicId, userId, genreId
			FROM ComicDRatingComic crc
			JOIN ComicDComicConGenres ccg ON ccg.comicId = crc.comicId 		
			WHERE `like`=1 AND crc.comicId < 53
		    """;
	@Query(value=queryMyPreference, nativeQuery=true)
    List<Map<String,Integer>> findDataForMyPerference() ;
	
    String queryComicInfoByClusterUser = """
		    SELECT crc.comicId, comicName, comicTitle, comicCover, comicDescription, crc.userId
			FROM ComicDRatingComic crc
			JOIN ComicDUserPreferences cup ON cup.userId = crc.userId 	
			JOIN ComicD cd ON crc.comicId = cd.comicId
			WHERE userId IN :userIds
			GROUP BY crc.comicId
		    """;
	@Query(value=queryComicInfoByClusterUser, nativeQuery=true)
    List<Map<String,Integer>> findComicInfoByClusterUser(List<Integer> userIds) ;
	
    String queryComicIdByClusterUser = """
		    SELECT DISTINCT crc.comicId
		    FROM ComicDRatingComic crc
			JOIN ComicDUserPreferences cup ON cup.userId = crc.userId 	
			WHERE crc.userId IN (:userIds)
		    """;
	@Query(value=queryComicIdByClusterUser, nativeQuery=true)
    List<Integer> findComicIdByClusterUser(List<Integer> userIds) ;
	
    String queryGenreIdByClusterUser = """
		    SELECT DISTINCT ccg.genreId
		    FROM ComicDRatingComic crc
			JOIN ComicDComicConGenres ccg ON ccg.genreId = crc.genreId 	
			WHERE crc.userId = :userId
		    """;
	@Query(value=queryGenreIdByClusterUser, nativeQuery=true)
    List<Integer> findGenreIdByUserId(Integer userId) ;
    
    
   
}
