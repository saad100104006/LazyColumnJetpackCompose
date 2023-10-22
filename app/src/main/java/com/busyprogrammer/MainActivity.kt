package com.busyprogrammer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.busyprogrammer.R
import com.busyprogrammer.ui.theme.Green
import com.busyprogrammer.ui.theme.LazyColumnTheme
import com.busyprogrammer.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LazyColumnTheme {
                LazyColumnExample(persons)
            }
        }
    }
}

@Composable
fun CustomItem(person: Person) {
    val expanded = remember { mutableStateOf(false) }

    Surface(
        color = White,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Row {
                Image(
                    painter = painterResource(person.avatar),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(74.dp)
                        .padding( 10.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = person.names, style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )

                    Text(text = person.occupations)
                }

                Image(
                    painter = if (expanded.value) {
                        painterResource(id = R.drawable.baseline_expand_less_24)
                    } else {
                        painterResource(id = R.drawable.baseline_expand_more_24)
                    },
                    contentDescription = "",
                    modifier = Modifier.padding(top = 10.dp).clickable {
                        expanded.value = !expanded.value
                    })
            }

            if (expanded.value) {
                Column{
                    Text(text =  person.occupationDetails)
                }
            }
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyColumnExample(categories: List<Person>) {
    val previousCategory = remember { mutableStateOf("") }
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        categories.forEach { person->
            if(person.category != previousCategory.value) {
                stickyHeader {
                    StickyHeader(person.category)
                }
            }

            previousCategory.value = person.category

            item{
                CustomItem(person)
            }
        }

    }
}

@Composable
private fun StickyHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .background(Green)
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LazyColumnTheme {
        LazyColumnExample(persons)
    }
}