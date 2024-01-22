package com.mazzocchi.video_app.friends.service.repository;

import com.mazzocchi.video_app.friends.service.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository

public interface FriendsRepository extends JpaRepository<FriendsEntity, Long> {

    // Send a friend request
    @Modifying
    @Query(value = "INSERT INTO friends (user_id_sender, user_id_receiver, status, request_date_time) VALUES (:senderId, :receiverId, :status, :requestDateTime)", nativeQuery = true)
    void sendFriendRequest(Long senderId, Long receiverId, String status, String requestDateTime);

    // Accept a friend request
    @Modifying
    @Query(value = "How can I use JPA hibernate to achieve this query without SQL, only jPA: UPDATE video.friends\n" +
            "SET status = :status, request_date_time = CURRENT_TIMESTAMP\n" +
            "WHERE user_id_receiver =:receiverId AND user_id_sender =:senderId;\n" +
            "SELECT * FROM video.friends;\n" +
            "\n" +
            "INSERT video.user_friends (user_id, friend_id)\n" +
            "VALUES (:senderId, :receiverId); \n" +
            "SELECT * FROM video.user_friends;", nativeQuery = true)
    void acceptFriendRequest(Long senderId, Long receiverId, String status);

    // Decline a friend request
    @Modifying
    @Query(value = "UPDATE friends SET status = :status WHERE user_id_sender = :senderId AND user_id_receiver = :receiverId", nativeQuery = true)
    void declineFriendRequest(Long senderId, Long receiverId, String status);

    FriendsEntity findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    // Search for a friend whether the id passed is the sender or the receiver
    @Query(value = "SELECT * FROM friends WHERE user_id_sender = :userId OR user_id_receiver = :userId", nativeQuery = true)
    Optional<List<FriendsEntity>> findBySenderId(Long userId);

    // Get the friend requests of a user (Example: receiver id = 2, then return the friend requests where the receiver id is 2 and are pending)
    @Query(value = "SELECT * FROM friends WHERE user_id_receiver = :receiverId AND status = 'PENDING'", nativeQuery = true)
    Optional<List<FriendsEntity>> listRequests(Long receiverId);

}
