package exercicio2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class Main {
        public static void main(String[] args) {
            Semaphore semaphore = new Semaphore(0);
            Lock lock;

            lock.lock();

            lock.unlock();
        }
}
