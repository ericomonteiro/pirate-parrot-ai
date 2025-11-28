package com.github.ericomonteiro.copilot.data.repository

import com.github.ericomonteiro.copilot.db.Database
import com.github.ericomonteiro.copilot.db.Problem
import com.github.ericomonteiro.copilot.db.Solution
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class ProblemRepository(private val database: Database) {
    private val queries = database.databaseQueries
    
    suspend fun searchProblems(query: String): List<Problem> = withContext(Dispatchers.IO) {
        queries.searchProblems(query).executeAsList()
    }
    
    suspend fun getAllProblems(): List<Problem> = withContext(Dispatchers.IO) {
        queries.selectAllProblems().executeAsList()
    }
    
    suspend fun getProblemById(id: Long): Problem? = withContext(Dispatchers.IO) {
        queries.selectProblemById(id).executeAsOneOrNull()
    }
    
    suspend fun getSolution(problemId: Long, language: String): Solution? = 
        withContext(Dispatchers.IO) {
            queries.selectSolutionByProblemAndLanguage(problemId, language).executeAsOneOrNull()
        }
    
    suspend fun insertProblem(
        platform: String,
        problemName: String,
        problemNumber: Long?,
        difficulty: String,
        description: String,
        createdAt: Long
    ) = withContext(Dispatchers.IO) {
        queries.insertProblem(
            platform,
            problemName,
            problemNumber,
            difficulty,
            description,
            createdAt
        )
    }
    
    suspend fun insertSolution(
        problemId: Long,
        language: String,
        code: String,
        explanation: String,
        timeComplexity: String,
        spaceComplexity: String
    ) = withContext(Dispatchers.IO) {
        queries.insertSolution(
            problemId,
            language,
            code,
            explanation,
            timeComplexity,
            spaceComplexity
        )
    }
    
    suspend fun getSetting(key: String): String? = withContext(Dispatchers.IO) {
        queries.getSetting(key).executeAsOneOrNull()
    }
    
    suspend fun setSetting(key: String, value: String) = withContext(Dispatchers.IO) {
        queries.setSetting(key, value)
    }
}
