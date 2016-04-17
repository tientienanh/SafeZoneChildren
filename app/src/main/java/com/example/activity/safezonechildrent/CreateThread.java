package com.example.activity.safezonechildrent;

/**
 * Created by Tien on 14-Apr-16.
 */
public class CreateThread {
    private static CreateThread instance = new CreateThread();
    public ChildThread childThread;
    public static CreateThread getInstance() {
        return instance;
    }
    public CreateThread() {
        childThread = new ChildThread();
        childThread.start();
    }
}
