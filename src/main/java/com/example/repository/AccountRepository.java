package com.example.repository;
import com.example.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    // custom query to find user by username
    Account findByUsername(String username);

    // custom query to find user by id
    // could have done this in the messageRepository using a custom query but this was faster and easier
    Account findByAccountId(Integer account_id);
}
