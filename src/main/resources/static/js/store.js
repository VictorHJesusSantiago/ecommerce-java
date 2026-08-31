// Store JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // Add to cart functionality
    document.querySelectorAll('.add-to-cart').forEach(function(btn) {
        btn.addEventListener('click', function() {
            var productId = this.getAttribute('data-product-id');
            var quantity = document.getElementById('quantity') ? document.getElementById('quantity').value : 1;
            addToCart(productId, quantity);
        });
    });

    // Add to wishlist functionality
    document.querySelectorAll('.add-to-wishlist').forEach(function(btn) {
        btn.addEventListener('click', function() {
            var productId = this.getAttribute('data-product-id');
            addToWishlist(productId);
        });
    });

    // Newsletter form
    var newsletterForm = document.querySelector('.newsletter-form');
    if (newsletterForm) {
        newsletterForm.addEventListener('submit', function(e) {
            e.preventDefault();
            var email = this.querySelector('input[name="email"]').value;
            subscribeNewsletter(email);
        });
    }
});

function addToCart(productId, quantity) {
    fetch('/api/v1/cart/items', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        },
        body: JSON.stringify({
            productId: parseInt(productId),
            quantity: parseInt(quantity)
        })
    })
    .then(function(response) {
        if (response.ok) {
            showNotification('Item added to cart!');
            updateCartCount();
        } else {
            showNotification('Failed to add item to cart', 'error');
        }
    })
    .catch(function(error) {
        console.error('Error:', error);
        showNotification('An error occurred', 'error');
    });
}

function addToWishlist(productId) {
    fetch('/api/v1/wishlist/items', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        },
        body: JSON.stringify({
            productId: parseInt(productId)
        })
    })
    .then(function(response) {
        if (response.ok) {
            showNotification('Item added to wishlist!');
        } else {
            showNotification('Failed to add item to wishlist', 'error');
        }
    })
    .catch(function(error) {
        console.error('Error:', error);
    });
}

function subscribeNewsletter(email) {
    fetch('/api/v1/newsletter/subscribe', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email: email })
    })
    .then(function(response) {
        if (response.ok) {
            showNotification('Successfully subscribed to newsletter!');
        } else {
            showNotification('Failed to subscribe', 'error');
        }
    })
    .catch(function(error) {
        console.error('Error:', error);
    });
}

function updateCartCount() {
    fetch('/api/v1/cart/count', {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
    })
    .then(function(response) { return response.json(); })
    .then(function(data) {
        var badge = document.querySelector('.cart-count');
        if (badge && data.data) {
            badge.textContent = data.data;
        }
    })
    .catch(function(error) {
        console.error('Error:', error);
    });
}

function showNotification(message, type) {
    type = type || 'success';
    var notification = document.createElement('div');
    notification.className = 'notification notification-' + type;
    notification.textContent = message;
    notification.style.cssText = 'position:fixed;top:20px;right:20px;padding:15px 20px;border-radius:4px;color:#fff;z-index:10000;animation:slideIn 0.3s ease;';
    notification.style.backgroundColor = type === 'error' ? '#dc3545' : '#28a745';
    document.body.appendChild(notification);
    setTimeout(function() { notification.remove(); }, 3000);
}

function changeImage(element) {
    var mainImage = document.getElementById('mainProductImage');
    if (mainImage) {
        mainImage.src = element.getAttribute('data-full-url');
    }
    document.querySelectorAll('.thumbnail').forEach(function(t) { t.classList.remove('active'); });
    element.classList.add('active');
}

function changeQuantity(delta) {
    var input = document.getElementById('quantity');
    if (input) {
        var newValue = parseInt(input.value) + delta;
        if (newValue >= 1 && newValue <= 10) {
            input.value = newValue;
        }
    }
}

function showTab(tabName) {
    document.querySelectorAll('.tab-content').forEach(function(tab) { tab.style.display = 'none'; });
    document.querySelectorAll('.tab-btn').forEach(function(btn) { btn.classList.remove('active'); });
    var target = document.getElementById(tabName + '-tab');
    if (target) target.style.display = 'block';
    event.target.classList.add('active');
}
