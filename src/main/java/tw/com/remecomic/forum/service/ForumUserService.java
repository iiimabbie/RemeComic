package tw.com.remecomic.forum.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tw.com.remecomic.forum.model.bean.ForumGroup;
import tw.com.remecomic.forum.model.bean.ForumGroupUsers;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.bean.ForumUserConnection;
import tw.com.remecomic.forum.model.dao.ForumGroupDao;
import tw.com.remecomic.forum.model.dao.ForumGroupUsersDao;
import tw.com.remecomic.forum.model.dao.ForumPostReportDao;
import tw.com.remecomic.forum.model.dao.ForumUserConnectionDao;
import tw.com.remecomic.forum.model.dao.ForumUserDao;
import tw.com.remecomic.forum.model.dto.ForumGroupDto;
import tw.com.remecomic.forum.model.dto.ForumUserDto;
import tw.com.remecomic.forum.model.dto.ForumUserForVerifyDto;
import tw.com.remecomic.forum.model.dto.ForumUserProfileDto;

@Service
@Transactional
public class ForumUserService {

	@Autowired
	private ForumUserConnectionDao userConnDao;
	@Autowired
	private ForumGroupDao groupDao;
	@Autowired
	private ForumGroupUsersDao groupConnectionDao;
	@Autowired
	private ForumUserDao userDao;
	@Autowired
	private ForumPostReportDao reportDao;
	
	public Optional<ForumUser> findUserById(Integer userId) {
		return userDao.findById(userId);
	}
	
	public List<ForumUser> findUserAll() {
		return userDao.findAll();
	}
////////////////////////////users (info for profile) ///////////////////////////////

//	public ForumUserProfileDto getUserProfile(Integer userId) {
//		return userDao.findById(userId);
//	}
	
////////////////////////////users (info for verify) ///////////////////////////////
	
	//////////////// show : user's deletedPostsCount >= 2 (users) ( for verify ) /////////////////	
//	public List<ForumUserForVerifyDto> getUsersInIdList(List<Integer> userIdList) {
//		
//		List<ForumUser> usersList = userDao.findAllById(userIdList);
//		if(usersList!=null) {
//			return usersList.stream().map(ForumUserForVerifyDto::sendToFrontend).toList();			
//		}
//		return null;
//	}
	//////////////// show : user's report has reject times >= 2 (users) ( for verify ) /////////////////	
	public List<Map<String, Object[]>> getUsersReportReject(List<Integer> userIdList) {
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime past = now.minusDays(30);  // 這裡記得改成 7!!!
		Date dateNow = Date.from(now.atZone(java.time.ZoneId.systemDefault()).toInstant());
		Date datePast = Date.from(past.atZone(java.time.ZoneId.systemDefault()).toInstant());
		
		System.out.println("有跑到這裡 getUsersReportReject");
		List<Map<String, Object[]>> usersList = reportDao.findByUserReportRejectInTimePeriod(userIdList,datePast,dateNow);
		if(usersList!=null) {
			return usersList;
		}
		return null;
	}
	//////////////// update : user's deletedPostsCount + banPostExpire (users) ( for verify ) /////////////////
	public List<ForumUserForVerifyDto> updateUsersAboutDeleteColumn(List<Integer> userIdList) {
		
		List<ForumUser> usersList = userDao.findAllById(userIdList);
		if(usersList!=null) {
			usersList.forEach(user->{
				user.setDeletedPostsCount(0);
				
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime expire = now.plusDays(3);
				Date dateExpire = Date.from(expire.atZone(java.time.ZoneId.systemDefault()).toInstant());
				user.setPostBanExpire(dateExpire);
			});
			return usersList.stream().map(ForumUserForVerifyDto::sendToFrontendWithExpire).toList();			
		}
		return null;
	}
	
