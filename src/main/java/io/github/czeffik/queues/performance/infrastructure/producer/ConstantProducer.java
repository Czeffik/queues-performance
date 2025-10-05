package io.github.czeffik.queues.performance.infrastructure.producer;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import io.github.czeffik.queues.performance.domain.Producer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConstantProducer implements Producer {
  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  private volatile boolean running = true;

  @Override
  public void produceTo(MessageQueue queue) {
    executor.submit(
        () -> {
          while (running) {
            queue.offer(System.nanoTime());
          }
        });
  }

  @Override
  public Type type() {
    return Type.CONSTANT;
  }

  @Override
  public void close() throws Exception {
    running = false;
    executor.shutdownNow();
  }
}
