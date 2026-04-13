package aughtone.kmp.showcase.kmpshowcase.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import aughtone.kmp.showcase.kmpshowcase.Mood
import coil3.compose.AsyncImage
import kmpshowcase.composeapp.generated.resources.Res
import kmpshowcase.composeapp.generated.resources.action_add_entry
import kmpshowcase.composeapp.generated.resources.action_refresh
import kmpshowcase.composeapp.generated.resources.app_name
import kmpshowcase.composeapp.generated.resources.button_cancel
import kmpshowcase.composeapp.generated.resources.dialog_new_entry_title
import kmpshowcase.composeapp.generated.resources.image_desc_nature
import kmpshowcase.composeapp.generated.resources.label_content
import kmpshowcase.composeapp.generated.resources.label_mood
import kmpshowcase.composeapp.generated.resources.label_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = koinViewModel()
) {
    val entries by viewModel.entries.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(Res.string.app_name)) },
                actions = {
                    IconButton(onClick = { viewModel.refresh() }) {
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
                HeaderImage()
            }
            items(entries) { entry ->
                EntryItem(entry)
            }
        }

        if (showAddDialog) {
            AddEntryDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { title, content, mood ->
                    viewModel.addEntry(title, content, mood)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun HeaderImage() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(bottom = 16.dp),
        shape = MaterialTheme.shapes.large
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?q=80&w=2560&auto=format&fit=crop",
            contentDescription = stringResource(Res.string.image_desc_nature),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryDialog(
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
                        modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable).fillMaxWidth()
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
