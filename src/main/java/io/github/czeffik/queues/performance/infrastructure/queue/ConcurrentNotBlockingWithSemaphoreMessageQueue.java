package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ConcurrentNotBlockingWithSemaphoreMessageQueue implements MessageQueue {
  private final ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue<>();
  private final Semaphore available = new Semaphore(0);

  @Override
  public long poll(long timeout, TimeUnit unit) throws InterruptedException {
    if (!available.tryAcquire(timeout, unit)) {
      return -1; // timeout
    }
    var result = queue.poll();
    if (result == null) {
      return -1;
    }
    return result;
  }

  @Override
  public boolean offer(long timeNanos) {
    var result = queue.offer(timeNanos);
    available.release();
    return result;
  }

  @Override
  public int size() {
    return queue.size();
  }
}
