$().ready(function () {
    $('form').each(function () {
        $(this).validate(
            {
                rules: {
                    name: {
                        required: true,
                        pattern: "[A-Z][A-Z]-[0-9][0-9]",
                    }
                },
                messages: {
                    name: {
                        required: "Please enter name of new Group",
                        pattern: "Group name must have two letters(uppercase) and two numbers like: AB-09",
                    },

                },

                submitHandler: function (
                    form) {
                    form
                        .submit();
                }

            });

    });
});