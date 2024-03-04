package tw.com.remecomic.forum.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumPostForward;
import tw.com.remecomic.forum.model.bean.ForumUser;

@Repository
public interface ForumPostForwardDao extends JpaRepository<ForumPostForward,ForumUser> {
	//原來是follow的dao
//	public ForumPostLike findByLikedPostAndLikeFromUser(ForumPost post,UserA user);
	
	public ForumPostForward findByForwardedPostAndForwardFromUser(ForumPost post,ForumUser user);
}
