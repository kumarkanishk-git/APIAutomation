package userManagement;


import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;



import java.io.File;

import static io.restassured.RestAssured.*;

public class JsonSchemaValidation  {
    @Test()
    public void validateJsonSchema() {
        File schema = new File("./resources/ExpectedSchema.json");
        given()
                .when()
                .get("https://api.restful-api.dev/objects")
                .then()
                .assertThat()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(schema));

    }
}
