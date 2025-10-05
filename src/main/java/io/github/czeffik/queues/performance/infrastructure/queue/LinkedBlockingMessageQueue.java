package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingMessageQueue implements MessageQueue {
  private final LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>();

  @Override
  public long poll(long timeout, TimeUnit unit) throws InterruptedException {
    var result = queue.poll(timeout, unit);
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
