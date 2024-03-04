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

import tw.com.remecomic.helpCenter.model.bean.HelpServiceResponse;
import tw.com.remecomic.helpCenter.service.HelpServiceResponseService;

@RestController
public class HelpServiceResponseController {

	@Autowired
	private HelpServiceResponseService responseService;
	
	@GetMapping("/helpcenter/response/{responseId}")
	public ResponseEntity<HelpServiceResponse> getResponseById(@PathVariable Integer responseId) {
		Optional<HelpServiceResponse> response = responseService.findResponseById(responseId);
		if (response.isPresent()) {
			return ResponseEntity.ok(response.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/helpcenter/response")
	public ResponseEntity<List<HelpServiceResponse>> getAllResponse() {
		List<HelpServiceResponse> response = responseService.findAllResponse();
		if (response != null) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/helpcenter/response/add")
	public ResponseEntity<HelpServiceResponse> addResponse(@RequestBody HelpServiceResponse serviceResponse) {
		HelpServiceResponse response = responseService.addResponse(serviceResponse);
		if (response != null) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/helpcenter/response/delete")
	public ResponseEntity<HelpServiceResponse> deleteResponse(@RequestParam Integer id) {
		boolean delete = responseService.deleteResponseById(id);
		if (delete) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
