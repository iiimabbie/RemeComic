package tw.com.remecomic.comic.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tw.com.remecomic.comic.model.bean.ComicD;
import tw.com.remecomic.comic.model.bean.ComicDComicConGenres;
import tw.com.remecomic.comic.model.bean.ComicDEpisodeUpdate;
import tw.com.remecomic.comic.model.bean.ComicDGenres;
import tw.com.remecomic.comic.model.bean.ComicDMedia;
import tw.com.remecomic.comic.model.dao.ComicDComicConGenresDao;
import tw.com.remecomic.comic.model.dao.ComicDDao;
import tw.com.remecomic.comic.model.dao.ComicDEpisodeUpdateDao;
import tw.com.remecomic.comic.model.dao.ComicDGenresDao;
import tw.com.remecomic.comic.model.dao.ComicDMediaDao;
import tw.com.remecomic.comic.model.dto.ComicDDto;
import tw.com.remecomic.comic.model.dto.ComicDEpisodeUpdateDto;
import tw.com.remecomic.comic.model.dto.ComicDEpisodesDto;
import tw.com.remecomic.comic.model.dto.ComicDSummariesDto;
import tw.com.remecomic.money.model.dao.MoneyPromotionDao;
import tw.com.remecomic.util.DateUtil;
import tw.com.remecomic.util.PageUtil;

@Service
public class ComicDService {
	@Autowired
	ComicDDao comicDDao;
	
	@Autowired
	ComicDEpisodeUpdateDao comicDEpisodeUpdateDao;
	
	@Autowired
	ComicDMediaDao comicDMediaDao;
	
	@Autowired
	ComicDGenresDao comicDGenreDao;
	
	@Autowired
	ComicDComicConGenresDao comicDComicConGenresDao;
	
	@Autowired
	MoneyPromotionDao moneyPromotionDao;
	
	
	public Optional<ComicD> findById(Integer comicId) {
		return comicDDao.findById(comicId);
		
	}
	
	public ComicDDto findSimpleComicById(Integer comicId) {
		Optional<ComicD> comicD = comicDDao.findById(comicId);
		if (comicD.isPresent()) {
			return ComicDDto.toDto(comicD.get());
		}return null;

	}
	
