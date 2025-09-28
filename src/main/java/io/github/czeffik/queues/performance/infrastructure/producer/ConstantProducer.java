package io.github.czeffik.queues.performance.infrastructure.producer;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import io.github.czeffik.queues.performance.domain.Producer;

public class ConstantProducer implements Producer {

  @Override
  public void produceTo(MessageQueue queue) {
    queue.offer("pif paf");
  }
}
