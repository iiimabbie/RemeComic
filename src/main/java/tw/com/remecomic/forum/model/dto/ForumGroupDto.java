package tw.com.remecomic.forum.model.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.forum.model.bean.ForumGroup;

@Data
@NoArgsConstructor
@Component
public class ForumGroupDto {
	private Integer groupId;
	private String groupName;
	private Integer managerUserId;
	private Integer MemberAmount;
	public static ForumGroupDto sendToFrontend(ForumGroup group){
		ForumGroupDto groupDto = new ForumGroupDto();
		groupDto.setGroupId(group.getGroupId());
		groupDto.setGroupName(group.getGroupName());
		groupDto.setManagerUserId(group.getManagerUser().getUserId());
		groupDto.setMemberAmount(group.getUsers()==null?0:group.getUsers().size());
		return groupDto;
	}
}
