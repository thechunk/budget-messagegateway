package messagegateway

import common.constants.Kafka
import common.entities.BudgetRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.util.SerializationUtils
import java.io.Serializable
import java.util.*

@Service
class KafkaProducer(val kafkaTemplate: KafkaTemplate<String, ByteArray>) : Producer {
    val logger = LoggerFactory.getLogger(KafkaProducer::class.java)!!

    override fun sendMessage(message: Serializable) {
        val desc = (message as BudgetRecord).description
        logger.debug("Send message: $desc")
        kafkaTemplate.send(Kafka.RECORDS_TOPIC, UUID.randomUUID().toString(), SerializationUtils.serialize(message))
        kafkaTemplate.flush()
    }
}