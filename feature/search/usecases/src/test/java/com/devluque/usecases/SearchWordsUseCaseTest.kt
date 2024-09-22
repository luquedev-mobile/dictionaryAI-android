package com.devluque.usecases

import com.devluque.entities.Word
import com.devluque.sampleWords
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class SearchWordsUseCaseTest{
    @Test
    fun `invoke calls repository`() {
        val wordFlow = flowOf(sampleWords("t","f","y"))
        val useCase = SearchWordsUseCase(
            mock {
                on { searchWords("") } doReturn wordFlow
            }
        )

        val result = useCase("")

        assertEquals(wordFlow, result)
    }
}