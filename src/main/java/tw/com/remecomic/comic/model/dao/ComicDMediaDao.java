package tw.com.remecomic.comic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.comic.model.bean.ComicDMedia;

@Repository
public interface ComicDMediaDao extends JpaRepository<ComicDMedia, Integer>  {
	

}
