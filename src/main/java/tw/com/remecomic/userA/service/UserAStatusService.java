package tw.com.remecomic.userA.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tw.com.remecomic.userA.model.bean.UserAStatus;
import tw.com.remecomic.userA.model.dao.UserAStatusDao;

//修改會員資料時，身份組的部分想用下拉式選單。
//要用for去跑選項，這樣我的status需要作dto嗎?
//感覺需要，不然不就每次他都跑一堆用戶資料出來?

@Service
@Transactional
public class UserAStatusService {

	private UserAStatusDao sDao;

	/***
	 * 以ID尋找Status(所有資料，包含所有用戶的所有資料) </br>
	 * 感覺就用不太到，有可能未來會刪除
	 * 
	 * @param userId
	 * @return Optional
	 */
	public Optional<UserAStatus> findById(Integer statusId) {

		return sDao.findById(statusId);
	}

	/***
	 * 尋找所有Status(所有資料) </br>
	 * 
	 * @return List<UserAStatus>
	 */
	public List<UserAStatus> findAll() {
		return sDao.findAll();
	}

	/***
	 * 存檔Status資料(包括新增與更新) </br>
	 * 
	 * @param status
	 * @return UserAStatus
	 */
	public UserAStatus save(UserAStatus userStatus) {
		return sDao.save(userStatus);
	}

}
