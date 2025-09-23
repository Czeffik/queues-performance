package io.github.czeffik.queues.performance

import io.github.czeffik.queues.performance.interfaces.rest.common.ErrorResponseValidator
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import io.github.czeffik.queues.performance.interfaces.rest.SwaggerRequestSender
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification

@Slf4j
@ActiveProfiles(['test'])
@SpringBootTest(
    classes = [App],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class AppFT extends Specification implements SwaggerRequestSender, ErrorResponseValidator {
    @LocalServerPort
    int localServerPort
    @Shared
    JsonSlurper jsonSlurper = new JsonSlurper()


    def 'should return 200 and html on swagger ui endpoint'() {
        when:
            def response = getSwaggerUIRequest()
        then:
            with(response) {
                statusCode() == 200
                contentType.contains('text/html')
            }
    }

    def 'should return 200 and docs on api docs endpoint'() {
        when:
            def response = getApiDocsRequest()
        then:
            with(response) {
                statusCode() == 200
                contentType.contains('application/json')
            }
    }

    @Override
    JsonSlurper getSlurper() {
        return jsonSlurper
    }

    @Override
    int getPort() {
        return localServerPort
    }
}
