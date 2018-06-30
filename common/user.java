package common;

import Controller.makeButton;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class user extends Thread implements Serializable {
    private static final long serialVersionUID = 2L;
    private String password;
    private String userName;
    private transient DataInputStream in;
    private transient DataOutputStream out;
    private transient Socket socket;
    private Image image;
    private transient Controller.makeButton makeButton;
    private transient ObjectInputStream ois;

    public void setMakeButton(Controller.makeButton makeButton) {
        this.makeButton = makeButton;
    }

    public String getUserName() {
        return userName;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public Socket getSocket() {
        return socket;
    }

    public synchronized void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getPassword() {
        return password;
    }

    public Image getImage() {
        return image;
    }

    public synchronized void setImage(Image image) throws Exception {
        this.image = image;
    }

    public synchronized void setPassword(String password) throws Exception {
        this.password = password;
    }

    public synchronized void setUserName(String userName) throws Exception {
        this.userName = userName;
    }

    public user(Socket socket) throws Exception {
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
//        File file = new File("C:\\Users\\alireza\\IdeaProjects\\FinalProject\\src\\icons\\user.png");
//        setImage(new Image(file.toURI().toString()));
//        image = new Image(getClass().getResourceAsStream("../icons/user.png"));
    }

    public synchronized void listAddText(textMessage t) {
        if (t.getSender().getUserName().equals(userName) && t.getSender().getPassword().equals(password))
            makeButton.getListView().getItems().add(new Label("+   " + t.getMessage() + "       " + t.getDate()));
        else
            makeButton.getListView().getItems().add(new Label("-   " + t.getMessage() + "\n" + t.getDate()));
    }


    public synchronized void sendText(String string) throws Exception {
        out.writeUTF(string);
        out.flush();
    }

    public synchronized void readText() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(in);
        textMessage t = (textMessage) ois.readObject();
        t.writeTextMessage();
        makeButton.getListView().getItems().add(new Label("-   " + t.getMessage() + "\n" + t.getDate()));
    }

    public List<common.user> getUsers() throws Exception {
        this.sendText("G-U-L");
        ObjectInputStream ois = new ObjectInputStream(in);
        List<common.user> users = (List<user>) ois.readObject();
        if (users.isEmpty())
            return null;
        else
            return users;
    }

    public List<common.textMessage> getTextMessages(String string) throws Exception {
        this.sendText("G-T-M-L " + string);
        ObjectInputStream ois = new ObjectInputStream(in);
        List<common.textMessage> textMessages = (List<common.textMessage>) ois.readObject();
        if (textMessages.isEmpty())
            return null;
        else
            return textMessages;
    }

    public static synchronized void writeInFile(String string, user user) throws Exception {
        FileOutputStream fout = new FileOutputStream(string, true);
        ObjectOutputStream ous = new ObjectOutputStream(fout);
        ous.writeObject(user);
        ous.close();
        fout.close();
    }

    public synchronized static List<user> readUsers() {
        List<user> users = new ArrayList<>();
        try {
            FileInputStream f = new FileInputStream("usersFile");
            boolean b = true;
            while (b) {
                ObjectInputStream o = new ObjectInputStream(f);
                user u = (user) o.readObject();
                if (u != null)
                    users.add(u);
                else {
                    b = false;
                }
                System.out.println(u.getPassword());
            }
            f.close();
            return users;
        } catch (FileNotFoundException f) {
            return null;
        } catch (EOFException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public synchronized void close() throws Exception {
        socket.close();
        in.close();
        out.close();
        ois.close();
        Thread.currentThread().interrupt();
    }

    public void run() {

        while (true) {
            try {
                try {
                    ois = new ObjectInputStream(in);
                } catch (SocketException s) {
                    break;
                }
                textMessage t = (textMessage) ois.readObject();
                Platform.runLater(() -> {
                    makeButton.getListView().getItems().add("-   " + t.getMessage() + "\n" + t.getDate() + "\n\n\n");
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalStateException i) {
                i.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
