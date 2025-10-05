package io.github.czeffik.queues.performance.infrastructure.metrics;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

@Component
public class MessageMetrics {

  private final Counter messagesProducedTotal;
  private final Counter messagesConsumedTotal;

  public MessageMetrics(MeterRegistry registry, MessageQueue messageQueue, Tags tags) {
    this.messagesProducedTotal =
        Counter.builder("messages.produced.total")
            .tags(tags)
            .description("Total number of messages produced")
            .register(registry);

    this.messagesConsumedTotal =
        Counter.builder("messages.consumed.total")
            .tags(tags)
            .description("Total number of messages consumed")
            .register(registry);
    registry.gauge("message.queue.size", tags, messageQueue, MessageQueue::size);
  }

  public void incrementProduced() {
    messagesProducedTotal.increment();
  }

  public void incrementConsumed() {
    messagesConsumedTotal.increment();
  }
}
