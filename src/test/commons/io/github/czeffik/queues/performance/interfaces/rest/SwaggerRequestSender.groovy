package io.github.czeffik.queues.performance.interfaces.rest

import io.restassured.response.Response

trait SwaggerRequestSender extends RequestSender{
    Response getApiDocsRequest() {
        return request("/v3/api-docs")
            .get()
            .thenReturn()
    }

    Response getSwaggerUIRequest() {
        return request("/swagger-ui/index.html")
            .get()
            .thenReturn()
    }
}
