package tw.com.remecomic.comic.model.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComicDEpisodeBodyDto {
	
	private Integer episodeId;
	private String pagePhoto;
	private Integer pageId;
	private Integer pageNum;
	private Integer isBookMarked;
	private List<Map<String, Object>> userComments = new ArrayList<>() ;
	
	
	
	
	public static List<ComicDEpisodeBodyDto> toDto(List<Map<String,Object>> maps) {
		List<ComicDEpisodeBodyDto> bodyComments = new ArrayList<>();
		for(Map<String,Object>  map : maps) {
			Integer pageId =(Integer) map.get("pageId");
			Optional<ComicDEpisodeBodyDto> matchingPageDto = 
					bodyComments.stream()
					.filter(page -> page.getPageId().equals(pageId))
					.findFirst();
			
			if(matchingPageDto.isPresent()) {
				ComicDEpisodeBodyDto existPageDto = matchingPageDto.get();
				List<Map<String,Object>> existingComments = existPageDto.getUserComments();
				Map<String,Object> userComment = new HashMap<>();
				userComment.put("userId", (Integer) map.get("userId"));
				userComment.put("userName", (String) map.get("userName"));
				userComment.put("userGender", (String) map.get("userGender"));
				userComment.put("commentId", (Integer) map.get("commentId"));
				userComment.put("commentContent", (String) map.get("commentContent"));
				userComment.put("userPhoto", (String) map.get("userPhoto"));
				userComment.put("commentDate", (Date) map.get("commentDate"));
				
				
				if(existingComments.isEmpty() ||
						((Date) userComment.get("commentDate"))
						.before((Date) existingComments.get(existingComments.size() - 1).get("commentDate"))) {
			         existingComments.add(userComment);
					
				}else {
					for(int i = 0; i <= existingComments.size(); i++) {
						Date addDate = (Date) userComment.get("commentDate");
						if(addDate.after((Date)existingComments.get(i).get("commentDate"))) {
							existingComments.add(i, userComment);
							break;
						}
					}
					
				}
			}else {
				ComicDEpisodeBodyDto newBodydto = new ComicDEpisodeBodyDto();
				newBodydto.setEpisodeId((Integer) map.get("episodeId"));
				newBodydto.setPageId((Integer) map.get("pageId"));
				newBodydto.setPageNum((Integer) map.get("pageNum"));
				newBodydto.setPagePhoto((String) map.get("pagePhoto"));
				newBodydto.setIsBookMarked((Integer) map.get("isBookMarked"));

						
				Map<String,Object> userComment = new HashMap<>();
				userComment.put("userId", (Integer) map.get("userId"));
				userComment.put("userName", (String) map.get("userName"));
				userComment.put("userGender", (String) map.get("userGender"));
				userComment.put("commentId", (Integer) map.get("commentId"));
				userComment.put("commentContent", (String) map.get("commentContent"));
				userComment.put("userPhoto", (String) map.get("userPhoto"));
				userComment.put("commentDate", (Date) map.get("commentDate"));
				newBodydto.getUserComments().add(userComment);
				bodyComments.add(newBodydto);
			}
			
		}
		
		return bodyComments;
	
	}

}
