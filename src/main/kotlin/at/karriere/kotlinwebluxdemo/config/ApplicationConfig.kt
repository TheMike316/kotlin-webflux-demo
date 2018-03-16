package at.karriere.kotlinwebluxdemo.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import javax.annotation.PostConstruct


@Configuration
class ApplicationConfig(private val context: GenericApplicationContext) {

    @PostConstruct
    fun initBeans() = context.apply { beans().initialize(this) }
}