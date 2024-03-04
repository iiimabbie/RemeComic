package tw.com.remecomic.forum.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumGroup;
import tw.com.remecomic.forum.model.bean.ForumTag;

@Repository
public interface ForumGroupDao extends JpaRepository<ForumGroup, Integer> {
	//原來是follow的dao
//	public ForumFollow findByUserAndFollowedUser(UserA user,UserA followedUser);
	
	ForumGroup findByGroupName(String name);
	
}
