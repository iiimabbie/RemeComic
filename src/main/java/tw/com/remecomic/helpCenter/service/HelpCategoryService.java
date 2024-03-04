package tw.com.remecomic.helpCenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.helpCenter.model.bean.HelpCategory;
import tw.com.remecomic.helpCenter.model.dao.HelpCategoryDao;

@Service
public class HelpCategoryService {

	@Autowired
	private HelpCategoryDao categoryDao;

	// 新增
	public HelpCategory addCategory(HelpCategory caregory) {
		HelpCategory save = categoryDao.save(caregory);
		return save;
	}

	// 查詢全部
	public List<HelpCategory> findAllCategory() {
		return categoryDao.findAll();
	}

	// ID查詢
	public Optional<HelpCategory> findCategoryById(Integer id) {
		return categoryDao.findById(id);
	}

	// 刪除
	public void deleteCategory(HelpCategory caregory) {
		categoryDao.delete(caregory);
	}

	// ID刪除
	public boolean deleteCategoryById(Integer id) {
		Optional<HelpCategory> findById = categoryDao.findById(id);
		if(findById.isPresent()) {
			HelpCategory helpCategory = findById.get();
			categoryDao.delete(helpCategory);
			return true;
		}return false;
	}
}
