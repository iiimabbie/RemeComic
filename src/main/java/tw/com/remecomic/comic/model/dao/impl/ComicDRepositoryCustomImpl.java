package tw.com.remecomic.comic.model.dao.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import tw.com.remecomic.comic.model.dao.ComicDRepositoryCustom;

public class ComicDRepositoryCustomImpl implements ComicDRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;
    
    private static final Set<String> VALID_COLUMN_NAMES = 
    		Set.of("comicTitle", 
    				"comicDescription", 
    				"updateDay",
    				"publishDate", 
    				"editorChoice",
    				"purchasePrice",
    				"rentalPrice",
    				"comicCover");

    @Autowired
    public ComicDRepositoryCustomImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer updateComicDByColumnName(String columnName, Object updateValue, Integer comicId) {
		 if (!VALID_COLUMN_NAMES.contains(columnName)) {
	         throw new IllegalArgumentException("Invalid column name");
	     }
		 String updateSql = "UPDATE ComicD SET " + columnName + " = ? WHERE comicId = ?";
		 return jdbcTemplate.update(updateSql, updateValue, comicId);
      
     }
}