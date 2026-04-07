package com.example.a514lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class Part2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactListScreen()
                }
            }
        }
    }
}

class ContactViewModel : ViewModel() {
    // 1. Initial State Definition
    private val _contacts = mutableStateListOf<String>()
    val contacts: List<String> = _contacts

    var isLoading by mutableStateOf(false)
        private set
    
    private var currentPage = 0
    private val alphabet = ('A'..'Z').toList()

    init {
        loadMoreContacts() // initial load
    }

    fun loadMoreContacts() {
        if (isLoading) return
        if (currentPage >= alphabet.size) return // End of mock data

        isLoading = true
        // 3. Simulate delay
        viewModelScope.launch {
            delay(2000) 
            
            // Mock fetching contacts for the current letter
            val letter = alphabet[currentPage]
            val newContacts = (1..10).map { "$letter Contact $it" }
            _contacts.addAll(newContacts)
            
            currentPage++
            isLoading = false
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListScreen(viewModel: ContactViewModel = viewModel()) {
    val contacts = viewModel.contacts
    val isLoading = viewModel.isLoading
    
    // Group contacts by their first letter
    val groupedContacts = contacts.groupBy { it.first().toString() }

    val listState = rememberLazyListState()

    // 3. Trigger pagination when reaching the end
    LaunchedEffect(listState) {
        snapshotFlow { 
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            
            // Check if user has scrolled to the bottom (last visible item == totalItems - 1)
            totalItems > 0 && lastVisibleItemIndex >= totalItems - 1
        }
        .distinctUntilChanged()
        .filter { isAtEnd -> isAtEnd }
        .collect {
            viewModel.loadMoreContacts()
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        groupedContacts.forEach { (firstLetter, contactsForLetter) ->
            // 2. Sticky Header
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = firstLetter,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            items(contactsForLetter) { contact ->
                Text(
                    text = contact,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
                HorizontalDivider(color = Color.LightGray, thickness = 0.5f.dp)
            }
        }

        // 4. CircularProgressIndicator at the bottom
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
