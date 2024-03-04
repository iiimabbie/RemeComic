package tw.com.remecomic.orgcomic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.qos.logback.core.joran.conditional.IfAction;
import tw.com.remecomic.orgcomic.model.bean.OrgComicComment;
import tw.com.remecomic.orgcomic.model.dto.OrgComicCommentDto;
import tw.com.remecomic.orgcomic.service.OrgComicCommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class OrgComicCommentController {
	
	@Autowired
	private OrgComicCommentService orgComicCommentService;


    @PostMapping("/orgcomiccomment/add") //	ok
    public ResponseEntity<OrgComicComment> createOrgComicComment(@RequestBody OrgComicCommentDto codeDto) {
    	OrgComicComment code = new OrgComicComment(codeDto.getComicId(),codeDto.getUserId(),codeDto.getCommentContent());
    			
    	
    	
    	OrgComicComment orgComicComment123= orgComicCommentService.save(code);
    	
        return ResponseEntity.ok(orgComicComment123);
    }

       
    @GetMapping("/orgcomiccomment/{commentId}")	// ok	
    public ResponseEntity<OrgComicComment> getOrgComicCommentById(@PathVariable Integer commentId) {
        
    	Optional<OrgComicComment> commentfindid = orgComicCommentService.findById(commentId);
    	
    	
            if (commentfindid.isPresent()) {
                return ResponseEntity.ok(commentfindid.get());          
        }
        return ResponseEntity.notFound().build();
    }

    

    @GetMapping("/orgcomiccomment/all")	// ok
    public ResponseEntity<List<OrgComicComment>> getAllOrgComicComments() {
    	
    	List<OrgComicComment> commentall = orgComicCommentService.findAll();
    	
    	if(commentall != null) {
    		
    		
    		return ResponseEntity.ok(commentall);
    	}else {return ResponseEntity.notFound().build();}
    	      
    }

    @PutMapping("/orgcomiccomment/{commentId}")	//ok
    public ResponseEntity<OrgComicComment> updateOrgComicComment(@PathVariable Integer commentId, @RequestBody OrgComicComment updatedOrgComicComment) {
        OrgComicComment updatedComment = orgComicCommentService.updateComment(commentId, updatedOrgComicComment);
        if (updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/orgcomiccomment/delete/{commentId}")	//ok
    public ResponseEntity<Void> deleteOrgComicComment(@PathVariable Integer commentId) {
    	if(orgComicCommentService.deleteById(commentId)) {
    		 return ResponseEntity.noContent().build();
    		
    	}else {
         return ResponseEntity.notFound().build();
     }
    	

    }
}
