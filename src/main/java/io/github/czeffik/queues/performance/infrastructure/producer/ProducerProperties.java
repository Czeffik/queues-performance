package io.github.czeffik.queues.performance.infrastructure.producer;

import io.github.czeffik.queues.performance.domain.Producer;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("application.producers")
public class ProducerProperties {
  private Producer.Type type;
  private int number;

  public List<Producer> create() {
    return switch (type) {
      case CONSTANT -> ConstantProducer.Factory.create(number);
    };
  }
}
