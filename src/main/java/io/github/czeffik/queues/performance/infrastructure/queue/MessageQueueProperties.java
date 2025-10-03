package io.github.czeffik.queues.performance.infrastructure.queue;

import io.github.czeffik.queues.performance.domain.MessageQueue;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("application.queue")
public class MessageQueueProperties {
  private MessageQueue.Type type;

  public MessageQueue create() {
    return switch (type) {
      case ARRAY_BLOCKING_1k -> new ArrayBlockingMessageQueue(1_000);
      case ARRAY_BLOCKING_10k -> new ArrayBlockingMessageQueue(10_000);
      case ARRAY_BLOCKING_100k -> new ArrayBlockingMessageQueue(100_000);
      case LINKED_BLOCKING -> new LinkedBlockingMessageQueue();
      case CONCURRENT_NOT_BLOCKING -> new ConcurrentNotBlockingMessageQueue();
      case CONCURRENT_NOT_BLOCKING_WITH_SEMAPHORE ->
          new ConcurrentNotBlockingWithSemaphoreMessageQueue();
    };
  }
}
