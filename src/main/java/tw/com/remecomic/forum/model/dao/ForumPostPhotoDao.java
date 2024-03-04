package tw.com.remecomic.forum.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumPostPhoto;

@Repository
public interface ForumPostPhotoDao extends JpaRepository<ForumPostPhoto, Integer> {
	//原來是follow的dao
//	public ForumFollow findByUserAndFollowedUser(UserA user,UserA followedUser);
	List<ForumPostPhoto> findByPost(ForumPost post);
}
