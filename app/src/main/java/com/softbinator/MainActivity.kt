package com.softbinator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.softbinator.network.RetrofitBuilder
import com.softbinator.network.data.Animal
import com.softbinator.ui.theme.SoftbinatorTheme
import io.reactivex.rxjava3.kotlin.subscribeBy

class MainActivity : ComponentActivity() {

    private val viewModel = HomeViewModel(
        GetAnimalsUseCase(
            AnimalRepositoryImpl(
                AnimalRemoteDataSource(RetrofitBuilder.petFinderApi)
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SoftbinatorTheme {
                var text by remember {
                    mutableStateOf("")
                }

                /*val response = RetrofitBuilder.petFinderApi.getAnimals()
                    .subscribeBy(
                        onSuccess = { response ->
                            val count = response.animals.stream().count()
                            Log.d("ababa", "onCreate: $count")
                            text = response.animals.stream().map { it.name }.findFirst().get()
                        },
                        onError = { Log.e("ababa", "onCreate: ", it) }
                    )*/

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(text)
                    val moviePagingItems: LazyPagingItems<Animal> =
                        viewModel.moviesState.collectAsLazyPagingItems()
                    LazyColumn(modifier = Modifier.padding()) {
                        items(moviePagingItems.itemCount) { index ->
                            Text(
                                modifier = Modifier.height(50.dp),
                                text = moviePagingItems[index]?.name ?: "unknown",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        moviePagingItems.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
//                                    item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                                }

                                loadState.refresh is LoadState.Error -> {
                                    val error =
                                        moviePagingItems.loadState.refresh as LoadState.Error
                                    item {
                                        /*ErrorMessage(
                                            modifier = Modifier.fillParentMaxSize(),
                                            message = error.error.localizedMessage!!,
                                            onClickRetry = { retry() })*/
                                    }
                                }

                                loadState.append is LoadState.Loading -> {
//                                    item { LoadingNextPageItem(modifier = Modifier) }
                                }

                                loadState.append is LoadState.Error -> {
                                    val error = moviePagingItems.loadState.append as LoadState.Error
                                    /*item {
                                        ErrorMessage(
                                            modifier = Modifier,
                                            message = error.error.localizedMessage!!,
                                            onClickRetry = { retry() })
                                    }*/
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SoftbinatorTheme {
        Greeting("Android")
    }
}