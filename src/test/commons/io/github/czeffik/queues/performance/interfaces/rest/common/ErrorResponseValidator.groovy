package io.github.czeffik.queues.performance.interfaces.rest.common


import io.github.czeffik.queues.performance.interfaces.rest.ResponseParser
import io.restassured.response.Response
import org.springframework.http.HttpStatus

trait ErrorResponseValidator extends ResponseParser {
    boolean validateErrorResponse(Response response, String message, HttpStatus status) {
        def parsedResponse = parseResponse(response)
        assert parsedResponse.message == message
        assert validateCodeAndReason(parsedResponse, status)
        return true
    }

    boolean validateErrorResponseMatch(Response response, String message, HttpStatus status) {
        def parsedResponse = parseResponse(response)
        assert parsedResponse.message.matches(message)
        assert validateCodeAndReason(parsedResponse, status)
        return true
    }

    private boolean validateCodeAndReason(parsedResponse, HttpStatus status) {
        assert parsedResponse.code == status.value()
        assert parsedResponse.reason == status.reasonPhrase
        return true
    }
}
