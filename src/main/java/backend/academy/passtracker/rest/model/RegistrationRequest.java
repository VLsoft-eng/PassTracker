package backend.academy.passtracker.rest.model;

public record RegistrationRequest(String fullName, String email, String password
) {}