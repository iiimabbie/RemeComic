package tw.com.remecomic.comic.model.dao;

public interface ComicDEpisodeUpdateRepositoryCustom {
    Integer updateEpisodeByColumnName(String columnName, Object updateValue, Integer comicId);
    // Other custom methods
}