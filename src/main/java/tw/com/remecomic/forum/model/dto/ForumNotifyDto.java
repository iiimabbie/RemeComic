package tw.com.remecomic.forum.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.forum.model.bean.ForumNotify;

@Data
@NoArgsConstructor
@Component
public class ForumNotifyDto {
	private Integer notifyId;
	
	private Integer typeId;
	private String content;//depend on notify type to change
	
	private String targetTable;
	private Integer tableId;
	
	private String activeUserName;
	private String activeUserPhoto;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
	private Date activeTime;
	
	private Integer notifyStatus;
	
	public static ForumNotifyDto sendToFrontend(ForumNotify notify) {
		ForumNotifyDto notifyDto = new ForumNotifyDto();
		
		String userName = "someone";
		//照搬的資料
		notifyDto.setNotifyId(notify.getNotifyId());
		Integer typeId = notify.getNotifyType().getNotifyTypeId();
		notifyDto.setTypeId(typeId);
		Integer tableId = notify.getTableId();
		notifyDto.setTableId(tableId);
		if(notify.getActiveUser()!=null) {
			userName = notify.getActiveUser().getName();
			notifyDto.setActiveUserName(userName);
			notifyDto.setActiveUserPhoto(notify.getActiveUser().getUserPhoto());			
		}
		notifyDto.setActiveTime(notify.getActiveTime());
		notifyDto.setNotifyStatus(notify.getNotifyStatus());
		
		//要進一步判斷的資料
		String typeContent = notify.getNotifyType().getTypeContent();
		String completeNotify = "";
		String targetTable ="";
		switch(typeId) {
		case 1: //post is liked----------------table id => post id
			completeNotify+="your "+typeContent+" by "+userName;
			targetTable="post";
			System.out.println("1="+completeNotify);
			break;
		case 2: //post be forward--------------table id => post id
			completeNotify+="your "+typeContent+" by "+userName;
			targetTable="post";
			System.out.println("2="+completeNotify);
			break;
		case 3: //post received new comment----table id => post id
			completeNotify+="your "+typeContent+" from "+userName;
			targetTable="post";
			System.out.println("3="+completeNotify);
			break;
		case 4: //friend has new post----------table id => post id
			completeNotify+="your friend "+userName+" has new post";
			targetTable="post";
			System.out.println("4="+completeNotify);
			break;
		case 5: //have new message-------------table id => msg id
			completeNotify+="you "+typeContent+" from "+userName;
			targetTable="message";
			System.out.println("5="+completeNotify);
			break;
		case 6: //has followed-----------------table id => use id
			completeNotify+=userName+" "+typeContent+" you";
			targetTable="user";
			System.out.println("6="+completeNotify);
			break;
		}
		notifyDto.setContent(completeNotify);
		notifyDto.setTargetTable(targetTable);
		return notifyDto;
		
	}
	
}
