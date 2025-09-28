package io.github.czeffik.queues.performance.interfaces;

import io.github.czeffik.queues.performance.interfaces.app.Listener;
import io.github.czeffik.queues.performance.interfaces.rest.RestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({RestConfiguration.class})
@Configuration
public class InterfacesConfiguration {

  @Bean
  public Listener listener(Runnable multipleProducersSingleConsumer) {
    return new Listener(multipleProducersSingleConsumer);
  }
}
