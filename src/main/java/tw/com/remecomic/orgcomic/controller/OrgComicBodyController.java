package tw.com.remecomic.orgcomic.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tw.com.remecomic.orgcomic.model.bean.OrgComicBody;
import tw.com.remecomic.orgcomic.model.dto.OrgComicBodyDto;
import tw.com.remecomic.orgcomic.service.OrgComicBodyService;
import tw.com.remecomic.orgcomic.service.OrgComicLikeService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrgComicBodyController {
	
	@Autowired
    private OrgComicBodyService orgComicBodyService;
	
//    private List<OrgComicBody> orgComicBodies = new ArrayList<>();
    
    
    
//    @PostMapping("/comicbody/add")	//ok 	//{"comicId": ,    "comicEpisode": ,    "comicBodyPhoto": }
//    public ResponseEntity<OrgComicBody> createOrgComicBody(@RequestBody OrgComicBody orgComicBody) {
//    	  OrgComicBody createdComicBody = orgComicBodyService.save(orgComicBody);
//        return ResponseEntity.ok(createdComicBody);
//    }
	
	   @PostMapping("/comicbody/add")	//ok 	//{"comicId": ,    "comicEpisode": ,    "comicBodyPhoto": }
	    public ResponseEntity<OrgComicBody> createOrgComicBody(@RequestBody OrgComicBodyDto orgComicBodyDto) {
	    	  OrgComicBody OrgComicBody =new OrgComicBody(
	    			  orgComicBodyDto.getComicId(),
	    			  orgComicBodyDto.getComicEpisode(),
	    			  orgComicBodyDto.getComicBodyPhoto()
	    			  );
	    			  orgComicBodyService.save(OrgComicBody);
	        return ResponseEntity.ok(OrgComicBody);
	    }

    @GetMapping("/comicbody/{comicEpisode}")	//ok
    public ResponseEntity<OrgComicBody> getOrgComicBodyById(@PathVariable Integer comicEpisode) {
        Optional<OrgComicBody> orgComicBody = orgComicBodyService.findById(comicEpisode);
        
        if (orgComicBody.isPresent()) {
            return ResponseEntity.ok(orgComicBody.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 

    @GetMapping("/comicbody/all")	//ok
    public ResponseEntity<List<OrgComicBody>> getAllOrgComicBodies() {
        List<OrgComicBody> allComicBodies = orgComicBodyService.findAll();
        return ResponseEntity.ok(allComicBodies);
    }

    @PutMapping("/comicbody/update/{comicEpisode}")	// 
    
    public ResponseEntity<OrgComicBody> updateOrgComicBody(@PathVariable Integer comicEpisode,@RequestBody OrgComicBody updatedOrgComicBody) {
        OrgComicBody updatedBody = orgComicBodyService.updateOrgComicBody(comicEpisode, updatedOrgComicBody);
        if (updatedBody != null) {
            return ResponseEntity.ok(updatedBody);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/comicbody/delete/{comicEpisode}")	//ok
    
    
    public ResponseEntity<Void> deleteOrgComicBody(@PathVariable Integer comicEpisode) {
        boolean deleted = orgComicBodyService.deleteById(comicEpisode);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
