package br.com.angryapps.angry.db;

import br.com.angryapps.angry.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByGithubId(String githubId);
    Optional<User> findByEmail(String email);
}