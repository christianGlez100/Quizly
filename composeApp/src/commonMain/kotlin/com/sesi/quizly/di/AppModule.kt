package com.sesi.quizly.di

import com.sesi.quizly.ui.signin.viewmodel.SignInViewModel
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

fun appModule() = module {
    //single { Source }.withOptions { createdAtStart() }
    //single { Repo() } { RepoImpl(get()) }
    //factory { SignInViewModel() }
    viewModelOf(::SignInViewModel)
}