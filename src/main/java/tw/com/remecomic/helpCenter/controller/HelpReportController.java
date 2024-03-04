package tw.com.remecomic.helpCenter.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
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

import tw.com.remecomic.helpCenter.model.bean.HelpReport;
import tw.com.remecomic.helpCenter.service.HelpReportService;

@RestController
public class HelpReportController {

	@Autowired
	private HelpReportService reportService;
	
	@GetMapping("/helpcenter/report/{reportId}")
	public ResponseEntity<HelpReport> getReportById(@PathVariable Integer reportId) {
		Optional<HelpReport> findReportById = reportService.findReportById(reportId);
		if (findReportById.isPresent()) {
			return ResponseEntity.ok(findReportById.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/helpcenter/allReport")
	public ResponseEntity<List<HelpReport>> getAllReport() {
		List<HelpReport> report = reportService.findAllReport();
		if (report != null) {
			return ResponseEntity.ok(report);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/helpcenter/report/add")
	public ResponseEntity<HelpReport> addReport(@RequestBody HelpReport helpReport) {
		helpReport.setReportCurrentDate(LocalDateTime.now());
		
		String base64Pic = helpReport.getReportBase64Pic();
		if(base64Pic != null && !base64Pic.isEmpty()) {
			byte[] picture = Base64.getDecoder().decode(base64Pic);
			
			helpReport.setReportPhoto(picture);
		}
		
		HelpReport report = reportService.addReport(helpReport);
		if (report != null) {
			return ResponseEntity.ok(report);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/helpcenter/report/delete")
	public ResponseEntity<HelpReport> deleteReport(@RequestParam Integer id) {
		boolean delete = reportService.deleteReportById(id);
		if (delete) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
