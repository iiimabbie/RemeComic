package tw.com.remecomic.comic.model.dto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.comic.model.bean.ComicD;
import tw.com.remecomic.comic.model.bean.ComicDRatingComic;
import tw.com.remecomic.comic.model.bean.ComicDRatingEpisode;
import tw.com.remecomic.comic.model.dao.ComicDDao;
import tw.com.remecomic.userA.model.bean.UserA;
import tw.com.remecomic.userA.model.dao.UserADao;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDRatingComicDto {
		
	private Integer userId;
	private Integer comicId;
	private Integer like;
	private Date ratingDate;	
	
	
	public static ComicDRatingComicDto toDto(ComicDRatingComic ratingComic) {
		ComicDRatingComicDto dto = new ComicDRatingComicDto();  
	    dto.setLike(ratingComic.getLike());
	    dto.setRatingDate(ratingComic.getRatingDate());
	    dto.setUserId(ratingComic.getUserId());
	    dto.setComicId(ratingComic.getComicId());
	    
	    return dto;
	}
	
	public static ComicDRatingComic toEntity(ComicDRatingComicDto dto) {
	    ComicDRatingComic comicRating = new ComicDRatingComic();	    
	    comicRating.setLike(dto.getLike());
	    comicRating.setRatingDate(dto.getRatingDate());
	    comicRating.setUserId(dto.getUserId());
	    comicRating.setComicId(dto.getComicId());
	    
	    return comicRating;
	}

}
