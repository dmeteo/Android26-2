@file:OptIn(ExperimentalMaterial3Api::class)

package ru.urfu.droidpractice1.content

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.urfu.droidpractice1.R
import ru.urfu.droidpractice1.ui.theme.DroidPractice1Theme

@Composable
fun MainActivityScreen(
    viewModel: MainViewModel,
    onNavigateToSecond: () -> Unit
) {
    val context = LocalContext.current

    val likes = viewModel.likesCount
    val dislikes = viewModel.dislikesCount
    val isRead = viewModel.isRead

    val articleTitle = "«Джеймс Уэбб» нашёл следы воды в атмосфере далёкой экзопланеты"
    val articleItems = listOf(
        "Космический телескоп «Джеймс Уэбб» (JWST) обнаружил водяной пар в атмосфере экзопланеты, удалённой от Земли на десятки световых лет. Это одно из самых детальных наблюдений атмосферы планеты за пределами Солнечной системы.",
        "Наблюдения проводились методом транзитной спектроскопии: когда планета проходит на фоне своей звезды, часть света фильтруется сквозь её атмосферу. По «отпечаткам» в спектре учёные определяют, из каких молекул эта атмосфера состоит.",
        "Помимо воды, в данных нашли намёки на углекислый газ и облака из мелких частиц. Сам по себе водяной пар ещё не означает наличие жизни, но это важный шаг к поиску потенциально обитаемых миров.",
        "Чувствительности «Уэбба» хватает, чтобы изучать атмосферы небольших каменистых планет — раньше такие наблюдения были доступны в основном для огромных газовых гигантов.",
        "Учёные подчёркивают: для подтверждения выводов нужны дополнительные сеансы наблюдений. Но уже сейчас ясно, что телескоп открывает новую главу в изучении далёких планет."
    )

    val fullArticle = buildString {
        appendLine(articleTitle)
        appendLine()
        articleItems.forEachIndexed { index, string ->
            appendLine(string)
            if (index < articleItems.size - 1) appendLine()
        }
    }

    DroidPractice1Theme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.article_title)
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, fullArticle)
                                }
                                context.startActivity(
                                    Intent.createChooser(shareIntent,"Поделиться статьей")
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Поделиться"
                            )
                        }
                    }
                )
            }) { innerPadding ->
            LazyColumn (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = articleTitle,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(0.dp),
                    ) {
                        Button(
                            onClick = {
                                viewModel.like()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.padding(0.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ThumbUp,
                                    contentDescription = "Лайк"
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = likes.toString())
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.dislike()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.padding(0.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ThumbDown,
                                    contentDescription = "Дизлайк"
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = dislikes.toString())
                            }
                        }
                    }
                }

                item {
                    AsyncImage(
                        model = R.drawable.webb,
                        contentDescription = "Космический телескоп «Джеймс Уэбб»",
                        modifier = Modifier.fillMaxSize().height(220.dp),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    )
                }

                items(articleItems) {
                    item -> Text(text = item)
                }

                item {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(8.dp),
                        onClick = {
                            onNavigateToSecond()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isRead) Color.Yellow.copy(alpha=0.5f) else Color.Yellow,
                            contentColor = if (isRead) Color.Black.copy(alpha=0.5f) else Color.Black,
                        )
                    ) {
                        Text(
                            text = "Экзопланета TRAPPIST-1e: близнец Земли?", fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}