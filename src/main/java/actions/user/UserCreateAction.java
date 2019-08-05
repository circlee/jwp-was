package actions.user;

import db.DataBase;
import model.User;
import webserver.handler.ActionHandler;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserCreateAction implements ActionHandler<Void> {

    private static final String COMPLETE_REDIRECT_URL = "/index.html";



    @Override
    public Void actionHandle(HttpRequest httpRequest, HttpResponse httpResponse) {
        userCreate(httpRequest, httpResponse);
        httpResponse.redirect(COMPLETE_REDIRECT_URL);
        return null;
    }

    private void userCreate(HttpRequest httpRequest, HttpResponse httpResponse){
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        DataBase.addUser(new User(userId, password, name, email));
    }

}
