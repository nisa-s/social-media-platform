let currentUser = JSON.parse(localStorage.getItem('user')) || null;
let followingSet = new Set();

const API_BASE = "http://localhost:8080/api";

document.addEventListener("DOMContentLoaded", () => {
    if (currentUser?.userId) showFeed();
});

// EKRAN VE SEKME YÖNETİMİ
function showRegister() {
    document.getElementById('login-form-area').classList.add('hidden');
    document.getElementById('register-form-area').classList.remove('hidden');
}

function showLogin() {
    document.getElementById('register-form-area').classList.add('hidden');
    document.getElementById('login-form-area').classList.remove('hidden');
}

function switchTab(tabName) {
    const feedTab = document.getElementById('tab-feed');
    const exploreTab = document.getElementById('tab-explore');
    const likesTab = document.getElementById('tab-likes');

    // mod barlarını kapat (feed/explore/likes'e geçerken)
    document.getElementById("profile-bar")?.classList.add("hidden");
    document.getElementById("hashtag-header")?.classList.add("hidden");

    // class helper
    const activeCls = "px-6 py-3 font-bold text-blue-600 border-b-4 border-blue-600 transition-all duration-300";
    const passiveCls = "px-6 py-3 font-bold text-gray-400 border-b-4 border-transparent hover:text-blue-600 transition-all duration-300";

    feedTab.className = passiveCls;
    exploreTab.className = passiveCls;
    likesTab.className = passiveCls;

    if (tabName === 'feed') {
        feedTab.className = activeCls;
        loadFollowingFeed();            //  takip ettiklerim
    } else if (tabName === 'explore') {
        exploreTab.className = activeCls;
        loadPosts();                    // tüm postlar
    } else {
        likesTab.className = activeCls;
        loadMyLikes();                  //  beğeniler
    }
}


// AUTH İŞLEMLERİ
async function register() {
    const username = document.getElementById('reg-username').value.trim();
    const email = document.getElementById('reg-email').value.trim();
    const password = document.getElementById('reg-password').value;

    try {
        await axios.post(`${API_BASE}/auth/register`, { username, email, password });
        alert("Kayıt başarılı! Giriş yapabilirsiniz.");
        showLogin();
    } catch (err) {
        alert("Hata: " + (err.response?.data?.message || "Sunucu hatası"));
    }
}

async function login() {
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;

    try {
        const res = await axios.post(`${API_BASE}/auth/login`, { email, password });
        currentUser = { userId: res.data.userId, username: res.data.username };
        localStorage.setItem('user', JSON.stringify(currentUser));
        showFeed();
    } catch (err) {
        alert("Giriş hatalı!");
    }
}

function logout() {
    localStorage.removeItem('user');
    location.reload();
}

// POST VE ETKİLEŞİM İŞLEMLERİ
async function loadPosts() {
    try {
        const followingRes = await axios.get(`${API_BASE}/follows/following/${currentUser.userId}`);

        // follow set’i doldur (buton metni için gerekli)
        followingSet = new Set(
            Array.isArray(followingRes.data)
                ? followingRes.data.map(x => (typeof x === "object" ? x.id : x))
                : []
        );

        const res = await axios.get(`${API_BASE}/posts?currentUserId=${currentUser.userId}`);
        const container = document.getElementById('posts-container');
        container.innerHTML = "";
        res.data.forEach(post => container.innerHTML += createPostHTML(post));
    } catch (err) {
        console.error(err);
    }
}


async function loadMyLikes() {
    try {
        const res = await axios.get(`${API_BASE}/likes/user/${currentUser.userId}`);
        const container = document.getElementById('posts-container');
        container.innerHTML = "";
        for (const like of res.data) {
            const postRes = await axios.get(`${API_BASE}/posts/${like.postId}?currentUserId=${currentUser.userId}`);
            container.innerHTML += createPostHTML(postRes.data);
        }
    } catch (err) { console.error(err); }
}

