package tw.com.remecomic.forum.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumReason;

@Repository
public interface ForumReasonDao extends JpaRepository<ForumReason, Integer> {
	
	@Query(value="select reasonId as reasonId,reasonContent as reasonContent from ForumReason")
	List<Map<String, Object[]>> findAllReasons();
}
