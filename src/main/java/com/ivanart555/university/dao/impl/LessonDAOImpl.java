package com.ivanart555.university.dao.impl;

import static java.lang.String.format;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.jpa.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;

@Component
@Transactional
public class LessonDAOImpl implements LessonDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonDAOImpl.class);
    private static final String LESSONS = "lessons";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Lesson> getAll() {
        LOGGER.debug("Trying to get all Lessons.");

        List<Lesson> lessons = entityManager
                .createQuery("SELECT l FROM Lesson l LEFT JOIN FETCH l.course", Lesson.class)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();

        lessons = entityManager
                .createQuery("SELECT l FROM Lesson l LEFT JOIN FETCH l.classroom WHERE l in :lessons",
                        Lesson.class)
                .setParameter(LESSONS, lessons)
                .getResultList();

        lessons = entityManager
                .createQuery("SELECT l FROM Lesson l LEFT JOIN FETCH l.lecturer WHERE l in :lessons",
                        Lesson.class)
                .setParameter(LESSONS, lessons)
                .getResultList();

        lessons = entityManager
                .createQuery("SELECT l FROM Lesson l LEFT JOIN FETCH l.group WHERE l in :lessons",
                        Lesson.class)
                .setParameter(LESSONS, lessons)
                .getResultList();

        return lessons;
    }

    @Override
    public Lesson getById(Integer id) throws DAOException {
        LOGGER.debug("Trying to get Lesson by id: {}", id);

        Lesson lesson = entityManager.find(Lesson.class, id);

        if (lesson == null) {
            String msg = format("Lesson with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.debug("Found:'{}'", lesson);

        return lesson;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Trying to delete Lesson by id: {}", id);
        Lesson lesson = entityManager.find(Lesson.class, id);

        if (lesson != null) {
            entityManager.remove(lesson);
        }
    }

    @Override
    public void update(Lesson lesson) {
        LOGGER.debug("Trying to update {}", lesson);
        entityManager.merge(lesson);
    }

    @Override
    public void create(Lesson lesson) throws DAOException {
        LOGGER.debug("Trying to create {}", lesson);
        entityManager.persist(lesson);
    }

    @Override
    public List<Lesson> getByDateTimeIntervalAndGroupId(Integer groupId, LocalDateTime lessonStart,
            LocalDateTime lessonEnd) throws DAOException {
        LOGGER.debug("Trying to get Lessons by date/time interval({},{}) and Group id:{}.", lessonStart, lessonEnd,
                groupId);

        List<Lesson> lessons = entityManager
                .createQuery(
                        "SELECT l FROM Lesson l WHERE l.group.id = :groupId AND (l.lessonStart BETWEEN :lessonStart AND :lessonEnd "
                                + "OR l.lessonEnd BETWEEN :lessonStart AND :lessonEnd OR l.lessonStart <= :lessonStart AND l.lessonEnd >= :lessonEnd) "
                                + "ORDER BY l.id",
                        Lesson.class)
                .setParameter("groupId", groupId)
                .setParameter("lessonStart", lessonStart)
                .setParameter("lessonEnd", lessonEnd)
                .getResultList();

        if (lessons.isEmpty()) {
            String msg = format("Lessons were not found by date/time interval('%s','%s') and Group id:'%s'.",
                    lessonStart, lessonEnd, groupId);
            throw new EntityNotFoundException(msg);
        }

        return lessons;
    }

    @Override
    public List<Lesson> getByDateTimeIntervalAndLecturerId(Integer lecturerId, LocalDateTime lessonStart,
            LocalDateTime lessonEnd) throws DAOException {
        LOGGER.debug("Trying to get Lessons by date/time interval({},{}) and Lecturer id:{}.", lessonStart, lessonEnd,
                lecturerId);

        List<Lesson> lessons = entityManager
                .createQuery(
                        "SELECT l FROM Lesson l WHERE l.lecturer.id = :lecturerId AND (l.lessonStart BETWEEN :lessonStart AND :lessonEnd "
                                + "OR l.lessonEnd BETWEEN :lessonStart AND :lessonEnd OR l.lessonStart <= :lessonStart AND l.lessonEnd >= :lessonEnd) "
                                + "ORDER BY l.id",
                        Lesson.class)
                .setParameter("lecturerId", lecturerId)
                .setParameter("lessonStart", lessonStart)
                .setParameter("lessonEnd", lessonEnd)
                .getResultList();

        if (lessons.isEmpty()) {
            String msg = format("Lessons were not found by date/time interval('%s','%s') and Lecturer id:'%s'.",
                    lessonStart, lessonEnd, lecturerId);
            throw new EntityNotFoundException(msg);
        }

        return lessons;
    }
}
