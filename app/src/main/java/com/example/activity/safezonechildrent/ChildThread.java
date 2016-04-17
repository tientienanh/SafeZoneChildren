package com.example.activity.safezonechildrent;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Tien on 15-Apr-16.
 */
public class ChildThread extends Thread {
    public static Socket clientSocket;
    private static final String IP = "192.168.238.1";
    private static final int PORT = 2222;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    public HashMap<String, String> hashMap = new HashMap<>();
    String strResult;
    Context context;
    Callback mCallback;


    @Override
    public void run() {
        super.run();
        try {
            clientSocket = new Socket(IP, PORT);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());

            mCallback.onConnected(clientSocket.isConnected());
            while (true) {
                strResult = (String) ois.readObject();
                mCallback.onReceivedData(strResult);
                if (strResult.equals("logout")) {
                    break;
                }
            }

            oos.close();
            ois.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public interface Callback {
        public void onReceivedData(String json);
        public void onConnected(boolean isSuccess);
    }
}
