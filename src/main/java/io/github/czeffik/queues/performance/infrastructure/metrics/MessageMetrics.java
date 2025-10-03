package io.github.czeffik.queues.performance.infrastructure.metrics;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MessageMetrics {

  private final Counter messagesProducedTotal;
  private final Counter messagesConsumedTotal;

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
  }

  public void incrementProduced() {
    messagesProducedTotal.increment();
  }

  public void incrementConsumed() {
    messagesConsumedTotal.increment();
  }
}
