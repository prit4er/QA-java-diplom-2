package NomorepartiesApi.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    private String email;
    private String password;

    public static SignInRequest from(UserRequest userRequest) {
        return new SignInRequest(userRequest.getEmail(), userRequest.getPassword());
    }
}
