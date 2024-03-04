package tw.com.remecomic.comic.model.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.comic.model.bean.ComicD;
import tw.com.remecomic.comic.model.bean.ComicDEpisodeUpdate;
import tw.com.remecomic.comic.model.dao.ComicDDao;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDEpisodesDto {
	
//	@Autowired 
//	ComicDDao comicDDao;
	
	private Integer comicId; 
	private String comicTitle;
	private String comicCover;
	private Integer episodeLikes;
	private Integer episodeViews;
	private Date updateDate;
	private String episodeCover;
	private List<ComicDEpisodeUpdate> episodes;
	
	
	public static ComicDEpisodesDto toDto(ComicD comicD) {
		ComicDEpisodesDto dto = new ComicDEpisodesDto();
		dto.setComicId(comicD.getComicId());
	    dto.setComicTitle(comicD.getComicTitle());
	    dto.setComicCover(comicD.getComicCover());
	    List<ComicDEpisodeUpdate> comicEpisodes = 
	    	comicD.getEpisodes()
	    	.stream()
	    	.map(comicEpisode -> {
	    		ComicDEpisodeUpdate comic = new ComicDEpisodeUpdate(
    				comicEpisode.getEpisodeId(),
		    		comicEpisode.getEpisodeLikes(),
		    		comicEpisode.getEpisodeViews(),
		    		comicEpisode.getEpisodeNum(),
		    		comicEpisode.getEpisodeCover()	    		
	    		);return comic;
	    	}).collect(Collectors.toList());
	    	
	    dto.setEpisodes(comicEpisodes);
	    return dto;
	};
	
	public static ComicDEpisodesDto toDtoSimpleEpisode(Map<String, Object> episodeByComic){
		ComicDEpisodesDto dto = new ComicDEpisodesDto();
		dto.setComicId((Integer)episodeByComic.get("comicId"));
	    dto.setComicTitle((String)episodeByComic.get("comicTitle"));
	    dto.setComicCover((String)episodeByComic.get("comicCover"));
	    dto.setEpisodeLikes((Integer)episodeByComic.get("episodeLikes"));
	    dto.setEpisodeViews((Integer)episodeByComic.get("episodeViews"));
	    dto.setUpdateDate((Date)episodeByComic.get("updateDate"));
	    dto.setEpisodeCover((String)episodeByComic.get("episodeCover"));
	    return dto;
	};
	
	

}
