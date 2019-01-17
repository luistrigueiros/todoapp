package com.luist.todo.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UIScope
@SpringComponent
public class LoginForm extends FormLayout {
    private TextField username = new TextField("Username", "type in your username");
    private PasswordField password = new PasswordField("Password", "type in your password");
    private Button login = new Button("Login");

    public interface Handler {
        void onAuthentication();
    }

    private Handler handler;

    public LoginForm(AuthenticationProvider authenticationProvider) {
        add(username, password, login);
        login.addClickListener(event -> authenticate(authenticationProvider));
    }

    private void authenticate(AuthenticationProvider authenticationManager) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(username.getValue(), password.getValue());
        Authentication authenticate = authenticationManager.authenticate(authReq);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        if (handler != null) {
            handler.onAuthentication();
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
