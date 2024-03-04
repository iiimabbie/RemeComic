package tw.com.remecomic.userA.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.remecomic.userA.model.bean.UserABadge;
import tw.com.remecomic.userA.model.dao.UserABadgeDao;
import tw.com.remecomic.userA.model.dto.BadgeSimpleDto;

@Transactional
@Service
public class UserABadgeService {

	@Autowired
	private UserABadgeDao bDao;

	/***
	 * 以ID尋找Badge </br>
	 * 
	 * @param badgId
	 * @return Optional (Badge所有有關聯的資料)
	 */
	public Optional<UserABadge> findById(Integer badgId) {

		return bDao.findById(badgId);
	}

	/***
	 * 以ID尋找Badge </br>
	 * 
	 * @param badgId
	 * @return Badge (僅Badge的基本資料)
	 */
	public BadgeSimpleDto findOnlyBadgeById(Integer badgId) {
		Optional<UserABadge> userABadge = bDao.findById(badgId);
		if (userABadge.isPresent()) {
			return BadgeSimpleDto.toDto(userABadge.get());
		}
		return null;
	}

	/***
	 * 尋找所有Badge(包含User) </br>
	 * 
	 * @return List<UserABadge>
	 */
	public List<UserABadge> findAll() {
		return bDao.findAll();
	}

	/***
	 * 尋找所有Badge </br>
	 * 
	 * @return List<UserABadge>
	 */
	public List<BadgeSimpleDto> findOnlyBadgeAll() {
		List<UserABadge> badgeList = bDao.findAll();
		List<BadgeSimpleDto> badgeDtoList = new ArrayList<>();
		for (UserABadge badge : badgeList) {
			BadgeSimpleDto badgeDto = new BadgeSimpleDto();
			BeanUtils.copyProperties(badge, badgeDto);
			badgeDtoList.add(badgeDto);
		}
		return badgeDtoList;
	}

	/***
	 * 新增Badge資料 </br>
	 * 
	 * @param
	 * @return
	 */
	public BadgeSimpleDto addBadge(BadgeSimpleDto badgeDto) {
		UserABadge badge = BadgeSimpleDto.toEntity(badgeDto);
		UserABadge savedBadge = bDao.save(badge);
		return BadgeSimpleDto.toDto(savedBadge);
	}

	/***
	 * 修改Badge資料 </br>
	 * 
	 * @param
	 * @return
	 */
	public BadgeSimpleDto updateBadge(BadgeSimpleDto badgeDto) {
		if (badgeDto.getBadgeId() != null) {
			UserABadge existingBadge = bDao.findById(badgeDto.getBadgeId()).orElse(null);

			if (existingBadge != null) {
				if (badgeDto.getBadgeName() != null) {
					existingBadge.setBadgeName(badgeDto.getBadgeName());
				}
				if (badgeDto.getBadgePhoto() != null) {
					existingBadge.setBadgePhoto(badgeDto.getBadgePhoto());
				}
				UserABadge updatedBadge = bDao.save(existingBadge);
				return BadgeSimpleDto.toDto(updatedBadge);
			}
		}
		return null;
	}

	/***
	 * 就基本的刪啊 </br>
	 * 
	 * @param badgeId
	 */
	public void deleteById(Integer badgeId) {
		bDao.deleteById(badgeId);
	}

}
