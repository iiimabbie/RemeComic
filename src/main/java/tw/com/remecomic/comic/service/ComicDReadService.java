package tw.com.remecomic.comic.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.comic.model.bean.ComicDRead;
import tw.com.remecomic.comic.model.dao.ComicDReadDao;
import tw.com.remecomic.comic.model.dto.ComicDReadDto;

@Service
public class ComicDReadService {
	@Autowired 
	ComicDReadDao comicDReadDao;
	
	public List<ComicDReadDto> findReadPageByUserId(Integer comicId, Integer userId){
		List<Map<String,Object>> readByUser = comicDReadDao.findReadPageByUserId(comicId,userId);
		List<ComicDReadDto> readDtos = ComicDReadDto.toReadDto(readByUser);
		return readDtos;
	}
	
	public List<ComicDRead> saveAll(List<ComicDRead> reads){
		return comicDReadDao.saveAll(reads);
	}
	
	public ComicDRead save(ComicDRead read){
		return comicDReadDao.save(read);
	}
	
	
	public List<ComicDRead> findByUserIdAndPageIds(Integer userId, List<Integer> pageIds){
		return comicDReadDao.findByUserIdAndPageIds(userId,pageIds);
	}
	
	public ComicDRead findByUserIdAndPageId(Integer userId, Integer pageId){
		return comicDReadDao.findByUserIdAndPageId(userId,pageId);
	}
	
	
	

}
