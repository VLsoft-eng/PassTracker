package backend.academy.passtracker.core.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    ROLE_ADMIN("admin"),
    ROLE_STUDENT("student"),
    ROLE_TEACHER("teacher"),
    ROLE_DEANERY("deanery"),
    ROLE_UNKNOWN("unknown");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static UserRole fromValue(String value) {
        for (UserRole status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Неизвестное значение: " + value);
    }
}
