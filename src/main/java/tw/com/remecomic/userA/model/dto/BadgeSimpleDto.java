package tw.com.remecomic.userA.model.dto;

import org.springframework.beans.BeanUtils;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;
import tw.com.remecomic.userA.model.bean.UserABadge;

@Data
@NoArgsConstructor
public class BadgeSimpleDto {

	public Integer badgeId;
	public String badgeName;
	public String badgePhoto;

	public static BadgeSimpleDto toDto(UserABadge userABadge) {
		BadgeSimpleDto userBadgeDto = new BadgeSimpleDto();
		BeanUtils.copyProperties(userABadge, userBadgeDto);
		return userBadgeDto;
	}

	public void updateBadge(UserA userA) {
		BeanUtils.copyProperties(this, userA);
	}

	public static UserABadge toEntity(BadgeSimpleDto userBadgeDto) {
		UserABadge userABadge = new UserABadge();
		BeanUtils.copyProperties(userBadgeDto, userABadge);
		return userABadge;
	}
}
