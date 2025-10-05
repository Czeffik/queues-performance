package io.github.czeffik.queues.performance.domain;

public interface Producer extends AutoCloseable {
  void produceTo(MessageQueue queue);

  enum Type {
    CONSTANT
  }
}
