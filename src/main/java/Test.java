package main.java;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Cache cache = new Cache(10000, 2000, TimeUnit.MILLISECONDS); //check time in milliseconds

        cache.put("1", "First Object");
        cache.put("2", "Second Object");
        cache.put("3", "Third Object");

        cache.setAddTime("3", System.currentTimeMillis());

        cache.put("4", "Fourth Object");
        Thread.sleep(3000);
        cache.put("5", "Fifth Object");

        Thread.sleep(5000);

        cache.put("6", 45677);
        cache.put("7", 456789);

        System.out.println(cache.get("7"));

        System.out.println(cache.size());
        Thread.sleep(5000);
        System.out.println(cache.size());

        cache.setTimeToLive(56, TimeUnit.HOURS);

        Thread.sleep(2000);
        System.out.println(cache.size());
        Thread.sleep(8000);
        System.out.println(cache.size());
        cache.setTimeToLive(4, TimeUnit.SECONDS);
        cache.put("8", 45);
        System.out.println(cache.size());
        Thread.sleep(6000);
        System.out.println(cache.size());

        cache.clear();

    }
}
