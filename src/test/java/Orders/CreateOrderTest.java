package Orders;

import NomorepartiesApi.Auth.UserRequest;
import NomorepartiesApi.Orders.OrderRequest;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import NomorepartiesApi.Orders.OrdersManager;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import NomorepartiesApi.Orders.OrdersSteps;
import NomorepartiesApi.User.CreateUser;
import NomorepartiesApi.User.RandomUser;
import NomorepartiesApi.User.UserResponse;

import java.util.List;

public class CreateOrderTest {
    private final CreateUser createUser = new CreateUser();
    private final OrdersSteps ordersSteps = new OrdersSteps();
    private final  UserRequest newRandomUser = RandomUser.getRandomUser();

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
    @DisplayName("Создание заказа с авторизацией")
    public void verifyOrderCreationByRegisteredUser() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        OrderRequest orderRequest = new OrderRequest(ingredients);
        ValidatableResponse response = ordersSteps.createOrders(accessToken, orderRequest);
        OrdersManager.createOrderSuccess(response);
    }

    @Test
    @DisplayName("Создание заказа без авторизациии")
    public void verifyOrderCreationByUnregisteredUser() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        OrderRequest orderRequest = new OrderRequest(ingredients);
        ValidatableResponse response = ordersSteps.createOrders("1", orderRequest);
        OrdersManager.createOrderSuccess(response);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void verifyOrderCreationErrorWithoutIngredients() {
        OrderRequest orderRequest = new OrderRequest();
        ValidatableResponse response = ordersSteps.createOrders(accessToken, orderRequest);
        OrdersManager.createOrderNoIngredients(response);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void verifyOrderCreationErrorWithInvalidIngredientHashes() {
        List<String> ingredients = List.of("61c0c5a71d1f820dff01bdaaa6d", "61c0c5a71d1f82dfsdf001bdaaa6f");
        OrderRequest orderRequest = new OrderRequest(ingredients);
        ValidatableResponse response = ordersSteps.createOrders(accessToken, orderRequest);
        OrdersManager.createOrderInvalidHashIngredients(response);
    }
}