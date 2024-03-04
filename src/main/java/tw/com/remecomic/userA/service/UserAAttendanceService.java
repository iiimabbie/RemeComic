package tw.com.remecomic.userA.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.remecomic.userA.model.bean.UserA;
import tw.com.remecomic.userA.model.bean.UserAAttendance;
import tw.com.remecomic.userA.model.dao.UserAAttendanceDao;
import tw.com.remecomic.userA.model.dao.UserADao;

@Service
@Transactional
public class UserAAttendanceService {

	@Autowired
	private UserADao uDao;
	@Autowired
	private UserAAttendanceDao uaDao;

	public Map<String, Integer> markAttendance(Integer userId) {
		Optional<UserA> userOptional = uDao.findById(userId);
		Integer userAttDays = uDao.findUserAttDays(userId);
		Map<String, Integer> mapObj = new HashMap<>();

		if (userOptional.isPresent()) {
			UserA user = userOptional.get();
			UserAAttendance userAAttendance = new UserAAttendance();
			userAAttendance.setUser(user);
			userAAttendance.setAttendDate(new Date());
			uaDao.save(userAAttendance);

			Date previousDate = getPreviousDate(); // 昨天

			Optional<UserAAttendance> continueDay = uaDao.findByUserIdAndAttendDate(userId, previousDate);
			if (continueDay.isEmpty()) {
				user.setUserAtt(0);
			}

			Integer newUserAtt = userAttDays + 1;
			user.setUserAtt(newUserAtt);

			if (newUserAtt >= 10) {
				user.setUserAtt(0);
//				這裡寫發錢給他
				String url = "http://localhost:8080/remecomic/money/recharge?userId=1&rechargeAmount=10";
				boolean isSuccess = sendGetRequest(url);
				if (isSuccess == true) {
					mapObj.put("getMoney", 10);
					System.out.println("發錢了");
				} else {
					System.out.println("儲值沒有成功");
				}
				uDao.save(user);
			}

			mapObj.put("status", 1);
			return mapObj;
		} else {
			mapObj.put("status", 0);
			return mapObj;
//			throw new EntityNotFoundException("User not found with ID: " + userId);
		}
	}

///////////////	Date Format  ///////////////
	private Date getPreviousDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -1);

		// 將 Calendar 的日期轉換為 Date 物件
		Date previousDate = calendar.getTime();

		// 將 Date 物件格式化為 "yyyy-MM-dd" 字串形式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = dateFormat.format(previousDate);

		try {
			// 將格式化後的字串轉換回 Date 物件
			previousDate = dateFormat.parse(formattedDate);
		} catch (ParseException e) {
			// 轉換失敗時的處理邏輯
			e.printStackTrace();
		}

		return previousDate;
	}

	private static boolean sendGetRequest(String url) {
		try {
			// 建立 URL 物件
			URL requestUrl = new URL(url);

			// 建立 HttpURLConnection 物件
			HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

			// 設置請求方法為 GET
			connection.setRequestMethod("GET");

			// 讀取回應
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			// 關閉連接
			connection.disconnect();

			// 請求成功
			return true;
		} catch (Exception e) {
			// 請求失敗，印出錯誤訊息
			e.printStackTrace();
			return false;
		}
	}

}
