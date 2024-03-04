package tw.com.remecomic.userA.model.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleDto {
	public Integer userId;
	public String name;
	public String gender;
	public Date birthDate;
	public Date registerDate;
	public String userPhoto;
	public String email;

	public static UserSimpleDto toDto(UserA userA) {
		UserSimpleDto userDto = new UserSimpleDto();
		BeanUtils.copyProperties(userA, userDto);
		return userDto;
	}

	public void updateUserA(UserA userA) {
		BeanUtils.copyProperties(this, userA);
	}

	public static UserA toEntity(UserSimpleDto userDto) {
		UserA userA = new UserA();
		BeanUtils.copyProperties(userDto, userA);
		return userA;
	}

	public static UserSimpleDto fromGoogleData(String email, String name, String gender, Date birthDate,
			String userPhoto) {
		UserSimpleDto userSimpleDto = new UserSimpleDto();
		userSimpleDto.setEmail(email);
		userSimpleDto.setName(name);
		userSimpleDto.setGender(gender);
		userSimpleDto.setBirthDate(birthDate);
		userSimpleDto.setUserPhoto(userPhoto);
		return userSimpleDto;
	}

}
