package com.reddit.client.di
import com.reddit.client.ui.main.MainViewModel
import com.reddit.client.domain.RedditPagingSource
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun inject(viewModel: MainViewModel)

    fun inject(pagingSource: RedditPagingSource)
}
