package tw.com.remecomic.comic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tw.com.remecomic.comic.model.bean.ComicD;
import tw.com.remecomic.comic.model.bean.ComicDComicConGenres;
import tw.com.remecomic.comic.model.bean.ComicDDrafts;
import tw.com.remecomic.comic.model.bean.ComicDDraftsConComics;
import tw.com.remecomic.comic.model.bean.ComicDDraftsConGenres;
import tw.com.remecomic.comic.model.bean.ComicDGenres;
import tw.com.remecomic.comic.model.dao.ComicDComicConGenresDao;
import tw.com.remecomic.comic.model.dao.ComicDDao;
import tw.com.remecomic.comic.model.dao.ComicDDraftsConComicsDao;
import tw.com.remecomic.comic.model.dao.ComicDDraftsConGenresDao;
import tw.com.remecomic.comic.model.dao.ComicDDraftsDao;
import tw.com.remecomic.comic.model.dao.ComicDGenresDao;
import tw.com.remecomic.comic.model.dto.ComicDDraftsDto;
import tw.com.remecomic.util.JsonUtil;
import tw.com.remecomic.util.PageUtil;

@Service
public class ComicDDraftsService {
	@Autowired
	ComicDDraftsDao  comicDDraftsDao;
	
	@Autowired
	ComicDDraftsConGenresDao  comicDDraftsConGenresDao;
	
	@Autowired
	ComicDComicConGenresDao  comicDComicConGenresDao;
	
	@Autowired
	ComicDDraftsConComicsDao  comicDDraftsConComicsDao;
	
	@Autowired
	ComicDGenresDao comicDGenresDao;
	
	@Autowired
	ComicDDao  comicDDao;

	
	public Map<String,Object> findAll(Integer pageNum) {
		PageRequest pageRequest = PageRequest.of((pageNum - 1), 11
	              , Sort.Direction.DESC, "createdDate");
	      Page<ComicDDrafts> data = comicDDraftsDao.findAll(pageRequest);
	      List<PageUtil> pageUtil = new ArrayList<>();
	      pageUtil.add( new PageUtil(
	    		  data.getTotalPages(),
	    		  data.getTotalElements(), 
	    		  data.getNumber()));
	      List<ComicDDrafts> pageDatas = data.getContent(); 
	      List<Integer> comicIds = pageDatas.stream()
									      .map(draft -> draft.getDraftId())
									      .collect(Collectors.toList());
	      List<Map<String,Object>> genreNamesWithDraftId = comicDDraftsConGenresDao.findGenreNamesByComicDraftIds(comicIds);
	      List<ComicDDraftsDto> draftAddedGenre = pageDatas.stream()
	    		  .map(ComicDDraftsDto::toDto)
	    		  .map(dto -> {
	    			  List<String> genreNames = genreNamesWithDraftId.stream()
	    			  .filter(draft -> draft.get("draftId").equals(dto.getDraftId()))
	    			  .map(draft -> (String) draft.get("genreName"))
	    			  .collect(Collectors.toList());
	    			  
	    			 dto.getGenres().addAll(genreNames);
	    			 return dto;
	    		  })
	    		  .collect(Collectors.toList());
	     /* for(ComicDDraftsDto draft: draftAddedGenre) {
	    	  System.out.print(draft.getDraftId());
	    	 Integer comicDraftsId = comicDDraftsConComicsDao.findComicIdByDraftId(draft.getDraftId());
//	    	 !comicDraftsIds.isEmpty() ||
	    	  if( comicDraftsId != null) {
	    		 System.out.print("comicId============================" + comicDraftsId);
	    		  draft.setComicId(comicDraftsId);
	    	  }else {
	    		  draft.setComicId(0);
	    	  }
	      }*/
	      Map<String,Object> result = new HashMap<>();
	      result.put("pageUtil", pageUtil);
	      result.put("drafts",draftAddedGenre);
	      return result;	      		
	}
	
	public ComicDDrafts save(ComicDDrafts comicDDrafts) {
		return comicDDraftsDao.save(comicDDrafts);
	}
	
	public Optional<ComicDDrafts> findById(Integer draftId) {
		return comicDDraftsDao.findById(draftId);
	}
	
	public ComicDDraftsDto findByIdWithGenreNames(Integer draftId) {
		Optional<ComicDDrafts> draftOp= comicDDraftsDao.findById(draftId);
		if(draftOp.isPresent()) {
			ComicDDrafts draft = draftOp.get();
			ComicDDraftsDto draftDto = ComicDDraftsDto.toDto(draft);
			List<ComicDDraftsConGenres> comicConList = draft.getGenres();
			List<Integer> genreIds = comicConList.stream().map(data -> data.getGenreId()).collect(Collectors.toList());
			List<String> genreName = comicDGenresDao.findGenreNamesByGenreIds(genreIds);
			draftDto.getGenres().addAll(genreName);
		return draftDto;
		}return null;
		
	}
	
