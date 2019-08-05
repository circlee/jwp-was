package actions.user;

import db.DataBase;
import webserver.handler.ActionHandler;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Optional;

public class UserLoginAction implements ActionHandler<Void> {

    private static final String LOGIN_FAIL_REDIRECT_URL = "/user/login_failed.htm";
    private static final String LOGIN_SUCCESS_REDIRECT_URL = "/index.htm";
    private static final String LOGIN_FAIL_COOKIE = "logined=false; Path=/";
    private static final String LOGIN_SUCCESS_COOKIE = "logined=true; Path=/";

    @Override
    public Void actionHandle(HttpRequest httpRequest, HttpResponse httpResponse) {
        userLogin(httpRequest, httpResponse);
        return null;
    }

    private void userLogin(HttpRequest httpRequest, HttpResponse httpResponse){
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        boolean isUserLoginSuccess = isUserLoginSuccess(userId, password);

        if (!isUserLoginSuccess) {
            httpResponse.redirect(LOGIN_FAIL_REDIRECT_URL);
            httpResponse.setHttpHeader(HttpHeaders.SET_COOKIE, LOGIN_FAIL_COOKIE);
            return;
        }

        httpResponse.redirect(LOGIN_SUCCESS_REDIRECT_URL);
        httpResponse.setHttpHeader(HttpHeaders.SET_COOKIE, LOGIN_SUCCESS_COOKIE);

    }

    private boolean isUserLoginSuccess(String userId, String password) {
        return Optional.ofNullable(DataBase.findUserById(userId))
                .filter(user -> user.getPassword().equals(password))
                .isPresent();
    }

}
