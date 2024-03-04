package tw.com.remecomic.forum.model.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumGroup;
import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumTag;
import tw.com.remecomic.forum.model.bean.ForumUser;

@Repository
public interface ForumPostDao extends JpaRepository<ForumPost, Integer> {
	
	@Query(value="from ForumPost where postUser.userId in (:followingList)")
	List<ForumPost> findByPostUserIn(List<Integer> followingList,Pageable pgb);
	
	////////////////all (n+h) (show post) /////////////////
	Page<ForumPost> findByGroupIsNullAndPublicStatus(String publicString,Pageable pgb);
	
	@Query(value="SELECT post.*,likeAmount + forwardAmount + commentAmount AS totalInteractions "
			+ "FROM (SELECT p.*,COUNT(DISTINCT pl.userId) AS likeAmount,COUNT(DISTINCT pf.userId) AS forwardAmount,COUNT(DISTINCT pc.childPostId) AS commentAmount "
			+ "FROM ForumPost p LEFT JOIN ForumPostLike pl ON p.postId = pl.postId LEFT JOIN ForumPostForward pf ON p.postId = pf.postId LEFT JOIN ForumPostCommentedPost pc ON p.postId = pc.parentPostId "
			+ "WHERE p.postTime BETWEEN ?1 AND ?2 AND p.groupId is null  AND p.publicStatus = 'public' "
			+ "GROUP BY p.postId, p.userId, p.postContent, p.groupId, p.postTime) AS post ORDER by likeAmount + forwardAmount + commentAmount desc , post.postTime desc "
			,countQuery = "SELECT count(*) "
					+ "FROM (SELECT p.*,COUNT(DISTINCT pl.userId) AS likeAmount,COUNT(DISTINCT pf.userId) AS forwardAmount,COUNT(DISTINCT pc.childPostId) AS commentAmount "
					+ "FROM ForumPost p LEFT JOIN ForumPostLike pl ON p.postId = pl.postId LEFT JOIN ForumPostForward pf ON p.postId = pf.postId LEFT JOIN ForumPostCommentedPost pc ON p.postId = pc.parentPostId "
					+ "WHERE p.postTime BETWEEN ?1 AND ?2 AND p.groupId is null  AND p.publicStatus = 'public' "
					+ "GROUP BY p.postId, p.userId, p.postContent, p.groupId, p.postTime) AS post ORDER by (likeAmount + forwardAmount + commentAmount) desc, post.postTime desc "
			,nativeQuery = true)
	Page<ForumPost> findHotPostInTimePeriod(Date yesterday, Date today, Pageable pageable);
	
