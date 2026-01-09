package com.socialmedia.backend.exception;

public class ApiExceptions {

    public static class UserNotFound extends RuntimeException {
        public UserNotFound(Long userId) {
            super("Kullanıcı bulunamadı. userId=" + userId);
        }
    }

    public static class UserNotFoundByUsername extends RuntimeException {
    public UserNotFoundByUsername(String username) {
        super("Kullanıcı bulunamadı. username=" + username);
    }
}


    public static class DuplicateUsername extends RuntimeException {
        public DuplicateUsername(String username) {
            super("Bu kullanıcı adı zaten kullanılıyor: " + username);
        }
    }

    public static class DuplicateEmail extends RuntimeException {
        public DuplicateEmail(String email) {
            super("Bu e-posta zaten kullanılıyor: " + email);
        }
    }

    public static class InvalidCredentials extends RuntimeException {
        public InvalidCredentials() {
            super("Kullanıcı adı veya şifre hatalı");
        }
    }

    public static class AlreadyFollowing extends RuntimeException {
        public AlreadyFollowing(Long followerId, Long followingId) {
            super("Zaten takip ediliyor. followerId=" + followerId +
                  ", followingId=" + followingId);
        }
    }

    public static class NotFollowing extends RuntimeException {
        public NotFollowing(Long followerId, Long followingId) {
            super("Takip edilmiyor. followerId=" + followerId +
                  ", followingId=" + followingId);
        }
        
    }

    public static class AlreadyLiked extends RuntimeException {
        public AlreadyLiked(Long userId, Long postId) {
            super("Post zaten beğenilmiş. userId=" + userId +
                  ", postId=" + postId);
        }
    }

    public static class Unauthorized extends RuntimeException {
        public Unauthorized() {
            super("Bu işlem için yetkiniz yok");
        }
    }

    public static class PostNotFound extends RuntimeException {
        public PostNotFound(Long postId) {
            super("Post bulunamadı. postId=" + postId);
        }
    }
}
