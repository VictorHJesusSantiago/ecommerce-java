// Admin JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // Initialize tooltips
    var tooltips = document.querySelectorAll('[data-tooltip]');
    tooltips.forEach(function(el) {
        el.addEventListener('mouseenter', function() {
            var tooltip = document.createElement('div');
            tooltip.className = 'tooltip';
            tooltip.textContent = this.getAttribute('data-tooltip');
            tooltip.style.cssText = 'position:absolute;background:#333;color:#fff;padding:5px 10px;border-radius:4px;font-size:12px;z-index:1000;';
            document.body.appendChild(tooltip);
            var rect = this.getBoundingClientRect();
            tooltip.style.left = rect.left + 'px';
            tooltip.style.top = (rect.top - 30) + 'px';
        });
        el.addEventListener('mouseleave', function() {
            var tooltip = document.querySelector('.tooltip');
            if (tooltip) tooltip.remove();
        });
    });

    // Confirm delete actions
    document.querySelectorAll('.btn-delete').forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            if (!confirm('Are you sure you want to delete this item?')) {
                e.preventDefault();
            }
        });
    });

    // Toggle switches
    document.querySelectorAll('.toggle-switch').forEach(function(toggle) {
        toggle.addEventListener('change', function() {
            var id = this.getAttribute('data-id');
            var endpoint = this.getAttribute('data-endpoint');
            toggleStatus(id, endpoint);
        });
    });

    // Auto-dismiss alerts
    document.querySelectorAll('.alert').forEach(function(alert) {
        setTimeout(function() {
            alert.style.opacity = '0';
            setTimeout(function() { alert.remove(); }, 300);
        }, 5000);
    });
});

function toggleStatus(id, endpoint) {
    fetch(endpoint + '/' + id + '/toggle', {
        method: 'PUT',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
    })
    .then(function(response) {
        if (response.ok) {
            showNotification('Status updated successfully');
        } else {
            showNotification('Failed to update status', 'error');
        }
    })
    .catch(function(error) {
        console.error('Error:', error);
    });
}

function showNotification(message, type) {
    type = type || 'success';
    var notification = document.createElement('div');
    notification.className = 'alert alert-' + type;
    notification.textContent = message;
    notification.style.cssText = 'position:fixed;top:20px;right:20px;z-index:10000;animation:slideIn 0.3s ease;';
    document.body.appendChild(notification);
    setTimeout(function() { notification.remove(); }, 3000);
}

function confirmAction(message) {
    return confirm(message);
}

function formatDate(dateString) {
    var options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return new Date(dateString).toLocaleDateString('en-US', options);
}

function formatCurrency(amount) {
    return '$' + parseFloat(amount).toFixed(2);
}
