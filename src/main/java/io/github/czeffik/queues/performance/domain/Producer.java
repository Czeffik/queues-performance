package io.github.czeffik.queues.performance.domain;

public interface Producer extends AutoCloseable {
  void produceTo(MessageQueue queue);

  Type type();

  enum Type {
    CONSTANT,
    ONE_THOUSAND_PER_SECOND,
    PARKED_FOR_500_k_NANOS
  }
}
