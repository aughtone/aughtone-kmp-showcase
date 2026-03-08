package aughtone.kmp.showcase.kmpshowcase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform