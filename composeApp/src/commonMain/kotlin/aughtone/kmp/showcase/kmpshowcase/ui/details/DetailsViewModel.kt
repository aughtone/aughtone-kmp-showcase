package aughtone.kmp.showcase.kmpshowcase.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aughtone.kmp.showcase.kmpshowcase.domain.JournalRepository
import kotlinx.coroutines.flow.*

class DetailsViewModel(
    private val id: String,
    repository: JournalRepository
) : ViewModel() {

    val uiState: StateFlow<DetailsUiState> = repository.getEntry(id)
        .filterNotNull()
        .map { entry ->
            DetailsUiState(
                id = entry.id,
                title = entry.title,
                date = entry.date.toString(),
                content = entry.content,
                mood = entry.mood,
                isLoading = false
            )
        }
        .onStart { emit(DetailsUiState(isLoading = true)) }
        .catch { e -> emit(DetailsUiState(error = e.message)) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DetailsUiState(isLoading = true)
        )
}