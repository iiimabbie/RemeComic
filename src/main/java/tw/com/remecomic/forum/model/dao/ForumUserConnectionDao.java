package tw.com.remecomic.forum.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.bean.ForumUserConnection;

@Repository
public interface ForumUserConnectionDao extends JpaRepository<ForumUserConnection, Integer> {
	
	public ForumUserConnection findByUserAndPassiveUserAndConnectionType(ForumUser user,ForumUser followedUser,String connString);
	
	//該user跟隨的人
	public List<ForumUserConnection> findByUser(ForumUser user);
	
	//跟隨該user的人
	public List<ForumUserConnection> findByPassiveUser(ForumUser user);
}
