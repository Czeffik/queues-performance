package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class ConcurrentNotBlockingMessageQueue implements MessageQueue {
  private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

  @Override
  public String poll(long timeout, TimeUnit unit) throws InterruptedException {
    return queue.poll();
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
