package aughtone.kmp.showcase.kmpshowcase.feature.journal.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import aughtone.kmp.showcase.kmpshowcase.design.theme.ShowcaseTheme
import aughtone.kmp.showcase.kmpshowcase.domain.model.Mood
import coil3.compose.AsyncImage
import kmpshowcase.feature.journal.generated.resources.Res
import kmpshowcase.feature.journal.generated.resources.action_add_entry
import kmpshowcase.feature.journal.generated.resources.action_refresh
import kmpshowcase.feature.journal.generated.resources.app_name
import kmpshowcase.feature.journal.generated.resources.button_cancel
import kmpshowcase.feature.journal.generated.resources.dialog_new_entry_title
import kmpshowcase.feature.journal.generated.resources.image_desc_nature
import kmpshowcase.feature.journal.generated.resources.label_content
import kmpshowcase.feature.journal.generated.resources.label_mood
import kmpshowcase.feature.journal.generated.resources.label_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, KoinExperimentalAPI::class)
@Composable
fun ListScreen(
    onEntryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ListContent(
        uiState = uiState,
        onEntryClick = onEntryClick,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListContent(
    uiState: ListUiState,
    onEntryClick: (String) -> Unit,
    onEvent: (ListUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(Res.string.app_name)) },
                actions = {
                    IconButton(onClick = { onEvent(ListUiEvent.Refresh) }) {
                        Icon(Icons.Default.Refresh, contentDescription = stringResource(Res.string.action_refresh))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = Color.Unspecified,
                    navigationIconContentColor = Color.Unspecified,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = Color.Unspecified
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(Res.string.action_add_entry))
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                HeaderImage(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 16.dp)
                )
            }
            items(uiState.entries) { entry ->
                EntryItem(
                    entry = entry,
                    Modifier
                        .fillMaxWidth()
                        .clickable { onEntryClick(entry.id) }
                )
            }
        }

        if (showAddDialog) {
            AddEntryDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { title, content, mood ->
                    onEvent(ListUiEvent.AddEntry(title, content, mood))
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
private fun HeaderImage(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
        if (LocalInspectionMode.current) {
            PlaceholderImage(modifier = Modifier.fillMaxSize())
        } else {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?q=80&w=2560&auto=format&fit=crop",
                contentDescription = stringResource(Res.string.image_desc_nature),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun PlaceholderImage(modifier: Modifier) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text("Header Image Placeholder")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddEntryDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Mood) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var mood by remember { mutableStateOf(Mood.HAPPY) }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(Res.string.dialog_new_entry_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(Res.string.label_title)) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text(stringResource(Res.string.label_content)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = mood.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(Res.string.label_mood)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        Mood.entries.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption.toString()) },
                                onClick = {
                                    mood = selectionOption
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(title, content, mood) }) {
                Text(stringResource(Res.string.action_add_entry))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.button_cancel))
            }
        }
    )
}

@Preview(showBackground = true, locale = "en")
@Composable
private fun ListScreenPreview() {
    ShowcaseTheme {
        ListContent(
            uiState = ListUiState(
                entries = listOf(
                    ListUiState.Entry(
                        id = "1",
                        title = "A Great Day",
                        date = "2023/10/27",
                        content = "Today was an amazing day! I went for a walk and saw some beautiful trees.",
                        mood = Mood.HAPPY
                    ),
                    ListUiState.Entry(
                        id = "2",
                        title = "Feeling Calm",
                        date = "2023/10/27",
                        content = "Spent the evening reading a book. It was very relaxing.",
                        mood = Mood.CALM
                    )
                )
            ),
            onEntryClick = {},
            onEvent = {}
        )
    }
}
