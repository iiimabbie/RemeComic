package tw.com.remecomic.forum.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import tw.com.remecomic.forum.model.bean.WebsocketMessage;
import tw.com.remecomic.forum.model.bean.WebsocketOutgoingMessage;

@Controller
public class WebsocketGreetingController {
	
	@MessageMapping("/process-message")// /app/process-message
	@SendTo("/topic/greetings")
	public WebsocketOutgoingMessage greeting(WebsocketMessage message) throws InterruptedException {
		Thread.sleep(1000);
		return new WebsocketOutgoingMessage("Hello!"+HtmlUtils.htmlEscape(message.getName())+"!Nice to meet you!");
	}
	@GetMapping("/websocketMain")
	public String startGreet() {
		return "forum/index";
	}
}
