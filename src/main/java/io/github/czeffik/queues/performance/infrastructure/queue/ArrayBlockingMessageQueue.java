package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingMessageQueue implements MessageQueue {
  private final ArrayBlockingQueue<Long> queue;

  public ArrayBlockingMessageQueue(int size) {
    this.queue = new ArrayBlockingQueue<>(size);
  }

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
