package tw.com.remecomic.helpCenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.helpCenter.model.bean.HelpReport;
import tw.com.remecomic.helpCenter.model.bean.HelpType;
import tw.com.remecomic.helpCenter.model.dao.HelpTypeDao;

@Service
public class HelpTypeService {

	@Autowired
	private HelpTypeDao typeDao;

	// 新增
	public HelpType addType(HelpType type) {
		HelpType save = typeDao.save(type);
		return save;
	}

	// 查詢全部
	public List<HelpType> findAllType() {
		return typeDao.findAll();
	}

	// ID查詢
	public Optional<HelpType> findTypeById(Integer id) {
		return typeDao.findById(id);
	}

	// 刪除
	public void deleteType(HelpType type) {
		typeDao.delete(type);
	}

	// ID刪除
	public boolean deleteTypeById(Integer id) {
		Optional<HelpType> findById = typeDao.findById(id);
		if(findById.isPresent()) {
			HelpType type = findById.get();
			typeDao.delete(type);
			return true;
		}return false;
	}

}
