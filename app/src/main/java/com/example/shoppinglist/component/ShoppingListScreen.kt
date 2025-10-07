package com.example.shoppinglist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingListScreen() {
    // State untuk input item baru
    var newItem by remember { mutableStateOf("") }

    // State untuk pencarian
    var searchQuery by remember { mutableStateOf("") }

    // State untuk daftar item
    var items by remember { mutableStateOf(listOf<String>()) }

    // Filter item sesuai pencarian
    val filteredItems = items.filter {
        it.contains(searchQuery, ignoreCase = true)
    }

    // Layout utama
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Input tambah item
        ItemInput(
            text = newItem,
            onTextChange = { newItem = it },
            onAddItem = {
                if (newItem.isNotBlank()) {
                    items = items + newItem
                    newItem = ""
                }
            }
        )

        // Kolom pencarian
        SearchInput(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )

        // Daftar item belanja (tanpa dummy data!)
        ShoppingList(filteredItems)
    }
}
