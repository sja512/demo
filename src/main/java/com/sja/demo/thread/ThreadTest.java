package com.sja.demo.thread;

import com.sja.demo.thread.util.ThreadPoolUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author sja  created on 2019/1/25.
 */
public class ThreadTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPoolExecutor();
        CinemaTicket cinemaTicket = new CinemaTicket();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 100; i++) {
            executor.execute(new MyTask(cinemaTicket,countDownLatch));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("----------------------------结束");
    }

    static class MyTask implements Runnable {
        private CinemaTicket cinemaTicket;
        private CountDownLatch countDownLatch;
        MyTask(CinemaTicket cinemaTicket,CountDownLatch countDownLatch) {
            this.cinemaTicket = cinemaTicket;
            this.countDownLatch = countDownLatch;
        }
        public void run() {
            this.cinemaTicket.ticket();
            System.out.println(Thread.currentThread().getName());
            countDownLatch.countDown();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class CinemaTicket {
        private Integer qty = 10;

        /**
         * 卖票
         */
        public synchronized void ticket() {
            System.out.println("电影票号：" + qty);
            this.qty--;
//            if (qty < 0) {
//                System.out.println("无票了");
//            }

        }
    }
}




