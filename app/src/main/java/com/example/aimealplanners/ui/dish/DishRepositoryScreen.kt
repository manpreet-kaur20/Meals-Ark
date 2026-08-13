package com.example.aimealplanners.ui.dish

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aimealplanners.domain.model.Dish

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishRepositoryScreen(
    viewModel: DishViewModel
) {
    val dishes by viewModel.dishes.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add Dish")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dishes) { dish ->
                DishItem(dish = dish)
            }
        }

        if (showAddDialog) {
            AddDishDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, category, memo, url ->
                    viewModel.addDish(name, category, memo, url)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun DishItem(dish: Dish) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = dish.name, style = MaterialTheme.typography.titleLarge)
            Text(text = dish.category, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
            if (dish.memo.isNotEmpty()) {
                Text(text = dish.memo, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDishDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var memo by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Dish") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") })
                OutlinedTextField(value = memo, onValueChange = { memo = it }, label = { Text("Memo") })
                OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("URL") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(name, category, memo, url) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
