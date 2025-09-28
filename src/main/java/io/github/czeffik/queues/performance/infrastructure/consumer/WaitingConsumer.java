package io.github.czeffik.queues.performance.infrastructure.consumer;

import io.github.czeffik.queues.performance.domain.Consumer;
import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WaitingConsumer implements Consumer {
  private final long timeout;
  private final TimeUnit unit;

  @Override
  public void consumeFrom(MessageQueue messageQueue) {
    try {
      messageQueue.poll(timeout, unit);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