	public ComicDDrafts comicDraftUpdate (ComicDDrafts comicDraft) {
		Integer draftId = comicDraft.getDraftId();
		Optional<ComicDDrafts> isDraft = comicDDraftsDao.findById(draftId);
		//List<Integer> isDraftConComics = comicDDraftsConComicsDao.findByDraftId(draftId);
		ComicDDrafts savedDraft = null;
		if(isDraft.isPresent()) {
			ComicDDrafts draft = isDraft.get();
			draft.setComicTitle(comicDraft.getComicTitle());
			draft.setCreator(comicDraft.getCreator());
		    draft.setComicDescription(comicDraft.getComicDescription());
		    draft.setComicCover(comicDraft.getComicCover());
		    draft.setGenres(comicDraft.getGenres());
		    draft.setPublishDate(comicDraft.getPublishDate());
		    draft.setUpdateDay(comicDraft.getUpdateDay());
		    draft.setPurchasePrice(comicDraft.getPurchasePrice());
		    draft.setRentalPrice(comicDraft.getRentalPrice());
		    draft.setModificationTime(comicDraft.getModificationTime());
		    savedDraft = comicDDraftsDao.save(draft);
		    //comicDDraftsConComicsDao.save(draftId,isDraftConComics.get(0));
		}else {
			ComicDDrafts draft = new ComicDDrafts();
			draft = comicDraft;	
			savedDraft = comicDDraftsDao.save(draft);
		}
		return savedDraft;	
		
	}
	
	public List<String> handleGenreUpdate(Integer draftId, List<Map<String,Object>> genreNameColors) {
		comicDDraftsConGenresDao.deleteByDraftId(draftId);
	    List<Integer> genreIds = new ArrayList<>();
	    List<String> genreNames = new ArrayList<>();
	    for(Map<String, Object> genreNameColor : genreNameColors) {
	    	String genreName = (String)genreNameColor.get("genreName");
	    	genreNames.add(genreName);
	    	Integer genreId = comicDGenresDao.findGenreIdByGenreName(genreName);
	    	if(genreId == null || genreId == 0) {
	    		 ComicDGenres genre = new ComicDGenres();
	    		 genre.setGenreName(genreName);
	    		 ComicDGenres newGenre = comicDGenresDao.save(genre);
	    		 Integer newGenreId = newGenre.getGenreId();
	    		 genreIds.add(newGenreId);
	    		 genreNameColor.put("genreId", newGenreId);
	    		 JsonUtil.handleJsonColorFileUpdate(genreNameColor);
	    	}else {
	    		genreIds.add(genreId);
	    	}	
	    }
	    System.out.println("==========genreNames In handelGenreUpdate============" + genreNames);
	    List<ComicDDraftsConGenres> draftGenres = genreIds.stream()
	            .map(genreId -> new ComicDDraftsConGenres(draftId, genreId))
	            .collect(Collectors.toList());
	    comicDDraftsConGenresDao.saveAll(draftGenres);
		return genreNames;	
	}
	
	public void deleteById (Integer draftId) {
		comicDDraftsDao.deleteById(draftId);
		
	}
	
	public ComicDDraftsDto publishDraftToComic(LinkedHashMap<String,Object> map) {
		ComicDDrafts comicDDraft = ComicDDraftsDto.convertMapToComicDDrafts(map);
		Integer draftId = comicDDraft.getDraftId();
		ComicDDrafts draftToPublish = comicDDraftsDao.findById(draftId).get();
		Integer comicId = 0;
		List<Integer> comicIds = comicDDraftsConComicsDao.findByDraftId(draftId);
		ComicD newComicD = null;		
		
		
		if(!comicIds.isEmpty()) {
			comicId = comicIds.get(0);
			newComicD = setDataToComicD(comicId,comicDDraft);
			System.out.println("comicId======="+comicIds.get(0));
			ComicD comicD = comicDDao.findById(comicId).get();
			System.out.println("This is comicD debugging========"+ comicD.getComicCover()+ comicD.getComicId());
			comicD = newComicD;
			System.out.println("This is comicD debugging 22========"+ comicD.getComicCover() + "[=====]"+comicD.getComicId());
			comicDDao.save(comicD);
			comicDComicConGenresDao.deleteBaseOneComicId(comicD.getComicId());
			List<Integer> genreIds = comicDGenresDao.findGenreIdsByGenreNames((List<String>)map.get("genres"));
			if(genreIds.isEmpty()) {
				return null;
			}
			List<ComicDComicConGenres> conComicGenres = 
					genreIds.stream()
					.map(genreId -> new ComicDComicConGenres(comicIds.get(0), genreId))
					.collect(Collectors.toList());
			comicDComicConGenresDao.saveAll(conComicGenres);
			System.out.print("version size================="+comicIds.size());
			comicDDraftsConComicsDao.save(draftId,comicId);
			//draftToPublish.setVersion(draftToPublish.getVersion()+1);
		}else {
			newComicD = setDataToComicD(comicId,comicDDraft);
			Integer newComicId = newComicD.getComicId();
			List<Integer> genreIds = comicDGenresDao.findGenreIdsByGenreNames((List<String>)map.get("genres"));
			List<ComicDComicConGenres> conComicGenres = 
					genreIds.stream()
					.map(genreId -> new ComicDComicConGenres(newComicId, genreId))
					.collect(Collectors.toList());
			comicDComicConGenresDao.saveAll(conComicGenres);	
			draftToPublish.setVersion(1);
			comicDDraftsConComicsDao.save(draftId,newComicId);
			
		}

		ComicDDraftsDto draftDto = ComicDDraftsDto.toDto(draftToPublish);
		System.out.println(draftDto.getIsPublished());
		//comicDDraftsConComicsDao.save(draftId,newComicId);
		
		draftToPublish.setIsPublished(1);
		draftToPublish.setPublishDate(new Date());
		comicDDraftsDao.save(draftToPublish);
		return draftDto;
		
	}
	
