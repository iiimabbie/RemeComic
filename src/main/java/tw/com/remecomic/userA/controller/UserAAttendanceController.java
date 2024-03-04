package tw.com.remecomic.userA.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import tw.com.remecomic.userA.service.UserAAttendanceService;

@RestController
public class UserAAttendanceController {

	@Autowired
	private UserAAttendanceService uaService;

	@PostMapping("user/attendance/{userId}")
	public ResponseEntity<?> markAttendance(@PathVariable Integer userId) {
		try {
			Map<String, Integer> mapObj = uaService.markAttendance(userId);
			return ResponseEntity.status(HttpStatus.OK).body(mapObj);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/testDate")
	private Date getPreviousDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -1);

		return calendar.getTime();
	}

}
