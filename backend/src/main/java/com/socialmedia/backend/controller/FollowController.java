package com.socialmedia.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialmedia.backend.dto.FollowDTO;
import com.socialmedia.backend.dto.UserDTO;
import com.socialmedia.backend.service.FollowService;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    /**
     * Takip et
     * Ör: POST /api/follows?followerId=1&followingId=2
     */
    @PostMapping
    public FollowDTO follow(@RequestParam Integer followerId, @RequestParam Integer followingId) {
        return followService.followUser(followerId, followingId);
    }

    /**
     * Takipten çık
     * Ör: DELETE /api/follows?followerId=1&followingId=2
     */
    @DeleteMapping
    public void unfollow(@RequestParam Integer followerId, @RequestParam Integer followingId) {
        followService.unfollowUser(followerId, followingId);
    }

    /**
     * Toggle follow (takip ediyorsa çıkar, etmiyorsa takip et)
     * Ör: POST /api/follows/toggle?followerId=1&followingId=2
     */
    @PostMapping("/toggle")
    public ToggleResponse toggle(@RequestParam Integer followerId, @RequestParam Integer followingId) {
        boolean followedNow = followService.toggleFollow(followerId, followingId);
        return new ToggleResponse(followedNow);
    }

    /**
     * Bir kullanıcının takipçileri (UserDTO listesi)
     * Ör: GET /api/follows/followers/2
     */
    @GetMapping("/followers/{userId}")
    public List<UserDTO> followers(@PathVariable Integer userId) {
        return followService.getFollowers(userId);
    }

    /**
     * Bir kullanıcının takip ettikleri (UserDTO listesi)
     * Ör: GET /api/follows/following/1
     */
    @GetMapping("/following/{userId}")
    public List<UserDTO> following(@PathVariable Integer userId) {
        return followService.getFollowing(userId);
    }

    public record ToggleResponse(boolean active) {}

    @GetMapping("/isFollowing")
    public boolean isFollowing(@RequestParam Integer followerId, @RequestParam Integer followingId) {
        // FollowService içindeki mevcut isFollowing metodunu çağırır
        return followService.isFollowing(followerId, followingId);
    }
}