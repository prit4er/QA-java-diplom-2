package User;

import NomorepartiesApi.Auth.SignInRequest;
import NomorepartiesApi.Auth.UserRequest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import NomorepartiesApi.User.CreateUser;
import NomorepartiesApi.User.RandomUser;
import NomorepartiesApi.User.UserResponse;

public class UserLoginTest {

    private final CreateUser createUser = new CreateUser();
    private final UserResponse userManager = new UserResponse();
    private final UserRequest newRandomUser = RandomUser.getRandomUser();
    private String accessToken;

    @Before
    public void setUpUser() {
        ValidatableResponse create = createUser.create(newRandomUser);
        accessToken = UserResponse.assertCreationSucsess(create);
    }

    @After
    public void cleanUpUser() {
        if (accessToken != null) {
            ValidatableResponse response = createUser.delete(accessToken);
            UserResponse.assertDeleteSusses(response);
        }
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void verifySuccessfulUserLogin() {
        SignInRequest signInRequest = SignInRequest.from(newRandomUser);
        ValidatableResponse login = createUser.login(signInRequest);
        userManager.loginInSusses(login);
    }

    @Test
    @DisplayName("логин с неверным логином и email")
    public void verifyLoginErrorWithIncorrectEmail() {
        SignInRequest signInRequest = SignInRequest.from(newRandomUser);
        signInRequest.setEmail(signInRequest.getEmail() + "1");
        ValidatableResponse login = createUser.login(signInRequest);
        userManager.loginInFailed(login);
    }

    @Test
    @DisplayName("логин с неверным паролем")
    public void verifyLoginErrorWithIncorrectPassword() {
        SignInRequest signInRequest = SignInRequest.from(newRandomUser);
        signInRequest.setPassword(signInRequest.getPassword() + "1");
        ValidatableResponse login = createUser.login(signInRequest);
        userManager.loginInFailed(login);
    }
}