package tw.com.remecomic.forum.model.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumGroup;
import tw.com.remecomic.forum.model.bean.ForumMessage;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.userA.model.bean.UserA;

@Repository
public interface ForumMessageDao extends JpaRepository<ForumMessage, Integer> {
	//原來是follow的dao
//	public ForumFollow findByUserAndFollowedUser(UserA user,UserA followedUser);
	
	List<ForumMessage> findByGroup(ForumGroup group,Pageable pgb);
	List<ForumMessage> findByUserInAndAcceptedUserIn(ForumUser[] sendMsgUser,ForumUser[] acceptUser,Pageable pgb);
	
}
