package tw.com.remecomic.forum.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumNotify;
import tw.com.remecomic.forum.model.bean.ForumUser;

@Repository
public interface ForumNotifyDao extends JpaRepository<ForumNotify, Integer> {
	//原來是follow的dao
//	public ForumFollow findByUserAndFollowedUser(UserA user,UserA followedUser);
	
	List<ForumNotify> findByAcceptedUser(ForumUser user);
}
