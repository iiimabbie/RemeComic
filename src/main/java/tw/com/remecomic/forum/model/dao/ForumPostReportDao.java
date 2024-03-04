package tw.com.remecomic.forum.model.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumPostReport;
import tw.com.remecomic.forum.model.bean.ForumReportVerify;
import tw.com.remecomic.forum.model.bean.ForumUser;

@Repository
public interface ForumPostReportDao extends JpaRepository<ForumPostReport, Integer> {
	
	public ForumPostReport findByPostAndUser(ForumPost post,ForumUser user);
	
	public List<ForumPostReport> findByReportIdIn(Integer[] targetReportId);
	
	@Query(value="SELECT u.userId userId,u.name userName,u.userPhoto userPhoto,count(*) rejectTimes FROM `ForumPostReport` pr JOIN ForumUser u on u.userId = pr.user "
			+ "WHERE pr.reportTime BETWEEN ?2 and ?3 AND "
			+ "u.userId In ?1 AND pr.reportVerify = 3 GROUP BY u.userId ")
	List<Map<String,Object[]>> findByUserReportRejectInTimePeriod(List<Integer> userIdList,Date past,Date now);
	
	List<ForumPostReport> findByReportVerifyIn(List<ForumReportVerify> verifyList);
	
	List<ForumPostReport> findByPostInAndReportVerifyIn(List<ForumPost> postList,List<ForumReportVerify> verifyList);
}
