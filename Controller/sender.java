package Controller;

import java.util.Scanner;

public class sender extends Thread {
    private connect connect;
    private String msg;

    public sender(connect connect, String string) {
        this.connect = connect;
        this.msg = string;
    }

    @Override
    public void run() {
        try {
            connect.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
