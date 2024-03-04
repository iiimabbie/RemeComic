package tw.com.remecomic.forum.model.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.forum.model.bean.ForumPost;
import tw.com.remecomic.forum.model.bean.ForumPostPhoto;
import tw.com.remecomic.forum.model.bean.ForumPostReport;
import tw.com.remecomic.forum.model.bean.ForumPostTag;

@Data
@NoArgsConstructor
@Component
public class ForumReportForPostDto {
	
	private Integer postId;
	
	private Integer postUserId;
	private String postUserName;
	private String postUserPhoto;
	
	private String postContent;
	
	private Integer groupId;
	private String groupName;
	
//	private Integer verifyHidden;
//	private Integer haveApprovedReportCount;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
	private Date postTime;
	
	private Map<Integer,String> postTagMap;
	private List<ForumPostPhoto> photos;
	
	// Map<Integer(reason id), List<ForumReportFormatDto>>
	private Map<Integer, List<ForumReportFormatDto>> reportReasonMap;


	public static ForumReportForPostDto sendToFrontend(ForumPost post,List<Integer> verifyIdList) {
		ForumReportForPostDto reportDto = new ForumReportForPostDto();
		
		reportDto.setPostId(post.getPostId());
		reportDto.setPostUserId(post.getPostUser().getUserId());
		reportDto.setPostUserName(post.getPostUser().getName());
		reportDto.setPostUserPhoto(post.getPostUser().getUserPhoto());
		reportDto.setPostContent(post.getPostContent());
		if(post.getGroup()!=null) {
			reportDto.setGroupId(post.getGroup().getGroupId());
			reportDto.setGroupName(post.getGroup().getGroupName());
		}		
		reportDto.setPostTime(post.getPostTime());
//		reportDto.setVerifyHidden(post.getVerifyHidden());
		
		// tag
		Map<Integer,String> tagsMap = new HashMap<>();
		reportDto.setPostTagMap(tagsMap);
		if(post.getTags()!=null) {
			for(ForumPostTag tag : post.getTags()) {
				tagsMap.put(tag.getTag().getTagId(), tag.getTag().getTagName());
			}			
		}
		// photo
		if(post.getPhotos()!=null) {
			reportDto.setPhotos(post.getPhotos().stream().toList());		
		}
		
		Map<Integer, List<ForumReportFormatDto>> reasonMap = new HashMap<>();
		reportDto.setReportReasonMap(reasonMap);
		
		Collection<ForumPostReport> reports = post.getReports();
		
		if (reports != null) {
			
//			List<ForumPostReport> reportsHaveApprovedlist = reports.stream().filter(report->report.getReportVerify().getReportVerifyId()==2).toList();
//			reportDto.setHaveApprovedReportCount(reportsHaveApprovedlist.size());
			
			List<ForumPostReport> reportHaveVerifyIdList = reports.stream().filter(report->verifyIdList.contains(report.getReportVerify().getReportVerifyId())).toList();
			for (ForumPostReport report : reportHaveVerifyIdList) {
				Integer reasonId = report.getReason().getReasonId();
				if (!reasonMap.containsKey(reasonId)) {
					reasonMap.put(reasonId, new ArrayList<ForumReportFormatDto>());
				}
				reasonMap.get(reasonId).add(ForumReportFormatDto.sendToFrontend(report));
			}
		}
		return reportDto;
	}

}
