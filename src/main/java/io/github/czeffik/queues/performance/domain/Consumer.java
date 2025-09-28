package io.github.czeffik.queues.performance.domain;

public interface Consumer {
  void consumeFrom(MessageQueue messageQueue);

  enum Type {
    WAITING
  }
}
