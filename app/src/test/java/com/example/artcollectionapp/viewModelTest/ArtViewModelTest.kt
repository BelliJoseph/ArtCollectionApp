package com.example.artcollectionapp.viewModelTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.artcollectionapp.model.department.Department
import com.example.artcollectionapp.model.search.Search
import com.example.artcollectionapp.repository.ArtRepository
import com.example.artcollectionapp.viewModel.ArtViewModel
import com.example.artcollectionapp.viewModel.ResultState
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule var rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockArtRepository = mockk<ArtRepository>(relaxed = true)

    private lateinit var target : ArtViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        target = ArtViewModel(mockArtRepository, testDispatcher)
    }

    @After
    fun shutdown(){
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `get department IDs when trying to load from server returns loading state`() {
        //Assign - Given
        val resultState = mutableListOf<ResultState>()
        target.departmentLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.getDepartmentIDs()

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(2)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
    }

    @Test
    fun `get department IDs when trying to load from server returns success state`(){
        //Assign - Given
        every { mockArtRepository.getDepartmentIDs() } returns flowOf(ResultState.SUCCESS(mockk<Department>()))
        val resultState = mutableListOf<ResultState>()
        target.departmentLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.getDepartmentIDs()

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.SUCCESS::class.java)
    }

    @Test
    fun `get department IDs when trying to load from server returns error state`(){
        //Assign - Given
        every { mockArtRepository.getDepartmentIDs() } returns flowOf(ResultState.ERROR(Exception("Error")))
        val resultState = mutableListOf<ResultState>()
        target.departmentLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.getDepartmentIDs()

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.ERROR::class.java)
    }

    @Test
    fun `get objects by department when trying to load from server returns loading state`(){
        //Assign - Given
        val resultState = mutableListOf<ResultState>()
        target.artLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.getObjectsByDepartment(1)

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(2)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
    }

    @Test
    fun `get objects by department when trying to load from server returns success state`(){
        //Assign - Given
        every { mockArtRepository.getObjectsByDepartment(1)} returns flowOf(ResultState.SUCCESS(mockk<Department>()))
        val resultState = mutableListOf<ResultState>()
        target.artLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.getObjectsByDepartment(1)

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.SUCCESS::class.java)
    }

    @Test
    fun `get objects by department when trying to load from the server returns error state`(){
        //Assign - Given
        every { mockArtRepository.getObjectsByDepartment(1) } returns flowOf(ResultState.ERROR(Exception("Error")))
        val resultState = mutableListOf<ResultState>()
        target.artLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.getObjectsByDepartment(1)

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.ERROR::class.java)
    }

    @Test
    fun `search art with dates when trying to load from the server returns loading state`(){
        //Assign - Given
        val resultState = mutableListOf<ResultState>()
        target.artLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.searchArtWithDates(
            false, null, null,null,"art"
        )

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(2)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
    }

    @Test
    fun `search art with dates when trying to load from the server returns success state`(){
        //Assign - Given
        every { mockArtRepository.searchArtWithDates(
            false, null, null,null,"art"
        ) } returns flowOf(ResultState.SUCCESS(mockk<Search>()))
        val resultState = mutableListOf<ResultState>()
        target.artLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.searchArtWithDates(
            false, null, null,null,"art"
        )

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.SUCCESS::class.java)
    }

    @Test
    fun `search art with dates when trying to load from the server returns error state`(){
        //Assign - Given
        every { mockArtRepository.searchArtWithDates(
            false, null, null,null,"art"
        ) } returns flowOf(ResultState.ERROR(Exception("Error")))
        val resultState = mutableListOf<ResultState>()
        target.artLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.searchArtWithDates(
            false, null, null,null,"art"
        )

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.ERROR::class.java)
    }

    @Test
    fun `search art without dates when trying to load from the server returns loading state`(){
        //Assign - Given
        val resultState = mutableListOf<ResultState>()
        target.artLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.searchArtWithoutDates(
            false, null,"art"
        )

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(2)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
    }

    @Test
    fun `search art without dates when trying to load from the server returns success state`(){
        //Assign - Given
        every { mockArtRepository.searchArtWithoutDates(
            false, null,"art"
        ) } returns flowOf(ResultState.SUCCESS(mockk<Search>()))
        val resultState = mutableListOf<ResultState>()
        target.artLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.searchArtWithoutDates(
            false, null,"art"
        )

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.SUCCESS::class.java)
    }

    @Test
    fun `search art without dates when trying to load from the server returns error state`(){
        //Assign - Given
        every { mockArtRepository.searchArtWithoutDates(
            false, null,"art"
        ) } returns flowOf(ResultState.ERROR(Exception("Error")))
        val resultState = mutableListOf<ResultState>()
        target.artLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.searchArtWithoutDates(
            false, null,"art"
        )

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.ERROR::class.java)
    }

    @Test
    fun `user validation to check if key word was entered, when key word is null`(){
        //Assign - Given
        target.keyWord = null

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.keywordEntered).isEqualTo(false)
    }

    @Test
    fun `user validation to check if key word was entered, when key word is not null`(){
        //Assign - Given
        target.keyWord = "art"

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.keywordEntered).isEqualTo(true)
    }

    @Test
    fun `user validation to check if the begin year was entered, when begin year is null`(){
        //Assign - Given
        target.beginYear = null

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.dateEntered).isEqualTo(false)
    }

    @Test
    fun `user validation to check if the begin year is formatted correctly, when begin year is less than zero`(){
        //Assign - Given
        target.beginYear = -1

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.yearFormatted).isEqualTo(false)
    }

    @Test
    fun `user validation to check if the begin year is formatted correctly, when begin year is more than 4 digits long`(){
        //Assign - Given
        target.beginYear = 12345

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.yearFormatted).isEqualTo(false)
    }

    @Test
    fun `user validation to check if the begin year is formatted correctly, when begin year is correctly entered`(){
        //Assign - Given
        target.beginYear = 1234

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.yearFormatted).isEqualTo(true)
    }

    @Test
    fun `user validation to check if the end year was entered, when end year is null`(){
        //Assign - Given
        target.endYear = null

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.dateEntered).isEqualTo(false)
    }

    @Test
    fun `user validation to check if the end year is formatted correctly, when end year is less than zero`(){
        //Assign - Given
        target.endYear = -1

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.yearFormatted).isEqualTo(false)
    }

    @Test
    fun `user validation to check if the end year is formatted correctly, when end year is more than 4 digits long`(){
        //Assign - Given
        target.endYear = 12345

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.yearFormatted).isEqualTo(false)
    }

    @Test
    fun `user validation to check if the end year is formatted correctly, when end year is correctly entered`(){
        //Assign - Given
        target.endYear = 1234

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.yearFormatted).isEqualTo(true)
    }

    @Test
    fun `user validation to check if both years were entered, where begin year entered but end year wasn't`(){
        //Assign - Given
        target.beginYear = 1234
        target.endYear = null

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.dateEntered).isEqualTo(true)
        assertThat(validation.bothYearsEntered).isEqualTo(false)
    }

    @Test
    fun `user validation to check if both years were entered, where begin year wasn't entered but the end year was`(){
        //Assign - Given
        target.beginYear = null
        target.endYear = 1234

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.dateEntered).isEqualTo(true)
        assertThat(validation.bothYearsEntered).isEqualTo(false)
    }

    @Test
    fun `user validation to check if both years were entered, where both begin and end year entered, also correctly formatted`(){
        //Assign - Given
        target.beginYear = 1234
        target.endYear = 1234

        //Action - When
        val validation = target.userValidation()

        //Assertion - Then
        assertThat(validation.dateEntered).isEqualTo(true)
        assertThat(validation.bothYearsEntered).isEqualTo(true)
    }


    @Test
    fun `get objects in list where full list has 25 items and when trying to load from the server returns success state`(){
        //Assign - Given
        target.resultsFullList = mutableListOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)

        coEvery { mockArtRepository.getObjectId(1) } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns mockk{
                every { artistGender } returns "Female"
            }
        }

        val resultState = mutableListOf<ResultState>()
        target.artListLiveData.observeForever{
            resultState.add(it)
        }


        //Action - When
        target.getObjectsInList()

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.SUCCESS::class.java)

    }

    @Test
    fun `get objects in list where full list has 25 items and when trying to load from the server returns response not successful, throws Exception`(){
        //Assign - Given
        target.resultsFullList = mutableListOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)

        coEvery { mockArtRepository.getObjectId(1) } returns mockk{
            every { isSuccessful } returns false
        }

        val resultState = mutableListOf<ResultState>()
        target.artListLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.getObjectsInList()

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.ERROR::class.java)
    }

    @Test
    fun `get objects in list where full list has 25 items and when trying to load from the server the body of the message is null, throws Exception`(){
        //Assign - Given
        target.resultsFullList = mutableListOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)

        coEvery { mockArtRepository.getObjectId(1) } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns null
        }

        val resultState = mutableListOf<ResultState>()
        target.artListLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.getObjectsInList()

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.ERROR::class.java)
    }

    @Test
    fun `get objects in list where full list has 25 items and when trying to load from the server, response throws Exception`(){
        //Assign - Given
        target.resultsFullList = mutableListOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)

        coEvery { mockArtRepository.getObjectId(1) } throws Exception("Error")

        val resultState = mutableListOf<ResultState>()
        target.artListLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.getObjectsInList()

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.ERROR::class.java)

    }

    @Test
    fun `get objects in list where full list has only 10 items resulting in sub list to return only 10`(){
        //Assign - Given
        target.resultsFullList = mutableListOf(1,1,1,1,1,1,1,1,1,1)

        //Action - When
        target.getObjectsInList()

        //Assertion - Then
        assertThat(target.currentEnd).isEqualTo(10)
    }

    @Test
    fun `get objects in list where the full list is empty resulting in sub list to return an empty list`(){
        //Assign - Given
        target.resultsFullList = mutableListOf()

        val resultState = mutableListOf<ResultState>()
        target.artListLiveData.observeForever{
            resultState.add(it)
        }

        //Action - When
        target.getObjectsInList()

        //Assertion - Then
        assertThat(resultState).isNotEmpty()
        assertThat(resultState).hasSize(3)
        assertThat(resultState[0]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[1]).isInstanceOf(ResultState.LOADING::class.java)
        assertThat(resultState[2]).isInstanceOf(ResultState.DEFAULT::class.java)
    }

}