package tw.com.remecomic.helpCenter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.remecomic.helpCenter.model.bean.HelpType;
import tw.com.remecomic.helpCenter.service.HelpTypeService;

@RestController
public class HelpTypeController {

	@Autowired
	private HelpTypeService typeService;
	
	@GetMapping("/helpcenter/type/{typeId}")
	public ResponseEntity<HelpType> getTypeById(@PathVariable Integer typeId) {
		Optional<HelpType> findTypeById = typeService.findTypeById(typeId);
		if (findTypeById.isPresent()) {
			return ResponseEntity.ok(findTypeById.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/helpcenter/type")
	public ResponseEntity<List<HelpType>> getAllType(){
		List<HelpType> type = typeService.findAllType();
		if (type != null) {
			return ResponseEntity.ok(type);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/helpcenter/type/add")
	public ResponseEntity<HelpType> addType(@RequestBody HelpType helpType) {
		HelpType type = typeService.addType(helpType);
		if (type != null) {
			return ResponseEntity.ok(type);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/helpcenter/type/delete")
	public ResponseEntity<HelpType> deleteType(@RequestParam Integer id) {
		boolean delete = typeService.deleteTypeById(id);
		if (delete) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
