package util;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestCreator {

    private static Response sendRequest(
            final RequestType type,
            final RequestSpecification requestSpecification,
            final String uri,
            final Object requestBody
    ) {
        Response response;
        RequestSpecification specification = RestAssured.given(requestSpecification);
        if (requestBody != null) {
            specification.and()
                    .body(requestBody)
                    .when();
        }
        if (type == RequestType.POST) {
            response = specification.post(uri);
        } else {
            response = specification.get(uri);
        }
        return response.then()
                .extract()
                .response();
    }

    public static Response sendPOSTRequest(
            final RequestSpecification requestSpecification,
            final String uri,
            final Object requestBody
    ) {
        return sendRequest(RequestType.POST, requestSpecification, uri, requestBody);
    }

    public static Response sendGETRequest(
            final RequestSpecification requestSpecification,
            final String uri
    ) {
        return sendRequest(RequestType.GET, requestSpecification, uri, null);
    }
}
