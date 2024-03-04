package tw.com.remecomic.orgcomic.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.orgcomic.model.bean.OrgComicComment;
import tw.com.remecomic.orgcomic.model.bean.OrgComicLike;
import tw.com.remecomic.orgcomic.model.bean.OrgComicLikePK;

@Repository
public interface OrgComicLikeDao extends JpaRepository<OrgComicLike, OrgComicLikePK>  {
	
	List<OrgComicLike> findByComicId(Integer comicId);
	
}

	