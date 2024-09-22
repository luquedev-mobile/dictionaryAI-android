package com.devluque.data

import com.devluque.data.datasource.AiRemoteDataSource
import com.devluque.domain.Result
import com.devluque.entities.RemoteWordDetailRequest
import com.devluque.entities.WordDetailItem
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class AiRepositoryTest {
    @Mock
    private lateinit var remoteDataSource: AiRemoteDataSource

    @Mock
    private lateinit var remoteRequest: RemoteWordDetailRequest

    @Mock
    private lateinit var wordDetailItem: WordDetailItem

    private lateinit var repository: AiRepository

    @Before
    fun setUp() {
        repository = AiRepository(remoteDataSource)
    }

    @Test
    fun `generateWordDetail returns success when datasource succeeds`(): Unit = runBlocking {
        val successResponse = flowOf(Result.Success(wordDetailItem))
        whenever(remoteDataSource.generateWordDetail(remoteRequest)).thenReturn(successResponse)

        val result = repository.generateWordDetail(remoteRequest)

        assertEquals(Result.Success(wordDetailItem), result.first())
        verify(remoteDataSource).generateWordDetail(remoteRequest)
    }

    @Test
    fun `generateWordDetail returns error when datasource fails`(): Unit = runBlocking {
        val errorResponse = flowOf(Result.Error(Exception("Error")))
        whenever(remoteDataSource.generateWordDetail(remoteRequest)).thenReturn(errorResponse)

        val result = repository.generateWordDetail(remoteRequest)

        assertTrue(result.first() is Result.Error)
        verify(remoteDataSource).generateWordDetail(remoteRequest)
    }
}