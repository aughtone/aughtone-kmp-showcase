package aughtone.kmp.showcase.kmpshowcase.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import aughtone.kmp.showcase.kmpshowcase.JournalRepository
import aughtone.kmp.showcase.kmpshowcase.Mood
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: JournalRepository
) : ViewModel() {

    val entries: StateFlow<List<JournalEntry>> = repository.getEntries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun refresh() {
        viewModelScope.launch {
            repository.refreshEntries()
        }
    }

    fun addEntry(title: String, content: String, mood: Mood) {
        viewModelScope.launch {
            repository.addEntry(title, content, mood)
                .onFailure { e ->
                    // Here you can expose an error state flow or handle the UI error feedback.
                    println("ViewModel: Failed to add entry: ${e.message}")
                }
        }
    }
}
