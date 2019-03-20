package messagegateway

import common.entities.Amount
import common.entities.BudgetRecord
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.util.SerializationUtils
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

@EnableAutoConfiguration
@ComponentScan
class MessageGatewayApplication

fun main(args: Array<String>) {
    val ctx = runApplication<MessageGatewayApplication>(*args)
    val producer = ctx.getBean(KafkaProducer::class.java)

    var i = 0
    while (true) {
        val amount = Amount(
            BigDecimal(1.20).setScale(2, RoundingMode.HALF_EVEN),
            Currency.getInstance("HKD"))
        val record = BudgetRecord(
            "desc $i",
            amount,
            "credit",
            "groceries"
        )

        producer.sendMessage(record)

        i++
        Thread.sleep(500)
    }
}
