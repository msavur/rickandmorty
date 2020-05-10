package com.egemsoft.rickandmorty.util;

public class Producer implements Runnable {
    @Override
    public void run() {

        /*while (true) {
            synchronized (counter) {
                counter.incrementCount();
                System.out.println(" Incrementing count " + counter.getCount());
                counter.notifyAll();
                try {
                    counter.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }*/
    }
}