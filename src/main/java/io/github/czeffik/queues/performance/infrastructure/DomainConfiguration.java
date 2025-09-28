package io.github.czeffik.queues.performance.infrastructure;

import io.github.czeffik.queues.performance.domain.Consumer;
import io.github.czeffik.queues.performance.domain.MessageQueue;
import io.github.czeffik.queues.performance.domain.MultipleProducersSingleConsumer;
import io.github.czeffik.queues.performance.domain.Producer;
import io.github.czeffik.queues.performance.infrastructure.consumer.ConsumerProperties;
import io.github.czeffik.queues.performance.infrastructure.producer.ProducerProperties;
import io.github.czeffik.queues.performance.infrastructure.queue.MessageQueueProperties;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
  ConsumerProperties.class,
  ProducerProperties.class,
  MessageQueueProperties.class
})
@Configuration
public class DomainConfiguration {

  @Bean
  public Runnable multipleProducersSingleConsumer(
      List<Producer> producers, Consumer consumer, MessageQueue messageQueue) {
    return new MultipleProducersSingleConsumer(producers, consumer, messageQueue);
  }

  @Bean
  public List<Producer> producers(ProducerProperties producerProperties) {
    return producerProperties.create();
  }

  @Bean
  public Consumer consumer(ConsumerProperties consumerProperties) {
    return consumerProperties.create();
  }

  @Bean
  public MessageQueue messageQueue(MessageQueueProperties messageQueueProperties) {
    return messageQueueProperties.create();
  }
}
