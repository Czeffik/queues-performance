package io.github.czeffik.queues.performance.infrastructure.metrics;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MessageMetrics {

  private final Counter messagesProducedTotal;
  private final Counter messagesConsumedTotal;
  private final DistributionSummary latencySummary;

  public MessageMetrics(MeterRegistry registry, MessageQueue messageQueue) {
    this.messagesProducedTotal =
        Counter.builder("messages.produced.total")
            .description("Total number of messages produced")
            .register(registry);

    this.messagesConsumedTotal =
        Counter.builder("messages.consumed.total")
            .description("Total number of messages consumed")
            .register(registry);
    registry.gauge("message.queue.size", messageQueue, MessageQueue::size);

    latencySummary =
        DistributionSummary.builder("queue.latency.microseconds")
            .description("Latency in microseconds between produce and consume")
            .baseUnit("microseconds")
            .publishPercentileHistogram()
            .publishPercentiles(0.5, 0.95, 0.99)
            .register(registry);
  }

  public void incrementProduced() {
    messagesProducedTotal.increment();
  }

  public void incrementConsumed() {
    messagesConsumedTotal.increment();
  }

  public void recordLatency(long producedTime) {
    if (producedTime < 0) {
      return;
    }
    long latencyMicros = (System.nanoTime() - producedTime) / 1_000;
    latencySummary.record(latencyMicros);
  }
}
