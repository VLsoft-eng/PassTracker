package backend.academy.passtracker.core.specification;

import backend.academy.passtracker.core.entity.Group;
import backend.academy.passtracker.core.entity.PassRequest;
import backend.academy.passtracker.core.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.List;

public class PassRequestSpecification {

    public static Specification<PassRequest> userEqual(User user) {
        return (root, query, cb) -> user == null ? null : cb.equal(root.get("user"), user);
    }

    public static Specification<PassRequest> userSearchStringLike(String userSearchString) {
        return (root, query, cb) -> {
            if (userSearchString == null || userSearchString.isEmpty()) {
                return null;
            }
            Join<PassRequest, User> userJoin = root.join("user", JoinType.INNER);
            Predicate namePredicate = cb.like(cb.lower(userJoin.get("fullName")), "%" + userSearchString.toLowerCase() + "%");
            Predicate emailPredicate = cb.like(cb.lower(userJoin.get("email")), "%" + userSearchString.toLowerCase() + "%");
            return cb.or(namePredicate, emailPredicate);
        };
    }

    public static Specification<PassRequest> dateOverlapsWithPeriod(Instant start, Instant end) {
        return (root, query, cb) -> {
            if (start == null || end == null) {
                return null;
            }

            return cb.and(
                    cb.lessThanOrEqualTo(root.get("dateStart"), end),
                    cb.greaterThanOrEqualTo(root.get("dateEnd"), start)
            );
        };
    }

    public static Specification<PassRequest> dateBetweenStartAndEnd(Instant date) {
        return (root, query, cb) -> date == null ? null
                : cb.and(cb.lessThanOrEqualTo(root.get("dateStart"), date),
                cb.greaterThanOrEqualTo(root.get("dateEnd"), date));
    }

    public static Specification<PassRequest> isAcceptedEqual(Boolean isAccepted) {
        return (root, query, cb) -> isAccepted == null ? null : cb.equal(root.get("isAccepted"), isAccepted);
    }

    public static Specification<PassRequest> userInGroup(List<Long> groupNumber) {
        return (root, query, cb) -> {
            if (groupNumber == null || groupNumber.isEmpty()) {
                return null;
            }
            Join<PassRequest, User> userJoin = root.join("user", JoinType.INNER);
            Join<User, Group> groupJoin = userJoin.join("studentGroup", JoinType.INNER);
            return groupJoin.get("groupNumber").in(groupNumber);
        };
    }
}
