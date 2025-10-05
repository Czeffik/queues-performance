package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.jctools.queues.MpscArrayQueue;

public class JCToolsMpscArrayQueueWithSemaphore implements MessageQueue {
  private final MpscArrayQueue<Long> queue = new MpscArrayQueue<>(1 << 20);
  private final Semaphore available = new Semaphore(0);

  @Override
  public long poll(long timeout, TimeUnit unit) throws InterruptedException {
    if (!available.tryAcquire(timeout, unit)) {
      return -1; // timeout
    }
    var result = queue.relaxedPoll();
    if (result == null) {
      return -1;
    }
    return result;
  }

  @Override
  public boolean offer(long timeNanos) {
    var result = queue.relaxedOffer(timeNanos);
    available.release();
    return result;
  }

  @Override
  public int size() {
    return queue.size();
  }
}
