package tw.com.remecomic.orgcomic.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.orgcomic.model.bean.OrgComicComment;

@Repository
public interface OrgComicCommentDao extends JpaRepository<OrgComicComment, Integer>  {
	

}
