package common;

import sample.Main;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class textMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String date;
    private String Message;
    private user sender;
    private user recipient;

    public String getMessage() {
        return Message;
    }

    public String getDate() {
        return date;
    }

    public user getSender() {
        return sender;
    }

    public user getRecipient() {
        return recipient;
    }

    public textMessage(String Message, user sender, user recipient) {
        this.Message = Message;
        this.sender = sender;
        this.recipient = recipient;
        date = this.getdate();
    }

    public synchronized void sendTextMessage() throws Exception {
        this.writeTextMessage();
        sender.sendText("S-T-M " + Message + " " + sender.getUserName() + " " + sender.getPassword() + " " + this.recipient.getUserName() + " " + recipient.getPassword());
    }

    public synchronized void writeTextMessage() throws Exception {
        String name = sender.getUserName() + recipient.getUserName();
        File f1 = new File(recipient.getUserName() + sender.getUserName());
        if (f1.exists() && !f1.isDirectory()) {
            name = recipient.getUserName() + sender.getUserName();
        }
        FileOutputStream f = new FileOutputStream(name, true);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(this);
        o.close();
        f.close();
    }

    public synchronized static List<textMessage> readTextMessages(String[] strings) {
        List<textMessage> textMessages = new ArrayList<>();
        try {
            String name = strings[1] + strings[2];
            File f1 = new File(strings[2] + strings[1]);
            if (f1.exists() && !f1.isDirectory()) {
                name = strings[2] + strings[1];
            }
            FileInputStream f = new FileInputStream(name);
            boolean b = true;
            while (b) {
                ObjectInputStream o = new ObjectInputStream(f);
                textMessage t = (textMessage) o.readObject();
                if (t != null)
                    textMessages.add(t);
                else
                    b = false;
            }
            f.close();
            return textMessages;
        } catch (EOFException e) {

        } catch (FileNotFoundException f) {
            return textMessages;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textMessages;
    }

    public String getdate() {
        DateFormat datee = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date nowDate = new Date();
        return datee.format(nowDate);
    }

    public synchronized static textMessage makeTextMessage(String string, Socket socket) throws Exception {
        String[] splitArray1 = string.split("\\s+");
        int length = splitArray1.length;
        String[] splitArray = {splitArray1[length - 5], splitArray1[length - 4], splitArray1[length - 3], splitArray1[length - 2], splitArray1[length - 1]};
        user s = new user(socket);
        s.setUserName(splitArray[1]);
        s.setPassword(splitArray[2]);
        user r = new user(socket);
        r.setUserName(splitArray[3]);
        r.setPassword(splitArray[4]);
        return new textMessage(splitArray[0], s, r);
    }
}
