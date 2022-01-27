package com.awais.mvvmnavdaggerunit

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.awais.mvvmnavdaggerunit.animalslist.AnimalListModel
import com.awais.mvvmnavdaggerunit.animalslist.ApiKey
import com.awais.mvvmnavdaggerunit.animalslist.ListViewModel
import com.awais.mvvmnavdaggerunit.di.ApiModuleTest
import com.awais.mvvmnavdaggerunit.di.AppModule
import com.awais.mvvmnavdaggerunit.di.DaggerViewModelComponent
import com.awais.mvvmnavdaggerunit.di.PrefModuleTest
import com.awais.mvvmnavdaggerunit.network.AnimalApiService
import com.awais.mvvmnavdaggerunit.utils.SharedPreferenceHelper
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var animalApiService: AnimalApiService

    @Mock
    lateinit var pref: SharedPreferenceHelper

    private val application = Mockito.mock(Application::class.java)
    private var listViewModel = ListViewModel(application, test = true)

    private val key = "Test key"

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(animalApiService))
            .sharedPreferenceModule(PrefModuleTest(pref))
            .build().listViewModel(listViewModel)
    }

    @Test
    fun getApiKeySuccess() {
        Mockito.`when`(pref.getApiKey()).thenReturn(null)
        val apiKey = ApiKey("OK", key)
        val keySingle = Single.just(apiKey)
        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keySingle)

        val animal = AnimalListModel("Cow")
        val animalList = listOf(animal)
        val testSingle = Single.just(animalList)
        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)
        listViewModel.refresh(false)
        Assert.assertEquals(1, listViewModel.animals.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getApiKeyFailure() {
        Mockito.`when`(pref.getApiKey()).thenReturn(null)
        val keySingle = Single.error<ApiKey>(Throwable())
        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keySingle)
        listViewModel.refresh(false)
        Assert.assertEquals(null, listViewModel.animals.value)
        Assert.assertEquals(true, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getAnimalSuccess() {
        Mockito.`when`(pref.getApiKey()).thenReturn(key)
        val animal = AnimalListModel("Cow")
        val animalList = listOf(animal)

        val testSingle = Single.just(animalList)
        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)
        listViewModel.refresh(false)
        Assert.assertEquals(1, listViewModel.animals.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getAnimalsFailure() {
        Mockito.`when`(pref.getApiKey()).thenReturn(key)
        val testSingle = Single.error<List<AnimalListModel>>(Throwable())
        val keySingle = Single.just(ApiKey("OK", key))
        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)
        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keySingle)
        listViewModel.refresh(false)
        Assert.assertEquals(null, listViewModel.animals.value)
        Assert.assertEquals(true, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker({ it.run() }, true)
            }
        }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}