package tw.com.remecomic.comic.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.comic.model.bean.ComicDDraftsConComics;
import tw.com.remecomic.comic.model.bean.ComicDDraftsConComicsPK;
import tw.com.remecomic.comic.model.bean.ComicDDraftsConGenres;
@Repository
public interface ComicDDraftsConComicsDao extends JpaRepository<ComicDDraftsConComics, ComicDDraftsConComicsPK>  {
	
	@Query(value="SELECT draftId from ComicDDraftsConComics where comicId = :comicId", nativeQuery=true)
	List<Integer> findByComicId(Integer comicId);
	
	@Query(value="SELECT comicId from ComicDDraftsConComics where draftId = :draftId ", nativeQuery=true)
	List<Integer> findByDraftId(Integer draftId);	
	
	@Query(value="SELECT comicId from ComicDDraftsConComics where draftId = :draftId group by comicId", nativeQuery=true)
	Integer findComicIdByDraftId(Integer draftId);	
	
	@Query(value="SELECT comicId, draftId from ComicDDraftsConComics where draftId IN :draftIds", nativeQuery=true)
	List<ComicDDraftsConComics> findByDraftIds (List<Integer> draftIds);	
	
	@Query(value="SELECT comicId, draftId from ComicDDraftsConComics where comicId IN :comicIds", nativeQuery=true)
	List<ComicDDraftsConComics> findByComicIds (List<Integer> comicIds);	
	
   @Query(value="DELETE FROM ComicDDraftsConComics WHERE draftId = :draftId", nativeQuery=true)
    void deleteByDraftId(Integer draftId);
	
	@Query(value="DELETE FROM ComicDDraftsConComics WHERE draftId IN :draftIds", nativeQuery=true)
    void deleteByDraftIdIN(List<Integer> draftIds);
	
 
	String queryGenreNames = """
		select c.comicTitle, cdcc.draftId, cdcc.createdDate
		from ComicD c
		Right Join ComicDDraftsConComics cdcc ON cdcc.comicId = c.comicId
		Where cdcg.draftId IN :draftIds
	""";
	@Query(value=queryGenreNames, nativeQuery=true)
	List<Map<String,Object>> findComicDByDraftIds (List<Integer> draftIds);

	@Query(value="INSERT INTO ComicDDraftsConComics(draftId,comicId) VALUES ( :draftId, :comicId)", nativeQuery=true)
	List<Map<String,Object>> save(Integer draftId, Integer comicId);


}
