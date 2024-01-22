package com.mazzocchi.video_app.friends.service;

import com.mazzocchi.video_app.friends.dto.*;
import com.mazzocchi.video_app.friends.service.domain.*;
import com.mazzocchi.video_app.friends.service.repository.*;
import com.mazzocchi.video_app.security.*;
import com.mazzocchi.video_app.user.service.*;
import com.mazzocchi.video_app.user.service.domain.*;
import com.mazzocchi.video_app.user.service.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service

public class FriendsService {

    private final FriendsRepository friendsRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendsService(FriendsRepository friendsRepository, UserRepository userRepository) {
        this.friendsRepository = friendsRepository;
        this.userRepository = userRepository;
    }


    // Send a friend request logic
    public void sendFriendRequest(RequestFriendRequest request) {
        // Check if the friend request already exists
        if (friendsRepository.findBySenderIdAndReceiverId(request.getSenderId(), request.getReceiverId()) != null) {
            throw new RuntimeException("Friend request already exists");
        }

        final UserEntity sender = userRepository.findById(request.getSenderId()).orElseThrow(() -> new RuntimeException("Sender not found"));
        final UserEntity receiver = userRepository.findById(request.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Create a new friendEntity
        FriendsEntity friendsEntity = new FriendsEntity(
                sender,
                receiver,
                FriendshipStatus.PENDING,
                new Date()
        );

        // Save the friendEntity to the database
        friendsRepository.save(friendsEntity);
    }

    // List the friends of a user
    public List<FriendRequestModel> listFriends(Long senderId) {
        List<FriendsEntity> friendsEntityList = friendsRepository.findBySenderId(senderId).orElse(new ArrayList<>());

        // Map the list to get only the friends accepted
        List<FriendsEntity> friendsEntities = friendsEntityList.stream()
                .filter(friendsEntity -> friendsEntity.getStatus().equals(FriendshipStatus.ACCEPTED))
                .toList();

        List<FriendRequestModel> friends = friendsEntities.stream()
                .map(this::convertFromEntityToFriendRequestModel)
                .toList();

        return friends;
    }

    // List the friend requests of a user. Only if the logged user is the sender or receiver of the friend request
    public List<FriendRequestModel> listRequests(Long receiverId) {
        List<FriendsEntity> friendsEntityList = friendsRepository.listRequests(receiverId).orElse(new ArrayList<>());

        // Map the list to get only the friends accepted
        List<FriendsEntity> friendsEntities = friendsEntityList.stream()
                .filter(friendsEntity -> friendsEntity.getStatus().equals(FriendshipStatus.PENDING))
                .toList();

        List<FriendRequestModel> requests = friendsEntities.stream()
                .map(this::convertFromEntityToFriendRequestModel)
                .toList();

        return requests;
    }

    private FriendRequestModel convertFromEntityToFriendRequestModel (FriendsEntity friendsEntity) {
        FriendRequestModel friendRequestModel = new FriendRequestModel(
                friendsEntity.getId(),
                friendsEntity.getSender().getId(),
                friendsEntity.getReceiver().getId(),
                friendsEntity.getStatus()
        );
        return friendRequestModel;
    }

    // Accept a friend request logic
    public void acceptFriendRequest(Long senderId, Long receiverId) {
        // Check if the friend request exists
        FriendsEntity friendsEntity = friendsRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        if (friendsEntity == null) {
            throw new RuntimeException("Friend request not found");
        }

        // Check if the friend request is pending
        if (!friendsEntity.getStatus().equals(FriendshipStatus.PENDING)) {
            throw new RuntimeException("Friend request is not pending");
        }

        // Update the friend request status to accepted
        friendsRepository.acceptFriendRequest(senderId, receiverId, String.valueOf(FriendshipStatus.ACCEPTED));
    }

    // Decline a friend request logic
    public void declineFriendRequest(Long senderId, Long receiverId) {
        // Check if the friend request exists
        FriendsEntity friendsEntity = friendsRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        if (friendsEntity == null) {
            throw new RuntimeException("Friend request not found");
        }

        // Check if the friend request is pending
        if (!friendsEntity.getStatus().equals(FriendshipStatus.PENDING)) {
            throw new RuntimeException("Friend request is not pending");
        }

        // Update the friend request status to declined
        friendsRepository.declineFriendRequest(senderId, receiverId, String.valueOf(FriendshipStatus.DECLINED));
    }

}
