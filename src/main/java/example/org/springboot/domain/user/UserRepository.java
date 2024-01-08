package example.org.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);       //기존 회원인지 신규 회원인지 판단할 때 필요한 메서드
}
