package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.TimeUnit;
import org.jctools.queues.MpscArrayQueue;

public class JCToolsMpscArrayQueue implements MessageQueue {
  private final MpscArrayQueue<String> queue = new MpscArrayQueue<>(1 << 20);

  @Override
  public String poll(long timeout, TimeUnit unit) throws InterruptedException {
    return queue.relaxedPoll();
  }

  @Override
  public boolean offer(String s) {
    return queue.relaxedOffer(s);
  }

  @Override
  public int size() {
    return queue.size();
  }
}
