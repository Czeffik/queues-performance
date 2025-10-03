package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingMessageQueue implements MessageQueue {
  private final ArrayBlockingQueue<String> queue;

  public ArrayBlockingMessageQueue(int size) {
    this.queue = new ArrayBlockingQueue<>(size);
  }

  @Override
  public String poll(long timeout, TimeUnit unit) throws InterruptedException {
    return queue.poll(timeout, unit);
  }

  @Override
  public boolean offer(String s) {
    return queue.offer(s);
  }

  @Override
  public int size() {
    return queue.size();
  }
}
