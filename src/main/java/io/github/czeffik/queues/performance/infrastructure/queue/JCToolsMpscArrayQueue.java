package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.TimeUnit;
import org.jctools.queues.MpscArrayQueue;

public class JCToolsMpscArrayQueue implements MessageQueue {
  private final MpscArrayQueue<Long> queue = new MpscArrayQueue<>(1 << 20);

  @Override
  public long poll(long timeout, TimeUnit unit) throws InterruptedException {
    var result = queue.relaxedPoll();
    if (result == null) {
      return -1;
    }
    return result;
  }

  @Override
  public boolean offer(long timeNanos) {
    return queue.relaxedOffer(timeNanos);
  }

  @Override
  public int size() {
    return queue.size();
  }
}
