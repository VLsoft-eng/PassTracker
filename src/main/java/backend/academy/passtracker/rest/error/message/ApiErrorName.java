package backend.academy.passtracker.rest.error.message;

public interface ApiErrorName {
    String BAD_EXTENSION = "Bad Extension";
    String FILE_ALREADY_EXISTS = "File already exists";
    String TO0_MANY_FILES = "Too many files";
    String FILE_NOT_FOUND = "File not found";
    String BAD_REQUEST = "Bad Request";
    String INVALID_AUTH_CREDENTIALS = "Invalid Auth Credentials";
    String ILLEGAL_ARGUMENT_EXCEPTION = "Illegal Argument Exception";
    String EMAIL_ALREADY_USED = "Email Already Used";
    String FACULTY_NOT_FOUND = "Faculty Already Used";
    String FORBIDDEN = "Forbidden";
    String INTERNAL_SERVER_ERROR = "Internal Server Error";
    String USER_NOT_FOUND = "User Not Found";
    String GROUP_NOT_FOUND = "Group Not Found";
}
