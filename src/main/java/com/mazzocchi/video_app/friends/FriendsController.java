package com.mazzocchi.video_app.friends;

import com.mazzocchi.video_app.friends.dto.*;
import com.mazzocchi.video_app.friends.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/friends")
public class FriendsController {

    private final FriendsService friendsService;

    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @PostMapping("/add")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Friend request added successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid username or password"),
            @ApiResponse(responseCode = "401", description = "User not found"),
    })
    public ResponseEntity<String> addFriend(@RequestBody RequestFriendRequest request) {
        try {
            friendsService.sendFriendRequest(request);
            return new ResponseEntity<>("Friend request added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list/requests/{receiverId}")
    @ApiResponse(responseCode = "200", description = "Friend requests found", content = @Content)
    public ResponseEntity<?> listFriendsRequests(@PathVariable Long receiverId) {
        try {
            List<FriendRequestModel> friends = friendsService.listRequests(receiverId);

            return new ResponseEntity<>(friends, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list/{senderId}")
    @ApiResponse(responseCode = "200", description = "Friends found", content = @Content)
    public ResponseEntity<?> listFriends(@PathVariable Long senderId) {
        try {
            List<FriendRequestModel> friends = friendsService.listFriends(senderId);

            return new ResponseEntity<>(friends, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accept/{senderId}/{receiverId}")
    @ApiResponse(responseCode = "200", description = "Friend request accepted successfully", content = @Content)
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long senderId, @PathVariable Long receiverId) {
        try {
            friendsService.acceptFriendRequest(senderId, receiverId);
            return new ResponseEntity<>("Friend request accepted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/decline/{senderId}/{receiverId}")
    @ApiResponse(responseCode = "200", description = "Friend request declined successfully", content = @Content)
    public ResponseEntity<String> declineFriendRequest(@PathVariable Long senderId, @PathVariable Long receiverId) {
        try {
            friendsService.declineFriendRequest(senderId, receiverId);
            return new ResponseEntity<>("Friend request declined successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
