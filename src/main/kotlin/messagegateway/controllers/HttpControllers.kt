package messagegateway.controllers

import common.constants.Kafka
import messagegateway.engine.RecordKafkaProducer
import common.entities.MessageRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.util.SerializationUtils
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/budget")
class HttpControllers(val producer: RecordKafkaProducer) {
    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping("/ping")
    fun ping() : String {
        return "pong"
    }

    @PreAuthorize("#oauth2.hasScope('write')")
    @PostMapping("/record")
    fun processRecord(@RequestBody request: MessageRequest, principal: Principal) : String {
        val serializedRequest = SerializationUtils.serialize(request)
        if (serializedRequest != null) producer.sendMessage(Kafka.RECORDS_TOPIC, message = serializedRequest)
        return "ping"
    }
}