	@Query(value = "SELECT post.postId, post.userId, post.postContent, post.groupId, post.postTime, " +
	        "COUNT(DISTINCT pl.userId) AS likeAmount, COUNT(DISTINCT pf.userId) AS forwardAmount, COUNT(DISTINCT pc.childPostId) AS commentAmount, " +
	        "COUNT(DISTINCT pl.userId) + COUNT(DISTINCT pf.userId) + COUNT(DISTINCT pc.childPostId) AS totalInteractions " +
	        "FROM (SELECT p.postId, p.userId, p.postContent, p.groupId, p.postTime " +
	        "FROM ForumPost p LEFT JOIN ForumPostLike pl ON p.postId = pl.postId LEFT JOIN ForumPostForward pf ON p.postId = pf.postId LEFT JOIN ForumPostCommentedPost pc ON p.postId = pc.parentPostId " +
	        "WHERE p.postTime BETWEEN ?1 AND ?2 AND p.groupId IS NULL  AND p.publicStatus = 'public'  " +
	        "GROUP BY p.postId, p.userId, p.postContent, p.groupId, p.postTime) AS post " +
	        "LEFT JOIN ForumPostLike pl ON post.postId = pl.postId LEFT JOIN ForumPostForward pf ON post.postId = pf.postId LEFT JOIN ForumPostCommentedPost pc ON post.postId = pc.parentPostId " +
	        "GROUP BY post.postId, post.userId, post.postContent, post.groupId, post.postTime " +
	        "ORDER BY (COUNT(DISTINCT pl.userId) + COUNT(DISTINCT pf.userId) + COUNT(DISTINCT pc.childPostId)) DESC ",
	        countQuery = "SELECT count(*) " +
	                "FROM (SELECT p.postId, p.userId, p.postContent, p.groupId, p.postTime " +
	                "FROM ForumPost p LEFT JOIN ForumPostLike pl ON p.postId = pl.postId LEFT JOIN ForumPostForward pf ON p.postId = pf.postId LEFT JOIN ForumPostCommentedPost pc ON p.postId = pc.parentPostId " +
	                "WHERE p.postTime BETWEEN ?1 AND ?2 AND p.groupId IS NULL  AND p.publicStatus = 'public' " +
	                "GROUP BY p.postId, p.userId, p.postContent, p.groupId, p.postTime) AS post " +
	                "LEFT JOIN ForumPostLike pl ON post.postId = pl.postId LEFT JOIN ForumPostForward pf ON post.postId = pf.postId LEFT JOIN ForumPostCommentedPost pc ON post.postId = pc.parentPostId ",
	        nativeQuery = true)
	Page<Map<String,Object[]>> findHotPostInTimePeriodTryFast(Date yesterday, Date today, Pageable pageable);

	////////////////tag (n+h) (show post) /////////////////
	
	@Query(value="FROM ForumPost p JOIN ForumPostTag pt ON p.postId = pt.post WHERE pt.tag.tagId = ?1 AND p.group IS NULL AND p.publicStatus = 'public' ")
	Page<ForumPost> findByGroupIsNullByTag(Integer tagId,Pageable pgb);
	
	@Query(value="SELECT post.*,likeAmount + forwardAmount + commentAmount AS totalInteractions "
			+ "FROM (SELECT p.*,COUNT(DISTINCT pl.userId) AS likeAmount,COUNT(DISTINCT pf.userId) AS forwardAmount,COUNT(DISTINCT pc.childPostId) AS commentAmount "
			+ "FROM ForumPost p LEFT JOIN ForumPostLike pl ON p.postId = pl.postId LEFT JOIN ForumPostForward pf ON p.postId = pf.postId LEFT JOIN ForumPostCommentedPost pc ON p.postId = pc.parentPostId JOIN ForumPostTag pt ON p.postId = pt.postId "
			+ "WHERE pt.tagId = ?3 AND (p.postTime BETWEEN ?1 AND ?2) AND p.groupId IS NULL AND p.publicStatus = 'public'"
			+ "GROUP BY p.postId, p.userId, p.postContent, p.groupId, p.postTime) AS post ORDER by (likeAmount + forwardAmount + commentAmount) desc "
			,countQuery = "SELECT count(*) "
					+ "FROM (SELECT p.*,COUNT(DISTINCT pl.userId) AS likeAmount,COUNT(DISTINCT pf.userId) AS forwardAmount,COUNT(DISTINCT pc.childPostId) AS commentAmount "
					+ "FROM ForumPost p LEFT JOIN ForumPostLike pl ON p.postId = pl.postId LEFT JOIN ForumPostForward pf ON p.postId = pf.postId LEFT JOIN ForumPostCommentedPost pc ON p.postId = pc.parentPostId JOIN ForumPostTag pt ON p.postId = pt.postId "
					+ "WHERE pt.tagId = ?3 AND (p.postTime BETWEEN ?1 AND ?2) AND p.groupId IS NULL AND p.publicStatus = 'public' "
					+ "GROUP BY p.postId, p.userId, p.postContent, p.groupId, p.postTime) AS post ORDER by (likeAmount + forwardAmount + commentAmount) desc"
			,nativeQuery = true)
	Page<ForumPost> findHotPostInTimePeriodByTag(Date yesterday, Date today, Integer tagId,Pageable pageable);
	
	
	////////////////filter by follows (n+h) (show post) /////////////////
	
