package com.eShelf.info.e.library.repo;

import com.eShelf.info.e.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = " SELECT  email_id FROM info_library.library_user where id in ?1 "  , nativeQuery = true)
     List<String> findEmailsIdByIds(List<UUID> userId);
}