	////////////////update : user's banReportExpire (users) ( for verify ) /////////////////
	public List<ForumUserForVerifyDto> updateUsersAboutBanReportRight(List<Integer> userIdList) {
		
		List<ForumUser> usersList = userDao.findAllById(userIdList);
		if(usersList!=null) {
			usersList.forEach(user->{				
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime expire = now.plusDays(3);
				Date dateExpire = Date.from(expire.atZone(java.time.ZoneId.systemDefault()).toInstant());
				user.setReportBanExpire(dateExpire);
			});
			return usersList.stream().map(ForumUserForVerifyDto::sendToFrontendWithExpire).toList();			
		}
		return null;
	}
	
////////////////////////////following ///////////////////////////////
	
	public Optional<ForumUser> findById(Integer userId) {
		return userDao.findById(userId);
	}
	
	//////////////// show all (following & followers) /////////////////

	public List<ForumUserDto> getAllFollowing(ForumUser user,ForumUser loginUser) {
		List<ForumUserConnection> findAllFollowing = userConnDao.findByUser(user);
		if (findAllFollowing != null) {
			List<ForumUserDto> list = findAllFollowing.stream()
					.filter(follow -> follow.getConnectionType().equals("follow"))
					.map(ForumUserConnection::getPassiveUser)
					.map(targetUser-> ForumUserDto.sendToFrontendNoFollowWithInfo(targetUser,loginUser)).toList();
			return list;
		}
		return null;
	}
	public List<ForumUserDto> getAllFollower(ForumUser user,ForumUser loginUser) {
		List<ForumUserConnection> findAllFollower = userConnDao.findByPassiveUser(user);
		if (findAllFollower != null) {
			List<ForumUserDto> list = findAllFollower.stream()
					.filter(follow -> follow.getConnectionType().equals("follow"))
					.map(ForumUserConnection::getUser)
					.map(targetUser-> ForumUserDto.sendToFrontendNoFollowWithInfo(targetUser,loginUser)).toList();
			return list;
		}
		return null;
	}

	public List<ForumUserDto> getAllFollowingWithFollow(ForumUser user) {
		List<ForumUserConnection> findAllFollow = userConnDao.findByUser(user);
		if (findAllFollow != null) {
			List<ForumUserDto> list = findAllFollow.stream()
					.filter(follow -> follow.getConnectionType().equals("follow"))
					.map(ForumUserConnection::getPassiveUser)
					.map(ForumUserDto::sendToFrontendNoFollow).toList();
			return list;
		}
		return null;
	}
	//////////////// show filter by search (following) /////////////////
	
	//////////////// show recommended to follow (following) /////////////////	

	//////////////// add+delete (following + blocking) /////////////////
	public ForumUserDto changeFollow(ForumUser user, ForumUser followedUser,String connString) {

		ForumUserConnection connection = userConnDao.findByUserAndPassiveUserAndConnectionType(user, followedUser,connString);

		if (connection != null) { // 取消跟隨/屏蔽成功
			userConnDao.delete(connection);
			return null;
		} else { // 成功跟隨/屏蔽該user
			ForumUserConnection follow = new ForumUserConnection();
			follow.setUser(user);
			follow.setPassiveUser(followedUser);
			follow.setConnectionType(connString);
			ForumUserConnection save = userConnDao.save(follow);
			
			return ForumUserDto.sendToFrontendNoFollowWithInfo(save.getPassiveUser(),user);
		}
	}

////////////////////////////blocking ///////////////////////////////

	//////////////// show all (blocking) /////////////////
	public List<ForumUserDto> getAllBlocking(ForumUser user) {
		List<ForumUserConnection> findAllFollow = userConnDao.findByUser(user);
		if (findAllFollow != null) {
			List<ForumUserDto> list = findAllFollow.stream()
					.filter(follow -> follow.getConnectionType().equals("block"))
					.map(ForumUserConnection::getPassiveUser)
					.map(ForumUserDto::sendToFrontendNoFollow).toList();
			return list;
		}
		return null;
	}

