package messagegateway.controllers

import common.constants.Auth
import common.constants.Kafka
import messagegateway.engine.RecordKafkaProducer
import common.entities.MessageRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices
import org.springframework.util.SerializationUtils
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/budget")
class HttpControllers(val producer: RecordKafkaProducer, val service: ResourceServerTokenServices) {
    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping("/ping")
    fun ping() : String {
        return "pong"
    }

    @PreAuthorize("#oauth2.hasScope('write')")
    @PostMapping("/record")
    fun processRecord(@RequestBody request: MessageRequest, principal: Principal) : String {
        var isGoogleUser = false
        if (principal is OAuth2Authentication) {
        }
        val serializedRequest = SerializationUtils.serialize(request.record)
        if (serializedRequest != null) producer.sendMessage(Kafka.RECORDS_TOPIC, message = serializedRequest)
        return "ping"
    }
}