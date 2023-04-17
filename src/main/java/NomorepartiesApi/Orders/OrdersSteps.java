package NomorepartiesApi.Orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import NomorepartiesApi.BaseUrlSetUp;

public class OrdersSteps extends BaseUrlSetUp {
        private static final String ORDERS = "/orders";

        @Step("Отправить запрос на создание заказа пользователем")
        public ValidatableResponse createOrders(String token, OrderRequest orderRequest) {
            return getSpec()
                    .header("Authorization", token)
                    .body(orderRequest)
                    .when()
                    .post(ORDERS)
                    .then().log().all();
        }

        @Step("Отправить запрос на получение списка заказов")
        public static ValidatableResponse getOrdersListUser(String token) {
            return getSpec()
                    .header("Authorization", token)
                    .when()
                    .get(ORDERS)
                    .then().log().all();
        }
}
