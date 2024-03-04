package tw.com.remecomic.comic.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.comic.model.bean.ComicDGenres;

@Repository
public interface ComicDGenresDao extends JpaRepository<ComicDGenres, Integer>  {
	
	 //Optional<ComicDGenres> findByGenreName(String genreName);
	 /*@Query(value="SELECT genreName FROM ComicDGenres WHERE genreId = ?1", nativeQuery = true)
	 String findById(Integer genreId);*/
	
	 @Query(value="SELECT genreId FROM ComicDGenres WHERE genreName = ?1", nativeQuery = true)
	 Integer findGenreIdByGenreName(String genreName);
	 
	 @Query("SELECT genreId FROM ComicDGenres  WHERE genreName IN :genreNames")
	 List<Integer> findGenreIdsByGenreNames(List<String> genreNames);
	 
	 @Query("SELECT genreName FROM ComicDGenres  WHERE genreId IN :genreIds")
	 List<String> findGenreNamesByGenreIds(List<Integer> genreIds);
	 
	 @Query(value="SELECT * FROM ComicDGenres WHERE genreName = ?1", nativeQuery = true)
	 Optional<ComicDGenres> findByGenreName(String genreName);
	 
	 @Query(value="SELECT genreName FROM ComicDGenres WHERE genreId = ?1", nativeQuery = true)
	 String findGenreNameByGenreId(Integer genreId);
	 
	 @Query(value="Insert into ComicDGenres(genreName) values(:genreName) ", nativeQuery = true)
	 void save(String genreName);
	 
	 @Query(value="DELETE From ComicDGenres WHERE genreId = ?1", nativeQuery = true)
	 void deleteById(Integer genreId);

	 
	 @Query(value="DELETE From ComicDGenres WHERE genreName = ?1", nativeQuery = true)
	 void deleteByGenreName(String genreName);

}
