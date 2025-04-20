package com.sesi.quizly.ui.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sesi.quizly.ui.signin.viewmodel.SignInViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import quizly.composeapp.generated.resources.Res
import quizly.composeapp.generated.resources.ic_user


@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel()
){
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 30.dp)) {
                Image(
                    painter = painterResource(Res.drawable.ic_user),
                    contentDescription = "user",
                    modifier = Modifier.size(100.dp).clip(RoundedCornerShape(50.dp))
                )
            }

            Row(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            ) {
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("User Name") }, maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))

            }
            Row(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            ) {
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Email") }, maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))

            }

            Row(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            ) {
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Password") }, maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))

            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ElevatedButton(onClick = {  }, modifier = Modifier.fillMaxWidth()) {
                    Text("Sigin")
                }

            }


        }
    }
}