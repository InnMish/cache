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
    TimeUnit timeUnit;

    Cache(long timeToLive, long checkTime, TimeUnit timeUnit) {
        setTimeToLive(timeToLive, timeUnit);
        setCheckTime(checkTime);
        useTimer();
    }

    long getTimeToLive() {
        return timeToLive;
    }

    public void setCheckTime(long checkTime) {
        if (checkTime <= 30) {
            throw new RuntimeException("checkTime parameter must not be less than 30");
        }
        this.checkTime = checkTime;
    }

    void setTimeToLive(long timeToLive, TimeUnit type) {
        if (timeToLive <= 0 || type == null) {
            throw new RuntimeException("timeToLive parameter must be bigger than 0");
        }
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
