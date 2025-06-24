package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import utils.JsonReader;

import java.io.IOException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class getPostmanEcho{
    @Test
    public void validateWithTestDataFromJson() throws IOException, ParseException {
        baseURI = "https://postman-echo.com";
        Response response = given()
                .auth()
                .basic(JsonReader.getTestData("username"), JsonReader.getTestData("password"))
                .when()
                .get("/basic-auth");

        int actualStatus = response.getStatusCode();// RestAssured
        assertEquals(actualStatus, StatusCode.SUCCESS.code);// TestNG
        System.out.println(response.body().asString());
    }

    @Test
    public void validateResponseBodyWithDigestAuth() {
        baseURI = "https://postman-echo.com";
        Response response = given()
                .auth()
                .digest("postman", "password")
                .when()
                .get("/basic-auth");

        int actualStatus = response.getStatusCode();// RestAssured
        assertEquals(actualStatus, StatusCode.SUCCESS.code);// TestNG
        System.out.println(response.body().asString());
    }

    @Test(groups = "SmokeSuite")
    public void validateResponseBodyWithBasicAuth() {
        baseURI = "https://postman-echo.com";
        Response response = given()
                .auth()
                .basic("postman", "password")
                .when()
                .get("/basic-auth");

        int actualStatus = response.getStatusCode();// RestAssured
        assertEquals(actualStatus, 200);// TestNG
        System.out.println(response.body().asString());
    }
}
