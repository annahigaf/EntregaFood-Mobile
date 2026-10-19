package com.entregafood.app.data

data class User(val id: String, val name: String, val email: String)

data class Session(val token: String, val user: User)

data class Restaurant(
    val id: String,
    val name: String,
    val category: String,
    val rating: Double,
    val etaMin: Int,
    val etaMax: Int,
    val deliveryFee: Double,
    val open: Boolean,
    val color: Long,
)

// Dados fictícios até existir a API de restaurantes (UC02).
object MockRestaurants {
    val categories = listOf("Todos", "Lanches", "Pizza", "Japonesa", "Brasileira", "Doces", "Saudável")

    val list = listOf(
        Restaurant("1", "Burger do Zé", "Lanches", 4.7, 25, 35, 5.9, true, 0xFFF2B84B),
        Restaurant("2", "Forno a Lenha Napoli", "Pizza", 4.8, 35, 50, 0.0, true, 0xFFD62839),
        Restaurant("3", "Sushi Kaze", "Japonesa", 4.5, 40, 55, 7.5, true, 0xFF2F6F8F),
        Restaurant("4", "Marmitaria da Vó", "Brasileira", 4.9, 20, 30, 4.0, true, 0xFF1F8A5B),
        Restaurant("5", "Doce Encanto", "Doces", 4.6, 30, 40, 6.0, false, 0xFF8E4A9E),
        Restaurant("6", "Verde Bowl", "Saudável", 4.4, 25, 35, 3.5, true, 0xFF5C8A2F),
    )

    fun byId(id: String?) = list.firstOrNull { it.id == id }
}