	public List<ForumUserDto> getAllBlockingWithFollow(ForumUser user) {
		List<ForumUserConnection> findAllFollow = userConnDao.findByUser(user);
		if (findAllFollow != null) {
			List<ForumUserDto> list = findAllFollow.stream()
					.filter(follow -> follow.getConnectionType().equals("block"))
					.map(ForumUserConnection::getPassiveUser)
					.map(ForumUserDto::sendToFrontendNoFollow).toList();
			return list;
		}
		return null;
	}

////////////////////////////in group ///////////////////////////////

	//////////////// show (in group) /////////////////
	// 顯示該使用者的所有group
	public List<ForumGroupDto> getAllGroup(ForumUser user) {
		List<ForumGroupUsers> findAllGroup = groupConnectionDao.findByUser(user);
		if (findAllGroup != null) {
			List<ForumGroupDto> list = findAllGroup.stream().map(ForumGroupUsers::getGroup)
					.map(ForumGroupDto::sendToFrontend).toList();
			return list;
		}
		return null;
	}

	// 顯示該group中的所有user
	public List<ForumUserDto> getUsersInGroup(ForumGroup group) {
		List<ForumGroupUsers> findAllGroup = groupConnectionDao.findByGroup(group);
		if (findAllGroup != null) {
			List<ForumUserDto> list = findAllGroup.stream().map(ForumGroupUsers::getUser)
					.map(ForumUserDto::sendToFrontendNoFollow).toList();
			return list;
		}
		return null;
	}

	public Optional<ForumGroup> findGroupById(Integer userId) {
		return groupDao.findById(userId);
	}

	//////////////// update manager (in group) /////////////////
	public ForumGroupDto changeGroupManager(ForumUser user, ForumGroup group) {
		group.setManagerUser(user);
		return ForumGroupDto.sendToFrontend(group);
	}

	//////////////// add (in group) /////////////////
	// 新建group
	public ForumGroupDto buildGroup(ForumUser user, String groupName) {
		
		ForumGroup existGroup = groupDao.findByGroupName(groupName);
		if(existGroup==null) { // 如果群組名稱沒有撞名
			ForumGroup group = new ForumGroup();
			ForumGroupUsers groupUsersConn = new ForumGroupUsers();
			group.setManagerUser(user);
			group.setGroupName(groupName);
			groupUsersConn.setGroup(group);
			groupUsersConn.setUser(user);
			groupDao.save(group);
			ForumGroupUsers save = groupConnectionDao.save(groupUsersConn);			
			if (save != null) {
				ForumGroupDto list = ForumGroupDto.sendToFrontend(save.getGroup());
				return list;
			}
		}
		return null;
	}

	// 新增user進入group
	public ForumGroupDto addUserInGroup(ForumGroup group, List<Integer> userIdList) {

		Collection<ForumGroupUsers> usersList = group.getUsers();

		List<ForumGroupUsers> saveConnList = new ArrayList<>();
		List<ForumUser> userList = userDao.findByUserIdIn(userIdList);
		for (ForumUser user : userList) {
			ForumGroupUsers groupUsersConn = new ForumGroupUsers();
			groupUsersConn.setGroup(group);
			groupUsersConn.setUser(user);
			saveConnList.add(groupUsersConn);
		}
		groupConnectionDao.saveAll(saveConnList);

		ForumGroupDto save = ForumGroupDto.sendToFrontend(group);
		return save;
	}
	//////////////// delete (by manager) (in group) /////////////////
	// 刪除group
	public boolean deleteGroup(ForumGroup group) {
		groupDao.delete(group);
		Optional<ForumGroup> findById = groupDao.findById(group.getGroupId());
		if(findById.isEmpty()) {
			return true;
		}
		return false;
	}
	// 把user從group內刪除
	public boolean deleteUserInGroup(ForumGroup group,ForumUser user) {
		ForumGroupUsers groupConn = groupConnectionDao.findByGroupAndUser(group, user);
		if(groupConn!=null) {
			groupConnectionDao.delete(groupConn);
			ForumGroupUsers findById = groupConnectionDao.findByGroupAndUser(group, user);
			if(findById==null) {
				System.out.println("findById is null");
				return true;
			}			
		}
		return false;
	}

////////////////////////////filter by search ///////////////////////////////

}