	public ComicDSummariesDto findCalculatedComicById(Integer comicId) {
		Optional<ComicD> comicD = comicDDao.findById(comicId);
		if (comicD.isPresent()) {
			Map<String,Object> comicComment = comicDDao.findComicCommentByComicId(comicId);
			Map<String,Object> summaries = comicDDao.findComicSummariesById(comicId);
			Map<String,Object> episodeData = comicDEpisodeUpdateDao.findEpSimpleDataByComicId(comicId);
			
			
			Date today = new Date();
			ComicDSummariesDto comicSum = new ComicDSummariesDto(					
					(Integer)summaries.get("comicId"), //comicId
					(String)summaries.get("comicTitle"), //comicTitle
					(String)summaries.get("creator"), //creator
					(String)summaries.get("comicCover"), //comicCover
					(String)summaries.get("comicDescription"),
					(String)summaries.get("updateday"), //updateDay
					(Date)summaries.get("publishDate"), //publishDate
					(Boolean)summaries.get("editorChoice"), //editorChoice
					(Integer)summaries.get("purchasePrice"), //purchasePrice
					(Integer)summaries.get("rentalPrice"), //rentalPrice
					(BigDecimal)summaries.get("comicLikesEP"),
					(Long)summaries.get("comicLikes"),
					(BigDecimal)summaries.get("comicViews"),//comicViews
					(Long)comicComment.get("comments"),
					(Integer)episodeData.get("episodeLikes"),
					(Date)episodeData.get("updateDate")
					);
			
			Optional<ComicDMedia> mediaData = comicDMediaDao.findById(comicId);
			if(mediaData.isPresent()){
				ComicDMedia media = mediaData.get();
				comicSum.setVideo(media.getVideo());
				comicSum.setHorizontalPhoto(media.getHorizontalPhoto());
			}
			//Date today = new Date();
			List<Integer> comicWithPromo = moneyPromotionDao.findPromotionBeforeToday(today);
			if(comicWithPromo.contains((Integer)summaries.get("comicId"))) {
				comicSum.setComicPromotion(true);
			}else {
				comicSum.setComicPromotion(false);
			}
			return comicSum;					
		}return null;

	}
	
	
	public List<ComicDSummariesDto> findCalculatedComicByIds(List<Integer> comicIds) {
		List<Map<String,Object>> comicSummaries = comicDDao.findComicSimpleSummaries(comicIds);
		List<ComicDSummariesDto> comicSummariesList = new ArrayList<>();
		if (!comicSummaries.isEmpty()) {		
		for(Map<String,Object> comicSummary : comicSummaries) {
			ComicDSummariesDto comicSum = new ComicDSummariesDto();					
				comicSum.setComicId((Integer) comicSummary.get("comicId"));
			    comicSum.setComicTitle((String) comicSummary.get("comicTitle"));
			    comicSum.setCreator((String) comicSummary.get("creator"));
			    comicSum.setComicCover((String) comicSummary.get("comicCover"));
			    comicSum.setComicDescription((String) comicSummary.get("comicDescription"));
			    comicSum.setUpdateDay((String) comicSummary.get("updateday"));
			    comicSum.setPublishDate((Date) comicSummary.get("publishDate"));
			    comicSum.setEditorChoice((Boolean) comicSummary.get("editorChoice"));
			    comicSum.setPurchasePrice((Integer) comicSummary.get("purchasePrice"));
			    comicSum.setRentalPrice((Integer) comicSummary.get("rentalPrice"));
			    comicSum.setComicLikesEP((BigDecimal) comicSummary.get("comicLikesEP"));
			    comicSum.setComicViews((BigDecimal) comicSummary.get("comicViews"));
			    comicSum.setComicLikes((Long) comicSummary.get("comicLikes"));
			    comicSum.setComments((Long) comicSummary.get("comments"));
			    comicSum.setLatestEpisodeLikes((Integer) comicSummary.get("latestEpLikes"));
			    comicSum.setLatestEpisodeViews((Integer) comicSummary.get("latestEpViews"));
				comicSum.setVideo((String) comicSummary.get("videoUrl")); 
			    comicSum.setHorizontalPhoto((String) comicSummary.get("horizontalPhotoUrl")); 
			    comicSummariesList.add(comicSum);
	
			}
		comicSummariesList.removeIf(comicSummary -> comicSummary.getComicTitle() == null);
		Collections.sort(comicSummariesList, new Comparator<ComicDSummariesDto>() {
		    @Override
		    public int compare(ComicDSummariesDto o1, ComicDSummariesDto o2) {
		        Integer latestEpisodeView1 = o1.getLatestEpisodeViews();
		        Integer latestEpisodeView2 = o2.getLatestEpisodeViews();

		        if (latestEpisodeView1 == null && latestEpisodeView2 == null) {
		        	return o1.getPublishDate().compareTo(o2.getPublishDate());
		            // Both are null, consider them equal
		        } else if (latestEpisodeView1 == null) {
		            return -1; // Treat null as less than non-null
		        } else if (latestEpisodeView2 == null) {
		            return 1; // Treat null as less than non-null
		        }
		        return latestEpisodeView2.compareTo(latestEpisodeView1);
		    }
		});

			 
			 return comicSummariesList;
		}return null;

	}
	

	
	public Map<String,List<?>> findComicEpisodeByPage(Integer pageNum) {
	      PageRequest pageRequest = PageRequest.of((pageNum - 1), 8
	              , Sort.Direction.ASC, "comicId");
	      Page<ComicD> data = comicDDao.findAll(pageRequest);
	      List<PageUtil> pageUtil = new ArrayList<>();
	      pageUtil.add( new PageUtil(
	    		  data.getTotalPages(),
	    		  data.getTotalElements(), 
	    		  data.getNumber()));
	      List<ComicD> pageData = data.getContent();      
	      List<ComicDEpisodesDto> comicEpisodes =
	    		  pageData.stream()
	    		  .map(comic -> ComicDEpisodesDto.toDto(comic))
	    		  .collect(Collectors.toList());
	      Map<String,List<?>> result = new HashMap<>();
	      result.put("pageUtil", pageUtil);
	      result.put("comicEpisodes",comicEpisodes);
	      return result;	      
	  }
	
	public Map<String,List<?>> findComicEpisodeBySearch(Map<String,Object> comicIdsPage) {
	      PageRequest pageRequest = PageRequest.of(((Integer)comicIdsPage.get("pageNum")- 1), 8
	              , Sort.Direction.ASC, "comicId");
	      Page<ComicD> data = comicDDao.findByComicIdIn((List<Integer>)comicIdsPage.get("comicIds"), pageRequest);
	      List<PageUtil> pageUtil = new ArrayList<>();
	      pageUtil.add( new PageUtil(
	    		  data.getTotalPages(),
	    		  data.getTotalElements(), 
	    		  data.getNumber()));
	      List<ComicD> pageData = data.getContent();      
	      List<ComicDEpisodesDto> comicEpisodes =
	    		  pageData.stream()
	    		  .map(comic -> ComicDEpisodesDto.toDto(comic))
	    		  .collect(Collectors.toList());
	      Map<String,List<?>> result = new HashMap<>();
	      result.put("pageUtil", pageUtil);
	      result.put("comicEpisodes",comicEpisodes);
	      return result;	      
	  }
	
	
	
