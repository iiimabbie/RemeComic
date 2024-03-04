package tw.com.remecomic.comic.model.dao;

public interface ComicDRepositoryCustom {
    Integer updateComicDByColumnName(String columnName, Object updateValue, Integer comicId);
    // Other custom methods
}