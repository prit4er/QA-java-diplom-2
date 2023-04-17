package Orders;

import NomorepartiesApi.Auth.UserRequest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import NomorepartiesApi.Orders.OrdersManager;
import NomorepartiesApi.Orders.OrdersSteps;
import NomorepartiesApi.User.CreateUser;
import NomorepartiesApi.User.RandomUser;
import NomorepartiesApi.User.UserResponse;


public class GetOrderTest {
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
    @DisplayName("Получение заказов авторизованного пользователя")
    public void testGetOrderListForRegisteredUser() {
        ValidatableResponse response = OrdersSteps.getOrdersListUser(accessToken);
        OrdersManager.getOrderListInLoginUser(response);
    }

    @Test
    @DisplayName("Получение заказов не авторизованного пользователя")
    public void testUnableToGetOrderListForNonRegisteredUser() {
        ValidatableResponse response = OrdersSteps.getOrdersListUser("1");
        OrdersManager.getOrderListNoLoginUser(response);
    }
}