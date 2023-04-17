package NomorepartiesApi.User;

import NomorepartiesApi.Auth.SignInRequest;
import NomorepartiesApi.Auth.UserRequest;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import NomorepartiesApi.BaseUrlSetUp;

public class CreateUser extends BaseUrlSetUp {
    private final static String REGISTER = "/auth/register";
    private final static String USER = "/auth/user";
    private final static String LOGIN = "/auth/login";

    @Step("Request to create a user")
    public ValidatableResponse create(UserRequest userRequest) {
        return getSpec()
                .body(userRequest)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("User delete request")
    public ValidatableResponse delete(String token) {
        return getSpec()
                .header("Authorization", token)
                .when()
                .delete(USER)
                .then().log().all();
    }

    @Step("Courier Entry Request")
    public ValidatableResponse login(SignInRequest signInRequest) {
        return getSpec()
                .body(signInRequest)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Request to change user data")
    public ValidatableResponse update(String token, UserRequest userRequest) {
        return getSpec()
                .header("Authorization", token)
                .body(userRequest)
                .when()
                .patch(USER)
                .then().log().all();
    }
}