package tw.com.remecomic.forum.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumPostTag;

@Repository
public interface ForumPostTagDao extends JpaRepository<ForumPostTag, Integer> {
	
	public List<ForumPostTag> findByPost(ForumPost post);
	public void deleteByPost(ForumPost post);

}
