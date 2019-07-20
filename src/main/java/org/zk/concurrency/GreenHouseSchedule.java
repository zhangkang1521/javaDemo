package org.zk.concurrency;


import java.util.Calendar;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GreenHouseSchedule {

    ScheduledThreadPoolExecutor scheExec = new ScheduledThreadPoolExecutor(10);
    private Calendar lastTime = Calendar.getInstance();



    class CollecData implements Runnable {
        @Override
        public void run() {
            System.out.println("collect data");
        }
    }

    public static void main(String[] args) {
        GreenHouseSchedule gh = new GreenHouseSchedule();
//        gh.scheExec.scheduleAtFixedRate(gh.new CollecData(), 3, 1, TimeUnit.SECONDS);
        gh.scheExec.schedule(gh.new CollecData(), 1, TimeUnit.SECONDS);
    }


}
