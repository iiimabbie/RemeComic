package tw.com.remecomic.orgcomic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.orgcomic.model.bean.OrgComic;
import tw.com.remecomic.orgcomic.model.bean.OrgComicComment;
import tw.com.remecomic.orgcomic.model.bean.OrgComicLike;
import tw.com.remecomic.orgcomic.model.dao.OrgComicDao;

@Service
public class OrgComicService {

	
	@Autowired
	private OrgComicDao orgComicDao;
	

	public OrgComic save(OrgComic orgComic) {
        return orgComicDao.save(orgComic);
    }

    public Optional<OrgComic> findById(Integer ComicId) {
        return orgComicDao.findById(ComicId);
    }

    public List<OrgComic> findAll() {
        return orgComicDao.findAll();
    }

    public OrgComic updateOrgComic(Integer comicId, OrgComic updatedOrgComic) {
        Optional<OrgComic> optionalOrgComic = orgComicDao.findById(comicId);
//        return orgComicDao.save(updatedOrgComic);
        if (optionalOrgComic.isPresent()) {
            OrgComic existingOrgComic = optionalOrgComic.get();
            existingOrgComic.setVerify(updatedOrgComic.getVerify());
            existingOrgComic.setComicName(updatedOrgComic.getComicName());
            existingOrgComic.setOrgComicCover(updatedOrgComic.getOrgComicCover());
            existingOrgComic.setOrgPublishDate(updatedOrgComic.getOrgPublishDate());
            existingOrgComic.setIntroduction(updatedOrgComic.getIntroduction());
            existingOrgComic.setGenreId(updatedOrgComic.getGenreId());
            return orgComicDao.save(existingOrgComic);
        } else {
            return null; 
        }
    }

    public boolean deleteById(Integer comicId) {
        Optional<OrgComic> optionalOrgComic = orgComicDao.findById(comicId);

        if (optionalOrgComic.isPresent()) {
            OrgComic orgComic = optionalOrgComic.get();
            orgComicDao.deleteById(orgComic.getComicId());
            return true;
        } else {
            return false;
        }
    }
    
    
}
