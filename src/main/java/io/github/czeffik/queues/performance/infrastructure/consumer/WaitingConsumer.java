package io.github.czeffik.queues.performance.infrastructure.consumer;

import io.github.czeffik.queues.performance.domain.Consumer;
import io.github.czeffik.queues.performance.domain.MessageQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WaitingConsumer implements Consumer {
  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  private final long timeout;
  private final TimeUnit unit;
  private volatile boolean running = true;

  @Override
  public void consumeFrom(MessageQueue messageQueue) {
    executor.submit(
        () -> {
          while (running) {
            try {
              messageQueue.poll(timeout, unit);
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          }
        });
  }

  @Override
  public void close() throws Exception {
    running = false;
    executor.shutdown();
    executor.awaitTermination(timeout, unit);
  }
}
