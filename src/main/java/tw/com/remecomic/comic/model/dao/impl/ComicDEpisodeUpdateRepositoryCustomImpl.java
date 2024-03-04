package tw.com.remecomic.comic.model.dao.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import tw.com.remecomic.comic.model.dao.ComicDEpisodeUpdateRepositoryCustom;

public class ComicDEpisodeUpdateRepositoryCustomImpl implements ComicDEpisodeUpdateRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;
    
    private static final Set<String> VALID_COLUMN_NAMES = 
    		Set.of("episodeNum", 
    				"episodeCover" 
    				);

    @Autowired
    public ComicDEpisodeUpdateRepositoryCustomImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer updateEpisodeByColumnName(String columnName, Object updateValue, Integer episodeId) {
		 if (!VALID_COLUMN_NAMES.contains(columnName)) {
	         throw new IllegalArgumentException("Invalid column name");
	     }
		 String updateSql = "UPDATE ComicDEpisodeUpdate SET " + columnName + " = ? WHERE episodeId = ?";
		 return jdbcTemplate.update(updateSql, updateValue, episodeId);
      
     }
}