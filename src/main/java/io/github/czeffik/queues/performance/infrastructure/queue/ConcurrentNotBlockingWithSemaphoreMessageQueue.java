package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ConcurrentNotBlockingWithSemaphoreMessageQueue implements MessageQueue {
  private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
  private final Semaphore available = new Semaphore(0);

  @Override
  public String poll(long timeout, TimeUnit unit) throws InterruptedException {
    if (!available.tryAcquire(timeout, unit)) {
      return null; // timeout
    }
    return queue.poll();
  }

  @Override
  public boolean offer(String s) {
    var result = queue.offer(s);
    available.release();
    return result;
  }

  @Override
  public int size() {
    return queue.size();
  }
}
