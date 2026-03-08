package aughtone.kmp.showcase.kmpshowcase.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import aughtone.kmp.showcase.kmpshowcase.JournalRepository
import aughtone.kmp.showcase.kmpshowcase.Mood
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ListViewModel : ViewModel() {
    private val repository = JournalRepository()

    val entries: StateFlow<List<JournalEntry>> = repository.getEntries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addEntry(title: String, content: String, mood: Mood) {
        repository.addEntry(title, content, mood)
    }
}
