package tw.com.remecomic.helpCenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.helpCenter.model.bean.HelpQuestion;
import tw.com.remecomic.helpCenter.model.dao.HelpQuestionDao;

@Service
public class HelpQuestionService {

	@Autowired
	private HelpQuestionDao questionDao;
	
	//新增
	public HelpQuestion addQuestion(HelpQuestion question) {
		HelpQuestion save = questionDao.save(question);
		return save;
	}

	//查詢全部
	public List<HelpQuestion> findAllQuestion() {
		return questionDao.findAll();
	}

	//ID查詢
	public Optional<HelpQuestion> findQuestionById(Integer id) {
		return questionDao.findById(id);
	}
	
	//分類查詢
	public List<HelpQuestion> findQuestionByCategory(Integer category) {
		return questionDao.findQuestionByCategory(category);
	}

	//模糊查詢
	public HelpQuestionService(HelpQuestionDao questionDao) {
		this.questionDao = questionDao;
	}
	
	public List<HelpQuestion> findQuestionByWord(String question){
		return questionDao.findQuestionByWord(question);
	}
	
	//ID刪除
	public boolean deleteQuestionById(Integer id) {
		Optional<HelpQuestion> findById = questionDao.findById(id);
		if(findById.isPresent()) {
			HelpQuestion question = findById.get();
			questionDao.delete(question);
			return true;
		}return false;
	}

	//編輯
	public HelpQuestion updateQuestion(Integer id, HelpQuestion helpQuestion) {
		Optional<HelpQuestion> findById = questionDao.findById(id);
		if(findById.isPresent()) {
			HelpQuestion question = findById.get();
			question.setCategory(helpQuestion.getCategory());
			question.setQuestion(helpQuestion.getQuestion());
			question.setAnswer(helpQuestion.getAnswer());
			
			return questionDao.save(question);
		}
		return null;
	}
	
}
