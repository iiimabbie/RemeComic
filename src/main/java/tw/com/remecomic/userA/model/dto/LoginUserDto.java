package tw.com.remecomic.userA.model.dto;

import org.springframework.beans.BeanUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

	public Integer userId;
	public String name;
	public String userPhoto;
	public String email;

	public static LoginUserDto toDto(UserA userA) {
		LoginUserDto loginUserDto = new LoginUserDto();
		BeanUtils.copyProperties(userA, loginUserDto);
		return loginUserDto;
	}

	public static UserA toEntity(LoginUserDto loginUserDto) {
		UserA userA = new UserA();
		BeanUtils.copyProperties(loginUserDto, userA);
		return userA;
	}

	public static LoginUserDto toSimpleDto(UserA user) {
		LoginUserDto loginDto = new LoginUserDto();
		loginDto.setUserId(user.getUserId());
		loginDto.setEmail(user.getEmail());
		loginDto.setUserPhoto(user.getUserPhoto());
		loginDto.setName(user.getName());
		return loginDto;
	}

}
