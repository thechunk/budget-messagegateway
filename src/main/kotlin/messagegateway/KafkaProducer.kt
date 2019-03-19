package messagegateway

import common.constants.Kafka
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.util.SerializationUtils
import java.io.Serializable

@Service
class KafkaProducer(val kafkaTemplate: KafkaTemplate<String, ByteArray>) : Producer {
    override fun sendMessage(message: Serializable) {
        kafkaTemplate.send(Kafka.RECORDS_TOPIC, SerializationUtils.serialize(message))
    }
}