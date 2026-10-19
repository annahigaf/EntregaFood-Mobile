package com.entregafood.app.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.entregafood.app.data.MockRestaurants
import com.entregafood.app.data.Restaurant
import com.entregafood.app.ui.theme.AppColors
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userName: String,
    onRestaurantClick: (Restaurant) -> Unit,
    onLogout: () -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("Todos") }

    val filtered = remember(query, category) {
        MockRestaurants.list.filter {
            (category == "Todos" || it.category == category) &&
                it.name.contains(query.trim(), ignoreCase = true)
        }
    }

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp, end = 16.dp,
                top = padding.calculateTopPadding() + 16.dp,
                bottom = padding.calculateBottomPadding() + 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Olá, $userName", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
                        Text("Entregar em: Rua Exemplo, 123", fontSize = 13.sp, color = AppColors.Muted)
                    }
                    TextButton(onClick = onLogout) { Text("Sair", fontWeight = FontWeight.Bold) }
                }
            }
            item {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("Buscar restaurante") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(MockRestaurants.categories) { c ->
                        FilterChip(
                            selected = c == category,
                            onClick = { category = c },
                            label = { Text(c) },
                            shape = RoundedCornerShape(18.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = AppColors.Surface,
                                selectedContainerColor = AppColors.Primary,
                                selectedLabelColor = Color.White,
                            ),
                        )
                    }
                }
            }
            item { Text("Restaurantes perto de você", fontSize = 18.sp, fontWeight = FontWeight.Bold) }

            if (filtered.isEmpty()) {
                item {
                    Text(
                        "Nenhum restaurante encontrado. Tente outra busca ou categoria.",
                        color = AppColors.Muted,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    )
                }
            }
            items(filtered, key = { it.id }) { r -> RestaurantCard(r) { onRestaurantClick(r) } }
        }
    }
}

@Composable
private fun RestaurantCard(r: Restaurant, onClick: () -> Unit) {
    val fee = if (r.deliveryFee == 0.0) "Entrega grátis"
    else "Entrega R$ " + String.format(Locale("pt", "BR"), "%.2f", r.deliveryFee)

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        border = BorderStroke(1.dp, AppColors.Border),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(72.dp).clip(RoundedCornerShape(12.dp)).background(Color(r.color)),
                contentAlignment = Alignment.Center,
            ) { Text(r.name.first().toString(), color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold) }

            Spacer(Modifier.width(16.dp))
            Column {
                Text(r.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("${r.category} • ★ ${"%.1f".format(Locale.US, r.rating)}", fontSize = 13.sp, color = AppColors.Muted)
                Text("${r.etaMin}-${r.etaMax} min • $fee", fontSize = 13.sp, color = AppColors.Muted)
                if (!r.open) Text("Fechado agora", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AppColors.Danger)
            }
        }
    }
}
