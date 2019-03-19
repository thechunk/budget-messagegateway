package messagegateway

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.util.SerializationUtils
import java.io.Serializable

@Service
class KafkaProducer(val kafkaTemplate: KafkaTemplate<String, ByteArray>) : Producer {
    override fun sendMessage(message: Serializable) {
        kafkaTemplate.send(Constants.KAFKA_RECORDS_TOPIC, SerializationUtils.serialize(message))
    }
}