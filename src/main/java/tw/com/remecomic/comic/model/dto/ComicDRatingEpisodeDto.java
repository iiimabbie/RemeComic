package tw.com.remecomic.comic.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.comic.model.bean.ComicDRatingEpisode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDRatingEpisodeDto {
		
	private Integer userId;
	private Integer episodeId;
	private Integer episodeLike;
	private Date episodeRatingDate;
	
	public static ComicDRatingEpisodeDto toDto(ComicDRatingEpisode ratingEpisode) {
		ComicDRatingEpisodeDto dto = new ComicDRatingEpisodeDto();
		dto.setUserId(ratingEpisode.getUserId());
		dto.setEpisodeId(ratingEpisode.getEpisodeId());
		dto.setEpisodeLike(ratingEpisode.getEpisodeLike());
		dto.setEpisodeRatingDate(ratingEpisode.getEpisodeRatingDate());
		return dto;
	}
	
	public static ComicDRatingEpisode toEntity(ComicDRatingEpisodeDto ratingEpisodeDto) {
		ComicDRatingEpisode episodeRating = new ComicDRatingEpisode();
		episodeRating.setUserId(ratingEpisodeDto.getUserId());
		episodeRating.setEpisodeId(ratingEpisodeDto.getEpisodeId());
		episodeRating.setEpisodeLike(ratingEpisodeDto.getEpisodeLike());
		episodeRating.setEpisodeRatingDate(ratingEpisodeDto.getEpisodeRatingDate());
		return episodeRating;
	}
	

}
