package com.example.taskmanagement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.example.taskmanagement.ui.theme.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaskManagementTheme {
        HomeScreen()
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel = viewModel()) {
    val plusIcon = painterResource(id = R.drawable.add)
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.openPopup() }) {
                IconHolder(painter = plusIcon, contentDesc = "A big plus")
            }
        }
    ) {
        Box(modifier = Modifier
            .background(DeepDark)
            .fillMaxSize()
        ) {
            Column {
                Header()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        Greeting()
                        Search()
                        Progress()
                        Tasks()
                        if (viewModel.popup) {
                            CreateTaskDialog()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Header() {
    val menuIcon = painterResource(id = R.drawable.menu)
    val bellIcon = painterResource(id = R.drawable.bell)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        IconHolder(painter = menuIcon, contentDesc = "The menu icon")
        Text(
            text = "Today's Agenda",
            color = Color.White,
            fontSize = 5.em
        )
        IconHolder(painter = bellIcon, contentDesc = "The bell icon")
    }
}

@Composable
fun Greeting(name: String = "Android") {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Hi, $name!",
            fontSize = 3.em,
            color = Color.White
        )
        Text(
            text = "Be productive today",
            fontSize = 5.em,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun Search() {
    val searchIcon = painterResource(id = R.drawable.search)
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = {text = it},
        placeholder = { Text(text = "Search tasks", color = Color.White, fontSize = 4.em)},
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        trailingIcon = { IconHolder(painter = searchIcon, contentDesc = "Search icon", height = 24) },
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            textColor = Color.White,
            backgroundColor = SoftDark
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    )
}

@Composable
fun Progress(viewModel: MainViewModel = viewModel()) {
    val month = "September"
    val day = 3

    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .background(SoftDark)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Task Progress",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 5.em
                )
                Text(
                    text = "${viewModel.numberOfCompleteTasks.toInt()}/${viewModel.numberOfTasks.toInt()} tasks done",
                    color = Color.LightGray,
                    fontSize = 3.6.em
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(SoftBlue)
                ) {
                    Text(
                        text = "$month $day",
                        color = Color.White,
                        modifier = Modifier
                            .padding(6.dp)
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = 1f,
                    strokeWidth = 8.dp,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .size(80.dp)
                )
                CircularProgressIndicator(
                    progress = viewModel.progress,
                    strokeWidth = 8.dp,
                    color = SoftBlue,
                    modifier = Modifier
                        .size(80.dp)
                )
                Text(
                    text = "${viewModel.progressPercent}%",
                    fontSize = 5.em,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

        }
    }
}

@Composable
fun Tasks(viewModel: MainViewModel = viewModel()) {
    Column (
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        viewModel.tasks.forEach{
            TaskComposable(it.name, it.desc, it.complete, { viewModel.completeTask(it.id) })
        }
    }
}

@Composable
fun TaskComposable(title: String, _desc: String, complete: Boolean, onClick: () -> Unit) {
    val checkIcon = painterResource(id = R.drawable.check)

    var desc by remember { mutableStateOf("") }
    if (_desc.length > 34) {
        desc = _desc.slice(0..31) + "..."
    } else {
        desc = _desc
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SoftDark)
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 5.em,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = desc,
                    color = Color.LightGray,
                    fontSize = 3.em
                )
            }
            Button(
                onClick = onClick,
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = SoftBlue
                ),
                modifier = Modifier
                    .size(40.dp)
            ) {
                if (complete) {
                    IconHolder(painter = checkIcon, contentDesc = "A checkmark", height = 24)
                }
            }
        }
    }
}

@Composable
fun CreateTaskDialog(viewModel: MainViewModel = viewModel()) {
    Dialog(
        onDismissRequest = { viewModel.closePopup() }
    ) {
        var title by remember { mutableStateOf("") }
        var desc by remember { mutableStateOf("") }

        Box(modifier = Modifier
            .background(DeepDark)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .padding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text(text = "Title", color = Color.White, fontSize = 4.em)},
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        textColor = Color.White,
                        backgroundColor = SoftDark
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
                TextField(
                    value = desc,
                    onValueChange = { desc = it },
                    placeholder = { Text(text = "Description", color = Color.White, fontSize = 4.em)},
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        textColor = Color.White,
                        backgroundColor = SoftDark
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(112.dp)
                )
                Button(
                    onClick = { createTask(title, desc, { viewModel.createTask(title, desc) }) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = "Create task",
                        color = Color.White,
                        fontSize = 4.em
                    )
                }
            }
        }
    }
}