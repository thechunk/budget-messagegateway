package messagegateway.engine

import common.engine.KafkaProducer
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class RecordKafkaProducer(kafkaTemplate: KafkaTemplate<String, ByteArray>) : KafkaProducer(kafkaTemplate)