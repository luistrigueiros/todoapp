package com.luist.todo.ui;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

//@Route("login")
public class LoginView extends VerticalLayout implements LoginForm.Handler {
    private LoginForm loginForm;
    private Dialog dialog = new Dialog();

    public LoginView(LoginForm loginForm) {
        this.loginForm = loginForm;
        dialog.add(loginForm);
        dialog.open();
    }

    @Override
    public void onAuthentication() {
        dialog.close();
    }
}
