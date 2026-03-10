package aughtone.kmp.showcase.kmpshowcase.database

import androidx.datastore.core.okio.OkioSerializer
import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.use

object JournalEntrySerializer : OkioSerializer<List<JournalEntry>> {
    override val defaultValue: List<JournalEntry> = emptyList()

    override suspend fun readFrom(source: BufferedSource): List<JournalEntry> {
        return try {
            Json.decodeFromString(source.readUtf8())
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: List<JournalEntry>, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(Json.encodeToString(t))
        }
    }
}
