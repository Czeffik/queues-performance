package io.github.czeffik.queues.performance.domain;

public interface Consumer extends AutoCloseable {
  void consumeFrom(MessageQueue messageQueue);

  Type type();

  enum Type {
    WAITING
  }
}