  public List<ComicDEpisodeUpdateDto> findComicEpsiodeExpandByPage(Integer pageNum) {
      PageRequest pageRequest = PageRequest.of((pageNum - 1), 10
              , Sort.Direction.ASC, "comicId");
      List<ComicD> pageData = comicDDao.findAll(pageRequest).getContent();      
      List<ComicDEpisodeUpdateDto> comicEpisodes = new ArrayList<>();   
      for(ComicD comic : pageData) {	  
    	  for (ComicDEpisodeUpdate episode : comic.getEpisodes()) {
    		  ComicDEpisodeUpdateDto comicEpisode = new ComicDEpisodeUpdateDto(); 
        	  comicEpisode.setComicId(comic.getComicId());
        	  comicEpisode.setComicTitle(comic.getComicTitle());
        	  comicEpisode.setComicCover(comic.getComicCover());
    		  comicEpisode.setEpisodeLikes(episode.getEpisodeLikes());
    		  comicEpisode.setEpisodeViews(episode.getEpisodeViews());
    		  comicEpisode.setEpisodeNum(episode.getEpisodeNum());
    		  comicEpisode.setEpisodeCover(episode.getEpisodeCover());
    		  comicEpisodes.add(comicEpisode);
    	  }
      }return comicEpisodes;
      
  }
  
  
	
	public List<ComicD> findAll() {
		return comicDDao.findAll();
	}
	
	public List<ComicDDto> findSimpleComicAll() {
		List<ComicDDto> simpleComicDDtoList = 
			comicDDao.findAll().stream()
		    .map(ComicDDto::toDto)
		    .collect(Collectors.toList());		
		return simpleComicDDtoList;
	
	}
	
	public List<ComicDDto> findCalculatedComicAll() {
		List<ComicD> comicDs = comicDDao.findAll();
		List<ComicDDto> result = new ArrayList<>();
		for(ComicD comic : comicDs) {		
			ComicDDto comicDDto = ComicDDto.toCalculatedData(comic);	
			List<String> genreNames = new ArrayList<>();
			for (Integer id : comicDDto.getGenre()){
				genreNames.add(comicDGenreDao.findGenreNameByGenreId(id));				
			}
			comicDDto.setGenreName(genreNames);
			result.add(comicDDto);
		}return result;

	}
	
	
	public List<ComicDSummariesDto> findCalculatedComicAllFast() {		
		List<Map<String,Object>> summaries = comicDDao.findComicSummaries();
		List<Map<String,Object>> comments = comicDDao.findComicComment();
		List<Map<String,Object>> genres = comicDDao.findComicGenre();
		return calculatedToDto(summaries,comments,genres);
	}
	
	
	public Map<String,Object> findCalculatedComicCrossColumn(Map<String,Object> search) {	
		String formattedSearchString = String.format("%%%s%%", (String)search.get("searchTerm"));
		List<Map<String,Object>> summariesResult = comicDDao.findComicSummariesBySearchTerm(formattedSearchString);
		List<Map<String,Object>> commentsResult = comicDDao.findComicCommentBySearchTerm(formattedSearchString);
		List<Integer> genresResult = comicDDao.findComicGenreBySearchTerm(formattedSearchString);
		List<Integer> combinedSearchIds = new ArrayList<>();
		if(!summariesResult.isEmpty() || !commentsResult.isEmpty() || !genresResult.isEmpty()){
			/*summariesResult.forEach(data -> {
				Integer comicLikesEp = (Integer)data.get("comicLikesEP");
				Integer comicLikes = (Integer)data.get("comicLikes");
				comicLikes += comicLikesEp;			
			});*/
			combinedSearchIds.addAll(
					summariesResult.stream()
					.map(data -> (Integer)data.get("comicId"))
					.collect(Collectors.toList()));
			
		}if(!commentsResult.isEmpty()) {
			combinedSearchIds.addAll(
					commentsResult.stream()
					.map(data -> (Integer)data.get("comicId"))
					.collect(Collectors.toList()));
			
		}if(!genresResult.isEmpty()) {
			combinedSearchIds.addAll(genresResult);
			
		}
		Map<String,Object> result = new HashMap<>();
		result.put("comicIds", combinedSearchIds);
		result.put("comics", calculatedComicByIdsToDto(combinedSearchIds));
		return result;
		
	}
	
	
	
	
	private List<ComicDSummariesDto> calculatedToDto(	
			List<Map<String, Object>> summaries, 
			List<Map<String, Object>> comments,
			List<Map<String, Object>> genres) {
		
		List<ComicDSummariesDto> dtoList = new ArrayList<>();
		for (Map<String, Object> comicSummary : summaries) {
		    ComicDSummariesDto dto = new ComicDSummariesDto(
		        (Integer) comicSummary.get("comicId"),
		        (String) comicSummary.get("comicTitle"),
		        (String) comicSummary.get("comicDescription"),
		        (String) comicSummary.get("updateDay"),
		        (Date) comicSummary.get("publishDate"),
		        (Boolean) comicSummary.get("editorChoice"),
		        (Integer) comicSummary.get("purchasePrice"),
		        (Integer) comicSummary.get("rentalPrice"),
		        (BigDecimal) comicSummary.get("comicLikesEP"),
		        (BigDecimal) comicSummary.get("comicViews"),
		        (Long) comicSummary.get("comicLikes")
		    );
		    dtoList.add(dto);
		}
		
		for (Map<String,Object> comment : comments) {
		    Integer comicId = (Integer) comment.get("comicId");
		    Long numComments = (Long) comment.get("comments");
		    for (ComicDSummariesDto dto : dtoList) {
		        if (dto.getComicId().equals(comicId)) {
		            dto.setComments(numComments);
		            break; 
		        }
		    }
		}
		
		for (Map<String,Object> genre : genres) {
		    Integer comicId = (Integer) genre.get("comicId");
		    String genreName = (String) genre.get("genreName");
		    for (ComicDSummariesDto dto : dtoList) {
		        if (dto.getComicId().equals(comicId)) {
		            dto.getGenreName().add(genreName);
		            break; 
		        }
		    }
		}
		return dtoList;	
	}
	
