package io.github.czeffik.queues.performance.interfaces.app;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class Listener {
  private final Runnable multipleProducersSingleConsumer;

  @EventListener
  public void handle(ApplicationReadyEvent readyEvent) {
    multipleProducersSingleConsumer.run();
  }
}
