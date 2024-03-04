package tw.com.remecomic.orgcomic.controller;	

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import tw.com.remecomic.orgcomic.model.bean.OrgComic;
import tw.com.remecomic.orgcomic.model.dao.OrgComicDao;
import tw.com.remecomic.orgcomic.model.dto.OrgComicDto;
import tw.com.remecomic.orgcomic.service.OrgComicService;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class OrgComicController {

	@Autowired
	private OrgComicService orgComicService;
	
//    private final List<OrgComic> orgComics = new ArrayList<>();

    
    @PostMapping("/orgcomic/add")//ok
    public ResponseEntity<OrgComic> createOrgComic(@RequestBody OrgComicDto orgComicDto) {
    	OrgComic orgComic = new OrgComic(
    			orgComicDto.getVerify(),
    			orgComicDto.getComicName(),
    			orgComicDto.getOrgComicCover(),
    			orgComicDto.getOrgPublishDate(),
    			orgComicDto.getIntroduction(),
    			orgComicDto.getUserId(),
    			orgComicDto.getGenreid()
    			);   
        orgComicService.save(orgComic);
        return ResponseEntity.ok(orgComic);
    }

    @GetMapping("/orgcomic/{comicId}")
    public ResponseEntity<OrgComicDto> getOrgComicById(@PathVariable Integer comicId) {
        Optional<OrgComic> comicFindId = orgComicService.findById(comicId);

        if (comicFindId.isPresent()) {
            OrgComic orgComic = comicFindId.get();
            OrgComicDto orgComicDto = new OrgComicDto(
                    orgComic.getVerify(),
                    orgComic.getComicName(),
                    orgComic.getOrgComicCover(),
                    orgComic.getOrgPublishDate(),
                    orgComic.getIntroduction(),
                    orgComic.getUserId(),
                    orgComic.getComicId(),
                    orgComic.getGenreId());
            return ResponseEntity.ok(orgComicDto);
        }

        return ResponseEntity.notFound().build();
    }
//    @GetMapping("/orgcomic/{comicId}")	//ok
//    public ResponseEntity<OrgComic> getOrgComicById(@PathVariable Integer comicId) {
//        
//    	Optional<OrgComic> comicfindid= orgComicService.findById(comicId);
//    	
//            if (comicfindid.isPresent()) {
//            	OrgComic orgComic = comicfindid.get();
//                return ResponseEntity.ok(comicfindid.get());       
//        }
//        return ResponseEntity.notFound().build();
//    }

    @GetMapping("/orgcomic/all")
    public ResponseEntity<List<OrgComicDto>> getAllOrgComics() {
        List<OrgComic> orgComics = orgComicService.findAll();

        if (!orgComics.isEmpty()) {
            List<OrgComicDto> orgComicDtos = orgComics.stream()
                    .map(orgComic -> new OrgComicDto(
                            orgComic.getVerify(),
                            orgComic.getComicName(),
                            orgComic.getOrgComicCover(),
                            orgComic.getOrgPublishDate(),
                            orgComic.getIntroduction(),
                            orgComic.getUserId(),
                            orgComic.getComicId(),
                            orgComic.getGenreId()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(orgComicDtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
//    @GetMapping("/orgcomic/all")	//
//    public ResponseEntity<List<OrgComic>> getAllOrgComics() {
//    	orgComics.clear();
//        List<OrgComic> orgComics = orgComicService.findAll();
//
//        if (!orgComics.isEmpty()) {
//            return ResponseEntity.ok(orgComics);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @PutMapping("/orgcomic/update/{comicId}")//ok
//    public ResponseEntity<OrgComic> updateOrgComic(@PathVariable Integer comicId, @RequestBody OrgComic updatedOrgComic) {
//        OrgComic orgComic = orgComicService.updateOrgComic(comicId, updatedOrgComic);
//        
//        if (orgComic != null) {
//            return ResponseEntity.ok(orgComic);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    

    @PutMapping("/orgcomic/update/{comicId}")//ok
    public ResponseEntity<OrgComic> updateOrgComic(@PathVariable Integer comicId, @RequestBody OrgComicDto updatedOrgComicDto) {   	 
        Optional<OrgComic> optionalOrgComic = orgComicService.findById(comicId);
        
        if (optionalOrgComic.isPresent()) {
           OrgComic orgComic = optionalOrgComic.get();

            orgComic.setVerify(updatedOrgComicDto.getVerify());
            orgComic.setComicName(updatedOrgComicDto.getComicName());
            orgComic.setOrgComicCover(updatedOrgComicDto.getOrgComicCover());
            orgComic.setOrgPublishDate(updatedOrgComicDto.getOrgPublishDate());
            orgComic.setIntroduction(updatedOrgComicDto.getIntroduction());
            orgComic.setUserId(updatedOrgComicDto.getUserId());
            orgComic.setGenreId(updatedOrgComicDto.getGenreid());
       	
            orgComicService.save(orgComic);
           
            return ResponseEntity.ok(orgComic);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

    @DeleteMapping("/orgcomic/delete/{comicId}")//ok
    public ResponseEntity<OrgComic> deleteOrgComic(@PathVariable Integer comicId) {
        
    	boolean deleted = orgComicService.deleteById(comicId);

    	if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}

