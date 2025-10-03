package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingMessageQueue implements MessageQueue {
  private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

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
