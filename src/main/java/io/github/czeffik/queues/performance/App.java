package io.github.czeffik.queues.performance;

import io.github.czeffik.queues.performance.infrastructure.DomainConfiguration;
import io.github.czeffik.queues.performance.infrastructure.InfrastructureConfiguration;
import io.github.czeffik.queues.performance.interfaces.InterfacesConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
  DomainConfiguration.class,
  InterfacesConfiguration.class,
  InfrastructureConfiguration.class
})
@SpringBootApplication
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
