package tw.com.remecomic.money.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.remecomic.money.websocket.pojo.Result;
import tw.com.remecomic.userA.model.dto.LoginUserDto;

@RestController
public class MoneyWebSocketController {

    @GetMapping("/money/websocket/setFlag")
    public ResponseEntity<Result> login(HttpSession session) {
        Result result = new Result();

        if (session != null) {
            result.setFlag(true);
            LoginUserDto loginUser = (LoginUserDto) session.getAttribute("loginUser");

//            System.out.println(loginUser.getName());
        } else {
            result.setFlag(false);
            result.setMessage("fail");
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
