package messagegateway

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@EnableAutoConfiguration
@ComponentScan
class MessageGatewayApplication

fun main(args: Array<String>) {
    runApplication<MessageGatewayApplication>(*args)
}
