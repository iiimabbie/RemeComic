package tw.com.remecomic.forum.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumGroup;
import tw.com.remecomic.forum.model.bean.ForumGroupUsers;
import tw.com.remecomic.forum.model.bean.ForumUser;

@Repository
public interface ForumGroupUsersDao extends JpaRepository<ForumGroupUsers, Integer> {
	//原來是follow的dao
//	public ForumFollow findByUserAndFollowedUser(UserA user,UserA followedUser);
	public List<ForumGroupUsers> findByUser(ForumUser user);
	public List<ForumGroupUsers> findByGroup(ForumGroup group);
	public ForumGroupUsers findByGroupAndUser(ForumGroup group,ForumUser user);
	
	
	
}
