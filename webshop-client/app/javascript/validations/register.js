$(function () {
    disableSubmitIfEmpty();

    $('#password, #passwordValidation').on('keyup', function () {
        const password = $('#password').val();
        const passwordValidation = $('#passwordValidation').val();

        if (password !== passwordValidation) {
            console.log('Not matching');
            $('#message').html('Not Matching').css('color', 'red');
            $('#submitBtn').attr('disabled', true)
        } else {
            if (passwordIsAtLeastCharsLong(6)){
                $('#message').html('Matching').css('color', 'green');
                $('#submitBtn').removeAttr('disabled')
            } else {
                $('#message').html('Password must be at least 6 characters long!').css('color', 'red');
                $('#submitBtn').attr('disabled', true)
            }

        }
    });

    $.fn.regexMask = function(mask) {
        $(this).keypress(function (event) {
            if (!event.charCode) return true;
            var part1 = this.value.substring(0, this.selectionStart);
            var part2 = this.value.substring(this.selectionEnd, this.value.length);
            if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2))
                return false;
        });
    };
    var mask = new RegExp('^[A-Za-z0-9 ]*$')
    $('#userName').regexMask(mask)
});

function disableSubmitIfEmpty() {
    const password = $('#password').val();
    const passwordValidation = $('#passwordValidation').val();

    if (password === "" && passwordValidation === "") {
        $('#submitBtn').attr('disabled', true)
    }
}

function passwordIsAtLeastCharsLong(num) {
    return $('#password').val().length > num
}