package tw.com.remecomic.orgcomic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.comic.model.bean.ComicDRatingComic;
import tw.com.remecomic.comic.model.bean.ComicDRatingComicPK;
import tw.com.remecomic.orgcomic.model.bean.OrgComicLike;
import tw.com.remecomic.orgcomic.model.bean.OrgComicLikePK;
import tw.com.remecomic.orgcomic.model.dao.OrgComicLikeDao;

@Service
public class OrgComicLikeService {

	
	private OrgComicLikePK pk;
	
    @Autowired
    private OrgComicLikeDao orgComicLikeDao;

    public OrgComicLike save(OrgComicLike orgComicLike) {
        return orgComicLikeDao.save(orgComicLike);
    }

    public Optional<OrgComicLike> findById(Integer comicId, Integer userId) {
    	pk = new OrgComicLikePK(comicId, userId);
        return orgComicLikeDao.findById(pk);
    }

    public List<OrgComicLike> findAll() {
        return orgComicLikeDao.findAll();
    }

    public boolean deleteById(Integer comicId, Integer userId) {
    	pk = new OrgComicLikePK(comicId, userId);
        Optional<OrgComicLike> optionalOrgComicLike = orgComicLikeDao.findById(pk);

        if (optionalOrgComicLike.isPresent()) {
            orgComicLikeDao.deleteById(pk);
            return true;
        } else {
            return false;
        }
    }
    
    
    
//    public OrgComicLike updateOrgComicLike(Integer comicId, Integer userId, OrgComicLike updatedOrgComicLike) {
//    	pk = new OrgComicLikePK(comicId, userId);
//        Optional<OrgComicLike> optionalOrgComicLike = orgComicLikeDao.findById(pk);
//
//        if (optionalOrgComicLike.isPresent()) {
//            OrgComicLike existingOrgComicLike = optionalOrgComicLike.get();
//            existingOrgComicLike.setComicId(updatedOrgComicLike.getComicId());
//            existingOrgComicLike.setUser(updatedOrgComicLike.getUser());
//            return orgComicLikeDao.save(existingOrgComicLike);
//        } else {
//            return null;
//        }
//    }
}
