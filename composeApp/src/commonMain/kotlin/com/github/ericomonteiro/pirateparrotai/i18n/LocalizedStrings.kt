package com.github.ericomonteiro.pirateparrotai.i18n

import androidx.compose.runtime.*

private val LocalStrings = compositionLocalOf<StringResources> { EnglishStrings }

object LocalizedStrings {
    val current: StringResources
        @Composable
        get() = LocalStrings.current
    
    fun getStrings(language: AppLanguage): StringResources {
        return when (language) {
            AppLanguage.ENGLISH -> EnglishStrings
            AppLanguage.PORTUGUESE_BR -> PortugueseBRStrings
            AppLanguage.SPANISH -> SpanishStrings
        }
    }
}

@Composable
fun ProvideStrings(
    language: AppLanguage,
    content: @Composable () -> Unit
) {
    val strings = LocalizedStrings.getStrings(language)
    CompositionLocalProvider(LocalStrings provides strings) {
        content()
    }
}

@Composable
fun strings(): StringResources = LocalizedStrings.current
