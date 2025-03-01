let message_timeout = document.getElementById("message-timer");

setTimeout(function () {
    if (message_timeout) {
        message_timeout.style.display = "none";
    }
}, 5000);