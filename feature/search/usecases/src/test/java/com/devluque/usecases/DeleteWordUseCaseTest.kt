package com.devluque.usecases

import com.devluque.data.WordsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteWordUseCaseTest{
    @Test
    fun `invoke calls repository`() = runBlocking {
        val mockRepository = mock<WordsRepository>()
        val useCase = DeleteWordUseCase(mockRepository)

        useCase("word")

        verify(mockRepository).deleteWord("word")
    }
}