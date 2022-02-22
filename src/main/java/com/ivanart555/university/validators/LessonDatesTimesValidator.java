package com.ivanart555.university.validators;

import com.ivanart555.university.annotations.LessonDatesTimes;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;
import java.time.LocalDateTime;

public class LessonDatesTimesValidator implements ConstraintValidator<LessonDatesTimes, Object> {
    private static final Long LESSON_DURATION_MINUTES = 60L;

    @Autowired
    private Environment env;

    private String lessonStart;
    private String lessonEnd;

    @Override
    public void initialize(LessonDatesTimes constraintAnnotation) {
        this.lessonStart = constraintAnnotation.lessonStart();
        this.lessonEnd = constraintAnnotation.lessonEnd();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDateTime lessonStartValue = (LocalDateTime) new BeanWrapperImpl(value)
                .getPropertyValue(lessonStart);
        LocalDateTime lessonEndValue = (LocalDateTime) new BeanWrapperImpl(value)
                .getPropertyValue(lessonEnd);

        if (lessonEndValue == null) {
            return false;
        }

        int compareValue = lessonEndValue.compareTo(lessonStartValue);
        Duration duration = Duration.between(lessonStartValue, lessonEndValue);

        return (compareValue > 0 && duration.toMinutes() == LESSON_DURATION_MINUTES);
    }
}
