package tw.com.remecomic.forum.model.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumPostCommentedPost;

@Repository
public interface ForumPostCommentedPostDao extends JpaRepository<ForumPostCommentedPost, Integer> {
	
	@Query(value="SELECT pc.childPost from ForumPostCommentedPost pc  "
			+ " where pc.parentPost = ?1 and pc.childPost.group IS NULL and pc.childPost.publicStatus = 'public' ORDER BY pc.childPost.postTime DESC ")
	Page<ForumPost> findCommentByPost(ForumPost post,Pageable pgb);

}
