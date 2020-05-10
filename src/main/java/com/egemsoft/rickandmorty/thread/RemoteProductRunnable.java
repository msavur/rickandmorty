package com.egemsoft.rickandmorty.thread;

public class RemoteProductRunnable implements Runnable {

    String value;

    public RemoteProductRunnable(String value) {
        this.value = value;
    }

    @Override
    public void run() {
        System.out.println(value);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
