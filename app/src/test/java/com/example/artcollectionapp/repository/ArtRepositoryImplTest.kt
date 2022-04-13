package com.example.artcollectionapp.repository

import com.example.artcollectionapp.model.`object`.Art
import com.example.artcollectionapp.model.department.Department
import com.example.artcollectionapp.model.department.DepartmentX
import com.example.artcollectionapp.model.search.Search
import com.example.artcollectionapp.rest.ArtCollectionAPI
import com.example.artcollectionapp.viewModel.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ArtRepositoryImplTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockArtCollectionApi = mockk<ArtCollectionAPI>(relaxed = true)
    private val mockDepartmentX = mockk<DepartmentX>(relaxed = true)
    private val mockArt = mockk<Response<Art>>(relaxed = true)

    private lateinit var target: ArtRepository

    @Before
    fun startup(){
        Dispatchers.setMain(testDispatcher)
        target = ArtRepositoryImpl(mockArtCollectionApi)
    }

    @After
    fun shutdown(){
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `get department IDs when the server returns a success response`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.getDepartments() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk{
                every { departments } returns listOf(mockDepartmentX)
            }
        }

        //Action - When
        target.getDepartmentIDs().collect {
            //Assertion - Then
            val success = it as ResultState.SUCCESS<Department>
            assertThat(success.response).isNotNull()
            assertThat(success.response.departments).isEqualTo(listOf(mockDepartmentX))
        }

        coVerify { mockArtCollectionApi.getDepartments() }
    }

    @Test
    fun `get department IDs when the server response is not successful and returns an Exception`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.getDepartments() } returns mockk {
            every { isSuccessful } returns false
        }

        //Action - When
        target.getDepartmentIDs().collect() {
            //Assertion - Then
            val success = it as ResultState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Get Departments unsuccessful")
        }

        coVerify { mockArtCollectionApi.getDepartments() }
    }

    @Test
    fun `get department IDs when the response is successful but body of message is null, throws and Exception`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.getDepartments() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }

        //Action - When
        target.getDepartmentIDs().collect() {
            //Assertion - Then
            val success = it as ResultState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Get Departments no response")
        }

        coVerify { mockArtCollectionApi.getDepartments() }
    }

    @Test
    fun `get department IDs when the response throws an error Exception`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.getDepartments() } throws Exception("Error")

        //Action - When
        target.getDepartmentIDs().collect() {
            //Assertion - Then
            val success = it as ResultState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Error")
        }
        coVerify { mockArtCollectionApi.getDepartments() }
    }

    @Test
    fun `get objects by department when the response from the server is successful`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.getObjectsByDepartment(1) } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { total } returns 100
            }
        }

        //Action - When
        target.getObjectsByDepartment(1).collect(){
            //Assertion - Then
            val success = it as ResultState.SUCCESS<Search>
            assertThat(success.response).isNotNull()
            assertThat(success.response.total).isEqualTo(100)
        }
        coVerify { mockArtCollectionApi.getObjectsByDepartment(1) }
    }

    @Test
    fun `get objects by department when the server response is not successful and throws an Exception`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.getObjectsByDepartment(1) } returns mockk{
            every { isSuccessful } returns false
        }

        //Action - When
        target.getObjectsByDepartment(1).collect(){
            //Assertion - Then
            val success = it as ResultState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Get Objects by Department unsuccessful")
        }
        coVerify { mockArtCollectionApi.getObjectsByDepartment(1) }
    }

    @Test
    fun `get objects by department when the response is successful but the body is null, throws Exception`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.getObjectsByDepartment(1) } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns null
        }

        //Action - When
        target.getObjectsByDepartment(1).collect(){
            //Assertion - Then
            val success = it as ResultState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Get Objects by Department no response")
        }
        coVerify { mockArtCollectionApi.getObjectsByDepartment(1) }
    }

    @Test
    fun `get objects by department when the response from the server throws an error Exception`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.getObjectsByDepartment(1) } throws Exception("Error")

        //Action - When
        target.getObjectsByDepartment(1).collect(){
            //Assertion - Then
            val success = it as ResultState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Error")
        }
        coVerify { mockArtCollectionApi.getObjectsByDepartment(1) }
    }

    @Test
    fun `search art with dates when the response from the server is successful`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.searchArtWithDates(
            false, null, null, null, "art"
        ) } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns mockk{
                every { total } returns 100
            }
        }

        //Action - When
        target.searchArtWithDates(
            false, null, null, null, "art"
        ).collect(){

            //Assertion - Then
            val success = it as ResultState.SUCCESS<Search>
            assertThat(success.response).isNotNull()
            assertThat(success.response.total).isEqualTo(100)
        }
        coVerify { mockArtCollectionApi.searchArtWithDates(
            false, null, null, null, "art"
        ) }
    }

    @Test
    fun `search art with dates when the response is successful but the body is null, throws an Exception`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.searchArtWithDates(
            false, null, null, null, "art"
        ) } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns null
        }

        //Action - When
        target.searchArtWithDates(
            false, null, null, null, "art"
        ).collect(){

            //Assertions - Then
            val success = it as ResultState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Search Collection no response")
        }
        coVerify { mockArtCollectionApi.searchArtWithDates(
            false, null, null, null, "art"
        ) }
    }

    @Test
    fun `search art with dates where the response from the server is not successful, throws an Exception`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.searchArtWithDates(
            false, null, null, null, "art"
        ) } returns mockk{
            every { isSuccessful } returns false
        }

        //Action - When
        target.searchArtWithDates(
            false, null, null, null, "art"
        ).collect(){

            //Assertion - Then
            val success = it as ResultState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Search Collection unsuccessful")
        }
        coVerify { mockArtCollectionApi.searchArtWithDates(
            false, null, null, null, "art"
        ) }
    }

    @Test
    fun `search art with dates when the response from the server throws an Exception`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.searchArtWithDates(
            false, null, null, null, "art"
        ) } throws Exception("Error")

        //Action - When
        target.searchArtWithDates(
            false, null, null, null, "art"
        ).collect(){

            //Assertion - Then
            val success = it as ResultState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Error")
        }
        coVerify { mockArtCollectionApi.searchArtWithDates(
            false, null, null, null, "art"
        ) }
    }

    @Test
    fun `search art without dates when the response from the server is successful`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.searchArtWithoutDates(
            false,null,"art"
        ) } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns mockk{
                every { total } returns 100
            }
        }

        //Action - When
        target.searchArtWithoutDates(
            false,null,"art"
        ).collect(){

            //Assertion - Then
            val success = it as ResultState.SUCCESS<Search>
            assertThat(success.response).isNotNull()
            assertThat(success.response.total).isEqualTo(100)
        }
        coVerify { mockArtCollectionApi.searchArtWithoutDates(
            false,null,"art"
        ) }
    }

    @Test
    fun `search art without dates when the response from the server is not successful, throws Exception`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.searchArtWithoutDates(
            false,null,"art"
        ) } returns mockk{
            every { isSuccessful } returns false
        }

        //Action - When
        target.searchArtWithoutDates(
            false,null,"art"
        ).collect(){

            //Assertion - Then
            val success = it as ResultState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Search Collection unsuccessful")
        }
        coVerify { mockArtCollectionApi.searchArtWithoutDates(
            false,null,"art"
        ) }
    }

    @Test
    fun `search art without dates where the response from server is successful but body is null, throws Exception`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.searchArtWithoutDates(
            false,null,"art"
        ) } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns null
        }

        //Action - When
        target.searchArtWithoutDates(
            false,null,"art"
        ).collect(){

            //Assertion - Then
            val success = it as ResultState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Search Collection no response")
        }
        coVerify { mockArtCollectionApi.searchArtWithoutDates(
            false,null,"art"
        ) }
    }

    @Test
    fun `search art without dates where the response from the server throws an Exception`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.searchArtWithoutDates(
            false,null,"art"
        ) } throws Exception("Error")

        //Action - When
        target.searchArtWithoutDates(
            false,null,"art"
        ).collect(){

            //Assertion - Then
            val success = it as ResultState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Error")
        }
        coVerify { mockArtCollectionApi.searchArtWithoutDates(
            false,null,"art"
        ) }
    }

    @Test
    fun `get object ID that returns an Art object`() = runTest {
        //Assign - Given
        coEvery { mockArtCollectionApi.getObjectID(1) } returns mockArt

        //Action - When
        val result = target.getObjectId(1)

        //Assertion - Then
        assertThat(result).isEqualTo(mockArt)
        coVerify { mockArtCollectionApi.getObjectID(1) }
    }

}