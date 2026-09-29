package com.example.bai_tap_09_bai_tap.repository;

import com.example.bai_tap_09_bai_tap.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    Page<User> findByEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(
            String email, String fullName, Pageable pageable);

    @Query("select u from User u where lower(u.username) like lower(concat('%', :keyword, '%')) "
            + "or lower(u.email) like lower(concat('%', :keyword, '%')) "
            + "or lower(u.fullName) like lower(concat('%', :keyword, '%'))")
    Page<User> search(@Param("keyword") String keyword, Pageable pageable);

    @Query("select count(p) from Product p where p.user.id = :userId")
    long countProductsByUserId(@Param("userId") Long userId);

    @Query("select u.id, count(p.id) from User u left join u.products p group by u.id")
    java.util.List<Object[]> countProductsForUsers();

    Optional<User> findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String username);

    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = "role")
    @Query("select u from User u where lower(u.username) = lower(:username) or lower(u.email) = lower(:email)")
    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query("select u from User u join fetch u.role where lower(u.email) = lower(:email)")
    Optional<User> findByEmailWithRole(@Param("email") String email);

    Page<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(
            String username, String email, String fullName, Pageable pageable);

    boolean existsByUsernameIgnoreCaseAndIdNot(String username, Long id);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
