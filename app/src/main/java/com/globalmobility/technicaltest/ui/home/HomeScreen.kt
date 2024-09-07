package com.globalmobility.technicaltest.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.globalmobility.technicaltest.R
import com.globalmobility.technicaltest.model.Characters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * @author Joaquin Espinoza
 *
 * @param state que pertenece a HomeState
 * @param paddingValues
 *
 */
@Composable
fun ScreenHome(state: HomeState, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        Row(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "The Rick and Morty APP",
                color = Color.Black,
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.hey_comic_font)),
            )
        }
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.background))
        ) {
            AnimatedVisibility(visible = state.loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    strokeCap = StrokeCap.Round,
                    color = Color.White
                )
                LoadingText()
            }
            AnimatedVisibility(visible = state.error) {
                ErrorText(smsError = state.smsError)
            }
            AnimatedVisibility(visible = state.success) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.characters) { character ->
                        ItemCharacter(characters = character)
                    }
                }
            }

        }
    }
}

/**
 * Muestra el mensaje de error del state
 */
@Composable
fun ErrorText(smsError: String) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Text(
            text = "Ha ocurrido un error!, Por favor intenta mas tarde",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.hey_comic_font))
        )
        Text(
            text = smsError,
            color = Color.Gray,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.hey_comic_font))
        )


    }
}

/**
 * Realiza una sumatoria de un +1 en un intervalo de 500 milisegundos
 * en donde cada que suma un +1 pasa como parametro a la funcion repeat que repite
 * el string en este caso el .
 */
//Loading text animation
@Composable
fun LoadingText() {
    var dotsCount by remember { mutableIntStateOf(0) }
    val maxDots = 4

    LaunchedEffect(key1 = dotsCount) {
        delay(500L)
        dotsCount = (dotsCount + 1)
        if (dotsCount > maxDots) {
            dotsCount = 0
        }
    }
    val text = "Cargando" + ".".repeat(dotsCount)

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.hey_comic_font))
        )
    }

}

/**\
 * Recibe un objeto de tipo Character y distribulle la informacion necesaria para su visualizacion
 * @author Joaquin Espinoza
 * @param characters
 */
//CardView with Glide image
@Composable
fun ItemCharacter(characters: Characters) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.backgroundCard))
            .padding(10.dp)
            .height(180.dp), shape = ShapeDefaults.Medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(0.5f)) {
                if (characters.image.isEmpty()) {
                    Image(
                        painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "image",
                        Modifier
                            .background(Color.Black)
                            .fillMaxSize(),
                        alignment = Alignment.Center
                    )
                } else {
                    GlideImage(
                        characters.image, modifier = Modifier
                            .background(Color.Black)
                            .fillMaxSize(),
                        alignment = Alignment.Center
                    )
                }

            }
            Spacer(modifier = Modifier.size(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = characters.name,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.hey_comic_font))
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    SmallCircle(status = characters.status)
                    Text(
                        text = characters.status,
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "-",
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = characters.species,
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }
                Text(
                    text = "Ultima ubicación actual",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(top = 15.dp)
                )

                Text(
                    text = characters.location.name,
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Visto por primera vez en",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(top = 15.dp)
                )

                Text(
                    text = characters.origin.name,
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )

            }

        }
    }
}

/**
 * Valida si el character tiene como estado Alive el circulo se
 * mostrara en verde de lo contrario en gris
 */
@Composable
fun SmallCircle(status: String) {
    val color = if (status == "Alive") {
        Color.Green
    } else {
        Color.Gray
    }
    Canvas(
        modifier = Modifier
            .size(15.dp)
            .padding(top = 5.dp)
    ) {
        drawCircle(
            color = color,
            radius = size.minDimension / 2
        )
    }
}

/**
 *
 * Carga la imagen en un hilo secundario y muestra un CircularProgressIndicator mientras se carga
 * usando corrutinas y la libreria glide para el consumo http de imaenes
 *
 * @author Joaquin Espinoza
 *
 * @param imageUrl url de la imagen en tipo string
 * @param modifier modificador para el composable
 * @param alignment alineacion de la imagen
 *
 */
@Composable
fun GlideImage(
    imageUrl: String,
    modifier: Modifier,
    alignment: Alignment
) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(imageUrl) {
        withContext(Dispatchers.IO) {
            try {
                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .submit()
                    .get()
                imageBitmap = bitmap.asImageBitmap()
                isLoading = false
            } catch (e: Exception) {
                isLoading = false
                // Manejo de errores
            }
        }
    }
    Box(modifier = modifier) {
        if (isLoading) {
            CircularProgressIndicator(modifier = modifier.align(alignment))
        } else {
            imageBitmap?.let { img ->
                Image(
                    bitmap = img,
                    contentDescription = "image",
                    modifier = modifier,
                    alignment = alignment,
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }
        }
    }
}

