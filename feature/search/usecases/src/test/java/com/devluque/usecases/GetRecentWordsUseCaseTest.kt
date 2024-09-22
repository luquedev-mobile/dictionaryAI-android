package com.devluque.usecases

import com.devluque.sampleWords
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetRecentWordsUseCaseTest{
    @Test
    fun `invoke calls repository`() {
        val wordFlow = flowOf(sampleWords("y"))
        val useCase = GetRecentWordsUseCase(
            mock {
                on { getRecentWords } doReturn wordFlow
            }
        )

        val result = useCase()

        assertEquals(wordFlow, result)
    }
}