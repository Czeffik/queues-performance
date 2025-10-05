package io.github.czeffik.queues.performance.domain;

import java.util.List;
import lombok.NonNull;

public class MultipleProducersSingleConsumer implements Runnable {
  private final List<Producer> producers;
  private final Consumer consumer;
  private final MessageQueue messageQueue;

  public MultipleProducersSingleConsumer(
      @NonNull List<Producer> producers,
      @NonNull Consumer consumer,
      @NonNull MessageQueue messageQueue) {
    this.producers = producers;
    this.consumer = consumer;
    this.messageQueue = messageQueue;
  }

  @Override
  public void run() {
    producers.forEach(this::produce);
    consume();
  }

  private void produce(@NonNull Producer producer) {
    producer.produceTo(this.messageQueue);
  }

  private void consume() {
    this.consumer.consumeFrom(this.messageQueue);
  }
}
