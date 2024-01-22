package com.mazzocchi.video_app.user.service.repository;

import com.mazzocchi.video_app.user.service.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);

    // Post a friend request to the database

     @Query(value = "INSERT INTO friends (user_id_sender, user_id_receiver, status, request_date_time) VALUES (:user_id_sender, :user_id_receiver, :status, :request_date_time)", nativeQuery = true)
        void postFriendRequest(@Param("user_id_sender") Long user_id_sender, @Param("user_id_receiver") Long user_id_receiver, @Param("status") String status, @Param("request_date_time") String request_date_time);


        // Accept a friend request from the database
        @Query(value = "UPDATE friends SET status = :status WHERE user_id_sender = :user_id_sender AND user_id_receiver = :user_id_receiver", nativeQuery = true)
        void acceptFriendRequest(@Param("user_id_sender") Long user_id_sender, @Param("user_id_receiver") Long user_id_receiver, @Param("status") String status);
}