package main.java;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class Cache {

    private long timeToLive;
    private long checkTime;
    private ConcurrentHashMap<String, Wrapper> cache = new ConcurrentHashMap<>();

    Cache(long timeToLive, long checkTime) {
        this.timeToLive = timeToLive;
        this.checkTime = checkTime;
        useTimer();
    }

    long getTimeToLive() {
        return timeToLive;
    }

    void setTimeToLive(long timeToLive, TimeUnit type) {
        this.timeToLive = type.toMillis(timeToLive);
    }

    void put(String key, Object value) {
        cache.put(key, new Wrapper(value, System.currentTimeMillis()));
    }

    Object get(String id) {
        return cache.get(id).getValue();
    }

    int size() {
        return cache.size();
    }

    void setAddTime(String id, long time) {
        cache.get(id).setAddTime(time);
    }

    void clear() {
        cache.clear();
    }

    ConcurrentHashMap<String, Wrapper> getCache() {
        return cache;
    }

    public void setCache(ConcurrentHashMap<String, Wrapper> cache) {
        this.cache = cache;
    }

    private void useTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (Map.Entry<String, Wrapper> pair : cache.entrySet()) {
                    if (System.currentTimeMillis() - pair.getValue().getAddTime() >= timeToLive) {
                        cache.remove(pair.getKey());
                    }
                }
            }
        };

        Timer timer = new Timer("Timer", true);
        timer.scheduleAtFixedRate(timerTask, 0, checkTime);
    }


}
