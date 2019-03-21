package messagegateway.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/budget")
class HttpControllers {
    @GetMapping("/ping")
    fun ping() : String {
        return "pong"
    }

    @PostMapping("/record")
    fun processRecord() : String {
        return "pong"
    }
}