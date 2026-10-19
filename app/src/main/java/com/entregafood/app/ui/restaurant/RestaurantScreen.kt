package com.entregafood.app.ui.restaurant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.entregafood.app.data.Restaurant
import com.entregafood.app.ui.theme.AppColors
import java.util.Locale

// Layout conforme o Figma. O cardápio completo será implementado no UC02.
@Composable
fun RestaurantScreen(restaurant: Restaurant?, onBack: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().systemBarsPadding().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TextButton(onClick = onBack) {
                Text("‹ Voltar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            if (restaurant == null) {
                Text("Restaurante não encontrado.", color = AppColors.Muted)
                return@Column
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(restaurant.color)),
                contentAlignment = Alignment.Center,
            ) {
                Text(restaurant.name.first().toString(), color = Color.White, fontSize = 56.sp, fontWeight = FontWeight.ExtraBold)
            }

            Text(restaurant.name, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = AppColors.Text)
            Text(
                "${restaurant.category} • ★ ${"%.1f".format(Locale.US, restaurant.rating)} • ${restaurant.etaMin}-${restaurant.etaMax} min",
                fontSize = 14.sp,
                color = AppColors.Muted,
            )
            Spacer(Modifier.height(12.dp))
            Text("O cardápio será exibido aqui.", fontSize = 15.sp, color = AppColors.Muted)
        }
    }
}
