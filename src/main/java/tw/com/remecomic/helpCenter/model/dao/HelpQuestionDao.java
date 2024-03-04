package tw.com.remecomic.helpCenter.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tw.com.remecomic.helpCenter.model.bean.HelpQuestion;

public interface HelpQuestionDao extends JpaRepository<HelpQuestion, Integer>{

	@Query("select hq from HelpQuestion hq where lower(hq.question) like lower(concat('%', :question , '%'))")
	List<HelpQuestion> findQuestionByWord(@Param("question")String question);
	
	@Query("SELECT hq FROM HelpQuestion hq WHERE hq.category = :category")
	List<HelpQuestion> findQuestionByCategory(@Param("category")Integer categoryId);
}
