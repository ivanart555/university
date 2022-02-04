$()
	.ready(
		function() {
			$('form')
				.each(
					function() {
						$(this)
							.validate(
								{
									rules: {
										name: {
											required: true,
											pattern: "[a-z]+",
										},
									},
									messages: {
										name: {
											required: "Please enter course name",
											pattern: "Course name must be lowercase",
										},
									},

									submitHandler: function(
										form) {
										form
											.submit();
									}

								});

					});
		});
