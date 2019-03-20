package messagegateway

import common.constants.Kafka
import org.springframework.context.annotation.Configuration
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean

@Configuration
class KafkaConfiguration {
    @Bean
    fun recordsTopic(): NewTopic {
        return NewTopic(Kafka.RECORDS_TOPIC, 3, 1.toShort())
    }
}