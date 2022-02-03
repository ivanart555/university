package com.ivanart555.university.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.ivanart555.university.validators.LessonDatesTimesValidator;

@Constraint(validatedBy = LessonDatesTimesValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LessonDatesTimes {
    String message() default "The end time of the lesson must be later than the start of the lesson and the duration of the lesson must match the program settings!";

    String lessonStart();

    String lessonEnd();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
