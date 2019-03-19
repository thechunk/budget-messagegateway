package messagegateway

import common.Amount
import common.BudgetRecord
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.util.SerializationUtils
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

@ExtendWith(SpringExtension::class)
@DirtiesContext
@EmbeddedKafka(topics = [Constants.KAFKA_RECORDS_TOPIC])
class KafkaProducerTests {
    @Suppress("SpringJavaAutowiredMembersInspection")
    @Autowired
    val broker: EmbeddedKafkaBroker? = null

    @Test
    fun `Send message`() {
        val consumerProps = KafkaTestUtils.consumerProps("consumer", "false", broker)
        consumerProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ByteArrayDeserializer::class.java
        val consumer = DefaultKafkaConsumerFactory<String, ByteArray>(consumerProps).createConsumer()
        broker!!.consumeFromAllEmbeddedTopics(consumer)

        val producerProps = KafkaTestUtils.senderProps(broker.brokersAsString)
        producerProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = ByteArraySerializer::class.java
        val tpl = KafkaTemplate(DefaultKafkaProducerFactory<String, ByteArray>(producerProps))

        val amount = Amount(
            BigDecimal(1.20).setScale(2, RoundingMode.HALF_EVEN),
            Currency.getInstance("HKD"))
        val record = BudgetRecord(
            "desc",
            amount,
            "credit",
            "groceries"
        )

        val producer = KafkaProducer(tpl)
        producer.sendMessage(record)

        tpl.flush()

        val records = KafkaTestUtils.getRecords(consumer).records(Constants.KAFKA_RECORDS_TOPIC)
        assert(records.count() == 1)
        val o = SerializationUtils.deserialize(records.asIterable().first().value()) as BudgetRecord
        assert(o.description == "desc")

        consumer.close()
    }
}