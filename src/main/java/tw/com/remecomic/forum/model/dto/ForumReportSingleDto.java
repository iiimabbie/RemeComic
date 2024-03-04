package tw.com.remecomic.forum.model.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.forum.model.bean.ForumPostReport;

@Data
@NoArgsConstructor
@Component
public class ForumReportSingleDto {
	
	private Integer reportId;
	
	private Integer postId;
	
	private Integer postUserId;//方便之後查詢該用戶是否該ban post
	private Integer postUserHaveBeenDeletedCount;
	private Integer reportUserId;//方便之後查詢該用戶是否該ban report
	
	private Integer postHaveApprovedReportCount;//方便之後查詢該貼文是否該被隱藏
	private Integer postVerifyHidden;//方便之後查詢該貼文是否該被刪除

	public static ForumReportSingleDto sendToFrontend(ForumPostReport report) {
		ForumReportSingleDto reportDto = new ForumReportSingleDto();
		reportDto.setReportId(report.getReportId());
		reportDto.setPostId(report.getPost().getPostId());
		
		reportDto.setPostUserId(report.getPost().getPostUser().getUserId());
		reportDto.setPostUserHaveBeenDeletedCount(report.getPost().getPostUser().getDeletedPostsCount());
		reportDto.setReportUserId(report.getUser().getUserId());
		
		List<ForumPostReport> reportsHaveApprovedlist = report.getPost().getReports().stream().filter(everyReport->everyReport.getReportVerify().getReportVerifyId()==2).toList();
		reportDto.setPostHaveApprovedReportCount(reportsHaveApprovedlist.size());
		
		reportDto.setPostVerifyHidden(report.getPost().getVerifyHidden());
		
		return reportDto;
	}

}
