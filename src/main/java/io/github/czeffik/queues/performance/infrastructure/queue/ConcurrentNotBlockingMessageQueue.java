package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class ConcurrentNotBlockingMessageQueue implements MessageQueue {
  private final ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue<>();

  @Override
  public long poll(long timeout, TimeUnit unit) throws InterruptedException {
    var result = queue.poll();
    if (result == null) {
      return -1;
    }
    return result;
  }

  @Override
  public boolean offer(long timeNanos) {
    return queue.offer(timeNanos);
  }

  @Override
  public int size() {
    return queue.size();
  }
}
