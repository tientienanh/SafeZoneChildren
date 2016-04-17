package com.example.activity.safezonechildrent;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Tien on 30-Mar-16.
 */
public class SocketAsynctask extends AsyncTask<HashMap, Void, String> {
    private Socket clientSocket;
    private static final String IP = "192.168.238.1";
    private static final int PORT = 2222;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    ProgressDialog progressDialog;
    Context context = null;

    SocketAsynctask(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }
    SocketAsynctask() {

    };
    SocketResponse socketResponse;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressBar.

        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    HashMap<String, JSONObject> hashMapResult = new HashMap<>();
    String strResult = "";
    @Override
    protected String doInBackground(HashMap... hashMaps) {
        HashMap<String, String> hashMap = hashMaps[0];
        try {
            clientSocket = new Socket(IP, PORT);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(hashMap);
            ois = new ObjectInputStream(clientSocket.getInputStream());
            strResult = (String) ois.readObject();
            oos.close();
            ois.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return strResult;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        socketResponse.response(result);

    }

    public interface SocketResponse {
        void response(String result);
    }
}
