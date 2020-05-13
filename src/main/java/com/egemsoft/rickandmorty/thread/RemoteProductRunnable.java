package com.egemsoft.rickandmorty.thread;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RemoteProductRunnable implements Runnable {

    int characterCount = 0;

    String value;

    public RemoteProductRunnable(String value) {
        this.value = value;
    }

    @Override
    public void run() {
        System.out.println(value);
        try {
            Thread.sleep(2000);
            characterCount += value.length();
            System.out.println("Count : " + characterCount);
            long nanos = ManagementFactory.getThreadMXBean().getThreadCpuTime(Thread.currentThread().getId());
            System.out.println(nanos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        final List<String> segments = new ArrayList<>();
        segments.add("Mahmut");
        segments.add("Savur ");
        segments.add("Halil");

        final AtomicInteger worldCount = new AtomicInteger();

        List<Future> workers = new ArrayList<>(segments.size());
        ExecutorService executor = Executors.newFixedThreadPool(25);
        for (String segment : segments) {
            Future<Integer> worker = executor.submit(() -> worldCount.addAndGet(counting(segment)));
            workers.add(worker);

            try {
                Integer integer = worker.get();
                System.out.println(integer);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }



        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            System.out.println("Still waiting...");
            System.exit(0);
        }

        int result = worldCount.get();

        System.out.println("result = " + result);

    }

    private static int counting(String segment) {
        return segment.length();
    }
}
