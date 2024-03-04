package tw.com.remecomic.money.websocket.pojo;

import lombok.Data;

@Data
public class ResultMessage {
    private boolean System;

    private String fromName;

    private Object message;
}
