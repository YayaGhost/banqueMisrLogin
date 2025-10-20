package com.yayaghost.banquemisr

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.yayaghost.banquemisr.ui.theme.BanqueMisrTheme
import com.yayaghost.banquemisr.ui.theme.BrickRed
import com.yayaghost.banquemisr.ui.theme.DarkGrey
import com.yayaghost.banquemisr.ui.theme.Grey
import com.yayaghost.banquemisr.ui.theme.LightRed20

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BanqueMisrTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFFFDF4F4)
                ) { innerPadding ->
                    BanqueMisrLoginDesign(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChangeLanguage(){
    val currentLanguage = AppCompatDelegate.getApplicationLocales()[0]?.language ?: "en"
    val toggleLanguage = if(currentLanguage == "en") "ar" else "en"

    TextButton(
        onClick = {
            val appLocale = LocaleListCompat.forLanguageTags(toggleLanguage)
            AppCompatDelegate.setApplicationLocales(appLocale)
        },
    ) {
        Text(
            text = stringResource(R.string.lang),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.cairo_bold, FontWeight.Bold)),
            color = BrickRed,
            maxLines = 3
        )
    }
}

@Composable
fun BanqueMisrLoginDesign(modifier: Modifier = Modifier) {

    val items = listOf(
        BanqueItem(R.drawable.our_products, stringResource(R.string.our_products)),
        BanqueItem(R.drawable.exchange_rate, stringResource(R.string.exchange_rate)),
        BanqueItem(R.drawable.security_tips, stringResource(R.string.security_tips)),
        BanqueItem(R.drawable.nearest_branch_or_atm, stringResource(R.string.nearest_branch_or_atm))
    )

    var usernameField by rememberSaveable { mutableStateOf("") }
    var passwordField by rememberSaveable { mutableStateOf("") }
    var passwordShown by rememberSaveable { mutableStateOf(false) }
    var fieldsFilled by rememberSaveable { mutableStateOf(false) }
    fieldsFilled = usernameField.isNotBlank() && passwordField.length >= 6

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp, horizontal = 32.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            Image(
                painter = painterResource(R.drawable.bm_icon),
                contentDescription = "bm_icon",
            )
            ChangeLanguage()
        }
        OutlinedTextField(
            value = usernameField,
            onValueChange = { usernameField = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            label = {Text(text = stringResource(R.string.username))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLabelColor = Grey,
                unfocusedBorderColor = Grey,
                focusedLabelColor = Grey,
                focusedBorderColor = Grey,
            )
        )
        OutlinedTextField(
            value = passwordField,
            onValueChange = { passwordField = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            label = {Text(text = stringResource(R.string.password))},
            trailingIcon = {
                val image = if(passwordShown)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                val description = if(passwordShown)
                    stringResource(R.string.hide_password)
                else
                    stringResource(R.string.show_password)

                IconButton(
                    onClick = {passwordShown = !passwordShown}
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = description
                    )
                }
            },
            visualTransformation = if(passwordShown)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedLabelColor = Grey,
                unfocusedBorderColor = Grey,
                focusedLabelColor = Grey,
                focusedBorderColor = Grey,
            )
        )
        Text(
            text = stringResource(R.string.forgot_username_password),
            modifier = Modifier
                .padding(vertical = 24.dp),
            color = DarkGrey,
            fontSize = 12.sp,
            textDecoration = TextDecoration.Underline
        )
        Button(
            onClick = {
                usernameField = ""
                passwordField = ""
                      },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            enabled = fieldsFilled,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = BrickRed,
                disabledContainerColor = LightRed20,
                disabledContentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.login),
                modifier = Modifier.padding(vertical = 8.dp),
                fontWeight =  FontWeight.Bold
            )
        }
        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.need_help))
                withStyle(
                    style = SpanStyle(
                        color = BrickRed,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline
                    )
                ){
                    append(stringResource(R.string.contact_us))
                }
            },
            fontSize = 12.sp
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 52.dp, bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEach { item ->
                BanqueItemDesign(item, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun BanqueItemDesign(item: BanqueItem, modifier: Modifier = Modifier){
    Column(
        modifier = modifier.padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(item.image),
            contentDescription = item.text,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = item.text,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun BanqueMisrLoginDesignPreview() {
    BanqueMisrTheme {
        BanqueMisrLoginDesign()
    }
}