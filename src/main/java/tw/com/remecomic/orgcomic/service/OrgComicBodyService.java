package tw.com.remecomic.orgcomic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import tw.com.remecomic.orgcomic.model.bean.OrgComicBody;
import tw.com.remecomic.orgcomic.model.dao.OrgComicBodyDao;

@Service
public class OrgComicBodyService {

    @Autowired
    private OrgComicBodyDao orgComicBodyDao;

    public OrgComicBody save(OrgComicBody orgComicBody) {
        return orgComicBodyDao.save(orgComicBody);
    }

    public Optional<OrgComicBody> findById(Integer comicEpisode) {
        return orgComicBodyDao.findById(comicEpisode);
    }

    public List<OrgComicBody> findAll() {
        return orgComicBodyDao.findAll();
    }
    public OrgComicBody updateOrgComicBody(Integer comicEpisode, OrgComicBody updatedOrgComicBody) {
        Optional<OrgComicBody> optionalOrgComicBody = orgComicBodyDao.findById(comicEpisode);

        if (optionalOrgComicBody.isPresent()) {
            OrgComicBody existingOrgComicBody = optionalOrgComicBody.get();

            // Check if updatedOrgComicBody contains new comicId and comicEpisode values
            if (updatedOrgComicBody.getComicId() != null) {
                existingOrgComicBody.setComicId(updatedOrgComicBody.getComicId());
            }

            if (updatedOrgComicBody.getComicEpisode() != null) {
                existingOrgComicBody.setComicEpisode(updatedOrgComicBody.getComicEpisode());
            }

            existingOrgComicBody.setComicBodyPhoto(updatedOrgComicBody.getComicBodyPhoto());
            return orgComicBodyDao.save(existingOrgComicBody);
        } else {
            return null;
        }
    }
//    public OrgComicBody updateOrgComicBody(Integer comicEpisode, OrgComicBody updatedOrgComicBody) {
//        Optional<OrgComicBody> optionalOrgComicBody = orgComicBodyDao.findById(comicEpisode);
//
//        if (optionalOrgComicBody.isPresent()) {
//            OrgComicBody existingOrgComicBody = optionalOrgComicBody.get();
//            existingOrgComicBody.setComicBodyPhoto(updatedOrgComicBody.getComicBodyPhoto());
//            return orgComicBodyDao.save(existingOrgComicBody);
//        } else {
//            return null;
//        }
//    }

    public boolean deleteById(Integer comicEpisode) {
        Optional<OrgComicBody> optionalOrgComicBody = orgComicBodyDao.findById(comicEpisode);

        if (optionalOrgComicBody.isPresent()) {
            orgComicBodyDao.deleteById(comicEpisode);
            return true;
        } else {
            return false;
        }
    }
}
