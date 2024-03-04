package tw.com.remecomic.forum.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumPostReport;
import tw.com.remecomic.forum.model.bean.ForumReportVerify;

@Repository
public interface ForumReportVerifyDao extends JpaRepository<ForumReportVerify, Integer> {
	
	
}
