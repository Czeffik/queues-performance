package io.github.czeffik.queues.performance.infrastructure;

import io.github.czeffik.queues.performance.infrastructure.consumer.ConsumerProperties;
import io.github.czeffik.queues.performance.infrastructure.producer.ProducerProperties;
import io.github.czeffik.queues.performance.infrastructure.queue.MessageQueueProperties;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApplicationProperties {
  private final ProducerProperties producer;
  private final ConsumerProperties consumer;
  private final MessageQueueProperties queue;

  public Tags toTags() {
    return Tags.of(
        Tag.of("producer.type", producer.getType().name()),
        Tag.of("producer.number", String.valueOf(producer.getNumber())),
        Tag.of("consumer.type", consumer.getType().name()),
        Tag.of("queue.type", queue.getType().name()));
  }
}
