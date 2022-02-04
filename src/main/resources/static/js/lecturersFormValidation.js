$().ready(function () {
    $('form').each(function () {
        $(this).validate(
            {
                rules: {
                    firstName: {
                        required: true,
                        pattern: "[A-Z][a-z]+",
                    },
                    lastName: {
                        required: true,
                        pattern: "[A-Z][a-z]+",
                    }
                },
                messages: {
                    firstName: {
                        required: "Please enter lecturer's first name",
                        pattern: "First name must be capitalized",
                    },
                    lastName: {
                        required: "Please enter lecturer's last name",
                        pattern: "Last name must be capitalized",
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