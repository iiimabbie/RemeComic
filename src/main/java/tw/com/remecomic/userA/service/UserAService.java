package tw.com.remecomic.userA.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.dao.ForumUserDao;
import tw.com.remecomic.money.model.dao.MoneyAccountDao;
import tw.com.remecomic.money.service.MoneyAccountService;
import tw.com.remecomic.userA.model.bean.UserA;
import tw.com.remecomic.userA.model.bean.UserABadge;
import tw.com.remecomic.userA.model.bean.UserAStatus;
import tw.com.remecomic.userA.model.dao.UserAAttendanceDao;
import tw.com.remecomic.userA.model.dao.UserABadgeDao;
import tw.com.remecomic.userA.model.dao.UserABadgeUserDao;
import tw.com.remecomic.userA.model.dao.UserADao;
import tw.com.remecomic.userA.model.dao.UserAStatusDao;
import tw.com.remecomic.userA.model.dto.LoginUserDto;
import tw.com.remecomic.userA.model.dto.UserDto;

@Service
@Transactional
public class UserAService {

	@Autowired
	private UserADao uDao;
	@Autowired
	private UserABadgeDao bDao;
	@Autowired
	private UserAStatusDao sDao;
	@Autowired
	private MoneyAccountDao moneyAccountDao;
	@Autowired
	private UserABadgeUserDao ubDao;
	@Autowired
	private UserAAttendanceDao uaDao;
	@Autowired
	private MoneyAccountService moneyAccountService;
	@Autowired
	private ForumUserDao forumUserDao;

	@PersistenceContext
	private EntityManager entityManager;

	/***
	 * 以ID尋找User(所有資料) </br>
	 */
	public Optional<UserA> findById(Integer userId) {

		return uDao.findById(userId);
	}

	/***
	 * 以ID尋找User(User資料) </br>
	 */
	public UserDto findUserDtoById(Integer userId) {
		Optional<UserA> optionalUser = uDao.findById(userId);
		if (optionalUser.isPresent()) {
			UserA user = optionalUser.get();
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			return userDto;
		}
		return null;
	}

	/***
	 * 尋找所有User(所有資料) </br>
	 */
	public List<UserA> findAll() {
		return uDao.findAll();
	}

	/***
	 * 尋找所有User(User資料) </br>
	 */
	public List<UserDto> findUserDtoAll() {
		List<UserA> userList = uDao.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		for (UserA user : userList) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}

	public List<Integer> findAllUserID() {

		return uDao.findAllUserId();
	}

	/***
	 * 以姓名"模糊搜尋"尋找User </br>
	 */
	public List<UserA> findUserByNameLike(String name) {
		return uDao.findUserByNameLike("%" + name + "%");
	}

	/***
	 * 以"多個或單個"徽章尋找User </br>
	 */
	public List<UserA> findUsersWithBadges(List<Integer> badgeIds) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserA> query = cb.createQuery(UserA.class);
		Root<UserA> rootUser = query.from(UserA.class);

		Join<UserA, UserABadge> badgesJoin = rootUser.join("userBadges");
		query.select(rootUser);
		query.groupBy(rootUser);
		query.having(cb.equal(cb.count(badgesJoin.get("badge")), badgeIds.size()));
		query.where(badgesJoin.get("badge").in(badgeIds));

		return entityManager.createQuery(query).getResultList();
	}

	/***
	 * 新增User 僅帶"基本"資料 </br>
	 */
	public UserDto addUser(UserDto userDto) {
		UserA userA = UserDto.toEntity(userDto);
		userA.setRegisterDate(new Date());
		UserA savedUser = uDao.save(userA);
		// 因為ForumUser資料需要與UserA同步，所以也要建立一筆資料
		ForumUser forumUser = new ForumUser();
		forumUser.setUser(savedUser);
		forumUser.setUserId(userDto.getUserId());
		forumUser.setName(userDto.getName());
		forumUser.setUserPhoto(userDto.getUserPhoto());
		forumUserDao.save(forumUser);

		return UserDto.toDto(savedUser);
	}

	public UserDto updateUserSimple(UserDto userDto) {
		if (userDto.getUserId() != null) {
			UserA existingUser = uDao.findById(userDto.getUserId()).orElse(null);

			// ForumUser需要同步UserA的資料
			Optional<ForumUser> existForumUserResult = forumUserDao.findById(userDto.getUserId());
			if (existForumUserResult.isPresent()) {
				ForumUser existForumUser = existForumUserResult.get();
				if (userDto.getName() != null) {
					existForumUser.setName(userDto.getName());
				}
				if (userDto.getUserPhoto() != null) {
					existForumUser.setUserPhoto(userDto.getUserPhoto());
				}
			}

			if (existingUser != null) {
				if (userDto.getName() != null) {
					existingUser.setName(userDto.getName());
				}
				if (userDto.getGender() != null) {
					existingUser.setGender(userDto.getGender());
				}
				if (userDto.getBirthDate() != null) {
					existingUser.setBirthDate(userDto.getBirthDate());
				}
				if (userDto.getRegisterDate() != null) {
					existingUser.setRegisterDate(userDto.getRegisterDate());
				}
				if (userDto.getStatus() != null) {
					UserAStatus newsta = new UserAStatus();
					newsta.setStatusId(userDto.getStatus().getStatusId());
					existingUser.setStatus(newsta);
				}
				if (userDto.getUserPhoto() != null) {
					existingUser.setUserPhoto(userDto.getUserPhoto());
				}
				if (userDto.getEmail() != null) {
					existingUser.setEmail(userDto.getEmail());
				}

//				UserA updatedUser = uDao.save(existingUser);
//				return UserDto.toDto(updatedUser);
				return UserDto.toDto(existingUser);
			}
			return null;
		}
		return null;

	}

	/***
	 * 刪除UserA資料 </br>
	 */
	public void deleteById(Integer userId) {
		uDao.deleteById(userId);
	}

	/***
	 * 確認email是否已存在 </br>
	 */
	public boolean checkIfEmailExist(String email) {
		UserA dbUser = uDao.findByEmail(email);
		if (dbUser != null) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * emailLogin </br>
	 */
	public LoginUserDto googleEmailLogin(String email, String name, String photo) {
		UserA dbUser = uDao.findByEmail(email);
		if (dbUser != null) {
			LoginUserDto loginUserDTO = new LoginUserDto(dbUser.getUserId(), dbUser.getName(), dbUser.getUserPhoto(),
					dbUser.getEmail());
			return loginUserDTO;
		} else {
			UserA newUser = new UserA();
			newUser.setEmail(email);
			newUser.setName(name);
			newUser.setUserPhoto(photo);

			UserA saveUser = uDao.save(newUser);
			saveUser.getUserId();

			moneyAccountService.createAccount(saveUser.getUserId());

			// 因為ForumUser資料需要與UserA同步，所以也要建立一筆資料
			ForumUser forumUser = new ForumUser();
			forumUser.setUser(newUser);
			forumUser.setUserId(saveUser.getUserId());
			forumUser.setName(name);
			forumUser.setUserPhoto(photo);
			forumUserDao.save(forumUser);

			return LoginUserDto.toDto(newUser);
		}
	}

	public LoginUserDto userIdByGoogleEmail(String email) {
		UserA user = uDao.findByEmail(email);
		return LoginUserDto.toSimpleDto(user);
	}

}
