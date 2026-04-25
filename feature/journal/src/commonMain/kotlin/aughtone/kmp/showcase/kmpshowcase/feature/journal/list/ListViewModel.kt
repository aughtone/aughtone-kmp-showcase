package aughtone.kmp.showcase.kmpshowcase.feature.journal.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aughtone.kmp.showcase.kmpshowcase.domain.model.JournalEntry
import aughtone.kmp.showcase.kmpshowcase.domain.model.Mood
import aughtone.kmp.showcase.kmpshowcase.domain.repository.JournalRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: JournalRepository
) : ViewModel() {

    val uiState: StateFlow<ListUiState> = repository.getEntries()
        .map { entries ->
            ListUiState(
                entries = entries.map { entry -> entry.toUiModel() }
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ListUiState(emptyList())
        )

    fun onEvent(event: ListUiEvent) {
        when (event) {
            is ListUiEvent.AddEntry -> addEntry(event.title, event.content, event.mood)
            is ListUiEvent.Refresh -> refresh()
        }
    }

    private fun addEntry(title: String, content: String, mood: Mood) {
        viewModelScope.launch {
            repository.addEntry(title, content, mood)
                .onFailure { e ->
                    // Here you can expose an error state flow or handle the UI error feedback.
                    println("ViewModel: Failed to add entry: ${e.message}")
                }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            repository.refreshEntries()
        }
    }
}

private fun JournalEntry.toUiModel() = ListUiState.Entry(
    id = id,
    title = title,
    date = date.toString(),
    content = content,
    mood = mood
)
