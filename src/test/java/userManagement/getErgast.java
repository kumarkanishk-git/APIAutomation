package userManagement;

import core.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ExtentReport;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class getErgast extends BaseTest {

    @Test(groups = "RegressionSuite")
    public void validateResponseBodyGetPathParam() {
        baseURI = "https://ergast.com/api";
        ExtentReport.extentlog =
                ExtentReport.extentreport
                        .startTest("validateResponseBodyGetPathParam","Validate 200 status code - positive testcase");
        Response response = given()
                .pathParam("raceSeason", 2017)
                .when()
                .get("/f1/{raceSeason}/circuits.json");
        int actualStatus = response.getStatusCode();// RestAssured
        assertEquals(actualStatus, 200);// TestNG
    }
}
