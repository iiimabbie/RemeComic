package tw.com.remecomic.comic.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.comic.model.bean.ComicDEpisodeUpdate;

@Data
@NoArgsConstructor
public class ComicDEpisodeUpdateDto {
	
	private Integer episodeId;
	private Integer comicId;
	private Integer episodeLikes;
	private Integer episodeNum;
	private Date updateDate;
	private String episodeCover;
	private Integer episodeViews;
	private String comicTitle;
	private String comicCover;
	private Integer rentalPrice;
	private Integer purchasePrice;
	private String horizontalPhoto;
    private List<Map<String,Object>> commentUsers;
	
	
	
	public static ComicDEpisodeUpdateDto toDto(ComicDEpisodeUpdate episode) {
        ComicDEpisodeUpdateDto dto = new ComicDEpisodeUpdateDto();
        dto.setEpisodeId(episode.getEpisodeId());
        dto.setComicId(episode.getComicId());
        dto.setEpisodeLikes(episode.getEpisodeLikes());
        dto.setEpisodeNum(episode.getEpisodeNum());
        dto.setUpdateDate(episode.getUpdateDate());
        dto.setEpisodeCover(episode.getEpisodeCover());
        dto.setEpisodeViews(episode.getEpisodeViews());

        return dto;
    }

	public static ComicDEpisodeUpdate toEntity(ComicDEpisodeUpdateDto dto) {
	    ComicDEpisodeUpdate episode = new ComicDEpisodeUpdate();
	    episode.setEpisodeId(dto.getEpisodeId());
	    episode.setComicId(dto.getComicId());
	    episode.setEpisodeLikes(dto.getEpisodeLikes());
	    episode.setEpisodeNum(dto.getEpisodeNum());
	    episode.setUpdateDate(dto.getUpdateDate());
	    episode.setEpisodeCover(dto.getEpisodeCover());
	    episode.setEpisodeViews(dto.getEpisodeViews());

	    return episode;
	}
	
	
	public static List<ComicDEpisodeUpdateDto> toBasicAndCommentDto(List<Map<String,Object>> maps) {
		List<ComicDEpisodeUpdateDto> basicCommentEpisodes = new ArrayList<>();
		for(Map<String,Object>  map : maps) {
			Integer episodeId =(Integer) map.get("episodeId");
			Optional<ComicDEpisodeUpdateDto> matchingEpisodeDto = 
					basicCommentEpisodes.stream()
					.filter(ep -> ep.getEpisodeId().equals(episodeId))
					.findFirst();
			
			if(matchingEpisodeDto.isPresent()) {
				ComicDEpisodeUpdateDto existEpDto = matchingEpisodeDto.get();
				Map<String,Object> userComment = new HashMap<>();
				userComment.put("userId", (Integer) map.get("userId"));
				userComment.put("userName", (String) map.get("userName"));
				userComment.put("comment", (String) map.get("commentContent"));
				userComment.put("pageId", (Integer) map.get("pageId"));
				existEpDto.getCommentUsers().add(userComment);
			}else {
				ComicDEpisodeUpdateDto newEpdto = new ComicDEpisodeUpdateDto();
				newEpdto.setComicId((Integer) map.get("comicId"));
				newEpdto.setComicTitle((String) map.get("comicTitle"));
				newEpdto.setComicCover((String) map.get("comicCover"));
				newEpdto.setEpisodeId((Integer) map.get("episodeId"));
				newEpdto.setEpisodeLikes((Integer) map.get("episodeLikes"));
				newEpdto.setEpisodeNum((Integer) map.get("episodeNum"));
				newEpdto.setUpdateDate((Date) map.get("updateDate")); // Assuming this is a Date field
				newEpdto.setEpisodeCover((String) map.get("episodeCover"));
				newEpdto.setEpisodeViews((Integer) map.get("episodeViews"));
				newEpdto.setRentalPrice((Integer) map.get("rentalPrice"));
				newEpdto.setPurchasePrice((Integer) map.get("purchasePrice"));
				newEpdto.setHorizontalPhoto((String) map.get("horizontalPhoto"));

				
				List<Map<String,Object>> initiateUsers = new ArrayList<>();			
				newEpdto.setCommentUsers(initiateUsers);
				Map<String,Object> userComment = new HashMap<>();
				userComment.put("userId", (Integer) map.get("userId"));
				userComment.put("userName", (String) map.get("userName"));
				userComment.put("comment", (String) map.get("commentContent"));
				userComment.put("pageId", (Integer) map.get("pageId"));
				newEpdto.getCommentUsers().add(userComment);
				basicCommentEpisodes.add(newEpdto);
			}
		
		}return basicCommentEpisodes;
	
	}

}
