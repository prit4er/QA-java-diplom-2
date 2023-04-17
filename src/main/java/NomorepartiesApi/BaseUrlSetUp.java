package NomorepartiesApi;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseUrlSetUp {
    private static final String BASE_URL ="https://stellarburgers.nomoreparties.site/api";

    protected static RequestSpecification getSpec(){

        return given().log().all()
                      .contentType(ContentType.JSON)
                      .baseUri(BASE_URL);
    }
}