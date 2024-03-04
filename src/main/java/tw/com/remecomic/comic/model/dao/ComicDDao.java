package tw.com.remecomic.comic.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.comic.model.bean.ComicD;

@Repository
public interface ComicDDao extends JpaRepository<ComicD, Integer>, ComicDRepositoryCustom {
	
	
	@Query(value="Select * from ComicD where comicId IN :comicIds", nativeQuery=true)
    List<ComicD> findComicByIds(List<Integer> comicIds);	
	
	/*String queryCommentEpisodes = """
		    SELECT c.comicId, c.comicTitle, c.comicCover, c.comicDescription, c.updateDay, c.publishDate, 
		    c.editorChoice, c.purchasePrice, c.rentalPrice, 
			count(distinct(ccc.pageId)) AS comments,
			sum(ceu.episodeLikes) as comicLikesEP,
			sum(ceu.episodeViews) as comicViews,
			count(distinct(ccr.ratingComicId)) AS comicLikes,
			FIRST_VALUE(ceu.episodeViews) OVER (PARTITION BY c.comicId ORDER BY ceu.episodeNum DESC) AS latestEpViews,
			FIRST_VALUE(ceu.episodeLikes) OVER (PARTITION BY c.comicId ORDER BY ceu.episodeNum DESC) AS latestEpLikes,
			cdm.video as VideoUrl,
			cdm.horizontalPhoto as horizontalPhotoUrl
			FROM ComicD c 
			LEFT JOIN ComicDEpisodeUpdate ceu ON ceu.comicId = c.comicId 
			LEFT JOIN ComicDRatingComic ccr ON ccr.comicId = c.comicId 
			LEFT JOIN ComicDEpisodeBody ceb ON ceb.episodeId = ceu.episodeId 
			LEFT JOIN ComicDComment ccc ON ccc.pageId = ceb.pageId 
			LEFT JOIN ComicDMedia cdm ON cdm.comicId = c.comicId 
			WHERE c.comicId IN :comicIds
			group by comicId
		    """;
	@Query(value=queryCommentEpisodes, nativeQuery=true)
    List<Map<String,Object>> findComicSimpleSummaries(List<Integer> comicIds) ;*/
	
	String queryCommentEpisodes = """
		    SELECT c.comicId, c.comicTitle, c.comicCover, c.comicDescription, c.updateDay, c.publishDate, 
		    c.editorChoice, c.purchasePrice, c.rentalPrice, 
			sum(ceu.episodeViews) as comicViews,
			count(distinct(ccr.ratingComicId)) AS comicLikes,
			FIRST_VALUE(ceu.episodeViews) OVER (PARTITION BY c.comicId ORDER BY ceu.episodeNum DESC) AS latestEpViews,
			cdm.video as VideoUrl,
			cdm.horizontalPhoto as horizontalPhotoUrl
			FROM ComicD c 
			LEFT JOIN ComicDEpisodeUpdate ceu ON ceu.comicId = c.comicId 
			LEFT JOIN ComicDRatingComic ccr ON ccr.comicId = c.comicId 
			LEFT JOIN ComicDMedia cdm ON cdm.comicId = c.comicId 
			WHERE c.comicId IN :comicIds
			group by comicId
		    """;
	@Query(value=queryCommentEpisodes, nativeQuery=true)
    List<Map<String,Object>> findComicSimpleSummaries(List<Integer> comicIds) ;
	
	
	String queryRecomEpisodes = """
		    SELECT c.comicId, c.comicTitle, c.comicCover, c.comicDescription, c.updateDay, c.publishDate, 
		    c.editorChoice, c.purchasePrice, c.rentalPrice, 
			sum(ceu.episodeViews) as comicViews,
			count(distinct(ccr.ratingComicId)) AS comicLikes,
			FIRST_VALUE(ceu.episodeViews) OVER (PARTITION BY c.comicId ORDER BY ceu.episodeNum DESC) AS latestEpViews
			FROM ComicD c 
			LEFT JOIN ComicDEpisodeUpdate ceu ON ceu.comicId = c.comicId 
			LEFT JOIN ComicDRatingComic ccr ON ccr.comicId = c.comicId 
			WHERE c.comicId IN :comicIds
			group by comicId
			ORDER BY comicViews DESC
			LIMIT 5
		    """;
	@Query(value=queryRecomEpisodes, nativeQuery=true)
    List<Map<String,Object>> findComicInfoForRecom(List<Integer> comicIds) ;
	
	
	String query = """
		    SELECT c.comicId, c.comicTitle, c.comicDescription, c.updateDay, c.publishDate, c.editorChoice, c.purchasePrice, c.rentalPrice,
			sum(ceu.episodeLikes) as comicLikesEP,
			sum(ceu.episodeViews) as comicViews,
			count(distinct(ccr.ratingComicId)) AS comicLikes
			FROM ComicD c 
			JOIN ComicDEpisodeUpdate ceu ON ceu.comicId = c.comicId 
			JOIN ComicDRatingComic ccr ON ccr.comicId = c.comicId 
			group by c.comicId
		    """;
	@Query(value=query, nativeQuery=true)
    List<Map<String,Object>> findComicSummaries() ;
    
