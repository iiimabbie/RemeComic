package tw.com.remecomic.orgcomic.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.orgcomic.model.bean.OrgComicComment;
import tw.com.remecomic.orgcomic.model.dao.OrgComicCommentDao;

import java.util.List;
import java.util.Optional;

@Service
public class OrgComicCommentService {
	
	@Autowired
    private OrgComicCommentDao orgComicCommentDao;


    public OrgComicComment save(OrgComicComment orgComicComment) {
        return orgComicCommentDao.save(orgComicComment);
    }


    public Optional<OrgComicComment> findById(Integer commentId) {
        return orgComicCommentDao.findById(commentId);
    }


    public List<OrgComicComment> findAll() {
        return orgComicCommentDao.findAll();
    }


    public OrgComicComment updateComment(Integer commentId, OrgComicComment updatedComment) {
        Optional<OrgComicComment> optionalOrgComicComment = orgComicCommentDao.findById(commentId);

        if (optionalOrgComicComment.isPresent()) {
            OrgComicComment existingComment = optionalOrgComicComment.get();
            existingComment.setComicId(updatedComment.getComicId());
            existingComment.setUserId(updatedComment.getUserId());
            existingComment.setCommentContent(updatedComment.getCommentContent());
            return orgComicCommentDao.save(existingComment);
        } else {
            return null;
        }
    }

  
    public boolean deleteById(Integer commentId) {
        Optional<OrgComicComment> optionalOrgComicComment = orgComicCommentDao.findById(commentId);

        if (optionalOrgComicComment.isPresent()) {
            orgComicCommentDao.deleteById(commentId);
            return true;
        } else {          
            return false;
        }
    }
}
