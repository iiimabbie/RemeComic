package tw.com.remecomic.comic.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;

@Data
public class ComicDReadDto {
	private Integer isBookMarked;
	private Integer episodeId;
	private Integer comicId;
	private Integer totalPage;
	private Integer maxPageNum;
	private List<Integer> pageIds = new ArrayList<>(); 
		
	public static List<ComicDReadDto> toReadDto(List<Map<String, Object>> maps) {
		List<ComicDReadDto> readDtos = new ArrayList<>();
		
		for(Map<String,Object> map : maps) {
			Integer comicId = (Integer)map.get("comicId");
			Integer episodeId = (Integer)map.get("episodeId");
			Optional<ComicDReadDto> findReadDto = readDtos.stream()
					.filter(read -> read.getEpisodeId().equals(episodeId))
					.findFirst();
			if(findReadDto.isPresent()) {
				ComicDReadDto findRead = findReadDto.get();
				if((Integer)map.get("isBookMarked")==1 ||
						findRead.getIsBookMarked() == 1) {
					findRead.setIsBookMarked(1);
				}
				Integer maxPageNum = findRead.getMaxPageNum() == null ? 0 : findRead.getMaxPageNum();
				Integer pageNum = (Integer)map.get("pageNum");
				Integer comparedPageNum = Math.max(maxPageNum, pageNum);
				findRead.setMaxPageNum(comparedPageNum);
				findRead.getPageIds().add((Integer)map.get("pageId"));
			}else {
				ComicDReadDto readDto = new ComicDReadDto();
				readDto.setComicId(comicId);
				readDto.setEpisodeId(episodeId);
				readDto.setIsBookMarked((Integer)map.get("isBookMarked"));
				readDto.setTotalPage((Integer)map.get("totalPage"));
				readDto.setMaxPageNum((Integer)map.get("pageNum"));
				readDto.getPageIds().add((Integer)map.get("pageId"));
				readDtos.add(readDto);
				
			}
			
		}return readDtos;
		
	}
	

}
