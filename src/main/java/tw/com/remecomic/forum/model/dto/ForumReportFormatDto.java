package tw.com.remecomic.forum.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.forum.model.bean.ForumPostReport;

@Data
@NoArgsConstructor
@Component
public class ForumReportFormatDto {
	
	private Integer reportId;
	private Integer postId;
	private Integer reportUserId;
	private String reportUserName;
	private String reportUserPhoto;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
	private Date reportTime;
	
	private Integer reasonId;
	private String reasonContent;
	private Integer verifyId;
	private String verifyStatus;
	
	public static ForumReportFormatDto sendToFrontend(ForumPostReport report) {
		ForumReportFormatDto reportFormatDto = new ForumReportFormatDto();
		reportFormatDto.setReportId(report.getReportId());
		reportFormatDto.setPostId(report.getPost().getPostId());
		reportFormatDto.setReportUserId(report.getUser().getUserId());
		reportFormatDto.setReportUserName(report.getUser().getName());
		reportFormatDto.setReportUserPhoto(report.getUser().getUserPhoto());
		reportFormatDto.setReportTime(report.getReportTime());
		reportFormatDto.setReasonId(report.getReason().getReasonId());
		reportFormatDto.setReasonContent(report.getReason().getReasonContent());
		reportFormatDto.setVerifyId(report.getReportVerify().getReportVerifyId());
		reportFormatDto.setVerifyStatus(report.getReportVerify().getStatus());
		return reportFormatDto;
	}
	
}
