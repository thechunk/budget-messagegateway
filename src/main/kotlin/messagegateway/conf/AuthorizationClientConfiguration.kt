package messagegateway.conf

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler
import org.springframework.security.oauth2.provider.token.RemoteTokenServices

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
class AuthorizationClientConfiguration(val environment: Environment) : GlobalMethodSecurityConfiguration() {
    override fun createExpressionHandler(): MethodSecurityExpressionHandler = OAuth2MethodSecurityExpressionHandler()

    @Primary
    @Bean
    fun tokenService() : RemoteTokenServices {
        val tokenService = RemoteTokenServices()
        tokenService.setCheckTokenEndpointUrl(environment.getProperty("rcheung.oauth2.client.check-token-uri"))
        tokenService.setClientId(environment.getProperty("rcheung.oauth2.client.id"))
        tokenService.setClientSecret(environment.getProperty("rcheung.oauth2.client.secret"))
        return tokenService
    }
}
