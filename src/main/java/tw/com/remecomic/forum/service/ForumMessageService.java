package tw.com.remecomic.forum.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tw.com.remecomic.forum.model.bean.ForumGroup;
import tw.com.remecomic.forum.model.bean.ForumMessage;
import tw.com.remecomic.forum.model.bean.ForumUser;
import tw.com.remecomic.forum.model.dao.ForumMessageDao;
import tw.com.remecomic.forum.model.dto.ForumMessageDto;

@Service
@Transactional
public class ForumMessageService {
	@Autowired
	private ForumMessageDao msgDao;
////////////////////////////show message ///////////////////////////////
	
	//////////////// in group (show message) /////////////////
	public PageImpl<ForumMessageDto> getMsgInGroupByPage(ForumGroup group,Integer pageNumber) {
		Pageable pgb = PageRequest.of(pageNumber-1, 10,Sort.by(Sort.Order.desc("sendTime"),
		        												Sort.Order.desc("messageId")));
//		Pageable pgb = PageRequest.of(pageNumber-1, 10,Sort.Direction.DESC,"sendTime");
		List<ForumMessage> msgs = msgDao.findByGroup(group,pgb);
		if (msgs != null) {
			List<ForumMessageDto> list = msgs.stream().map(ForumMessageDto::sendToFrontend).toList();
			return new PageImpl<ForumMessageDto>(list,pgb,list.size());
		}
		return null;
	}
	
	//////////////// one by one (show message) /////////////////
	public PageImpl<ForumMessageDto> getMsgIndividualByPage(ForumUser logUser,ForumUser chatWithUser,Integer pageNumber) {
		Pageable pgb = PageRequest.of(pageNumber-1, 10,Sort.by(Sort.Order.desc("sendTime"),
																Sort.Order.desc("messageId")));
		ForumUser[] userArray = {logUser,chatWithUser};
		List<ForumMessage> msgs = msgDao.findByUserInAndAcceptedUserIn(userArray,userArray,pgb);
		if (msgs != null) {
			for(ForumMessage msg : msgs) {
				if(msg.getAcceptedUser().equals(logUser) && msg.getMessageStatus()==0 ) {
					msg.setMessageStatus(1);					
				}
			}
			List<ForumMessageDto> list = msgs.stream().map(ForumMessageDto::sendToFrontend).toList();
			return new PageImpl<ForumMessageDto>(list,pgb,list.size());
		}
		return null;
	}
	
	public Optional<ForumMessage> findById(Integer msgId) {
		return msgDao.findById(msgId);
	}

////////////////////////////add message ///////////////////////////////
	
	//////////////// in group (add message) /////////////////
	public ForumMessageDto addMsgInGroup(ForumUser user,ForumGroup group,String content) {
		ForumMessage msg = new ForumMessage();
		msg.setUser(user);
		msg.setGroup(group);
		msg.setMessageContent(content);
		msg.setMessageStatus(0);
		ForumMessage save = msgDao.save(msg);
		if (save != null) {
			return ForumMessageDto.sendToFrontend(save);
		}
		return null;
	}
	
	//////////////// one by one (add message) /////////////////
	public ForumMessageDto addMsgIndividual(ForumUser user,ForumUser acceptUser,String content) {
		ForumMessage msg = new ForumMessage();
		msg.setUser(user);
		msg.setAcceptedUser(acceptUser);
		msg.setMessageContent(content);
		msg.setMessageStatus(0);
		ForumMessage save = msgDao.save(msg);
		if (save != null) {
			return ForumMessageDto.sendToFrontend(save);
		}
		return null;
	}

////////////////////////////delete message ///////////////////////////////

	//////////////// in 24 hours (delete message) /////////////////
	public boolean deleteMsgById(ForumMessage msg) {
		msgDao.delete(msg);
		Optional<ForumMessage> existMsg = msgDao.findById(msg.getMessageId());
		return existMsg.isEmpty() ? true : false;
	}
	
}
