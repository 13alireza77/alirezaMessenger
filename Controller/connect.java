package Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class connect {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private boolean start;
    private common.user user;

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public Socket getSocket() {
        return socket;
    }

    public common.user getUser() {
        return user;
    }

    public connect(String add, int port) throws Exception {
        socket = new Socket(add, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        user = new common.user(socket);
        System.out.println("!");
    }

    public synchronized void sendMessage(String msg) throws Exception {
        out.writeUTF(msg);
        out.flush();
    }

    public synchronized String readMessage() throws Exception {
        return in.readUTF();
    }

    public synchronized boolean sendInfo(TextField userName, PasswordField password) throws Exception {
        String un = userName.getText();
        String pw = password.getText();
        this.sendMessage("S-U-C " + un + " " + pw);
        ObjectInputStream ois = new ObjectInputStream(in);
        common.textMessage t = (common.textMessage) ois.readObject();
        if (t.getMessage().contains("TRUE/SU") || t.getMessage().contains("TRUE/LOGIN"))
            return true;
        if (t.getMessage().contains("FALSE/SU") || t.getMessage().contains("FALSE/LOGIN"))
            return false;
        return true;
    }

    public boolean loginInfo(TextField userName, PasswordField password) throws Exception {
        String un = userName.getText();
        String pw = password.getText();
        this.sendMessage("L-C " + un + " " + pw);
        ObjectInputStream ois = new ObjectInputStream(in);
        common.textMessage t = (common.textMessage) ois.readObject();
        if (t.getMessage().contains("TRUE/SU") || t.getMessage().contains("TRUE/LOGIN"))
            return true;
        if (t.getMessage().contains("FALSE/SU") || t.getMessage().contains("FALSE/LOGIN"))
            return false;
        return true;
    }

    public synchronized void close() throws Exception {
        out.close();
        in.close();
        socket.close();
    }


}
