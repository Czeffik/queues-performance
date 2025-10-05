package io.github.czeffik.queues.performance.infrastructure.producer;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import io.github.czeffik.queues.performance.domain.Producer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OneThousandPerSecondProducer implements Producer {
  private static final int THOUSAND = 1000;
  private static final long PERIOD_IN_NANOS = 1_000_000_000L / THOUSAND; // 1ms
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  @Override
  public void produceTo(MessageQueue queue) {
    scheduler.scheduleAtFixedRate(
        () -> queue.offer("pif paf"), 0, PERIOD_IN_NANOS, TimeUnit.NANOSECONDS);
  }

  @Override
  public void close() throws Exception {
    scheduler.shutdownNow();
  }
}
