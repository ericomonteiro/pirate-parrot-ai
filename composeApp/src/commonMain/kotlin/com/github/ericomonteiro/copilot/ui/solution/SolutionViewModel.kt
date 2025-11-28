package com.github.ericomonteiro.copilot.ui.solution

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ericomonteiro.copilot.ai.AIService
import com.github.ericomonteiro.copilot.data.repository.ProblemRepository
import com.github.ericomonteiro.copilot.db.Problem
import com.github.ericomonteiro.copilot.db.Solution
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SolutionViewModel(
    private val problemId: Long,
    private val repository: ProblemRepository,
    private val aiService: AIService
) : ViewModel() {
    
    private val _state = MutableStateFlow(SolutionState())
    val state: StateFlow<SolutionState> = _state.asStateFlow()
    
    init {
        loadProblem()
    }
    
    private fun loadProblem() {
        viewModelScope.launch {
            try {
                val problem = repository.getProblemById(problemId)
                _state.update { it.copy(problem = problem) }
                if (problem != null) {
                    loadOrGenerateSolution()
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message ?: "Failed to load problem") }
            }
        }
    }
    
    private fun loadOrGenerateSolution() {
        viewModelScope.launch {
            val problem = _state.value.problem ?: return@launch
            val language = _state.value.selectedLanguage
            
            // Try to load from database first
            val cached = repository.getSolution(problem.id, language)
            if (cached != null) {
                _state.update { it.copy(solution = cached, isLoading = false) }
                return@launch
            }
            
            // Generate new solution
            _state.update { it.copy(isLoading = true, error = null) }
            
            aiService.generateSolution(problem.description, language)
                .onSuccess { response ->
                    // Save to database
                    repository.insertSolution(
                        problemId = problem.id,
                        language = language,
                        code = response.code,
                        explanation = response.explanation,
                        timeComplexity = response.timeComplexity,
                        spaceComplexity = response.spaceComplexity
                    )
                    
                    // Load from database to get the ID
                    val solution = repository.getSolution(problem.id, language)
                    _state.update { it.copy(solution = solution, isLoading = false) }
                }
                .onFailure { error ->
                    _state.update { 
                        it.copy(
                            error = error.message ?: "Failed to generate solution",
                            isLoading = false
                        )
                    }
                }
        }
    }
    
    fun selectLanguage(language: String) {
        _state.update { it.copy(selectedLanguage = language, solution = null) }
        loadOrGenerateSolution()
    }
    
    fun retry() {
        _state.update { it.copy(error = null) }
        loadOrGenerateSolution()
    }
}

data class SolutionState(
    val problem: Problem? = null,
    val solution: Solution? = null,
    val selectedLanguage: String = "Kotlin",
    val isLoading: Boolean = false,
    val error: String? = null
)
