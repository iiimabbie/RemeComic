package tw.com.remecomic.comic.model.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.com.remecomic.comic.model.bean.ComicD;
import tw.com.remecomic.comic.model.bean.ComicDComicConGenres;
import tw.com.remecomic.comic.model.bean.ComicDEpisodeBody;
import tw.com.remecomic.comic.model.bean.ComicDEpisodeUpdate;
import tw.com.remecomic.comic.model.dao.ComicDGenresDao;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDDto {
	
//	@Autowired 
//	ComicDGenresDao comicDGenresDao;
	
	private Integer comicId; 
	private String comicTitle;
	private String creator;
	private String comicDescription;
	private String comicCover;
	private String updateDay;
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishDate;
	private Integer editorChoice;
	private Integer purchasePrice;
	private Integer rentalPrice;
	private List<Integer> genre;
	private List<String> genreName;
	private Integer comicLikes;
	private Integer comicLikesEP;
	private Integer comicViews;
	private Integer comments;
	//private List<ComicDRatingComic> comicRating;
	
	
	public static ComicDDto toDto(ComicD comicD) {
		ComicDDto dto = new ComicDDto();
		dto.setComicId(comicD.getComicId());
	    dto.setComicTitle(comicD.getComicTitle());
	    dto.setCreator(comicD.getCreator());
	    dto.setComicDescription(comicD.getComicDescription());
	    dto.setComicCover(comicD.getComicCover());
	    dto.setUpdateDay(comicD.getUpdateDay());
	    dto.setPublishDate(comicD.getPublishDate());
	    dto.setEditorChoice(comicD.getEditorChoice());
	    dto.setPurchasePrice(comicD.getPurchasePrice());
	    dto.setRentalPrice(comicD.getRentalPrice());    
	    return dto;
	}
	
	public static ComicDDto summariesToDto(Object[] obj) {
		ComicDDto dto = new ComicDDto();
		dto.setComicId((Integer)obj[0]);
	    dto.setComicTitle((String)obj[1]);
	    dto.setComicDescription((String)obj[2]);
	    dto.setUpdateDay((String)obj[3]);
	    dto.setPublishDate((Date)obj[4]);
	    dto.setEditorChoice((Integer)obj[5]);
	    dto.setPurchasePrice((Integer)obj[6]);
	    dto.setRentalPrice((Integer)obj[7]); 
	    dto.setRentalPrice((Integer)obj[8]); 
	    dto.setComicLikesEP((Integer)obj[9]);
	    dto.setComicViews((Integer)obj[10]);
	    dto.setComicLikes((Integer)obj[11]);	
		return dto;
	}
	

	public static ComicDDto commentToDto(Object[] obj) {
		ComicDDto dto = new ComicDDto();
		dto.setComicId((Integer)obj[0]);
	    dto.setComicLikesEP((Integer)obj[1]);	
		return dto;
	}
	
	/*public static ComicDDto genreToDto(Object[] obj) {
		ComicDDto dto = new ComicDDto();
		dto.setComicId((Integer)obj[0]);
	    dto.setComicLikesEP((Long)obj[1]);	
		return dto;
	}*/
	
	
	
	public static ComicDDto toCalculatedData(ComicD comicD) {
		ComicDDto dto = new ComicDDto();
		dto.setComicId(comicD.getComicId());
	    dto.setComicTitle(comicD.getComicTitle());
	    dto.setCreator(comicD.getCreator());
	    dto.setComicDescription(comicD.getComicDescription());
	    dto.setUpdateDay(comicD.getUpdateDay());
	    dto.setPublishDate(comicD.getPublishDate());
	    /*dto.setEditorChoice(comicD.getEditorChoice());*/
	    dto.setPurchasePrice(comicD.getPurchasePrice());
	    dto.setRentalPrice(comicD.getRentalPrice()); 
	    
	    List<Integer> gernreIds = 
	    		comicD.getComicGenres().stream().map(ComicDComicConGenres::getGenreId).collect(Collectors.toList());  
	    dto.setGenre(gernreIds);
	    
	    Integer comicRatings=0;
	    if(!comicD.getComicRatings().isEmpty()){
	    	comicRatings = comicD.getComicRatings().size();
	    }
	    Integer episodeRatingSum = comicD.getEpisodes().stream()
	    												.map(ComicDEpisodeUpdate::getEpisodeLikes)
	    												.filter(likes -> likes!= null)
	    												.reduce(0, Integer::sum);
	   dto.setComicLikes(comicRatings + episodeRatingSum);
	    
	    Integer comments = 0;
	    List<ComicDEpisodeUpdate>  episodes = comicD.getEpisodes();
	    for (ComicDEpisodeUpdate episode : episodes) {
	    	List<ComicDEpisodeBody > bodies =  episode.getEpisodeBodies();
	    	if(bodies!= null && !bodies.isEmpty()) {
	    		for (ComicDEpisodeBody body : bodies) {
		    		comments += body.getPageComments().size();    		
		    	}
	    	}
	    		    	
	    }
	    dto.setComments(comments);

	    return dto;
	}
	
	public static ComicD toEntity(ComicDDto comicDDto) {
        ComicD comicD = new ComicD();

        comicD.setComicId(comicDDto.getComicId());
        comicD.setComicTitle(comicDDto.getComicTitle());
        comicD.setCreator(comicDDto.getCreator());
        comicD.setComicDescription(comicDDto.getComicDescription());
        comicD.setComicCover(comicDDto.getComicCover());
        comicD.setUpdateDay(comicDDto.getUpdateDay());
        comicD.setPublishDate(comicDDto.getPublishDate());
        comicD.setEditorChoice(comicDDto.getEditorChoice());
        comicD.setPurchasePrice(comicDDto.getPurchasePrice());
        comicD.setRentalPrice(comicDDto.getRentalPrice());

        return comicD;
    }

	

}