async function createPost() {
    const content = document.getElementById('post-content').value;
    if (!content.trim()) return;
    try {
        await axios.post(`${API_BASE}/posts`, { userId: currentUser.userId, content });
        document.getElementById('post-content').value = "";
        loadPosts();
        updateUserStats();
    } catch (err) { console.error(err); }
}

async function toggleLike(postId) {
    try {
        await axios.post(`${API_BASE}/likes/toggle?userId=${currentUser.userId}&postId=${postId}`);
        loadPosts();
    } catch (err) { console.error(err); }
}

async function toggleFollow(followingId) {
    try {
        const res = await axios.post(`${API_BASE}/follows/toggle?followerId=${currentUser.userId}&followingId=${followingId}`);
        updateUserStats();
        loadPosts();
    } catch (err) { console.error(err); }
}

async function deletePost(postId) {
    if (!confirm("Silinsin mi?")) return;
    try {
        await axios.delete(`${API_BASE}/posts/${postId}?userId=${currentUser.userId}`);
        loadPosts();
    } catch (err) { console.error(err); }
}

function createPostHTML(post) {
    const isMyPost = post.author.id == currentUser.userId;
    const heartColor = post.likedByCurrentUser ? 'text-red-500' : 'text-gray-400';

    return `
        <div class="bg-white p-4 rounded-xl shadow-sm border border-gray-100">
            <div class="flex justify-between items-center mb-2">
                <button
  onclick="viewProfile(${post.author.id})"
  class="font-bold text-gray-800 hover:underline text-left">
  ${post.author.username}
</button>

                ${isMyPost ? `<button onclick="deletePost(${post.id})" class="text-red-400 text-xs">Sil</button>` : ''}
                ${!isMyPost ? (() => {
            const isFollowing = followingSet.has(post.author.id);
            const text = isFollowing ? "Takibi Bırak" : "Takip Et";
            const cls = isFollowing
                ? "text-xs bg-gray-100 text-gray-700 px-3 py-1 rounded-full font-bold hover:bg-gray-200 transition"
                : "text-xs bg-blue-50 text-blue-600 px-3 py-1 rounded-full font-bold hover:bg-blue-100 transition";

            return `<button id="follow-btn-${post.author.id}"
                    onclick="toggleFollow(${post.author.id})"
                    class="${cls}">${text}</button>`;
        })() : ''}

            </div>
            <p class="text-gray-700 my-2">${post.content}</p>
            <div class="flex items-center space-x-4 border-t pt-2">
                <button onclick="toggleLike(${post.id})" class="${heartColor} flex items-center">
                    <span class="text-lg mr-1">♥</span> ${post.likeCount}
                </button>
                <span class="text-xs text-gray-400">${new Date(post.createdAt).toLocaleString('tr-TR')}</span>
            </div>
        </div>
    `;
}

async function showFeed() {
    document.getElementById('login-screen').classList.add('hidden');
    document.getElementById('main-feed').classList.remove('hidden');
    updateUserStats();
    switchTab('feed');
}

async function updateUserStats() {
    document.getElementById('stat-username').innerText = `@${currentUser.username}`;
    document.getElementById('avatar-initial').innerText = currentUser.username.charAt(0).toUpperCase();
    try {
        const res = await axios.get(`${API_BASE}/auth/profile/${currentUser.userId}`);
        if (res.data.profilePicture) {
            const img = document.getElementById('profile-img');
            img.src = res.data.profilePicture;
            img.classList.remove('hidden');
            document.getElementById('initial-text').classList.add('hidden');
        }
        // takip / takipçi sayılarını güncelle
        const followingRes = await axios.get(`${API_BASE}/follows/following/${currentUser.userId}`);
        const followersRes = await axios.get(`${API_BASE}/follows/followers/${currentUser.userId}`);
        document.getElementById('stat-following').innerText =
            Array.isArray(followingRes.data) ? followingRes.data.length : (followingRes.data?.count ?? 0);

        document.getElementById('stat-followers').innerText =
            Array.isArray(followersRes.data) ? followersRes.data.length : (followersRes.data?.count ?? 0);

        // bazı backend'ler {count: x} döndürebilir, o yüzden güvenli al
        const followingCount = Array.isArray(followingRes.data) ? followingRes.data.length : (followingRes.data?.count ?? 0);
        const followersCount = Array.isArray(followersRes.data) ? followersRes.data.length : (followersRes.data?.count ?? 0);

        document.getElementById('stat-following').innerText = followingCount;
        document.getElementById('stat-followers').innerText = followersCount;

    } catch (err) { console.warn(err); }
}
// -------------------- HASHTAG DESTEK (SADECE EK) --------------------

