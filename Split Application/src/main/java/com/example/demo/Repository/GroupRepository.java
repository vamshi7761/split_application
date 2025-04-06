//package com.example.demo.Repository;
//
//import java.util.Set;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import com.example.demo.Entity.Group;
//
//public interface GroupRepository extends JpaRepository<Group, Long> {
//
//	// Custom query to retrieve the user IDs for a specific group
//	@Query("SELECT u.id FROM Group g JOIN g.users u WHERE g.groupId = :groupId")
//	Set<Long> findUserIdsByGroupId(@Param("groupId") Long groupId);
//}
package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    // Custom query to retrieve the user IDs for a specific group
  
}
