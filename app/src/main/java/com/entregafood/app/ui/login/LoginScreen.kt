package com.entregafood.app.ui.login

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.entregafood.app.ui.LoginUiState
import com.entregafood.app.ui.theme.AppColors

/** Campo com rótulo fixo acima e texto de exemplo (igual ao Figma). */
@Composable
private fun LabeledField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    error: String?,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Column(Modifier.fillMaxWidth()) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = AppColors.Text)
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            singleLine = true,
            isError = error != null,
            supportingText = error?.let { { Text(it) } },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = trailingIcon,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = AppColors.Surface,
                unfocusedContainerColor = AppColors.Surface,
                unfocusedBorderColor = AppColors.Border,
                focusedPlaceholderColor = AppColors.Muted,
                unfocusedPlaceholderColor = AppColors.Muted,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun LoginScreen(
    state: LoginUiState,
    onLogin: (email: String, password: String) -> Unit,
    onDismissError: () -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val focus = LocalFocusManager.current

    fun submit() {
        emailError = if (Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) null else "Digite um e-mail válido."
        passwordError = if (password.length >= 6) null else "A senha deve ter pelo menos 6 caracteres."
        if (emailError == null && passwordError == null) {
            focus.clearFocus()
            onLogin(email.trim(), password)
        }
    }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(60.dp))

            // Marca
            Box(
                modifier = Modifier.size(64.dp).clip(RoundedCornerShape(20.dp)).background(AppColors.Primary),
                contentAlignment = Alignment.Center,
            ) { Text("E", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold) }
            Spacer(Modifier.height(16.dp))
            Text("Entrega Food", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = AppColors.Text)
            Spacer(Modifier.height(4.dp))
            Text("Entre para pedir da sua região.", fontSize = 15.sp, color = AppColors.Muted)
            Spacer(Modifier.height(32.dp))

            // Campos
            LabeledField(
                label = "E-mail",
                value = email,
                onValueChange = { email = it; emailError = null; onDismissError() },
                placeholder = "voce@email.com",
                error = emailError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focus.moveFocus(FocusDirection.Down) }),
            )
            Spacer(Modifier.height(16.dp))
            LabeledField(
                label = "Senha",
                value = password,
                onValueChange = { password = it; passwordError = null; onDismissError() },
                placeholder = "Sua senha",
                error = passwordError,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { submit() }),
                trailingIcon = {
                    TextButton(onClick = { showPassword = !showPassword }) {
                        Text(if (showPassword) "Ocultar" else "Mostrar", fontSize = 14.sp)
                    }
                },
            )

            if (state.error != null) {
                Spacer(Modifier.height(8.dp))
                Text(state.error, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
            }
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = ::submit,
                enabled = !state.loading,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp),
            ) {
                if (state.loading) {
                    CircularProgressIndicator(Modifier.size(22.dp), color = Color.White, strokeWidth = 2.dp)
                } else {
                    Text("Entrar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(8.dp))
            TextButton(
                onClick = { /* TODO: recuperação de senha (UC01) */ },
                colors = ButtonDefaults.textButtonColors(contentColor = AppColors.Muted),
            ) { Text("Esqueci minha senha", fontSize = 14.sp) }
            TextButton(onClick = { /* TODO: tela de cadastro (UC01) */ }) {
                Text("Ainda não tenho conta. Criar conta", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(24.dp))
            Text("Teste: cliente@entregafood.com / 123456", fontSize = 12.sp, color = AppColors.Muted)
        }
    }
}
