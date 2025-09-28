package io.github.czeffik.queues.performance.infrastructure.producer;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import io.github.czeffik.queues.performance.domain.Producer;
import java.util.List;
import java.util.stream.IntStream;

public class ConstantProducer implements Producer {

  @Override
  public void produceTo(MessageQueue queue) {
    queue.offer("pif paf");
  }

  public static class Factory {
    public static Producer create() {
      return new ConstantProducer();
    }

    public static List<Producer> create(int number) {
      return IntStream.range(0, number).mapToObj(x -> create()).toList();
    }
  }
}
