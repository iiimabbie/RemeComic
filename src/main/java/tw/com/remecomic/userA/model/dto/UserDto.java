package tw.com.remecomic.userA.model.dto;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.userA.model.bean.UserA;
import tw.com.remecomic.userA.model.bean.UserAAttendance;
import tw.com.remecomic.userA.model.bean.UserABadge;
import tw.com.remecomic.userA.model.bean.UserAStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	public Integer userId;
	public String name;
	public String gender;
	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
	public Date birthDate;
	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
	public Date registerDate;
	public String userPhoto;
	public String email;
	public Integer userAtt;

	@JsonIgnoreProperties("users")
	public UserAStatus status;

	@JsonIgnoreProperties("users")
	public List<UserABadge> badges;

	@JsonIgnoreProperties("users")
	public List<UserAAttendance> userAttendance;

	public static UserDto toDto(UserA userA) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userA, userDto);
		return userDto;
	}

	public void updateUserA(UserA userA) {
		BeanUtils.copyProperties(this, userA);
	}

	public static UserA toEntity(UserDto userDto) {
		UserA userA = new UserA();
		BeanUtils.copyProperties(userDto, userA);
		return userA;
	}

//	public List<UserDto> objToSimpleDto(List<Map<String, Object>> userObject){
//		List<UserDto> userList = new ArrayList();
//		
//		UserAStatusDao.findById(statusId);
//		for(Map<String, Object> user : userObject){	
//			String name = (String)user.get("name");
//			UserDto userDto = new UserDto();
//			userDto.setName(name);
//			userDto.setBirthDate((Date)user.get("birthDate"));
//			//set the rest of the Dto value;
//			List<String> userNames = userList.stream().map(UserDto::getName()).collect(Collectors.toList());
//			if(name includes(userNames)) {
//				userDto.getBadgeNames().add(user.get("bageName"));
//			}else {
//				userList.add(userDto);
//			}
//			
//			
//		}
//
//	return null;
//}

//	public static UserDto fromGoogleData(String email, String name, String gender, Date birthDate, String userPhoto) {
//		UserDto userDto = new UserDto();
//		userDto.setEmail(email);
//		userDto.setName(name);
//		userDto.setGender(gender);
//		userDto.setBirthDate(birthDate);
//		userDto.setUserPhoto(userPhoto);
//		return userDto;
//	}

}
