package Controller;

import java.net.SocketException;
import java.util.NoSuchElementException;

public class reciever extends Thread {
    private connect connect;

    public reciever(connect connect) {
        this.connect = connect;
    }

    private String check;

    @Override
    public void run() {
        try {
            while (true) {
                check = connect.readMessage();
                if (check.contains("TRUE/SU") || check.contains("TRUE/LOGIN"))
                    connect.setStart(true);
                if (check.contains("FALSE/SU") || check.contains("FALSE/LOGIN"))
                    connect.setStart(false);
            }
        } catch (NoSuchElementException e) {
            try {
                connect.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
