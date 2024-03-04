package tw.com.remecomic.comic.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ComicDRatingAndOrdersDto {
	private List<Integer> ratingEpisodeIds = new ArrayList<>();
	private List<Integer> orderEpisodeIds= new ArrayList<>();

	
	public static ComicDRatingAndOrdersDto toRatingAndOrderDto(List<Map<String, Object>> maps) {
		ComicDRatingAndOrdersDto rateAndOrderDto = new ComicDRatingAndOrdersDto();
		for(Map<String,Object> map : maps) {
			Integer ratingEpisodeId = (Integer)map.get("ratingEpisodeId");
			Integer orderEpisodeId = (Integer)map.get("orderEpisodeId");
			if(ratingEpisodeId != null) {
				rateAndOrderDto.getRatingEpisodeIds().add(ratingEpisodeId);
			}
			if(orderEpisodeId != null) {
				rateAndOrderDto.getOrderEpisodeIds().add(orderEpisodeId);
			}
		}
		return rateAndOrderDto;
		
	}
	

}
