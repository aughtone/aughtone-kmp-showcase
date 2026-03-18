package aughtone.kmp.showcase.kmpshowcase.database

import androidx.datastore.core.okio.OkioSerializer
import aughtone.kmp.showcase.kmpshowcase.database.model.JournalEntryEntity
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.use

object JournalEntrySerializer : OkioSerializer<List<JournalEntryEntity>> {
    override val defaultValue: List<JournalEntryEntity> = emptyList()

    override suspend fun readFrom(source: BufferedSource): List<JournalEntryEntity> {
        return try {
            Json.decodeFromString(source.readUtf8())
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: List<JournalEntryEntity>, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(Json.encodeToString(t))
        }
    }
}
