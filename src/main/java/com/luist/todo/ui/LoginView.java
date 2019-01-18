package com.luist.todo.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("login")
public class LoginView extends FormLayout {
    //private Label pleasePerformLoginFirst = new Label("Please perform login first");
    private TextField username = new TextField("Username", "type in your username");
    private PasswordField password = new PasswordField("Password", "type in your password");
    private Button login = new Button("Login");


    public LoginView(AuthenticationProvider authenticationProvider) {
        add(username, password, login);
        login.addClickListener(event -> authenticate(authenticationProvider));
    }

    private void authenticate(AuthenticationProvider authenticationManager) {
        String username = this.username.getValue();
        String password = this.password.getValue();
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authReq);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UI.getCurrent().navigate("main");
    }

}
