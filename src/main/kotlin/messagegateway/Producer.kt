package messagegateway

interface Producer {
    fun sendMessage(message: String)
}