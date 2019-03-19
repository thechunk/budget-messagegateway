package messagegateway

import java.io.Serializable

interface Producer {
    fun sendMessage(message: Serializable)
}