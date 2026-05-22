package com.malick.shoestore.services;

import com.malick.shoestore.dtos.LoginRequest;
import com.malick.shoestore.dtos.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
