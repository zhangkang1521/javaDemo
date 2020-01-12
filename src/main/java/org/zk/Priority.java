package org.zk;

import java.util.ArrayList;
import java.util.List;

public class Priority {

    static volatile boolean notStart = true;
    static volatile boolean notEnd = true;

    public static void main(String[] args) {
        List<Job> jobs = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            Job job1 = new Job(i % 2 == 0 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY);
            Thread thread1 = new Thread(job1);
            thread1.setPriority(job1.priority);
            thread1.start();
            jobs.add(job1);
        }
        notStart = false;
        SleepUtils.second(1);
        notEnd = false;
        for (Job job : jobs) {
            System.out.printf("%2d, %d\n", job.priority, job.count);
        }

    }

    static class Job implements Runnable {
        int priority;
        long count = 0;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            while(notStart) {
                Thread.yield();
            }
            while (notEnd) {
                count++;
            }
        }
    }
}
