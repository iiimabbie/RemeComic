package tw.com.remecomic.comic.model.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import tw.com.remecomic.comic.model.bean.ComicDDrafts;
import tw.com.remecomic.comic.model.bean.ComicDDraftsConGenres;
@Data
public class ComicDDraftsDto {
	
	
	private Integer draftId; 
	private String comicTitle;
	private String creator;
	private String comicDescription;
	private String comicCover;
	private String updateDay;
	private Date publishDate;
	private Integer editorChoice;
	private Integer purchasePrice;
	private Integer rentalPrice;
	private Date modificationTime;
	private Date createdDate;
	private List<String> genres = new ArrayList<>(); 
	private Integer isPublished;
	private Integer version;
//	private Integer comicId;
	
	
	 public static ComicDDrafts convertMapToComicDDrafts(LinkedHashMap<String, Object> map) {
		 return mapToComicDDrafts(map);
		
	  }

	 
	 public static List<ComicDDrafts> convertMapsToComicDDrafts(List<LinkedHashMap<String, Object>> maps) {
		 return maps.stream()
				 .map( data -> mapToComicDDrafts(data))
				 .collect(Collectors.toList());
	       
	  }
	 
	 private static ComicDDrafts mapToComicDDrafts(LinkedHashMap<String, Object> map) {
		 ComicDDrafts draft = new ComicDDrafts();
		 Integer draftId = map.get("draftId") != null && map.get("draftId") != "" ? Integer.valueOf(map.get("draftId").toString()) : 0;   
		 //Integer comicId = map.get("comicId") != null && map.get("comicId") != "" ? Integer.valueOf(map.get("comicId").toString()) : 0; 	
	        String comicTitle = (String) map.get("comicTitle");	
	        String creator = (String) map.get("creator");
	        String comicDescription = (String) map.get("comicDescription");
	        String comicCover = (String) map.get("comicCover");
	        List<ComicDDraftsConGenres> genres =  new ArrayList<>(); 	        
	        Object publishDateObj = map.get("publishDate");
	        Date publishDate = null;
	        if(publishDateObj instanceof String ) {
	        	try {
	        		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        		 publishDate = sdf.parse((String)publishDateObj); 
	        		 System.out.println("=================="+publishDate);
	        	}catch (ParseException e) {
	        	    e.printStackTrace();
	        	}
	        }else {
	        	Date today = new Date(); 
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(today); 
		        calendar.add(Calendar.DAY_OF_MONTH, 7);
		        publishDate = calendar.getTime(); 
	        }
	       
	        String updateDay = (String) map.get("updateDay");
	        Integer purchasePrice = map.get("purchasePrice") != null ? Integer.valueOf(map.get("purchasePrice").toString()) : 0;
	        Integer rentalPrice = map.get("rentalPrice") != null ? Integer.valueOf(map.get("rentalPrice").toString()) : 0;
	        Integer isPublished = map.get("isPublished") != null ? Integer.valueOf(map.get("rentalPrice").toString()) : 0;
	        return new ComicDDrafts(draftId, comicTitle, creator, comicDescription, 
	        		comicCover, genres, publishDate, updateDay, purchasePrice, rentalPrice, isPublished);
	 }


	 public static ComicDDraftsDto toDto(ComicDDrafts comicDrafts) {
	        ComicDDraftsDto dto = new ComicDDraftsDto();
	        dto.setDraftId(comicDrafts.getDraftId());
	        dto.setComicTitle(comicDrafts.getComicTitle());
	        dto.setCreator(comicDrafts.getCreator());
	        dto.setComicDescription(comicDrafts.getComicDescription());
	        dto.setComicCover(comicDrafts.getComicCover());
	        dto.setUpdateDay(comicDrafts.getUpdateDay());
	        dto.setPublishDate(comicDrafts.getPublishDate());
	        dto.setEditorChoice(comicDrafts.getEditorChoice());
	        dto.setPurchasePrice(comicDrafts.getPurchasePrice());
	        dto.setRentalPrice(comicDrafts.getRentalPrice());
	        dto.setModificationTime(comicDrafts.getModificationTime());
	        dto.setIsPublished(comicDrafts.getIsPublished());
	        dto.setVersion(comicDrafts.getVersion());

	        return dto;
	    }
	 


}
