package io.github.czeffik.queues.performance.domain;

public interface Producer {
  void produceTo(MessageQueue queue);

  enum Type {
    CONSTANT
  }
}
