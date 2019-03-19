package messagegateway

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(val kafkaTemplate: KafkaTemplate<String, String>) : Producer {
    override fun sendMessage(message: String) {
        kafkaTemplate.send(Constants.KAFKA_RECORDS_TOPIC, message)
    }
}