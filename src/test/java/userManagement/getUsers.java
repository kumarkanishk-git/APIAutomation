package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import static org.testng.Assert.*;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.ExtentReport;
import utils.JsonReader;
import utils.PropertyReader;
import utils.SoftAssertUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class getUsers extends BaseTest {
    SoftAssertUtil softAssertUtil = new SoftAssertUtil();

    @Test(groups = {"SmokeSuite", "RegressionSuite"})
    public void getUserData() {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("getUserData","Validate 200 status code");
        given().when().get("https://reqres.in/api/users?page=2").then().assertThat().statusCode(200);
    }

    @Test(groups = "RegressionSuite")
    public void validateGetResponseBody() {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("validateGetResponseBody","Validate 200 status code");
        //Set base URI for the API
        baseURI = "https://jsonplaceholder.typicode.com";
        //Send a Get request and validate the response body using 'then'
        given().when().get("/todos/1").then().assertThat().statusCode(200).body(not(isEmptyString())).body("title", equalTo("delectus aut autem")).body("userId", equalTo(1));
    }

    @Test
    public void validateGetResponseHasItems() {
        //Set base URI for the API
        baseURI = "https://jsonplaceholder.typicode.com";
        //Send a Get request and validate the response body using 'then'
        Response response = given().when().get("/posts").then().extract().response();
        //Use Hamcrest to check that the response body contains specific items
        assertThat(response.jsonPath().getList("title"), hasItems("qui est esse", "nesciunt quas odio"));
    }

    @Test
    public void validateGetResponseHasSize() {
        //Set base URI for the API
        baseURI = "https://jsonplaceholder.typicode.com";
        //Send a Get request and validate the response body using 'then'
        Response response = given().when().get("/comments").then().extract().response();
        //Use Hamcrest to check that the response body contains specific items
        assertThat(response.jsonPath().getList(""), hasSize(500));
    }

    @Test
    public void validateListContainsInOrder() {
        //Set base URI for the API
        baseURI = "https://jsonplaceholder.typicode.com";
        //Send a Get request and validate the response body using 'then'
        Response response = given().when().get("/comments?postId=1").then().extract().response();
        //Use Hamcrest to check that the response body contains specific items in a specific order
        List<String> expectedEmails = Arrays.asList("Eliseo@gardner.biz", "Jayne_Kuhic@sydney.com", "Nikita@garfield.biz", "Lew@alysha.tv", "Hayden@althea.biz");
        assertThat(response.jsonPath().getList("email"), contains(expectedEmails.toArray(new String[0])));
    }

    @Test
    public void testGetUsers() {
        baseURI = "https://reqres.in/api";
        Response response = given().queryParam("page", 2).when().get("/users");

        //Assert that response status code
        response.then().statusCode(200);

        //Assert that response contains 6 users
        response.then().body("data", hasSize(6));

        //Assert that the 2nd user in the list has correct values
        response.then().body("data[1].id", is(8));
        response.then().body("data[1].email", is("lindsay.ferguson@reqres.in"));
        response.then().body("data[1].first_name", is("Lindsay"));
        response.then().body("data[1].last_name", is("Ferguson"));
        response.then().body("data[1].avatar", is("https://reqres.in/img/faces/8-image.jpg"));
    }

    @Test
    public void testGetWithSingleQueryParameters() {
        baseURI = "https://reqres.in/api";
        Response response = given().queryParam("page", 2).when().get("/users");

        int actualStatus = response.getStatusCode();// RestAssured
        assertEquals(actualStatus, 200);// TestNG
    }

    @Test
    public void testGetWithMultipleQueryParameters() {
        baseURI = "https://api.restful-api.dev";
        Response response = given().queryParam("id", 3).queryParam("id", 5).queryParam("id", 10).when().get("/objects");
        int actualStatus = response.getStatusCode();// RestAssured
        assertEquals(actualStatus, 200);// TestNG
    }

    @Test
    public void testCreateUserWithFormParam() {
        baseURI = "https://reqres.in/api";
        Response response = given().contentType("application/x-www-form-urlencoded").formParam("name", "John Doe").formParam("job", "Developer").when().post("/users");

        response.then().body("name", equalTo("John Doe"));
        response.then().body("job", equalTo("Developer"));
    }

    @Test
    public void testSingleHeader() {
        baseURI = "https://reqres.in/api";
        given().header("Content-Type", "application/json").queryParam("page", 2).when().get("/users").then().statusCode(200);
    }

    @Test
    public void testMultipleHeader() {
        String bearerToken = "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";
        baseURI = "https://api.github.com";
        given().header("Content-Type", "application/json").headers("Authorization", "Bearer " + bearerToken).queryParam("page", 2).when().get("/user/repos").then().statusCode(200).log().body();
    }

    @Test
    public void testMultipleHeaderWithMap() {
        String bearerToken = "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";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + bearerToken);
        baseURI = "https://api.github.com";
        given().headers(headers).queryParam("page", 2).when().get("/user/repos").then().statusCode(200).log().body();
    }

    @Test
    public void testFetchHeader() {
        baseURI = "https://reqres.in/api";
        Response response = given().header("Content-Type", "application/json").queryParam("page", 2).when().get("/users");
        Headers headers = response.getHeaders();
        for (Header h : headers) {
            if (h.getName().equals("Server")) {
                //System.out.println(h.getName() + " : " + h.getValue());
                assertEquals(h.getValue(), "cloudflare", "Server Not Found.");
                System.out.println("testFetchHeader passed Successfully");
            }
        }
    }

    @Test
    public void testFetchCookie() {
        baseURI = "https://reqres.in/api";
        given().cookie("cookieKey1", "cookieValue1").cookie("cookieKey2", "cookieValue2").queryParam("page", 2).when().get("/users").then().statusCode(200);
    }

    @Test
    public void testFetchCookiesUsingBuilder() {
        baseURI = "https://reqres.in/api";
        Cookie cookies = new Cookie.Builder("cookieKey1", "cookieValue1").setComment("using cookie key").build();
        given().cookie(cookies).queryParam("page", 2).when().get("/users").then().statusCode(200);
    }

    @Test
    public void testFetchCookies() {
        baseURI = "https://reqres.in/api";
        Response response = given().queryParam("page", 2).when().get("/users");


        Map<String, String> myCookies = response.getCookies();
        Cookies cookies = response.getDetailedCookies();
        cookies.getValue("server");
        assertEquals(cookies.getValue("server"), "cloudflare", "Cookie Not Found.");
        assertThat(myCookies, hasKey("JSESSIONID"));
        assertThat(myCookies, hasValue("ABCDEF123456"));
    }

    @Test
    public void verifyTheStatusCodeDelete() {
        baseURI = "https://reqres.in/api";
        Response response = given().when().delete("/users/2");

        int actualStatus = response.getStatusCode();// RestAssured
        assertEquals(actualStatus, 204);// TestNG
        System.out.println(response.body().asString());
    }


    @Test
    public void getUserDataViaProperty() {
        baseURI = PropertyReader.propertyReader("./config.properties", "server");
        given().queryParam("page", 2).when().get("/users").then().assertThat().statusCode(200);
    }

    @Test
    public void getUserDataViaPropertyAndTestData() throws IOException, ParseException {
        baseURI = PropertyReader.propertyReader("./config.properties", "server");
        given()
                .queryParam("page", 2)
                .when()
                .get(JsonReader.getTestData("endpoint"))
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void softAssertion() {

        softAssertUtil.assertTrue(true, "true");
        softAssertUtil.assertAll();
    }

    @Test(description ="validateWithSoftAssertUtil" )
    public void validateWithSoftAssertUtil() {
        baseURI = "https://reqres.in/api";
        Response response = given().queryParam("page", 2).when().get("/users");

        //Assert that response status code
        softAssertUtil.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Status code is not 200");
        softAssertUtil.assertAll();
        System.out.println("validateWithSoftAssertUtil executed successfully");
    }

    @DataProvider(name = "testData")
    public Object[][] testData() {
        return new Object[][]{{"1", "John"}, {"2", "Jane"}, {"3", "Bob"}};
    }

    @Test(dataProvider = "testData")
    @Parameters({"id", "name"})
    public void testEndPoint(String id, String name) {
        baseURI = "https://reqres.in/api";
        given().queryParam("id", id).queryParam("name", name).when().get("/users").then().statusCode(200);
    }

    @Test
    public void testJsonArray() throws IOException, ParseException {
        JsonReader.getJsonArrayData("languages", 0);
        JSONArray jsonArray = JsonReader.getJsonArray("employee");
        Iterator<String> i1 = jsonArray.iterator();
        while (i1.hasNext()) {
            System.out.println(i1.next());
        }
    }
}
