package net.lodgames.user.repository;

import net.lodgames.user.constants.UserStatus;
import net.lodgames.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByUserIdAndStatusLessThanEqual(long receiver, UserStatus status);
    boolean existsByUserId(long userId);
}
