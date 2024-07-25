package com.example.firebaseintegrity

import com.example.firebaseintegrity.ui.theme.FirebaseIntegrityTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseIntegrityTheme {
                loginScreen()
            }
        }
    }
    val auth = FirebaseAuth.getInstance()
    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { task ->
                if (task.user != null) {
                    println("User Logged In")
                    var user = auth.currentUser
                    println(user?.uid)
                }
            }
    }
    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { task->

                if (task.user != null) {
                    println("User Created")
                    var user = auth.currentUser
                    println(user?.uid)
                }
            }
    }
    @Composable
    fun loginScreen() {
        var email = remember {
            mutableStateOf("")
        }
        var password = remember {
            mutableStateOf("")
        }
        Scaffold {
                innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(50.dp)
            ) {
                Card(modifier = Modifier
                    .height(300.dp)
                    .width(700.dp)) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            label = { Text(text = "Email") },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            label = { Text(text = "Password") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { signUp(email.value, password.value) }) {
                            Text(text = "Register")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { signIn(email.value, password.value) }) {
                            Text(text = "Login")
                        }
                    }
                }
            }
        }
    }
}



