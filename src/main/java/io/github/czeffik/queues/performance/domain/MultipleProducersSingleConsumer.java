package io.github.czeffik.queues.performance.domain;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.NonNull;

public class MultipleProducersSingleConsumer implements Runnable, AutoCloseable {
  private final List<Producer> producers;
  private final Consumer consumer;
  private final MessageQueue messageQueue;
  private final ExecutorService producersExecutor;
  private final ExecutorService consumerExecutor;
  private volatile boolean running = true;

  public MultipleProducersSingleConsumer(
      @NonNull List<Producer> producers,
      @NonNull Consumer consumer,
      @NonNull MessageQueue messageQueue) {
    this.producers = producers;
    this.consumer = consumer;
    this.messageQueue = messageQueue;
    this.producersExecutor = Executors.newFixedThreadPool(producers.size());
    this.consumerExecutor = Executors.newSingleThreadExecutor();
  }

  @Override
  public void run() {
    producers.forEach(this::produce);
    consume();
  }

  private void produce(@NonNull Producer producer) {
    producersExecutor.submit(
        () -> {
          while (running) {
            producer.produceTo(this.messageQueue);
          }
        });
  }

  private void consume() {
    consumerExecutor.submit(
        () -> {
          while (running) {
            this.consumer.consumeFrom(this.messageQueue);
          }
        });
  }

  @Override
  public void close() throws Exception {
    this.running = false;
  }
}
