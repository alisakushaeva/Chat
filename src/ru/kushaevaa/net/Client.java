package ru.kushaevaa.net;

import ru.kushaevaa.gui.MainWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private String host;
    private int port;
    private Socket s;
    private ChatIO cio;
    private ArrayList<String> message = new ArrayList<>();

    public Client(String host, int port){
        this.host = host;
        this.port = port;
    }
    public void start() throws IOException {
        s = new Socket(host, port);
        cio = new ChatIO(s);
        new Thread(()->{
            try {
                cio.startReceiving(this::parse);
            } catch (IOException e) {
                message.add("Ошибка подключенного клиента: "+e.getMessage());
            }
        }).start();
    }

    public Void parse(String msg){
        var data = msg.split(":", 2);
        //System.out.println("0="+data[0] + " 1=" + data[1]);
        Command cmd = null;
        try{
            cmd = Command.valueOf(data[0]);
        }
        catch(Exception ignored){
        }
        switch (cmd){
            case INTRODUCE:{
                if(data.length > 1 && data[1].trim().length()>0) {
                    fireEvent(new ChatEvent(data[1]));
                }
                else{
                    fireEvent(new ChatEvent("Пожалуйста, представьтесь: "));
                }
                break;
            }
            case MESSAGE:{
                if(data.length > 1 && data[1].trim().length()>0) {
                    fireEvent(new ChatEvent(data[1]));
                }
                break;
            }
            case LOGGED_IN:{
                if(data.length > 1 && data[1].trim().length()>0) {
                    fireEvent(new ChatEvent("Пользователь " + data[1] + " вошёл в чат."));
                }
                break;
            }
            case LOGGED_OUT:{
                if(data.length > 1 && data[1].trim().length()>0) {
                    fireEvent(new ChatEvent("Пользователь " + data[1] + " покинул чат."));
                }
                break;
            }
            case null:{

            }
        }
        return null;
    }
    protected List<ChatListener> listeners = new ArrayList<>();
    public void addListener(ChatListener a)
    {
        listeners.add(a);
    }
    public void fireEvent( ChatEvent e){
            for(int i =0; i< listeners.size(); i++)
            {
                listeners.get(i).chatAvailiable(e);
            }
    }
    public void send(String userData){
        cio.sendMessage(userData);
    }
}
