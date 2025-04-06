package com.example.demo.Repository;

import com.example.demo.Entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {

    //User findByUsername(String name);

    //@Query("SELECT u.userId FROM User u WHERE u.userId IN (SELECT userId FROM groupssss g JOIN g.userIds userId WHERE g.groupId = :groupId)")
    @Query(value = "SELECT ug.user_id FROM user_group ug WHERE ug.group_id = :groupId", nativeQuery = true)
    Set<Long> findUserIdsByGroupId(@Param("groupId") Long groupId);
}
