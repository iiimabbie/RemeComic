package tw.com.remecomic.forum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumPostCollect;
import tw.com.remecomic.forum.model.bean.ForumPostForward;
import tw.com.remecomic.forum.model.bean.ForumPostLike;
import tw.com.remecomic.forum.model.bean.ForumPostReport;
import tw.com.remecomic.forum.model.bean.ForumReason;
import tw.com.remecomic.forum.model.bean.ForumReportVerify;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.dao.ForumPostCollectDao;
import tw.com.remecomic.forum.model.dao.ForumPostDao;
import tw.com.remecomic.forum.model.dao.ForumPostForwardDao;
import tw.com.remecomic.forum.model.dao.ForumPostLikeDao;
import tw.com.remecomic.forum.model.dao.ForumPostReportDao;
import tw.com.remecomic.forum.model.dao.ForumPostTagDao;
import tw.com.remecomic.forum.model.dao.ForumReasonDao;
import tw.com.remecomic.forum.model.dao.ForumReportVerifyDao;
import tw.com.remecomic.forum.model.dao.ForumTagDao;
import tw.com.remecomic.forum.model.dto.ForumReportForPostDto;
import tw.com.remecomic.forum.model.dto.ForumReportFormatDto;

@Service
@Transactional
public class ForumPostInteractiveService {
	@Autowired
	private ForumReasonDao reasonDao;
	@Autowired
	private ForumReportVerifyDao reportVerifyDao;
	@Autowired
	private ForumPostReportDao postReportConnDao;
	@Autowired
	private ForumPostDao postDao;
	@Autowired
	private ForumPostLikeDao likeDao;
	@Autowired
	private ForumPostForwardDao forwardDao;
	@Autowired
	private ForumPostCollectDao collectDao;
	@Autowired
	private ForumTagDao tagDao;
	@Autowired
	private ForumPostTagDao postTagConnectionDao;
	
	
	//////////////// like /////////////////
	public ForumPostLike changeLike(ForumPost likedPost,ForumUser likeFromUser) {
		
		ForumPostLike connection = likeDao.findByLikedPostAndLikeFromUser(likedPost, likeFromUser);

		if(connection!=null) {  //取消喜歡成功
			likeDao.delete(connection);
			return null;
		}else {    //成功喜歡該post
			ForumPostLike like = new ForumPostLike();
			like.setLikedPost(likedPost);
			like.setLikeFromUser(likeFromUser);
			ForumPostLike save = likeDao.save(like);
			return save;			
		}
	}
	
