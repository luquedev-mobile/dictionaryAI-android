package com.devluque.usecases

import com.devluque.domain.Result
import com.devluque.entities.RemoteWordDetailRequest
import com.devluque.entities.WordDetailItem
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FetchGenerateWordDetailUseCaseTest {
    @Test
    fun `invoke calls repository`() {
        //GIVEN
        val remoteWordDetailRequest = mock<RemoteWordDetailRequest>()
        val resultSuccess = mock<Result.Success<WordDetailItem>>()
        val resultFlow = flowOf(resultSuccess)
        val useCase = FetchGenerateWordDetailUseCase(mock {
            on { generateWordDetail(remoteWordDetailRequest) } doReturn resultFlow
        })

        //WHEN
        val result = useCase(remoteWordDetailRequest)

        //THEN
        assertEquals(resultFlow, result)
    }
}