	@Query(value="SELECT p.postId,p.userId,p.postContent,p.groupId,p.postTime,p.publicStatus,p.verifyHidden,GREATEST(COALESCE(CASE WHEN pf.userId IN :following THEN pf.forwardTime ELSE '1900-01-01' END), COALESCE(CASE WHEN pl.userId IN :following THEN pl.likeTime ELSE '1900-01-01' END), COALESCE(CASE WHEN p.userId IN :following THEN p.postTime ELSE '1900-01-01' END)) as activeTime "
			+ "FROM ForumPost p LEFT JOIN ForumPostLike pl ON p.postId = pl.postId LEFT JOIN ForumPostForward pf ON p.postId = pf.postId "
			+ "WHERE p.groupId IS NULL  AND p.publicStatus = 'public' "
			+ "AND (p.userId in :following OR pl.userId in :following OR pf.userId in :following ) "
			+ "GROUP by p.postId,p.userId,p.postContent,p.groupId,p.postTime ORDER BY GREATEST(COALESCE(CASE WHEN pf.userId IN :following THEN pf.forwardTime ELSE '1900-01-01' END), COALESCE(CASE WHEN pl.userId IN :following THEN pl.likeTime ELSE '1900-01-01' END), COALESCE(CASE WHEN p.userId IN :following THEN p.postTime ELSE '1900-01-01' END)) DESC;"
			,countQuery = "SELECT count(*) "
					+ "FROM ForumPost p LEFT JOIN ForumPostLike pl ON p.postId = pl.postId LEFT JOIN ForumPostForward pf ON p.postId = pf.postId "
					+ "WHERE p.groupId IS NULL  AND p.publicStatus = 'public' "
					+ "AND (p.userId in :following OR pl.userId in :following OR pf.userId in :following ) "
					+ "GROUP by p.postId,p.userId,p.postContent,p.groupId,p.postTime ORDER BY GREATEST(COALESCE(CASE WHEN pf.userId IN :following THEN pf.forwardTime ELSE '1900-01-01' END), COALESCE(CASE WHEN pl.userId IN :following THEN pl.likeTime ELSE '1900-01-01' END), COALESCE(CASE WHEN p.userId IN :following THEN p.postTime ELSE '1900-01-01' END)) DESC;"
					,nativeQuery = true)
	Page<ForumPost> findNewPostWithFollowIn(@Param("following")List<Integer> following, Pageable pageable);
	
	////////////////filter by users owner and forward (n) (show post) /////////////////
	
	@Query(value="from ForumPost p left join ForumPostForward pf ON p.postId = pf.forwardedPost where p.publicStatus = 'public' and p.group IS NULL AND (p.postUser = ?1 OR pf.forwardFromUser = ?1) GROUP by p.postId "
			+ " Order by GREATEST(COALESCE(CASE WHEN pf.forwardFromUser = ?1 THEN pf.forwardTime ELSE '1900-01-01' END),COALESCE(CASE WHEN p.postUser = ?1 THEN p.postTime ELSE '1900-01-01' END)) desc , p.postTime desc ")
	Page<ForumPost> findByPostUserOwerAndForward(ForumUser user,Pageable pgb);
	
	////////////////filter by users media (n) (show post) /////////////////
	
	@Query(value="from ForumPost p left join ForumPostPhoto pp "
			+ "ON p.postId = pp.post where p.publicStatus = 'public' and p.group IS NULL and p.postUser = ?1 and pp.post is not null GROUP by p.postId Order by p.postTime desc ")
	Page<ForumPost> findByPostUserOwerHavePhoto(ForumUser user,Pageable pgb);
	
	//////////////// filter by users comment (n) (show post) /////////////////
	
