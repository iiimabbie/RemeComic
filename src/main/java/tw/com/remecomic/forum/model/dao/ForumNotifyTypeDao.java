package tw.com.remecomic.forum.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumNotifyType;

@Repository
public interface ForumNotifyTypeDao extends JpaRepository<ForumNotifyType, Integer> {
	//原是follow的dao
//	public ForumFollow findByUserAndFollowedUser(UserA user,UserA followedUser);
}
