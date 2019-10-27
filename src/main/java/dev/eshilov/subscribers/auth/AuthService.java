package dev.eshilov.subscribers.auth;

public interface AuthService {

    AuthResponse authenticate(AuthRequest request);
}