	@Query(value="from ForumPost p right join ForumPostCommentedPost pc "
			+ "ON p.postId = pc.childPost where p.publicStatus = 'public' and p.group IS NULL and p.postUser = ?1 Order by p.postTime desc ")
	Page<ForumPost> findByPostUserOwerIsComment(ForumUser user,Pageable pgb);
	
	//////////////// filter by users like (n) (show post) /////////////////
	
	@Query(value="from ForumPost p right join ForumPostLike pl "
			+ "ON p.postId = pl.likedPost where p.publicStatus = 'public' and p.group IS NULL and pl.likeFromUser = ?1 ORDER BY pl.likeTime DESC")
	Page<ForumPost> findByPostUserLike(ForumUser user,Pageable pgb);
	
	//////////////// filter by user collect (n) (show post) /////////////////
	
	@Query(value="from ForumPost p right join ForumPostCollect pc "
			+ "ON p.postId = pc.collectedPost where p.publicStatus = 'public' and p.group IS NULL and pc.collectFromUser = ?1  ORDER BY pc.collectTime DESC")
	Page<ForumPost> findByPostUserCollect(ForumUser user,Pageable pgb);

	//////////////// filter by user private (n) (show post) /////////////////
	
	@Query(value="from ForumPost p where p.postUser = ?1 and p.publicStatus = 'private' ORDER BY p.postTime DESC ")
	Page<ForumPost> findByPostUserPrivate(ForumUser user,Pageable pgb);
	
	//////////////// filter by have report (n+h) (show post) /////////////////
	
	@Query(value="FROM ForumPost p RIGHT JOIN ForumPostReport pr ON p.postId = pr.post "
			+ "WHERE pr.reportVerify.reportVerifyId in ?1   AND p.publicStatus = 'public'  GROUP BY p.postId ORDER BY MAX(pr.reportTime) DESC")
	List<ForumPost> findByPostWithReport(List<Integer> verifyIdArray);
	
	@Query(value="FROM ForumPost p RIGHT JOIN ForumPostReport pr ON p.postId = pr.post "
			+ "WHERE pr.reportVerify.reportVerifyId in ?1  AND p.publicStatus = 'public'  GROUP BY p.postId ORDER BY MAX(pr.reportTime) DESC")
	Page<ForumPost> findByPostWithReportWithPage(List<Integer> verifyIdArray,Pageable pgb);
	
	//////////////// filter by search (n+h) (show post) /////////////////
	
	@Query(value="FROM ForumPost p JOIN ForumPostTag pt ON p.postId = pt.post JOIN ForumTag t ON t.tagId = pt.tag JOIN ForumUser u ON u.user = p.postUser "
			+ "WHERE ( p.postContent like %?1% or t.tagName like %?1% or u.name like  %?1% ) AND p.group IS NULL AND p.publicStatus = 'public' group by p.postId ")
	Page<ForumPost> findByGroupIsNullBySearch( String searchString,Pageable pgb);
	
