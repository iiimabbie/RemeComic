package tw.com.remecomic.helpCenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.helpCenter.model.bean.HelpQuestion;
import tw.com.remecomic.helpCenter.model.bean.HelpServiceResponse;
import tw.com.remecomic.helpCenter.model.dao.HelpServiceResponseDao;

@Service
public class HelpServiceResponseService {

	@Autowired
	private HelpServiceResponseDao responseDao;

	// 新增
	public HelpServiceResponse addResponse(HelpServiceResponse serviceResponse) {
		HelpServiceResponse save = responseDao.save(serviceResponse);
		return save;
	}

	// 查詢全部
	public List<HelpServiceResponse> findAllResponse() {
		return responseDao.findAll();
	}

	// ID查詢
	public Optional<HelpServiceResponse> findResponseById(Integer id) {
		return responseDao.findById(id);
	}

	// 刪除
	public void deleteResponse(HelpServiceResponse serviceResponse) {
		responseDao.delete(serviceResponse);
	}

	// ID刪除
	public boolean deleteResponseById(Integer id) {
		Optional<HelpServiceResponse> findById = responseDao.findById(id);
		if(findById.isPresent()) {
			HelpServiceResponse response = findById.get();
			responseDao.delete(response);
			return true;
		}return false;
	}
}
