package tw.com.remecomic.comic.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.comic.model.bean.ComicDComicConGenres;
import tw.com.remecomic.comic.model.bean.ComicDComicConGenresPK;
@Repository
public interface ComicDComicConGenresDao extends JpaRepository<ComicDComicConGenres, ComicDComicConGenresPK>  {
	
	@Query(value="SELECT comicId from ComicDComicConGenres where genreId = ?1", nativeQuery=true)
	List<Integer> findByGenreId(Integer genreId);
	
	@Query(value="SELECT comicId from ComicDComicConGenres where genreId IN :genreIds", nativeQuery=true)
	List<Integer> findByGenreIds(List<Integer> genreIds);
	
	List<ComicDComicConGenres> findByComicId(Integer comicId);	
	
	String deleteByComic="delete from ComicDComicConGenres Where comicId = :comicId";
	@Query(value=deleteByComic, nativeQuery=true)
	void deleteBaseOneComicId(Integer comicId);

}
