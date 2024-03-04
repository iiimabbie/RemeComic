package tw.com.remecomic.userA.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.remecomic.userA.model.bean.UserA;
import tw.com.remecomic.userA.model.bean.UserABadgeUser;
import tw.com.remecomic.userA.model.dao.UserABadgeDao;
import tw.com.remecomic.userA.model.dao.UserABadgeUserDao;
import tw.com.remecomic.userA.model.dao.UserADao;
import tw.com.remecomic.userA.model.dto.UserDto;

@Service
@Transactional
public class UserABadgeUserService {

	@Autowired
	private UserABadgeDao bDao;

	@Autowired
	private UserABadgeUserDao ubDao;
	@Autowired
	private UserADao uDao;

	public List<UserABadgeUser> findUserBadge(@Param("userId") Integer userId) {
		return ubDao.findUser(userId);
	}

	public void deleteUserBadge(@Param("userId") Integer userId) {
		ubDao.deleteBadge(userId);
	}

//	public void saveUserBadge(Integer userId, List<UserABadge> userABadge) {
//		for (UserABadgeUser badgeUser : userABadge) {
//			badgeUser.setUserId(userId);
//		}
//
//		ubDao.saveAll(userABadgeUser);
//	}

	public UserDto test(Map<String, Object> userBadgeMap) {
		Integer mapUserId = (Integer) userBadgeMap.get("userId");
		ubDao.deleteBadge(mapUserId);
		UserA user = uDao.findById(mapUserId).get();

		List<Integer> mapUserBadge = (List<Integer>) userBadgeMap.get("userBadgeId");

		List<UserABadgeUser> ubIdObjList = mapUserBadge.stream()
				.map(userBadgeId -> new UserABadgeUser(user, bDao.findById(userBadgeId).get()))
				.collect(Collectors.toList());
		ubDao.saveAll(ubIdObjList);

		return UserDto.toDto(user);

	}

}
