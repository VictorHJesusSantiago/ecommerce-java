// Product page JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // Variant selection
    document.querySelectorAll('.variant-option').forEach(function(btn) {
        btn.addEventListener('click', function() {
            if (!this.classList.contains('disabled')) {
                document.querySelectorAll('.variant-option').forEach(function(b) { b.classList.remove('active'); });
                this.classList.add('active');
                var variantId = this.getAttribute('data-variant-id');
                updatePriceForVariant(variantId);
            }
        });
    });

    // Thumbnail hover
    document.querySelectorAll('.thumbnail').forEach(function(thumb) {
        thumb.addEventListener('mouseenter', function() {
            var mainImage = document.getElementById('mainProductImage');
            if (mainImage) {
                mainImage.src = this.getAttribute('data-full-url');
            }
        });
    });

    // Review form submission
    var reviewForm = document.getElementById('reviewForm');
    if (reviewForm) {
        reviewForm.addEventListener('submit', function(e) {
            e.preventDefault();
            submitReview();
        });
    }

    // Star rating
    document.querySelectorAll('.star-rating .star').forEach(function(star) {
        star.addEventListener('click', function() {
            var rating = this.getAttribute('data-rating');
            updateStarRating(rating);
        });
    });
});

function updatePriceForVariant(variantId) {
    // Fetch variant price via API
    fetch('/api/v1/products/variants/' + variantId, {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
    })
    .then(function(response) { return response.json(); })
    .then(function(data) {
        if (data.data) {
            var priceEl = document.querySelector('.current-price');
            if (priceEl) {
                priceEl.textContent = '$' + data.data.price;
            }
        }
    })
    .catch(function(error) {
        console.error('Error:', error);
    });
}

function submitReview() {
    var productId = document.getElementById('reviewProductId').value;
    var rating = document.getElementById('reviewRating').value;
    var title = document.getElementById('reviewTitle').value;
    var comment = document.getElementById('reviewComment').value;

    fetch('/api/v1/reviews', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        },
        body: JSON.stringify({
            productId: parseInt(productId),
            rating: parseInt(rating),
            title: title,
            comment: comment
        })
    })
    .then(function(response) {
        if (response.ok) {
            showNotification('Review submitted! It will be visible after moderation.');
            document.getElementById('reviewForm').reset();
        } else {
            showNotification('Failed to submit review', 'error');
        }
    })
    .catch(function(error) {
        console.error('Error:', error);
    });
}

function updateStarRating(rating) {
    document.querySelectorAll('.star-rating .star').forEach(function(star) {
        var starRating = star.getAttribute('data-rating');
        if (starRating <= rating) {
            star.classList.add('active');
        } else {
            star.classList.remove('active');
        }
    });
    document.getElementById('reviewRating').value = rating;
}

function addToCartFromProduct() {
    var productId = document.querySelector('[data-product-id]').getAttribute('data-product-id');
    var quantity = document.getElementById('quantity') ? document.getElementById('quantity').value : 1;
    var variantId = document.querySelector('.variant-option.active');
    if (variantId) {
        variantId = variantId.getAttribute('data-variant-id');
    }

    var body = {
        productId: parseInt(productId),
        quantity: parseInt(quantity)
    };
    if (variantId) {
        body.variantId = parseInt(variantId);
    }

    fetch('/api/v1/cart/items', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        },
        body: JSON.stringify(body)
    })
    .then(function(response) {
        if (response.ok) {
            showNotification('Item added to cart!');
            updateCartBadge();
        } else {
            showNotification('Failed to add item to cart', 'error');
        }
    })
    .catch(function(error) {
        console.error('Error:', error);
    });
}

function updateCartBadge() {
    var badge = document.querySelector('.cart-count');
    if (badge) {
        badge.textContent = parseInt(badge.textContent || '0') + 1;
    }
}

function showNotification(message, type) {
    type = type || 'success';
    var notification = document.createElement('div');
    notification.className = 'notification notification-' + type;
    notification.textContent = message;
    notification.style.cssText = 'position:fixed;top:20px;right:20px;padding:15px 20px;border-radius:4px;color:#fff;z-index:10000;';
    notification.style.backgroundColor = type === 'error' ? '#dc3545' : '#28a745';
    document.body.appendChild(notification);
    setTimeout(function() { notification.remove(); }, 3000);
}
