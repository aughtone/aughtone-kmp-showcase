package aughtone.kmp.showcase.kmpshowcase.database

import androidx.datastore.core.okio.OkioSerializer
import aughtone.kmp.showcase.kmpshowcase.JournalEntryDto
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.use

object JournalEntrySerializer : OkioSerializer<List<JournalEntryDto>> {
    override val defaultValue: List<JournalEntryDto> = emptyList()

    override suspend fun readFrom(source: BufferedSource): List<JournalEntryDto> {
        return try {
            Json.decodeFromString(source.readUtf8())
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: List<JournalEntryDto>, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(Json.encodeToString(t))
        }
    }
}