	@Query(value="SELECT post.*,likeAmount + forwardAmount + commentAmount AS totalInteractions "
			+ "FROM (SELECT p.*,COUNT(DISTINCT pl.userId) AS likeAmount,COUNT(DISTINCT pf.userId) AS forwardAmount,COUNT(DISTINCT pc.childPostId) AS commentAmount "
			+ "FROM ForumPost p LEFT JOIN ForumPostLike pl ON p.postId = pl.postId LEFT JOIN ForumPostForward pf ON p.postId = pf.postId LEFT JOIN ForumPostCommentedPost pc ON p.postId = pc.parentPostId JOIN ForumPostTag pt ON p.postId = pt.postId "
			+ "JOIN ForumTag t ON t.tagId = pt.tagId JOIN ForumUser u ON u.userId = p.userId "
			+ "WHERE ( p.postContent like %?3% or t.tagName like %?3% or u.forumUserName like  %?3% ) AND (p.postTime BETWEEN ?1 AND ?2) AND p.groupId IS NULL AND p.publicStatus = 'public'"
			+ "GROUP BY p.postId, p.userId, p.postContent, p.groupId, p.postTime) AS post ORDER by (likeAmount + forwardAmount + commentAmount) desc  ,postTime desc"
			,countQuery = "SELECT count(*) "
					+ "FROM (SELECT p.*,COUNT(DISTINCT pl.userId) AS likeAmount,COUNT(DISTINCT pf.userId) AS forwardAmount,COUNT(DISTINCT pc.childPostId) AS commentAmount "
					+ "FROM ForumPost p LEFT JOIN ForumPostLike pl ON p.postId = pl.postId LEFT JOIN ForumPostForward pf ON p.postId = pf.postId LEFT JOIN ForumPostCommentedPost pc ON p.postId = pc.parentPostId JOIN ForumPostTag pt ON p.postId = pt.postId "
					+ "JOIN ForumTag t ON t.tagId = pt.tagId JOIN ForumUser u ON u.userId = p.userId "
					+ "WHERE ( p.postContent like %?3% or t.tagName like %?3% or  u.forumUserName like  %?3% ) AND (p.postTime BETWEEN ?1 AND ?2) AND p.groupId IS NULL AND p.publicStatus = 'public' "
					+ "GROUP BY p.postId, p.userId, p.postContent, p.groupId, p.postTime) AS post ORDER by (likeAmount + forwardAmount + commentAmount) desc ,postTime desc "
			,nativeQuery = true)
	Page<ForumPost> findHotPostInTimePeriodBySearch(Date yesterday, Date today,String searchString,Pageable pageable);
	
	//////////////// filter by users in group (n+h) (show post) /////////////////
	
	Page<ForumPost> findByGroup(ForumGroup group,Pageable pgb);
	
	@Query(value="SELECT post.*,likeAmount + forwardAmount + commentAmount AS totalInteractions "
			+ "FROM (SELECT p.*,COUNT(DISTINCT pl.userId) AS likeAmount,COUNT(DISTINCT pf.userId) AS forwardAmount,COUNT(DISTINCT pc.childPostId) AS commentAmount "
			+ "FROM ForumPost p LEFT JOIN ForumPostLike pl ON p.postId = pl.postId LEFT JOIN ForumPostForward pf ON p.postId = pf.postId LEFT JOIN ForumPostCommentedPost pc ON p.postId = pc.parentPostId "
			+ "WHERE p.postTime BETWEEN ?1 AND ?2 AND p.groupId = ?3 "
			+ "GROUP BY p.postId, p.userId, p.postContent, p.groupId, p.postTime) AS post ORDER by likeAmount + forwardAmount + commentAmount desc "
			,countQuery = "SELECT count(*) "
					+ "FROM (SELECT p.*,COUNT(DISTINCT pl.userId) AS likeAmount,COUNT(DISTINCT pf.userId) AS forwardAmount,COUNT(DISTINCT pc.childPostId) AS commentAmount "
					+ "FROM ForumPost p LEFT JOIN ForumPostLike pl ON p.postId = pl.postId LEFT JOIN ForumPostForward pf ON p.postId = pf.postId LEFT JOIN ForumPostCommentedPost pc ON p.postId = pc.parentPostId "
					+ "WHERE p.postTime BETWEEN ?1 AND ?2 AND p.groupId = ?3 "
					+ "GROUP BY p.postId, p.userId, p.postContent, p.groupId, p.postTime) AS post ORDER by (likeAmount + forwardAmount + commentAmount) desc"
			,nativeQuery = true)
	Page<ForumPost> findHotPostInTimePeriodByGroup(Date yesterday, Date today,Integer groupId,Pageable pageable);

	ForumPost findByPostIdAndPublicStatus(Integer postId, String publicStatusString);
	
	///////////////////////////// show comments belong a post (show post)///////////////////////////////


	
	
}
