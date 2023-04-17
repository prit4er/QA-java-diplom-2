package User;

import NomorepartiesApi.Auth.UserRequest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import NomorepartiesApi.User.CreateUser;
import NomorepartiesApi.User.RandomUser;
import NomorepartiesApi.User.UserResponse;

public class UserUpdateTest {
    private final CreateUser createUser = new CreateUser();
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
    @DisplayName("Изменение почты с авторизацией")
    public void verifyUpdateRegisteredUserEmail() {
        newRandomUser.setEmail(newRandomUser.getEmail() + "1");
        ValidatableResponse response = createUser.update(accessToken, newRandomUser);
        UserResponse.updateDateUserInSusses(response);
    }

    @Test
    @DisplayName("Изменение пароля с авторизацией")
    public void verifyUpdateRegisteredUserPassword() {
        newRandomUser.setPassword(newRandomUser.getPassword() + "1");
        ValidatableResponse response = createUser.update(accessToken, newRandomUser);
        UserResponse.updateDateUserInSusses(response);
    }

    @Test
    @DisplayName("Изменение имени с авторизацией")
    public void verifyUpdateRegisteredUserName() {
        newRandomUser.setName(newRandomUser.getName() + "1");
        ValidatableResponse response = createUser.update(accessToken, newRandomUser);
        UserResponse.updateDateUserInSusses(response);
    }

    @Test
    @DisplayName("Изменение имени без авторизации")
    public void verifyUpdateUnregisteredUserName() {
        newRandomUser.setName(newRandomUser.getName() + "1");
        ValidatableResponse response = createUser.update("1", newRandomUser);
        UserResponse.updateDateUserNotInFailed(response);
    }

    @Test
    @DisplayName("Изменение пароля без авторизации")
    public void verifyUpdateUnregisteredUserPassword() {
        newRandomUser.setPassword(newRandomUser.getPassword() + "1");
        ValidatableResponse response = createUser.update("1", newRandomUser);
        UserResponse.updateDateUserNotInFailed(response);
    }

    @Test
    @DisplayName("Изменение email без авторизации")
    public void verifyUpdateUnregisteredUserEmail() {
        newRandomUser.setEmail(newRandomUser.getEmail() + "1");
        ValidatableResponse response = createUser.update("1", newRandomUser);
        UserResponse.updateDateUserNotInFailed(response);
    }
}