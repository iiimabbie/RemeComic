package tw.com.remecomic.forum.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumPostLike;
import tw.com.remecomic.forum.model.bean.ForumUser;

@Repository
public interface ForumPostLikeDao extends JpaRepository<ForumPostLike,ForumUser> {
	
	public ForumPostLike findByLikedPostAndLikeFromUser(ForumPost post,ForumUser user);
}
