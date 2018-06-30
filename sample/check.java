package sample;

import common.textMessage;
import common.user;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class check extends Thread {
    private DataInputStream in;
    private Socket socket;
    private DataOutputStream out;

    public check(Socket socket) throws Exception {
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public synchronized void close() throws Exception {
        socket.close();
        in.close();
        out.close();
    }

    public void run() {
        try {
            while (true) {
                String check = null;
                check = in.readUTF();
                System.out.println(check);
                if (check.contains("S-U-C") || check.contains("L-C")) {
                    String[] splitArray1 = check.split("\\s+");
                    int length = splitArray1.length;
                    String[] splitArray = {splitArray1[length - 3], splitArray1[length - 2], splitArray1[length - 1]};
                    boolean b;
                    try {
                        b = common.user.readUsers().stream().anyMatch(user -> user.getUserName().equals(splitArray[1]) && user.getPassword().equals(splitArray[2]));
                    } catch (NullPointerException n) {
                        b = false;
                    }
                    common.user user = new common.user(socket);
                    user.setUserName(splitArray[1]);
                    user.setPassword(splitArray[2]);
                    ObjectOutputStream oos = new ObjectOutputStream(out);
                    common.user user1 = new user(socket);
                    if (splitArray[0].contains("S-U-C")) {
                        if (b == false) {
                            textMessage textMessage = new textMessage("TRUE/SU", user1, user);
                            oos.writeObject(textMessage);
                            oos.flush();
                            chat.clients.add(user);
                            user.writeInFile("usersFile", user);
                        } else {
                            textMessage textMessage = new textMessage("FALSE/SU", user1, user);
                            oos.writeObject(textMessage);
                            oos.flush();
                        }
                    }
                    if (splitArray[0].contains("L-C")) {
                        if (b) {
                            textMessage textMessage = new textMessage("TRUE/LOGIN", user1, user);
                            oos.writeObject(textMessage);
                            oos.flush();
                            chat.clients.add(user);
                        } else {
                            textMessage textMessage = new textMessage("FALSE/SU", user1, user);
                            oos.writeObject(textMessage);
                            oos.flush();
                        }
                    }
                }
                if (check.contains("S-T-M")) {
                    String[] splitArray1 = check.split("\\s+");
                    int length = splitArray1.length;
                    String[] splitArray = {splitArray1[length - 5], splitArray1[length - 4], splitArray1[length - 3], splitArray1[length - 2], splitArray1[length - 1]};
                    for (common.user u : chat.clients) {
                        if (u.getUserName().equals(splitArray[4]) && u.getPassword().equals(splitArray[3])) {
                            ObjectOutputStream ous = new ObjectOutputStream(u.getOut());
                            common.textMessage textMessage = new textMessage(splitArray[0], new user(socket), u);
                            ous.writeObject(textMessage);
                            ous.flush();
                            break;
                        }
                    }
                }
                if (check.contains("G-U-L")) {
                    ObjectOutputStream oos = new ObjectOutputStream(out);
                    try {
                        List<common.user> users = common.user.readUsers();
                        oos.writeObject(users);
                        oos.flush();
                    } catch (NullPointerException n) {
                        oos.writeObject(null);
                    }
                }
                if (check.contains("G-T-M-L")) {
                    String[] splitArray1 = check.split("\\s+");
                    int length = splitArray1.length;
                    String[] splitArray = {splitArray1[length - 3], splitArray1[length - 2], splitArray1[length - 1]};
                    ObjectOutputStream oos = new ObjectOutputStream(out);
                    List<common.textMessage> textMessages = new ArrayList<>();
                    try {
                        textMessages = common.textMessage.readTextMessages(splitArray);
                        oos.writeObject(textMessages);
                        oos.flush();
                    } catch (NullPointerException n) {
                        oos.writeObject(null);
                    }
                }
                if (check.contains("C-U-N")) {
                    String[] splitArray1 = check.split("\\s+");
                    int length = splitArray1.length;
                    String[] splitArray = {splitArray1[length - 3], splitArray1[length - 2], splitArray1[length - 1]};
                    List<common.user> users = common.user.readUsers();
                    if (users.stream().anyMatch(user -> user.getUserName().equals(splitArray[1]) && user.getPassword().equals(splitArray[2]))) {
                        new FileOutputStream("usersFile").close();
                        for (user u : users) {
                            if (u.getUserName().equals(splitArray[1]) && u.getPassword().equals(splitArray[2]))
                                u.setUserName(splitArray[0]);
                            common.user.writeInFile("usersFile", u);
                        }
                    }
                }
                if (check.contains("C-P-W")) {
                    String[] splitArray1 = check.split("\\s+");
                    int length = splitArray1.length;
                    String[] splitArray = {splitArray1[length - 3], splitArray1[length - 2], splitArray1[length - 1]};
                    List<common.user> users = common.user.readUsers();
                    System.out.println(Arrays.toString(splitArray));
                    if (users.stream().anyMatch(user -> user.getUserName().equals(splitArray[1]) && user.getPassword().equals(splitArray[2]))) {
                        new FileOutputStream("usersFile").close();
                        for (user u : users) {
                            if (u.getUserName().equals(splitArray[1]) && u.getPassword().equals(splitArray[2]))
                                u.setPassword(splitArray[0]);
                            common.user.writeInFile("usersFile", u);
                        }
                    }
                }
            }
        } catch (NoSuchElementException e) {
            try {
                this.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


