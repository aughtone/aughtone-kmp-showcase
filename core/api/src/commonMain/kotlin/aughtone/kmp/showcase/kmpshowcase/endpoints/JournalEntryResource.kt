package aughtone.kmp.showcase.kmpshowcase.endpoints

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/journal-entries")
class JournalEntryResource {

    /**
     * Represents a specific journal entry by its ID.
     * Use this for GET, PUT, or DELETE requests for a single entry.
     */
    @Serializable
    @Resource("{id}")
    class Id(
        val parent: JournalEntryResource = JournalEntryResource(),
        val id: String
    )
}