	String queryComment ="SELECT c.comicId,  "
			+ "count(distinct(ceb.pageId)) AS comments "
			+ "FROM ComicD c "
			+ "JOIN ComicDEpisodeUpdate ceu ON ceu.comicId = c.comicId "
			+ "JOIN ComicDEpisodeBody ceb ON ceb.episodeId = ceu.episodeId "
			+ "JOIN ComicDComment ccc ON ccc.pageId = ceb.PageId "
			+ "group by c.comicId ";
	@Query(value=queryComment, nativeQuery=true)
    List<Map<String,Object>> findComicComment();
    
    String queryGenre ="SELECT c.comicId, cg.genreName "
			+ "FROM ComicD c "
			+ "JOIN ComicDComicConGenres cccg ON cccg.comicId = c.comicId "
			+ "JOIN ComicDGenres cg ON cg.genreId = cccg.genreId ";
	@Query(value=queryGenre, nativeQuery=true)
    List<Map<String,Object>> findComicGenre();
    
    
    String querySummariesById = "SELECT c.comicId, c.comicTitle, c.creator, c.comicDescription, c.comicCover, c.updateDay, c.publishDate, c.editorChoice, c.purchasePrice, c.rentalPrice,"
			+ "sum(ceu.episodeLikes) as comicLikesEP,"
			+ "sum(ceu.episodeViews) as comicViews,"
			+ "count(distinct(ccr.ratingComicId)) AS comicLikes "
			+ "FROM ComicD c "
			+ "JOIN ComicDEpisodeUpdate ceu ON ceu.comicId = c.comicId "
			+ "JOIN ComicDRatingComic ccr ON ccr.comicId = c.comicId "
			+ "where c.comicId = :comicId ";
	@Query(value=querySummariesById, nativeQuery=true)
    Map<String,Object> findComicSummariesById(Integer comicId);
        
    
    String queryCommentById ="SELECT c.comicId,  "
			+ "count(distinct(ceb.pageId)) AS comments "
			+ "FROM ComicD c "
			+ "JOIN ComicDEpisodeUpdate ceu ON ceu.comicId = c.comicId "
			+ "JOIN ComicDEpisodeBody ceb ON ceb.episodeId = ceu.episodeId "
			+ "JOIN ComicDComment ccc ON ccc.pageId = ceb.PageId "
			+ "where c.comicId = :comicId ";
	@Query(value=queryCommentById, nativeQuery=true)
	Map<String,Object> findComicCommentByComicId(Integer comicId);
    
    String queryGenreById ="SELECT c.comicId, cg.genreName "
			+ "FROM ComicD c "
			+ "JOIN ComicDComicConGenres cccg ON cccg.comicId = c.comicId "
			+ "JOIN ComicDGenres cg ON cg.genreId = cccg.genreId "
			+ "WHERE c.comicId = :comicId";
	@Query(value=queryGenreById, nativeQuery=true)
	List<Map<String,Object>> findComicGenreByComicId(Integer comicId);
	
	
	String querySummariesByMultipleColumns = "SELECT " +
		    "c.comicId, " +
		    "SUM(ceu.episodeLikes) AS comicLikesEP, " +
		    "SUM(ceu.episodeViews) AS comicViews, " +
		    "COUNT(DISTINCT ccr.ratingComicId) AS comicLikes " +
		"FROM " +
		    "ComicD c " +
		"JOIN " +
		    "ComicDEpisodeUpdate ceu ON ceu.comicId = c.comicId " +
		"JOIN " +
		    "ComicDRatingComic ccr ON ccr.comicId = c.comicId " +
		"WHERE " +
		    "CAST(c.comicId AS CHAR) LIKE :searchTerm OR " +
		    "LOWER(c.comicTitle) LIKE LOWER(:searchTerm) OR " +
		    "LOWER(c.creator) LIKE LOWER(:searchTerm) OR " +
		    "LOWER(c.comicDescription) LIKE LOWER(:searchTerm) OR " +
		    "LOWER(c.comicCover) LIKE LOWER(:searchTerm) OR " +
		    "LOWER(c.updateDay) LIKE LOWER(:searchTerm) OR " +
		    "LOWER(c.publishDate) LIKE LOWER(:searchTerm) OR " +
		    "CAST(c.purchasePrice AS CHAR) LIKE :searchTerm OR " +
		    "CAST(c.rentalPrice AS CHAR) LIKE :searchTerm " +
//		    "CAST(comicViews AS CHAR) LIKE :searchTerm " +
		"GROUP BY " +
		    "c.comicId";