	private List<ComicDSummariesDto> calculatedComicByIdsToDto(List<Integer> comicIds){
		List<ComicDSummariesDto> dtoList = new ArrayList<>();
		for(Integer id :comicIds) {
			Map<String, Object> comicSummary = comicDDao.findComicSummariesById(id);
			ComicDSummariesDto dto = new ComicDSummariesDto(
			        (Integer) comicSummary.get("comicId"),
			        (String) comicSummary.get("comicTitle"),
			        (String) comicSummary.get("comicDescription"),
			        (String) comicSummary.get("updateDay"),
			        (Date) comicSummary.get("publishDate"),
			        (Boolean) comicSummary.get("editorChoice"),
			        (Integer) comicSummary.get("purchasePrice"),
			        (Integer) comicSummary.get("rentalPrice"),
			        (BigDecimal) comicSummary.get("comicLikesEP"),
			        (BigDecimal) comicSummary.get("comicViews"),
			        (Long) comicSummary.get("comicLikes")
			    );
			
			Map<String,Object> comment = comicDDao.findComicCommentByComicId(id);
		    Long numComments = (Long) comment.get("comments");
		    dto.setComments((Long)numComments);
		   
		    
			List<Map<String,Object>> genres = comicDDao.findComicGenreByComicId(id);
		    dto.getGenreName().addAll(
		    		genres.stream()
		    		.map(genre -> (String)genre.get("genreName"))
		    		.collect(Collectors.toList()));
		    
		    dtoList.add(dto);
		}
		return dtoList;	
	}
	
		
	public List<ComicDComicConGenres> findComicGenresByComicId(Integer comicId) {
		Optional<ComicD> comicD = comicDDao.findById(comicId);
		if (comicD.isPresent()) {
			return comicD.get().getComicGenres();			
		}return null;
	}
	
	
	public List<Map<String,Object>> findComicDraftsAll(){
		return comicDDao.findComicDraftsAll();		
	}
	
	
	public ComicD save(ComicD comic) {
			return comicDDao.save(comic);						
	}
	
	public List<Map<String,Object>> updateComicDByColumnName(List<Map<String, Object>> updateComics) throws ParseException{
		List<Map<String,Object>> genreNameAdded = null;
		for(Map<String, Object> mapComic : updateComics) {
			String colName = mapComic.keySet().stream()
					.filter(keyname -> !keyname.equals("comicId")).findFirst().get();
			Integer comicId = (Integer) mapComic.get("comicId");
			Object updateData = mapComic.get(colName);
			System.out.println(updateData);
		switch (colName) {
			case "genreName":
			case "genreNameColor":
				List<Map<String,Object>> genreNameColorList = (List<Map<String,Object>>) updateData;
				handleUpdateGenres(comicId, genreNameColorList);
				break;
			case "publishDate":
				Object parsedDate = DateUtil.parseDate((String) updateData);
	            updateComicAttribute(colName, parsedDate, comicId);
	            break;	            
			case "editorChoice":
				int editorChoiceValue = (Boolean) updateData ? 1 : 0;
	            updateComicAttribute(colName, editorChoiceValue, comicId);
	            break;
			default:
	            updateComicAttribute(colName, updateData, comicId);    			
	        
			}
		
		}return genreNameAdded;
		
	}
	
