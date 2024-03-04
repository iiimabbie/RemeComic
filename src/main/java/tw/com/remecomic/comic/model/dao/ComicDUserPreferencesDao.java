package tw.com.remecomic.comic.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.comic.model.bean.ComicDUserPreferences;

@Repository
public interface ComicDUserPreferencesDao extends JpaRepository<ComicDUserPreferences, Integer>  {

//	void saveAll(List<ComicDUserPreferences> userWithScores);
	


}
