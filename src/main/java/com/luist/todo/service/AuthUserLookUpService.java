package com.luist.todo.service;

import com.luist.todo.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthUserLookUpService {

	User findUser(String username) {
		User found = null;
		switch (username) {
		case "admin":
			found = new User("admin", "admin");
			break;
		case "luist":
			found = new User("luist", "luist");
			break;
		}
		return found;
	}

}
