document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');
    const inputs = form.querySelectorAll('input, select, textarea');

    form.addEventListener('submit', function (event) {
        let isValid = true;
        inputs.forEach(input => {
            if (!input.checkValidity()) {
                showValidationMessage(input);
                isValid = false;
            }
        });
        if (!isValid) {
            event.preventDefault();
        }
    });

    inputs.forEach(input => {
        input.addEventListener('input', function () {
            if (input.checkValidity()) {
                hideValidationMessage(input);
            } else {
                showValidationMessage(input);
            }
        });
    });

    function showValidationMessage(input) {
        const message = input.validationMessage || 'Please fill out this field.';
        let errorTooltip = input.nextElementSibling;
        if (!errorTooltip || !errorTooltip.classList.contains('error-tooltip')) {
            errorTooltip = document.createElement('div');
            errorTooltip.classList.add('error-tooltip');
            input.parentNode.insertBefore(errorTooltip, input.nextSibling);
        }
        errorTooltip.textContent = message;
        errorTooltip.style.display = 'block';
    }

    function hideValidationMessage(input) {
        let errorTooltip = input.nextElementSibling;
        if (errorTooltip && errorTooltip.classList.contains('error-tooltip')) {
            errorTooltip.style.display = 'none';
        }
    }
});
