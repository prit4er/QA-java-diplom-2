package NomorepartiesApi.User;

import NomorepartiesApi.Auth.UserRequest;
import org.apache.commons.lang3.RandomStringUtils;

    public class RandomUser {
            public static UserRequest getRandomUser() {
                return new UserRequest(RandomStringUtils.randomAlphabetic(10) + "@yandex.ru", "Aleksey", "Kolobov");
            }
        }