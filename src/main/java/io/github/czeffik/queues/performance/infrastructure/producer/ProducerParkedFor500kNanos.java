package io.github.czeffik.queues.performance.infrastructure.producer;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import io.github.czeffik.queues.performance.domain.Producer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

public class ProducerParkedFor500kNanos implements Producer {
  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  private volatile boolean running = true;

  @Override
  public void produceTo(MessageQueue queue) {
    executor.submit(
        () -> {
          while (running) {
            queue.offer(System.nanoTime());
            LockSupport.parkNanos(500_000);
          }
        });
  }

  @Override
  public Type type() {
    return Type.PARKED_FOR_500_k_NANOS;
  }

  @Override
  public void close() throws Exception {
    running = false;
    executor.shutdownNow();
  }
}