	//set create date convert to Dto add draftConComics
	/*public List<ComicDDrafts> publishDraftsToComics(List<LinkedHashMap<String,Object>> maps) {
		List<ComicDDrafts> comicDDrafts = ComicDDraftsDto.convertMapsToComicDDrafts(maps);
		comicDDrafts.stream().getDraftIds()
		List<ComicD> comics = comicDDrafts.stream().map(draft ->{
													draft.setIsPublished(1);
													comicDDraftsDao.save(draft);
													return setDataToComicD(comicId, draft);
												})
												.collect(Collectors.toList());
		List<ComicDComicConGenres> comicGenres = maps.stream()
				.flatMap( map -> comics.stream()
					.flatMap(comic -> {
						Integer comicId = comic.getComicId();
						List<Integer> genreIds = 
								comicDGenresDao.findGenreIdsByGenreNames((List<String>)map.get("genres"));
						return genreIds.stream().map(genreId -> new ComicDComicConGenres(comicId, genreId));
					})			
				)
				.collect(Collectors.toList());	
		comicDComicConGenresDao.saveAll(comicGenres);
		return comicDDrafts;
	}*/
	
	public ComicDDraftsDto unlockAndDuplicateDraft(Integer draftId) {
		Integer comicId = comicDDraftsConComicsDao.findComicIdByDraftId(draftId);
		List<Integer> draftIds = comicDDraftsConComicsDao.findByComicId(comicId);
		ComicDDrafts duplicateDraft = setDataToComicDDraft(comicDDraftsDao.findById(draftId).get());
		System.out.println(duplicateDraft.getDraftId());
		if(!draftIds.isEmpty() || draftIds != null ) {
			System.out.print("draftsId.size()========="+ draftIds.size());
			duplicateDraft.setVersion(draftIds.size()+1);
		}
		else {
			duplicateDraft.setVersion(1);
		}
		duplicateDraft.setIsPublished(0);
		comicDDraftsConComicsDao.save(duplicateDraft.getDraftId(),comicId);
		comicDDraftsDao.save(duplicateDraft);
		return ComicDDraftsDto.toDto(duplicateDraft);

	}
	
	
	
	
	private ComicD setDataToComicD(Integer comicId, ComicDDrafts comicDDraft) {
		ComicD comic = new ComicD();
		if(comicId == 0 || comicId == null) {
			comic.setComicTitle(comicDDraft.getComicTitle());
			comic.setCreator(comicDDraft.getCreator());
			comic.setComicDescription(comicDDraft.getComicDescription());
			comic.setComicCover(comicDDraft.getComicCover());
			comic.setUpdateDay(comicDDraft.getUpdateDay());
			comic.setPublishDate(comicDDraft.getPublishDate());
			comic.setPurchasePrice(comicDDraft.getPurchasePrice());
			comic.setRentalPrice(comicDDraft.getRentalPrice());
		}else {
			comic.setComicId(comicId);
			comic.setComicTitle(comicDDraft.getComicTitle());
			comic.setCreator(comicDDraft.getCreator());
			comic.setComicDescription(comicDDraft.getComicDescription());
			comic.setComicCover(comicDDraft.getComicCover());
			comic.setUpdateDay(comicDDraft.getUpdateDay());
			comic.setPublishDate(comicDDraft.getPublishDate());
			comic.setPurchasePrice(comicDDraft.getPurchasePrice());
			comic.setRentalPrice(comicDDraft.getRentalPrice());
		}
		ComicD newComicD = comicDDao.save(comic);	
		return comic;
	}
	
	private ComicDDrafts setDataToComicDDraft(ComicDDrafts comicDDraft) {
		ComicDDrafts draft = new ComicDDrafts();
		draft.setComicTitle(comicDDraft.getComicTitle());
		draft.setCreator(comicDDraft.getCreator());
		draft.setComicDescription(comicDDraft.getComicDescription());
		draft.setComicCover(comicDDraft.getComicCover());
		draft.setUpdateDay(comicDDraft.getUpdateDay());
		draft.setPublishDate(comicDDraft.getPublishDate());
		draft.setPurchasePrice(comicDDraft.getPurchasePrice());
		draft.setRentalPrice(comicDDraft.getRentalPrice());
		ComicDDrafts newDraft = comicDDraftsDao.save(draft);
		return newDraft;
	}
	

}
