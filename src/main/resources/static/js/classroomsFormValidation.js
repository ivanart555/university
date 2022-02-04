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
											pattern: "[0-9][0-9][0-9]",
										}
									},
									messages: {
										name: {
											required: "Please enter name of new Classroom",
											pattern: "Classroom name must have three numbers",
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
