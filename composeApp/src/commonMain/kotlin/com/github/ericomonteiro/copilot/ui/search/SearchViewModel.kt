package com.github.ericomonteiro.copilot.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ericomonteiro.copilot.data.repository.ProblemRepository
import com.github.ericomonteiro.copilot.db.Problem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: ProblemRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()
    
    init {
        loadProblems()
    }
    
    fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }
        searchProblems(query)
    }
    
    private fun searchProblems(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val results = if (query.isBlank()) {
                    repository.getAllProblems()
                } else {
                    repository.searchProblems(query)
                }
                _state.update { it.copy(results = results, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
    
    private fun loadProblems() {
        searchProblems("")
    }
}

data class SearchState(
    val query: String = "",
    val results: List<Problem> = emptyList(),
    val isLoading: Boolean = false
)
