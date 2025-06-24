package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;
import pojo.CityRequest;
import pojo.Data;
import pojo.PostRequestBody;
import pojo.RequestBody;
import utils.SoftAssertUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class getPost {
    SoftAssertUtil softAssertUtil = new SoftAssertUtil();
    String id;

    private static FileInputStream fileInputStreamMethod(String requestBodyFileName) {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(
                    new File(System.getProperty("user.dir") + "/resources/testData/" + requestBodyFileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileInputStream;
    }

    @Test(priority = 1)
    public void validatePostWithString() {
        baseURI = "https://api.restful-api.dev";
        Response response = given().header("Content-Type", "application/json").body("{\n   \"name\": \"Apple MacBook Pro 16\",\n   \"data\": {\n      \"year\": 2019,\n      \"price\": 1849.99,\n      \"CPU model\": \"Intel Core i9\",\n      \"Hard disk size\": \"1 TB\"\n   }\n}").when().post("/objects");
        id = response.jsonPath().getString("id");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePostWithString executed successfully");
    }

    @Test(priority = 2, dependsOnMethods = {"validatePostWithString"})
    public void validatePutWithString() {
        baseURI = "https://api.restful-api.dev";
        Response response = given().header("Content-Type", "application/json").body("{\n   \"name\": \"Apple MacBook Pro 16\",\n   \"data\": {\n      \"year\": 2019,\n      \"price\": 2049.99,\n      \"CPU model\": \"Intel Core i9\",\n      \"Hard disk size\": \"1 TB\",\n      \"color\": \"silver\"\n   }\n}").when().put("/objects/" + id);
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePutWithString executed successfully");
    }

    @Test(priority = 3, dependsOnMethods = {"validatePostWithString"})
    public void validatePatchWithString() {
        baseURI = "https://api.restful-api.dev";
        Response response = given().header("Content-Type", "application/json").body("{\n   \"name\": \"Apple MacBook Pro 16 (Updated Name)\"\n}").when().patch("/objects/" + id);
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePatchWithString executed successfully");
    }

    @Test(priority = 4)
    public void validatePostWithExternalFile() throws IOException {
        baseURI = "https://api.restful-api.dev";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("postRequestBody.json"), StandardCharsets.UTF_8))
                .when()
                .post("/objects");
        id = response.jsonPath().getString("id");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePostWithExternalFile executed successfully");
    }

    @Test(priority = 5, dependsOnMethods = {"validatePostWithExternalFile"})
    public void validatePutWithExternalFile() throws IOException {
        baseURI = "https://api.restful-api.dev";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("putRequestBody.json"), StandardCharsets.UTF_8))
                .when()
                .put("/objects/" + id);
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePutWithExternalFile executed successfully");
    }

    @Test(priority = 6, dependsOnMethods = {"validatePostWithExternalFile"})
    public void validatePatchWithExternalFile() throws IOException {
        baseURI = "https://api.restful-api.dev";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("patchRequestBody.json"), StandardCharsets.UTF_8))
                .when()
                .patch("/objects/" + id);
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePatchWithExternalFile executed successfully");
    }

    @Test(enabled = false)
    public void validatePostWithPOJO() throws IOException {
        PostRequestBody postRequest = new PostRequestBody();
        postRequest.setFirst_name("Tim");
        postRequest.setLast_name("David");
        postRequest.setEmail("timdavid@gmail.com");
        postRequest.setGender("Male");
        baseURI = "http://localhost:3000";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("/employees");
        id = response.jsonPath().getString("id");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePostWithPOJO executed successfully");
    }

    @Test(enabled = false)
    public void validatePostWithPOJOList() throws IOException {
        PostRequestBody postRequest = new PostRequestBody();
        List<String> listAddress = new ArrayList<>();
        listAddress.add("Home-> Patna");
        listAddress.add("Work-> Bangalore");
        postRequest.setFirst_name("Tim");
        postRequest.setLast_name("David");
        postRequest.setEmail("timdavid@gmail.com");
        postRequest.setGender("Male");
        postRequest.setAddress(listAddress);
        baseURI = "http://localhost:3000";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("/employees");
        id = response.jsonPath().getString("id");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePostWithPOJO executed successfully");
    }

    @Test(dependsOnMethods = {"validatePostWithPOJOList"}, enabled = false)
    public void validatePutWithPOJO() throws IOException {
        PostRequestBody putRequest = new PostRequestBody();
        List<String> listAddress = new ArrayList<>();
        listAddress.add("Home-> Noida");
        listAddress.add("Work-> Gurugram`");
        putRequest.setAddress(listAddress);
        baseURI = "http://localhost:3000";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(putRequest)
                .when()
                .put("/employees/" + id);
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePutWithPOJO executed successfully");
    }

    @Test(enabled = false)
    public void validatePostWithPOJOListObject() throws IOException {

        List<String> listAddress = new ArrayList<>();
        listAddress.add("Home-> Patna");
        listAddress.add("Work-> Bangalore");

        CityRequest cityRequest1 = new CityRequest();
        cityRequest1.setName("Bangalore");
        cityRequest1.setTemperature("30°C");

        CityRequest cityRequest2 = new CityRequest();
        cityRequest2.setName("Patna");
        cityRequest2.setTemperature("47°C");

        List<CityRequest> cityRequests = new ArrayList<>();
        cityRequests.add(cityRequest1);
        cityRequests.add(cityRequest2);

        PostRequestBody postRequest = new PostRequestBody();
        postRequest.setFirst_name("Tim");
        postRequest.setLast_name("David");
        postRequest.setEmail("timdavid@gmail.com");
        postRequest.setGender("Male");
        postRequest.setAddress(listAddress);
        postRequest.setCity(cityRequests);

        baseURI = "http://localhost:3000";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("/employees");
        id = response.jsonPath().getString("id");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePostWithPOJOListObject executed successfully");
    }

    @Test(priority = 1)
    public void validatePOSTtWithPOJO1() throws IOException {
        RequestBody postRequestBody = new RequestBody();
        postRequestBody.setName("Apple MacBook Pro 16");
        Data data = new Data();
        data.setYear(2019);
        data.setPrice(1849.99);
        data.setCPUModel("Intel Core i7");
        data.setHardDiskSize("1 TB");
        postRequestBody.setData(data);
        baseURI = "https://api.restful-api.dev";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(postRequestBody)
                .when()
                .post("/objects");
        id = response.jsonPath().getString("id");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePOSTtWithPOJO1 executed successfully");
    }

    @Test(priority = 2, dependsOnMethods = {"validatePOSTtWithPOJO1"})
    public void validatePutWithPOJO1() throws IOException {
        RequestBody putRequestBody = new RequestBody();
        putRequestBody.setName("Apple MacBook Pro 18");
        Data data = new Data();
        data.setYear(2020);
        data.setPrice(2049.99);
        data.setCPUModel("Intel Core i9");
        data.setHardDiskSize("2 TB");
        data.setColor("silver");
        putRequestBody.setData(data);
        baseURI = "https://api.restful-api.dev";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(putRequestBody)
                .when()
                .put("/objects/" + id);
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePutWithPOJO executed successfully");
    }

    @Test(priority = 3, dependsOnMethods = {"validatePutWithPOJO1"})
    public void validatePatchWithPOJO1() {
        RequestBody patchRequest = new RequestBody();
        patchRequest.setName("Apple MacBook Pro 20");
        baseURI = "https://api.restful-api.dev";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(patchRequest)
                .when()
                .patch("/objects/" + id);
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePatchWithPOJO1 executed successfully");
    }

    @Test(priority = 3, dependsOnMethods = {"validatePOSTtWithPOJO1"})
    public void validateDeSerializePOJO1() {
        RequestBody patchRequest = new RequestBody();
        String name = "Apple MacBook Pro 20";
        patchRequest.setName(name);
        baseURI = "https://api.restful-api.dev";
        Response response = given()
                .header("Content-Type", "application/json")
                .body(patchRequest)
                .when()
                .patch("/objects/" + id);
        RequestBody responseBody = response.as(RequestBody.class);
        System.out.println(responseBody.getData().getPrice());
        assertEquals(responseBody.getName(), name);
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println(response.getBody().asString());
        System.out.println("validatePatchWithPOJO1 executed successfully");
    }

}

