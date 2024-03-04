package tw.com.remecomic.comic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import tw.com.remecomic.comic.model.dao.ComicDDraftsConGenresDao;

@Service
public class ComicDDraftsConGenresService {
	@Autowired
	ComicDDraftsConGenresDao comicDDraftsConGenresDao;

}
