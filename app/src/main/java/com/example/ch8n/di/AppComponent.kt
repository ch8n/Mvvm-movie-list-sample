package com.example.ch8n.di

import com.example.ch8n.SampleApplication
import com.example.ch8n.di.modules.ActivityResolver
import com.example.ch8n.di.modules.DataSourceResolver
import com.example.ch8n.di.modules.NetworkResolver
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        NetworkResolver::class,
        ActivityResolver::class,
        DataSourceResolver::class
    )
)
interface AppComponent : AndroidInjector<SampleApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: SampleApplication): Builder

        fun build(): AppComponent
    }
}