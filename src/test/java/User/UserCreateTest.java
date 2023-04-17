package User;

import NomorepartiesApi.Auth.UserRequest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import NomorepartiesApi.User.CreateUser;
import NomorepartiesApi.User.RandomUser;
import NomorepartiesApi.User.UserResponse;

public class UserCreateTest {
    private final CreateUser createUser = new CreateUser();
    private final UserRequest newRandomUser = RandomUser.getRandomUser();
    private String accessToken;

    @After
    public void cleanUpUser() {
        if (accessToken != null) {
            ValidatableResponse response = createUser.delete(accessToken);
            UserResponse.assertDeleteSusses(response);
        }
    }

    @Test
    @DisplayName("Cоздать уникального пользователя")
    public void verifySuccessfulUserCreation() {
        ValidatableResponse create = createUser.create(newRandomUser);
        accessToken = UserResponse.assertCreationSucsess(create);
    }

    @Test
    @DisplayName("Cоздать пользователя, который уже зарегистрирован;")
    public void verifyFailedDuplicateUserCreation() {
        ValidatableResponse create = createUser.create(newRandomUser);
        accessToken = UserResponse.assertCreationSucsess(create);

        ValidatableResponse create2 = createUser.create(newRandomUser);
        UserResponse.assertCreationDoubleUserFailed(create2);
    }

    @Test
    @DisplayName("Cоздать пользователя и не заполнить email")
    public void verifyFailedUserCreationWithoutEmail() {
        newRandomUser.setEmail(null);
        ValidatableResponse create = createUser.create(newRandomUser);
        UserResponse.assertCreationUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Cоздать пользователя и не заполнить password")
    public void verifyFailedUserCreationWithoutPassword() {
        newRandomUser.setPassword(null);
        ValidatableResponse create = createUser.create(newRandomUser);
        UserResponse.assertCreationUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Cоздать пользователя и не заполнить name")
    public void verifyFailedUserCreationWithoutName() {
        newRandomUser.setName(null);
        ValidatableResponse create = createUser.create(newRandomUser);
        UserResponse.assertCreationUserNoRequiredField(create);
    }
}