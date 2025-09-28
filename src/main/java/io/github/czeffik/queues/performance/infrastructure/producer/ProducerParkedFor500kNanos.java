package io.github.czeffik.queues.performance.infrastructure.producer;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import io.github.czeffik.queues.performance.domain.Producer;
import java.util.concurrent.locks.LockSupport;

public class ProducerParkedFor500kNanos implements Producer {

  @Override
  public void produceTo(MessageQueue queue) {
    queue.offer("pif paf");
    LockSupport.parkNanos(500_000);
  }
}
