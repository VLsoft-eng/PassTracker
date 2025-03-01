package backend.academy.passtracker.core.specification;

import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.core.entity.PassRequest;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.core.enumeration.UserRole;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class UserSpecification {

    public static Specification<User> emailLike(String email) {
        return (root, query, cb) -> email == null ? null : cb.like(root.get("email"), email);
    }

    public static Specification<User> fullNameLike(String fullName) {
        return (root, query, cb) -> fullName == null ? null : cb.like(root.get("fullName"), fullName);
    }

    public static Specification<User> groupNumberEqual(Long groupNumber) {
        return (root, query, cb) -> groupNumber == null ? null : cb.equal(root.get("groupNumber"), groupNumber);
    }

    public static Specification<User> roleEqual(UserRole role) {
        return (root, query, cb) -> role == null ? null : cb.equal(root.get("role"), role);
    }

    public static Specification<User> isBlockedEqual(Boolean isBlocked) {
        return (root, query, cb) -> isBlocked == null ? null : cb.equal(root.get("isBlocked"), isBlocked);
    }

}
