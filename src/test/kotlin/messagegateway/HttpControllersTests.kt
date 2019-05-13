package messagegateway

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import common.entities.Amount
import common.entities.BudgetRecord
import common.entities.MessageRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

@WebMvcTest
@ContextConfiguration(classes=[MessageGatewayApplication::class])
@ActiveProfiles("test")
class HttpControllersTests(@Autowired val mockMvc : MockMvc) {
    @Test
    fun `Process record`() {
        val amount = Amount(BigDecimal(1.20).setScale(2, RoundingMode.HALF_EVEN),
            Currency.getInstance("HKD"))
        val record = BudgetRecord(
            "desc",
            amount,
            "credit",
            "groceries"
        )
        val request = MessageRequest(record)
        val j = jacksonObjectMapper().writeValueAsString(request)
        mockMvc.perform(
            post("/api/budget/record")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(j)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    }
}