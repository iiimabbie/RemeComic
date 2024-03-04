package tw.com.remecomic.comic.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.comic.model.bean.ComicD;
import tw.com.remecomic.comic.model.bean.ComicDDrafts;

@Repository
public interface ComicDDraftsDao extends JpaRepository<ComicDDrafts, Integer>{
	
	Page<ComicDDrafts> findAll(Pageable pageable);
	
	
	
    

}
