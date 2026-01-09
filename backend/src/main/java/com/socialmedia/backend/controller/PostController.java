package com.socialmedia.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialmedia.backend.dto.PostDTO;
import com.socialmedia.backend.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Post oluşturma
     * Body: { "userId": 1, "content": "..." }
     */
    @PostMapping
    public PostDTO create(@RequestBody CreatePostRequest req) {
        return postService.createPost(req.userId(), req.content());
    }

    /**
     * Tüm postları getir (createdAt desc)
     * currentUserId: "isLikedByCurrentUser" hesaplamak için kullanılır.
     * Ör: GET /api/posts?currentUserId=1
     */
    @GetMapping
    public List<PostDTO> list(@RequestParam Integer currentUserId) {
        return postService.getAllPosts(currentUserId);
    }

    /**
     * Post detay
     * Ör: GET /api/posts/10?currentUserId=1
     */
    @GetMapping("/{postId}")
    public PostDTO getById(@PathVariable Integer postId,
                           @RequestParam Integer currentUserId) {
        return postService.getPostById(postId, currentUserId);
    }

    /**
     * Post güncelleme
     * Body: { "userId": 1, "content": "yeni içerik" }
     */
    @PutMapping("/{postId}")
    public PostDTO update(@PathVariable Integer postId, @RequestBody UpdatePostRequest req) {
        return postService.updatePost(postId, req.userId(), req.content());
    }

    /**
     * Post silme
     * Ör: DELETE /api/posts/10?userId=1
     */
    @DeleteMapping("/{postId}")
    public void delete(@PathVariable Integer postId, @RequestParam Integer userId) {
        postService.deletePost(postId, userId);
    }

    // ---- Request DTOs (Controller'a özel) ----
    public record CreatePostRequest(Integer userId, String content) {}
    public record UpdatePostRequest(Integer userId, String content) {}
}
