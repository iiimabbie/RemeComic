package tw.com.remecomic.comic.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.comic.model.bean.ComicDComicConGenres;
import tw.com.remecomic.comic.model.bean.ComicDDraftsConGenres;
import tw.com.remecomic.comic.model.bean.ComicDDraftsConGenresPK;
@Repository
public interface ComicDDraftsConGenresDao extends JpaRepository<ComicDDraftsConGenres, ComicDDraftsConGenresPK>  {
	
	@Query(value="SELECT comicId from ComicDDraftsConGenres where genreId = :genreId", nativeQuery=true)
	List<Integer> findByGenreId(Integer genreId);
	
	@Query(value="SELECT * from ComicDDraftsConGenres where draftId = :draftId", nativeQuery=true)
	List<ComicDComicConGenres> findByDraftId(Integer draftId);	
	
    @Query(value="DELETE FROM ComicDDraftsConGenres WHERE draftId IN :draftIds", nativeQuery=true)
    void deleteByDraftIdIN(List<Integer> draftIds);
	
    @Query(value="DELETE FROM ComicDDraftsConGenres WHERE draftId = :draftId", nativeQuery=true)
    void deleteByDraftId(Integer draftId);
    
	String queryGenreNames = """
		select genreName, cdcg.draftId
		from ComicDGenres cg
		Right Join ComicDDraftsConGenres cdcg ON cdcg.genreId = cg.genreId
		Where cdcg.draftId IN :draftIds
	""";
	@Query(value=queryGenreNames, nativeQuery=true)
	List<Map<String,Object>> findGenreNamesByComicDraftIds (List<Integer> draftIds);




}
