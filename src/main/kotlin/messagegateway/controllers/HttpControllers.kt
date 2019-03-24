package messagegateway.controllers

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/budget")
class HttpControllers {
    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping("/ping")
    fun ping() : String {
        return "pong"
    }

    @PreAuthorize("#oauth2.hasScope('write')")
    @PostMapping("/record")
    fun processRecord() : String {
        return "pong"
    }
}