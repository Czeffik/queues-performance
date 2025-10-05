package io.github.czeffik.queues.performance.domain;

import java.util.concurrent.TimeUnit;

public interface MessageQueue {
  String poll(long timeout, TimeUnit unit) throws InterruptedException;

  boolean offer(String s);

  int size();

  enum Type {
    ARRAY_BLOCKING_1k,
    ARRAY_BLOCKING_10k,
    ARRAY_BLOCKING_100k,
    LINKED_BLOCKING,
    CONCURRENT_NOT_BLOCKING,
    CONCURRENT_NOT_BLOCKING_WITH_SEMAPHORE,
    JC_TOOLS,
    JC_TOOLS_WITH_SEMAPHORE,
    ;
  }
}
