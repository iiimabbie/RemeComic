package tw.com.remecomic.comic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.comic.model.dao.ComicDComicConGenresDao;

@Service
public class ComicDComicConGenresService {
	@Autowired
	ComicDComicConGenresDao comicDComicConGenresDao;
	

}
