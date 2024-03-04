package tw.com.remecomic.userA.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tw.com.remecomic.config.GoogleOAuthConfig;
import tw.com.remecomic.userA.model.dto.LoginUserDto;
import tw.com.remecomic.userA.service.UserAService;

@Controller
public class GoogleOAuth2NativeHttpController {

	@Autowired
	private GoogleOAuthConfig googleOAuthConfig;

	@Autowired
	private UserAService uService;

	@Autowired
	private RestTemplate restTemplate;

	private final String scope = "https://www.googleapis.com/auth/userinfo.email";

	@GetMapping("/googleLogin")
	public String googleLogin(HttpServletResponse response) {
		String authUrl = "https://accounts.google.com/o/oauth2/v2/auth?" + "client_id="
				+ googleOAuthConfig.getClientId() + "&response_type=code" + "&scope=openid%20email%20profile"
				+ "&redirect_uri=" + googleOAuthConfig.getRedirectUri() + "&state=state";
		return "redirect:" + authUrl;
	}

	@GetMapping("/googleCallback")
	public String oauth2Callback(@RequestParam(required = false) String code, HttpSession httpSession)
			throws IOException {
		if (code == null) {
			String authUri = "https://accounts.google.com/o/oauth2/v2/auth?response_type=code" + "client_id="
					+ googleOAuthConfig.getClientId() + "&redirect_uri=" + googleOAuthConfig.getRedirectUri()
					+ "&scope=" + scope;
			return "redirect:" + authUri;
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("code", code);
			map.add("client_id", googleOAuthConfig.getClientId());
			map.add("client_secret", googleOAuthConfig.getClientSecret());
			map.add("redirect_uri", googleOAuthConfig.getRedirectUri());
			map.add("grant_type", "authorization_code");

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

			ResponseEntity<String> response = restTemplate.postForEntity("Https://oauth2.googleapis.com/token", request,
					String.class);
			String credentials = response.getBody();

			JsonNode jsonNode = new ObjectMapper().readTree(credentials);
			String accessToken = jsonNode.get("access_token").asText();
//			String idToken = jsonNode.get("id_token").asText();

			HttpHeaders headers2 = new HttpHeaders();
			headers2.setBearerAuth(accessToken);

			HttpEntity<String> entity = new HttpEntity<>(headers2);
			ResponseEntity<String> response2 = restTemplate.exchange(
					"https://www.googleapis.com/oauth2/v1/userinfo?alt=json", HttpMethod.GET, entity, String.class);

			String payloadResponse = response2.getBody();

			JsonNode payloadJsonNode = new ObjectMapper().readTree(payloadResponse);

			String payloadGoogleId = payloadJsonNode.get("id").asText();
			String payloadEmail = payloadJsonNode.get("email").asText();
			String payloadName = payloadJsonNode.get("name").asText();
			String payloadPicture = payloadJsonNode.get("picture").asText();
			String payloadLocale = payloadJsonNode.get("locale").asText();

			System.out.println("sessionId" + httpSession.getId());
			System.out.println("payloadGoogleId: " + payloadGoogleId);
			System.out.println("payloadEmail: " + payloadEmail);
			System.out.println("payloadName: " + payloadName);
			System.out.println("payloadPicture: " + payloadPicture);
			System.out.println("payloadLocale: " + payloadLocale);

			LoginUserDto loginUser = uService.googleEmailLogin(payloadEmail, payloadName, payloadPicture);
			httpSession.setAttribute("loginUser", loginUser);

			LoginUserDto loginUserWithUserId = uService.userIdByGoogleEmail(payloadEmail);
			httpSession.setAttribute("loginUserWithUserId", loginUserWithUserId);
			httpSession.setAttribute("loginUserId", loginUserWithUserId.getUserId());
			httpSession.setAttribute("loginUserEmail", loginUserWithUserId.getEmail());

		}

		return "redirect:http://localhost:5173/frontstage/home";
	}

	@GetMapping("/userA/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		// Invalidate the session and clear the context
		HttpSession session = request.getSession();
		System.out.println("==========requestSession+==============:" + session);
		if (session != null) {
			session.invalidate();
		}
		Cookie cookie = new Cookie("RemeComicSession", null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0); // Set age to 0 to delete the cookie
		response.addCookie(cookie);

	}

	@GetMapping("/userA/checkCookie")
	public ResponseEntity<?> checkCookie(HttpServletRequest request,
			@CookieValue(name = "RemeComicSession", required = false) String checkCookie) {
		if (checkCookie != null) {
			System.out.print("================ checkCookie:" + checkCookie);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().body("Cookie not found.");
		}
	}

	@GetMapping("/user/getSession")
	public ResponseEntity<?> getSession(HttpSession httpSession) {
		LoginUserDto loginUser = (LoginUserDto) httpSession.getAttribute("loginUser");

		if (loginUser == null) {
			System.out.println("session空的");
			return new ResponseEntity<String>("session null", HttpStatus.UNAUTHORIZED);
		}

		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("userId", loginUser.getUserId().toString());
		responseMap.put("userEmail", loginUser.getEmail());
		responseMap.put("userName", loginUser.getName());
		responseMap.put("userPhoto", loginUser.getUserPhoto());

		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.OK);
	}

}