	@Query(value=querySummariesByMultipleColumns, nativeQuery=true)
	List<Map<String, Object>> findComicSummariesBySearchTerm(String searchTerm);
	
	
	
	String queryCommentByMultipleColumns = "SELECT c.comicId, "
	        + "count(distinct(ceb.pageId)) AS comments "
	        + "FROM ComicD c "
	        + "JOIN ComicDEpisodeUpdate ceu ON ceu.comicId = c.comicId "
	        + "JOIN ComicDEpisodeBody ceb ON ceb.episodeId = ceu.episodeId "
	        + "JOIN ComicDComment ccc ON ccc.pageId = ceb.PageId "
	        + "WHERE CAST(c.comicId AS CHAR) LIKE :searchTerm "
	        + "GROUP BY c.comicId";

	@Query(value=queryCommentByMultipleColumns, nativeQuery=true)
	List<Map<String, Object>> findComicCommentBySearchTerm(String searchTerm);
	

	
	String queryGenreByMultipleColumns = "SELECT c.comicId "
	        + "FROM ComicD c "
	        + "JOIN ComicDComicConGenres cccg ON cccg.comicId = c.comicId "
	        + "JOIN ComicDGenres cg ON cg.genreId = cccg.genreId "
	        + "WHERE CAST(c.comicId AS CHAR) LIKE :searchTerm "
	        + "OR LOWER(cg.genreName) LIKE LOWER(:searchTerm) "
	        + "GROUP BY c.comicId, cg.genreName";

	@Query(value=queryGenreByMultipleColumns, nativeQuery=true)
	List<Integer> findComicGenreBySearchTerm(String searchTerm);
	
	
	
	String querySummariesByNumbers = "SELECT " +
		    "c.comicId, " +
		    "c.comicTitle, " +
		    "c.creator, " +
		    "c.comicDescription, " +
		    "c.comicCover, " +
		    "c.updateDay, " +
		    "c.publishDate, " +
		    "c.editorChoice, " +
		    "c.purchasePrice, " +
		    "c.rentalPrice, " +
		    "SUM(ceu.episodeLikes) AS comicLikesEP, " +
		    "SUM(ceu.episodeViews) AS comicViews, " +
		    "COUNT(DISTINCT ccr.ratingComicId) AS comicLikes " +
		"FROM " +
		    "ComicD c " +
		"JOIN " +
		    "ComicDEpisodeUpdate ceu ON ceu.comicId = c.comicId " +
		"JOIN " +
		    "ComicDRatingComic ccr ON ccr.comicId = c.comicId " +
		"WHERE " +
		    "c.comicId > :searchTerm OR " +
		    "comicLikesEP > :searchTerm " +
		    "comicViews > :searchTerm " +
		"GROUP BY " +
		    "c.comicId";

	@Query(value=querySummariesByNumbers, nativeQuery=true)
	List<Map<String, Object>> findComicSummariesByNumbers(Integer searchTerm);
	
	
	
	Page<ComicD> findByComicIdIn(List<Integer> comicIds, Pageable pageable);
	
	
	String queryDrafts = "SELECT comicId, comicTitle, modifyTime, isPublished From ComicD "
			+ "where modifyTime is not null AND "
			+ "isPublished = 0";
	@Query(value=queryDrafts, nativeQuery=true)
	List<Map<String,Object>> findComicDraftsAll();
	
	
	String queryDraftsById = "SELECT comicId, comicTitle, modifyTime, isPublished From ComicD "
			+ "where comicId = :comicIds";
	@Query(value=queryDraftsById, nativeQuery=true)
	List<ComicD> findComicDraftsByComicIdIn(List<Integer> comicIds);
	
	
    

}
