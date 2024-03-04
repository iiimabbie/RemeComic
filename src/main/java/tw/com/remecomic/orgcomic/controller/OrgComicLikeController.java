package tw.com.remecomic.orgcomic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tw.com.remecomic.orgcomic.model.bean.OrgComicLike;
import tw.com.remecomic.orgcomic.model.dao.OrgComicLikeDao;
import tw.com.remecomic.orgcomic.model.dto.OrgComicDto;
import tw.com.remecomic.orgcomic.model.dto.OrgComicLikeDto;
import tw.com.remecomic.orgcomic.service.OrgComicLikeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class OrgComicLikeController {

    @Autowired
    private OrgComicLikeService orgComicLikeService;

    @PostMapping("/comiclike/add")	//ok
    public ResponseEntity<OrgComicLike> createOrgComicLike(@RequestBody OrgComicLikeDto orgComicLikeDto) {
        return ResponseEntity.ok(orgComicLikeService.save(new OrgComicLike(orgComicLikeDto.getComicId(), orgComicLikeDto.getUserId())));
    }

    @GetMapping("/comiclike/{comicId}/{userId}")	//ok   
    public ResponseEntity<OrgComicLike> getOrgComicLikeById(@PathVariable Integer comicId,@PathVariable Integer userId) {
        Optional<OrgComicLike> optionalOrgComicLike = orgComicLikeService.findById(comicId,userId);

        if (optionalOrgComicLike.isPresent()) {
            OrgComicLike orgComicLike = optionalOrgComicLike.get();
            return ResponseEntity.ok(orgComicLike);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/comiclike/all")	//ok
    public ResponseEntity<List<OrgComicLikeDto>> getAllOrgComicLikes() {
        List<OrgComicLike> orgComicLikes = orgComicLikeService.findAll();
        
        List<OrgComicLikeDto> orgComicLikeDto = orgComicLikes.stream()
                .map(orgComicLike -> new OrgComicLikeDto(
                		orgComicLike.getUserId(),
                		orgComicLike.getComicId()
                        ))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(orgComicLikeDto);
    }


    @DeleteMapping("/comiclike/delete/{comicId}/{userId}")	//ok
    public ResponseEntity<Void> deleteOrgComicLike(@PathVariable Integer comicId,@PathVariable Integer userId) {
    	orgComicLikeService.deleteById(comicId,userId);
        return ResponseEntity.noContent().build();
    }
    
    //改只找userId
//  @GetMapping("/comiclike/{comicId}/{userId}")//http://localhost:8080/remecomic/comiclike/1/1
//  public ResponseEntity<OrgComicLike> getOrgComicLikeById(@PathVariable Integer comicId,@PathVariable Integer userId) {
//      Optional<OrgComicLike> optionalOrgComicLike = orgComicLikeService.findById(comicId,userId);
//
//      if (optionalOrgComicLike.isPresent()) {
//          OrgComicLike orgComicLike = optionalOrgComicLike.get();
//          return ResponseEntity.ok(orgComicLike);
//      } else {
//          return ResponseEntity.notFound().build();
//      }
//  }
    
}