	private void handleUpdateGenres(Integer comicId, List<Map<String, Object>> genreNameColors) {
	    comicDComicConGenresDao.deleteBaseOneComicId(comicId);
	    //List<Map<String, Object>> newGenreIds = new ArrayList<>();
	    List<Integer> genreIds = new ArrayList<>();
	    for(Map<String, Object> genreNameColor : genreNameColors ) {
	    	String genreName = (String)genreNameColor.get("genreName");
	    	Integer genreId = comicDGenreDao.findGenreIdByGenreName(genreName);
	    	if(genreId == null || genreId == 0) {
	    		 ComicDGenres genre = new ComicDGenres();
	    		 genre.setGenreName(genreName);
	    		 Integer newGenreId = comicDGenreDao.save(genre).getGenreId();
	    		 genreIds.add(newGenreId);
	    		 genreNameColor.put("genreIds", newGenreId);
	    		 handleJsonColorFileUpdate(genreNameColor);
	    	}else {
	    		genreIds.add(genreId);
	    	}	
	    }
	    List<ComicDComicConGenres> comicGenres = genreIds.stream()
	            .map(genreId -> new ComicDComicConGenres(comicId, genreId))
	            .collect(Collectors.toList());
	    comicDComicConGenresDao.saveAll(comicGenres);
	}
	

	private void updateComicAttribute(String columnName, Object value, Integer comicId) {
	    comicDDao.updateComicDByColumnName(columnName, value, comicId);
	}
	
	
	private void handleJsonColorFileUpdate(Map<String,Object> newData) {
		try {
			 Path filePath = Paths.get("C:\\Users\\User\\Desktop\\G1\\workspace\\RemeComic_VueJS\\src\\assets\\comicD\\genreColor.json");
		        ObjectMapper objectMapper = new ObjectMapper();
		        String existingData = Files.readString(filePath);

		        // Parse existing data into a List
		        List<Map<String, Object>> jsonList = objectMapper.readValue(existingData, new TypeReference<List<Map<String, Object>>>() {});

		        // Add the new data directly to the list
		        jsonList.add(newData);

		        // Write the updated list back to the file
		        Files.writeString(filePath, objectMapper.writeValueAsString(jsonList));
			
		}catch (IOException e) {
            e.printStackTrace();
            
        }
	}
	
	
	
	/*public String updateComicDByColumnName(List<Map<String, Object>> updateComics) throws ParseException {
		Integer rowUpdate = 0;
		String genreNameConfirm = "No Add Genre";
		for(Map<String, Object> mapComic : updateComics) {
			String colName = mapComic.keySet().stream()
					.filter(keyname -> !keyname.equals("comicId")).findFirst().get();
			Object comicId = mapComic.get("comicId");
			Object updateData = mapComic.get(colName);
			System.out.print(comicId);
			System.out.print(updateData);
			if(colName == "genreName") {
				comicDComicConGenresDao.deleteBaseOneComicId((Integer)comicId);
				List<Integer> genreIds = comicDGenreDao.findGenreIdsByGenreNames((List<String>)updateData);
				List<ComicDComicConGenres> comicGenres = genreIds.stream()
					    .map(genreId -> {
					        ComicDComicConGenres comicGenre = new ComicDComicConGenres((Integer)comicId, genreId);
					        return comicGenre;
					    })
					    .collect(Collectors.toList());
				comicDComicConGenresDao.saveAll(comicGenres);
				
			} else if(colName == "publishDate") {
				updateData = DateUtil.parseDate((String)updateData);
				comicDDao.updateComicDByColumnName((String)colName, (Object)updateData, (Integer)comicId);
			} else if(colName == "editorChoice") {
				if((Boolean)updateData) {
					updateData = 1;
				}else {
					updateData = 0;
				}
				comicDDao.updateComicDByColumnName((String)colName, (Object)updateData, (Integer)comicId);
			} else {
				comicDDao.updateComicDByColumnName((String)colName, (Object)updateData, (Integer)comicId);
			}
		}return genreNameConfirm;							
	}*/
	
	
	public void deleteById(Integer comicId) {
		comicDDao.deleteById(comicId);
						
}

	

}
