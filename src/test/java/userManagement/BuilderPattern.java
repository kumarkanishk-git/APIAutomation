package userManagement;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class BuilderPattern {

    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    private RequestSpecification getRequestSpecification(String queryParam, String contentType) {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType(contentType)
                .addQueryParam("userId", queryParam)
                .build();
        return requestSpec;
    }

    private ResponseSpecification getResponseSpecification(int statusCode, String contentType) {
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(contentType)
                .build();
        return responseSpec;
    }

    @Test
    public void testRestAssuredBuilderPattern() {
        requestSpec = getRequestSpecification("1", "application/json");
        responseSpec = getResponseSpecification(200, "application/json");
        given()
                .spec(requestSpec)
                .when()
                .get("/posts")
                .then()
                .spec(responseSpec);
    }

    @Test
    public void validateGetResponseHasSize() {
        //Set base URI for the API
        baseURI = "https://jsonplaceholder.typicode.com";
        //Send a Get request and validate the response body using 'then'
        given()
                .contentType(ContentType.JSON)
                .queryParam("userId", "1")
                .when()
                .get("/posts")
                .then()
                .assertThat()
                .statusCode(200);
    }
}
