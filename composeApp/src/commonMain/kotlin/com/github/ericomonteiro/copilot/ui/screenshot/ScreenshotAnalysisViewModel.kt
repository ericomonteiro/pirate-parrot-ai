package com.github.ericomonteiro.copilot.ui.screenshot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ericomonteiro.copilot.ai.AIService
import com.github.ericomonteiro.copilot.ai.SolutionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ScreenshotAnalysisState(
    val isLoading: Boolean = false,
    val solution: SolutionResponse? = null,
    val error: String? = null,
    val selectedLanguage: String = "Kotlin",
    val screenshotBase64: String? = null
)

class ScreenshotAnalysisViewModel(
    private val aiService: AIService,
    private val screenshotBase64: String
) : ViewModel() {
    
    private val _state = MutableStateFlow(ScreenshotAnalysisState(screenshotBase64 = screenshotBase64))
    val state: StateFlow<ScreenshotAnalysisState> = _state.asStateFlow()
    
    init {
        analyzeCodingChallenge()
    }
    
    fun selectLanguage(language: String) {
        _state.value = _state.value.copy(selectedLanguage = language)
        analyzeCodingChallenge()
    }
    
    fun retry() {
        analyzeCodingChallenge()
    }
    
    private fun analyzeCodingChallenge() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            val result = aiService.analyzeCodingChallenge(
                imageBase64 = screenshotBase64,
                language = _state.value.selectedLanguage
            )
            
            result.fold(
                onSuccess = { solution ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        solution = solution,
                        error = null
                    )
                },
                onFailure = { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }
}
