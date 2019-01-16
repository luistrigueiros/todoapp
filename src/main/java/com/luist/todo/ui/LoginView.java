package com.luist.todo.ui;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginView extends VerticalLayout {
    public LoginView() {
        Label label = new Label("<<This is the login view>>");
        add(label);
        add(buildUsername());
    }

    private TextField buildUsername() {
        TextField username = new TextField();
        username.setLabel("Username");
        username.setPlaceholder("placeholder text");
        return username;
    }

}
