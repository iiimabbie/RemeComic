package tw.com.remecomic.forum.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.forum.model.bean.ForumGroup;
import tw.com.remecomic.forum.model.bean.ForumMessage;

@Data
@NoArgsConstructor
@Component
public class ForumMessageDto {
	private Integer messageId;
	
	private Integer userId;
	private String userName;
	private String userPhoto;
	
	private Integer acceptedUserId;
	private String acceptedUserName;
	private String acceptedUserPhoto;
	
	private Integer groupId;
	private String groupName;
	
	private String messageContent;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
	private Date sendTime;
	
	private Integer messageStatus;
	
	public static ForumMessageDto sendToFrontend(ForumMessage msg){
		ForumMessageDto msgDto = new ForumMessageDto();
		msgDto.setMessageId(msg.getMessageId());
		
		msgDto.setUserId(msg.getUser().getUserId());
		msgDto.setUserName(msg.getUser().getName());
		msgDto.setUserPhoto(msg.getUser().getUserPhoto());
		
		if(msg.getAcceptedUser()!=null) {
			msgDto.setAcceptedUserId(msg.getAcceptedUser().getUserId());
			msgDto.setAcceptedUserName(msg.getAcceptedUser().getName());
			msgDto.setAcceptedUserPhoto(msg.getAcceptedUser().getUserPhoto());			
		}else if(msg.getGroup()!=null) {
			msgDto.setGroupId(msg.getGroup().getGroupId());
			msgDto.setGroupName(msg.getGroup().getGroupName());
		}
		
		msgDto.setMessageContent(msg.getMessageContent());
		msgDto.setSendTime(msg.getSendTime());
		msgDto.setMessageStatus(msg.getMessageStatus());
		return msgDto;
	}
	
}
