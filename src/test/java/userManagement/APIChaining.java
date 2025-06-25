package userManagement;

import core.StatusCode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class APIChaining {

    public String generateAuthToken() {

        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\n\"userName\":\"kanishk_API\",\n\"password\":\"Test@123\"\n}")
                .post("https://bookstore.toolsqa.com/Account/v1/GenerateToken");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("Token generated");
        String authToken = response.path("token");
        return authToken;
    }

    @Test
    public void verifyBookstoreAddBooks(){
        String authToken = generateAuthToken();
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer "+ authToken)
                .body("{\n  \"userId\": \"fd9fddca-80b7-473c-ae26-613d25b7da71\",\n  \"collectionOfIsbns\": [\n    {\n      \"isbn\": \"9781593277574\"\n    }\n  ]\n}")
                .post("https://bookstore.toolsqa.com/BookStore/v1/Books");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println(response.getBody().asString());
        System.out.println("verifyBookstoreAddBooks executed successfully");
    }
}
