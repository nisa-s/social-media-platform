package com.socialmedia.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialmedia.backend.dto.LikeDTO;
import com.socialmedia.backend.service.LikeService;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    /**
     * Like ekle
     * Ör: POST /api/likes?userId=1&postId=10
     */
    @PostMapping
    public LikeDTO like(@RequestParam Integer userId, @RequestParam Integer postId) {
        return likeService.likePost(userId, postId);
    }

    /**
     * Like kaldır
     * Ör: DELETE /api/likes?userId=1&postId=10
     */
    @DeleteMapping
    public void unlike(@RequestParam Integer userId, @RequestParam Integer postId) {
        likeService.unlikePost(userId, postId);
    }

    /**
     * Toggle like (beğenilmişse kaldır, değilse ekle)
     * Ör: POST /api/likes/toggle?userId=1&postId=10
     */
    @PostMapping("/toggle")
    public ToggleResponse toggle(@RequestParam Integer userId, @RequestParam Integer postId) {
        boolean likedNow = likeService.toggleLike(userId, postId);
        return new ToggleResponse(likedNow);
    }

    /**
     * Bir postun tüm like'ları
     * Ör: GET /api/likes/post/10
     */
    @GetMapping("/post/{postId}")
    public List<LikeDTO> getPostLikes(@PathVariable Integer postId) {
        return likeService.getPostLikes(postId);
    }

    /**
     * Bir kullanıcının tüm like'ları
     * Ör: GET /api/likes/user/1
     */
    @GetMapping("/user/{userId}")
    public List<LikeDTO> getUserLikes(@PathVariable Integer userId) {
        return likeService.getUserLikes(userId);
    }

    public record ToggleResponse(boolean active) {}
}
