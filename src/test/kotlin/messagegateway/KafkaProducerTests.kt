package messagegateway

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
        val consumer = DefaultKafkaConsumerFactory<String, String>(consumerProps).createConsumer()
        broker!!.consumeFromAllEmbeddedTopics(consumer)

        val producerProps = KafkaTestUtils.senderProps(broker.brokersAsString)
        val tpl = KafkaTemplate(DefaultKafkaProducerFactory<String, String>(producerProps))

        val message = "hello"

        val producer = KafkaProducer(tpl)
        producer.sendMessage(message)

        val records = KafkaTestUtils.getRecords(consumer).records(Constants.KAFKA_RECORDS_TOPIC)
        assert(records.count() == 1)
        assert(records.asIterable().first().value() == message)
    }
}