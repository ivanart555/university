$(function () {

    $.validator.addMethod('dateBefore', function (value,
                                                  element, params) {
        // if end date is valid, validate it as well
        var end = $(params + element.form.id);
        if (!end.data('validation.running')) {
            $(element).data('validation.running', true);

            setTimeout($.proxy(
                function () {
                    this.element(end);
                }, this), 0);

            setTimeout(function () {
                $(element)
                    .data('validation.running', false);
            }, 0);
        }
        return this.optional(element)
            || this.optional(end[0])
            || new Date(value) < new Date(end.val());

    }, 'Must be before corresponding end date');

    $.validator.addMethod('dateAfter', function (value,
                                                 element, params) {
        // if start date is valid, validate it as well
        var start = $(params + element.form.id);
        if (!start.data('validation.running')) {
            $(element).data('validation.running', true);
            setTimeout($.proxy(
                function () {
                    this.element(start);
                }, this), 0);
            setTimeout(function () {
                $(element)
                    .data('validation.running', false);
            }, 0);
        }
        return this.optional(element)
            || this.optional(start[0])
            || new Date(value) > new Date($(start)
                .val());

    }, 'Must be after corresponding start date');

});

$('form')
    .each(
        function () {
            $(this)
                .validate(
                    {
                        rules: {
                            lessonStart: {
                                dateBefore: '#lessonEnd',
                                required: true
                            },
                            lessonEnd: {
                                dateAfter: '#lessonStart',
                                required: true
                            },
                            courseId: {
                                required: true
                            },
                            lecturerId: {
                                required: true
                            },
                            roomId: {
                                required: true
                            },
                            groupId: {
                                required: true
                            },
                        },
                        messages: {
                            lessonStart: {
                                required: "Please choose lesson's start date and time",
                            },
                            lessonEnd: {
                                required: "Please choose lesson's end date and time",
                            },
                            courseId: {
                                required: "Please choose course for lesson",
                            },
                            lecturerId: {
                                required: "Please choose lecturer for lesson",
                            },
                            roomId: {
                                required: "Please choose classroom for lesson",
                            },
                            groupId: {
                                required: "Please choose group for lesson",
                            },
                            submitHandler: function (
                                form) {
                                form
                                    .submit();
                            }
                        },
                    });
        });