// Metinden hashtagleri çıkar (#java, #bahar gibi)
function extractHashtags(text) {
    const matches = (text || "").match(/#([\p{L}\p{N}_]+)/gu) || [];
    return matches.map(t => t.slice(1).toLowerCase());
}

// Ekrandaki post listesinden hashtagleri üretip Trend Hashtagler'i doldurur
function renderHashtagListFromCurrentFeed() {
    const listEl = document.getElementById("hashtag-list");
    const postsEl = document.getElementById("posts-container");
    if (!listEl || !postsEl) return;

    // posts-container içindeki metinlerden hashtag say
    const counts = new Map();
    const postTexts = postsEl.querySelectorAll("p"); // senin post HTML’inde içerik p tag’inde

    postTexts.forEach(p => {
        extractHashtags(p.innerText).forEach(tag => {
            counts.set(tag, (counts.get(tag) || 0) + 1);
        });
    });

    const top = [...counts.entries()].sort((a, b) => b[1] - a[1]).slice(0, 10);

    listEl.innerHTML = "";
    if (top.length === 0) {
        listEl.innerHTML = `<span class="text-sm text-gray-400">Henüz hashtag yok.</span>`;
        return;
    }

    top.forEach(([tag, count]) => {
        listEl.innerHTML += `
      <button onclick="filterByHashtagLocal('${tag}')"
        class="px-3 py-1 rounded-full bg-blue-50 text-blue-600 text-sm font-bold hover:bg-blue-100 transition">
        #${tag} <span class="text-xs text-blue-400">(${count})</span>
      </button>
    `;
    });
}

// Backend’e bağlı kalmadan: hashtag’e göre feed’i frontend’de filtreler
async function filterByHashtagLocal(tag) {
    try {
        // Profil barı kapat, hashtag header aç
        document.getElementById("profile-bar")?.classList.add("hidden");
        document.getElementById("hashtag-header")?.classList.remove("hidden");
        document.getElementById("hashtag-title").innerText = `#${tag}`;

        const res = await axios.get(`${API_BASE}/posts?currentUserId=${currentUser.userId}`);
        const filtered = res.data.filter(p => extractHashtags(p.content).includes(tag.toLowerCase()));

        const container = document.getElementById("posts-container");
        container.innerHTML = "";

        if (filtered.length === 0) {
            container.innerHTML = `<p class="text-gray-500 text-center py-10">Bu hashtag ile post bulunamadı.</p>`;
            return;
        }

        filtered.forEach(post => container.innerHTML += createPostHTML(post));
    } catch (err) {
        console.error("Hashtag filter error:", err);
    }
}

// Feed her yüklendiğinde hashtag listesini otomatik güncellemek için “loadPosts” sonrası tetikleyelim
// Mevcut kodu değiştirmeden: loadPosts fonksiyonunu "wrap" ediyoruz
(function wrapLoadPostsForHashtags() {
    if (typeof loadPosts !== "function") return;

    const originalLoadPosts = loadPosts;
    loadPosts = async function () {
        await originalLoadPosts();
        // Feed basıldıktan sonra hashtagleri çıkar
        renderHashtagListFromCurrentFeed();
        // Feed'e dönünce hashtag header gizlensin
        document.getElementById("hashtag-header")?.classList.add("hidden");
    };
})();
// Profil moduna girince profile barı açmak için wrap
(function wrapViewProfileBar() {
    if (typeof viewProfile !== "function") return;

    const originalViewProfile = viewProfile;
    viewProfile = async function (userId) {
        document.getElementById("hashtag-header")?.classList.add("hidden");
        document.getElementById("profile-bar")?.classList.remove("hidden");
        await originalViewProfile(userId);
    };
})();
// PROFIL GORUNTULEME (GLOBAL)
async function viewProfile(userId) {
    try {
        // Profil barı aç
        document.getElementById("profile-bar")?.classList.remove("hidden");
        document.getElementById("hashtag-header")?.classList.add("hidden");

        // Başlığı güncelle
        const titleEl = document.getElementById("profile-title");
        if (titleEl) titleEl.innerText = "Profil";

        // Tüm postları çek
        const res = await axios.get(`${API_BASE}/posts?currentUserId=${currentUser.userId}`);

        // Sadece bu kullanıcıya ait olanları filtrele
        const userPosts = res.data.filter(p => p.author.id === userId);

        const container = document.getElementById("posts-container");
        container.innerHTML = "";

        if (userPosts.length === 0) {
            container.innerHTML = `<p class="text-gray-500 text-center py-10">Bu kullanıcının henüz gönderisi yok.</p>`;
            return;
        }

        userPosts.forEach(post => {
            container.innerHTML += createPostHTML(post);
        });

    } catch (err) {
        console.error("Profil yüklenemedi:", err);
    }
}

// GLOBAL OLDUĞUNDAN EMİN OL
window.viewProfile = viewProfile;

async function loadFollowingFeed() {
    try {
        // 1) Takip ettiklerin (following) listesini çek
        const followingRes = await axios.get(`${API_BASE}/follows/following/${currentUser.userId}`);

        followingSet = new Set(
            Array.isArray(followingRes.data)
                ? followingRes.data.map(x => (typeof x === "object" ? x.id : x))
                : []
        );

        const followingIds = new Set(followingSet);
        followingIds.add(currentUser.userId);


        // 2) Tüm postları çek
        const postsRes = await axios.get(`${API_BASE}/posts?currentUserId=${currentUser.userId}`);

        // 3) Sadece takip ettiklerimin postlarını filtrele
        const filtered = postsRes.data.filter(p => followingIds.has(p.author.id));

        // 4) Ekrana bas
        const container = document.getElementById("posts-container");
        container.innerHTML = "";

        if (filtered.length === 0) {
            container.innerHTML = `<p class="text-gray-500 text-center py-10">Takip ettiğiniz kişilerden henüz gönderi yok.</p>`;
            return;
        }

        filtered.forEach(post => {
            container.innerHTML += createPostHTML(post);
        });

        // Eğer hashtag sistemi eklediysen, burada yeniden üretmek istersen:
        if (typeof renderHashtagListFromCurrentFeed === "function") {
            renderHashtagListFromCurrentFeed();
        }
    } catch (err) {
        console.error("Takip akışı yüklenemedi:", err);
    }
}

async function uploadPhoto() {
    const file = document.getElementById('photo-input').files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.readAsDataURL(file); // Dosyayı oku
    reader.onload = async () => {
        const base64String = reader.result;

        try {
            // UserService'de updateProfile metoduna bağlanmalı
            await axios.put(`${API_BASE}/users/${currentUser.userId}/photo`, {
                photo: base64String
            });

            // Ekranda anlık güncelle
            const img = document.getElementById('profile-img');
            const initial = document.getElementById('initial-text');
            img.src = base64String;
            img.classList.remove('hidden');
            initial.classList.add('hidden');

            alert("Profil fotoğrafı güncellendi!");
        } catch (err) {
            alert("Yükleme başarısız.");
        }
    };
}
