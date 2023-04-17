package NomorepartiesApi.User;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UserResponse {

    @Step("Successful response to the user creation request.")
    public static String assertCreationSucsess(ValidatableResponse response) {
        return response.assertThat()
                       .statusCode(200)
                       .body("success", is(true))
                       .extract().path("accessToken");
    }

    @Step("Successfully responded to a request to delete a user.")
    public static void assertDeleteSusses(ValidatableResponse response) {
        response.assertThat()
                .statusCode(202)
                .body("message", equalTo("User successfully removed"));
    }

    @Step("Error on request to create a previously registered user.")
    public static void assertCreationDoubleUserFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Step("Error on the request to create a user with an empty required field")
    public static void assertCreationUserNoRequiredField(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Successful response to login request")
    public void loginInSusses(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    @Step("Error when specifying an incorrect username or password")
    public void loginInFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Successful response to a data change request by a registered user")
    public static void updateDateUserInSusses(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Successful response to a data change request by a registered user")
    public static void updateDateUserNotInFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}
