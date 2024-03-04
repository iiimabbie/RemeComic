package tw.com.remecomic.forum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tw.com.remecomic.forum.model.bean.ForumNotify;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.dao.ForumNotifyDao;
import tw.com.remecomic.forum.model.dto.ForumNotifyDto;

@Service
@Transactional
public class ForumNotifyService {
	@Autowired
	private ForumNotifyDao notifyDao;
	
////////////////////////////show notify ///////////////////////////////
	
	//////////////// all (show notify) /////////////////
	public List<ForumNotifyDto> getAllNotify(ForumUser user) {
		List<ForumNotify> msgs = notifyDao.findByAcceptedUser(user);
		for(ForumNotify msg:msgs) {
			msg.setNotifyStatus(1);
		}
		if (msgs != null) {
			List<ForumNotifyDto> list = msgs.stream().map(ForumNotifyDto::sendToFrontend).toList();
			return list;
		}
		return null;
	}

	//////////////// by not read (show notify) /////////////////
	// 在 前端 透過 js 做篩選，不另寫controller
	
	////////////////及時在 active 時就跳出通知 (show notify) /////////////////
	
////////////////////////////add notify ///////////////////////////////

	//////////////// remind user get report (add notify) /////////////////

////////////////////////////delete notify ///////////////////////////////

	//////////////// have read ones only left week (delete notify) /////////////////
	
	//////////////// reference table not exit id (delete notify) /////////////////

	
}
