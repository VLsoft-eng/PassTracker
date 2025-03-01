package backend.academy.passtracker.core.utils;

import backend.academy.passtracker.core.enumeration.UserRole;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUserRoleConverter implements Converter<String, UserRole> {
    @Override
    public UserRole convert(String source) {
        return UserRole.fromValue(source);
    }
}