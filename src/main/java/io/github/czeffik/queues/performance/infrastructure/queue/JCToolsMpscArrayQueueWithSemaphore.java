package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.jctools.queues.MpscArrayQueue;

public class JCToolsMpscArrayQueueWithSemaphore implements MessageQueue {
  private final MpscArrayQueue<String> queue = new MpscArrayQueue<>(1 << 20);
  private final Semaphore available = new Semaphore(0);

  @Override
  public String poll(long timeout, TimeUnit unit) throws InterruptedException {
    if (!available.tryAcquire(timeout, unit)) {
      return null; // timeout
    }
    return queue.relaxedPoll();
  }

  @Override
  public boolean offer(String s) {
    var result = queue.relaxedOffer(s);
    available.release();
    return result;
  }

  @Override
  public int size() {
    return queue.size();
  }
}
