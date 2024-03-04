package tw.com.remecomic.forum.model.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.forum.model.bean.ForumTag;
import tw.com.remecomic.forum.model.dto.ForumTagDto;
import tw.com.remecomic.forum.model.dto.ForumTagDto2;

@Repository
public interface ForumTagDao extends JpaRepository<ForumTag, Integer> {
	public List<ForumTag> findByTagNameIn(String[] tagArray);
	
	@Query(value="SELECT table1 FROM ForumTag table1 LEFT JOIN ForumPostTag table2 ON table1.tagId = table2.tag WHERE table2.postTagId IS NULL")
	public List<ForumTag> findNotUsedTag();
	
	public ForumTag findByTagName(String newTagString);
	
	@Query(value="Select pt.tag.tagId as tagId,t.tagName as tagName,count(pt.tag) as postAmount from ForumPostTag pt "
			+ "join ForumPost p on pt.post = p.postId join ForumTag t on pt.tag = t.tagId "
			+ "where p.postTime between ?1 and ?2 GROUP BY pt.tag order by  postAmount desc , p.postTime desc  LIMIT 10")
	public List<ForumTagDto2> findHotTagsInTimePeriod(Date yesterday,Date today);
	
	@Query(value="Select pt.tag.tagId as tagId,t.tagName as tagName,count(pt.tag) as postAmount from ForumPostTag pt "
			+ "join ForumPost p on pt.post = p.postId join ForumTag t on pt.tag = t.tagId "
			+ "where t.tagName like %:n% GROUP BY pt.tag order by count(pt.tag) desc LIMIT 20")
	public List<ForumTagDto2> findTagsBySearchSortByHot(@Param("n") String searchString);
	
}
