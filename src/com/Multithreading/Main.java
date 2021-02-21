package com.Multithreading;

import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger IA = new AtomicInteger(0);

    public static void main(String[] args) {
        Phaser phaser = new Phaser(2); // не даёт запустить более 2 потоков

        for (int i=0;i<7;i++) // он не сможет остановится пока, не будет сделанно действие, для это нужно что-бы количество потоков совпадало, в данном примере их должно быть 2
            new MyThread(phaser);
    }

    static class MyThread extends Thread{
        Phaser phaser;

        public MyThread(Phaser phaser) { // вкладываем наш класс с счётчиком потоков
            this.phaser = phaser;
            start();
        }

        @Override
        public void run() {
            for (int i=0;i<5;i++){
                IA.incrementAndGet();
                System.out.println(getName()+" работает, внешнее действие "+IA.get()+", внутренее действие "+(i+1));
                phaser.arriveAndAwaitAdvance(); // передает действие другому потоку
            }
            System.out.println(getName()+" больше не работает");
        }
    }
}