	//////////////// forward /////////////////
	public ForumPostForward changeForward(ForumPost forwardedPost,ForumUser forwardFromUser) {
		
		ForumPostForward connection = forwardDao.findByForwardedPostAndForwardFromUser(forwardedPost, forwardFromUser);

		if(connection!=null) {  //取消喜歡成功
			forwardDao.delete(connection);
			return null;
		}else {    //成功喜歡該post
			ForumPostForward forward = new ForumPostForward();
			forward.setForwardedPost(forwardedPost);
			forward.setForwardFromUser(forwardFromUser);
			ForumPostForward save = forwardDao.save(forward);
			return save;			
		}
	}
	//////////////// collect /////////////////
	public ForumPostCollect changeCollect(ForumPost collectedPost,ForumUser FromUser) {
		
		ForumPostCollect connection = collectDao.findByCollectedPostAndCollectFromUser(collectedPost, FromUser);

		if(connection!=null) {  //取消喜歡成功
			collectDao.delete(connection);
			return null;
		}else {    //成功喜歡該post
			ForumPostCollect collect = new ForumPostCollect();
			collect.setCollectedPost(collectedPost);
			collect.setCollectFromUser(FromUser);
			ForumPostCollect save = collectDao.save(collect);
			return save;			
		}
	}
	//////////////// report /////////////////
	public List<Map<String, Object[]>> getAllReasons() {
		List<Map<String, Object[]>> allList = reasonDao.findAllReasons();
		return allList;
	}
	public List<ForumReportVerify> getAllVerify() {
		List<ForumReportVerify> allList = reportVerifyDao.findAll();
		return allList;
	}
	public List<ForumReportVerify> getVerifyById(List<Integer> verifyIdList) {
		List<ForumReportVerify> allList = reportVerifyDao.findAllById(verifyIdList);
		return allList;
	}
	public List<ForumReportForPostDto> getAllReportsDto() {
		List<ForumPost> allList = postDao.findAll();
		List<Integer> verifyIdList = new ArrayList<>();
		verifyIdList.add(1);
		verifyIdList.add(2);
		verifyIdList.add(3);		
		return allList.stream().map(post->ForumReportForPostDto.sendToFrontend(post,verifyIdList)).toList();
	}
	public List<ForumPostReport> getAllReports() {
		List<ForumPostReport> allList = postReportConnDao.findAll();
		return allList;
	}
	public List<ForumPostReport> getAllReportsByVerify(List<ForumReportVerify> verifyList) {
		List<ForumPostReport> allList = postReportConnDao.findByReportVerifyIn(verifyList);
		return allList;
	}
//	public List<ForumPostReport> getAllReportsByPost(List<ForumPost> postList) {
//		List<ForumPostReport> allList = postReportConnDao.findByPostIn(postList);
//		return allList;
//	}
	public List<ForumReportFormatDto> getAllReportsTableFormat() {
		List<ForumPostReport> allList = postReportConnDao.findAll();
		return allList.stream().map(ForumReportFormatDto::sendToFrontend).toList();
	} 
	public ForumReason findReasonByReasonId(Integer reasonId) {
		Optional<ForumReason> findReasonResult = reasonDao.findById(reasonId);
		if(findReasonResult.isPresent()) {
			return findReasonResult.get();
		}
		return null;
	}
	public ForumReportForPostDto addOneReport(ForumPost post,ForumUser user,ForumReason reason) {
		ForumPostReport connection = postReportConnDao.findByPostAndUser(post, user);
		if(connection==null) { //該使用者還沒檢舉過這則貼文，所以可以檢舉
			ForumPostReport newReport = new ForumPostReport();
			newReport.setPost(post);
			newReport.setUser(user);
			newReport.setReason(reason);
			newReport.setReportVerify(reportVerifyDao.findById(1).get());
			ForumPostReport save = postReportConnDao.save(newReport);
			List<Integer> verifyIdList = new ArrayList<>();
			verifyIdList.add(1);
			verifyIdList.add(2);
			verifyIdList.add(3);
			return ForumReportForPostDto.sendToFrontend(save.getPost(),verifyIdList);
		}
		return null; //該使用者已經檢舉過這則貼文
	}
	public List<ForumReportForPostDto> changeReportStatus(Integer targetStatus,Integer[] targetReportId) {
		List<ForumPostReport> reports = postReportConnDao.findByReportIdIn(targetReportId);
		Optional<ForumReportVerify> findVerifyIdResult = reportVerifyDao.findById(targetStatus);
		if(reports!=null && findVerifyIdResult.isPresent()) {
			for(ForumPostReport report : reports) {
				report.setReportVerify(findVerifyIdResult.get());
			}
			List<Integer> verifyIdList = new ArrayList<>();
			verifyIdList.add(1);
			verifyIdList.add(2);
			verifyIdList.add(3);
			return reports.stream().map(ForumPostReport::getPost).map(post->ForumReportForPostDto.sendToFrontend(post, verifyIdList)).toList();
		}else{
			return null;
		}
	}
	public boolean deleteReprorts(Integer[] targetReportId) {
		List<ForumPostReport> reports = postReportConnDao.findByReportIdIn(targetReportId);
		if(reports!=null) {
			postReportConnDao.deleteAll(reports);
			return true;
		}return false;
	}
}
