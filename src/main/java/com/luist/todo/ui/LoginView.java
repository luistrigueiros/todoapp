package com.luist.todo.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginView extends VerticalLayout implements LoginForm.Handler {
    private LoginForm loginForm;
    private Label pleasePerformLoginFirst = new Label("Please perform login first");

    public LoginView(LoginForm loginForm) {
        this.loginForm = loginForm;
        loginForm.setHandler(this);
        add(pleasePerformLoginFirst, loginForm);
    }

    @Override
    public void onAuthentication() {
        UI.getCurrent().navigate("");
    }
}
