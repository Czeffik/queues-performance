package io.github.czeffik.queues.performance.infrastructure.consumer;

import io.github.czeffik.queues.performance.domain.Consumer;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("application.consumer")
public class ConsumerProperties {
  private Consumer.Type type;
  private TimeUnit unit;
  private long timeout;

  public Consumer create() {
    return switch (type) {
      case WAITING -> new WaitingConsumer(timeout, unit);
    };
  }
}
