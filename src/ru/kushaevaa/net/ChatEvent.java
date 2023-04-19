package ru.kushaevaa.net;
import java.util.EventObject;
public class ChatEvent extends EventObject {

    private String message;
    public ChatEvent(String source) {
        super(source);
        this.message = source;
    }
    public String getMessage1()
    {
        return message;
    }
}


