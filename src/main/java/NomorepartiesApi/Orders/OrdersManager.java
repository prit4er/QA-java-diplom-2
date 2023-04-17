package NomorepartiesApi.Orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;


public class OrdersManager {

    @Step("Cоздание заказа")
    public static void createOrderSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Cоздание заказа без ингредиентов")
    public static void createOrderNoIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Cоздание заказа с неверным хешем ингредиентов")
    public static void createOrderInvalidHashIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(500);
    }

    @Step("Получение списка заказов зарегистрированным пользователем")
    public static void getOrderListInLoginUser(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Получение списка заказов незарегистрированным пользователем")
    public static void getOrderListNoLoginUser(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}
