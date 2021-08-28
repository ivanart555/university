package com.ivanart555.university.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.LecturerDAO;
import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.LessonService;

@Component
public class LessonServiceImpl implements LessonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonServiceImpl.class);
    private static final String QUERY_DIDNT_EXECUTE = "Query didn't execute. Check SQL query.";
    private static final String SOMETHING_WRONG_WITH_DAO = "Something got wrong with DAO.";
    private LessonDAO lessonDAO;
    private CourseDAO courseDAO;
    private ClassroomDAO classroomDAO;
    private LecturerDAO lecturerDAO;
    private GroupDAO groupDAO;

    @Autowired
    public LessonServiceImpl(LessonDAO lessonDAO, CourseDAO courseDAO, ClassroomDAO classroomDAO,
            LecturerDAO lecturerDAO, GroupDAO groupDAO) {
        this.lessonDAO = lessonDAO;
        this.courseDAO = courseDAO;
        this.classroomDAO = classroomDAO;
        this.lecturerDAO = lecturerDAO;
        this.groupDAO = groupDAO;
    }

    @Override
    public List<Lesson> getAll() throws ServiceException {
        List<Lesson> lessons = lessonDAO.getAll();
        if (lessons.isEmpty()) {
            throw new ServiceException("There are no Lessons in database");
        }
        LOGGER.info("All Lessons were received successfully.");

        return lessons;
    }

    @Override
    public Lesson getById(Integer id) throws ServiceException {
        Lesson lesson = null;
        try {
            lesson = lessonDAO.getById(id);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Lesson with id {} not found!", id);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Lesson by id.", e);
        }
        LOGGER.info("Lesson with id {} received successfully.", id);

        return lesson;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        lessonDAO.delete(id);
        LOGGER.info("Lesson with id {} deleted successfully.", id);
    }

    @Override
    public void update(Lesson lesson) throws ServiceException {
        try {
            lessonDAO.update(lesson);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Lecturer.", e);
        }
        LOGGER.info("Lesson with id {} updated successfully.", lesson.getId());
    }

    @Override
    public void create(Lesson lesson) throws ServiceException {
        try {
            lessonDAO.create(lesson);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Lesson.", e);
        }
        LOGGER.info("Lesson with id {} created successfully.", lesson.getId());
    }

    @Override
    public void assignLessonToCourse(Lesson lesson, Integer courseId) throws ServiceException {
        try {
            courseDAO.getById(courseId);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(
                    "Failed to assign Lesson to Course. There is no Course with such id:" + courseId);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Course by id.", e);
        }

        lesson.setCourseId(courseId);

        try {
            lessonDAO.update(lesson);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Lesson.", e);
        }
        LOGGER.info("Lesson with id {} assigned to Course with id: {} successfully.", lesson.getId(), courseId);
    }

    @Override
    public void assignLessonToClassroom(Lesson lesson, Integer roomId) throws ServiceException {
        try {
            classroomDAO.getById(roomId);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(
                    "Failed to assign Lesson to Classroom. There is no Classroom with such id:" + roomId);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Classroom by id.", e);
        }
        LOGGER.info("Classroom with id {} received successfully.", roomId);

        lesson.setRoomId(roomId);

        try {
            lessonDAO.update(lesson);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign Lesson to Classroom.", e);
        }
        LOGGER.info("Lesson with id {} assigned to Classroom with id: {} successfully.", lesson.getId(), roomId);
    }

    @Override
    public void assignLessonToLecturer(Lesson lesson, Integer lecturerId) throws ServiceException {
        try {
            lecturerDAO.getById(lecturerId);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(
                    "Failed to assign Lesson to Lecturer. There is no Lecturer with such id:" + lecturerId);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Lecturer by id.", e);
        }

        lesson.setLecturerId(lecturerId);

        try {
            lessonDAO.update(lesson);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign Lesson to Lecturer.", e);
        }
        LOGGER.info("Lesson with id {} assigned to Lecturer with id: {} successfully.", lesson.getId(), lecturerId);
    }

    @Override
    public void assignLessonToGroup(Lesson lesson, Integer groupId) throws ServiceException {
        try {
            groupDAO.getById(groupId);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(
                    "Failed to assign Lesson to Group. There is no Group with such id:" + groupId);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Group by id.", e);
        }

        try {
            lessonDAO.assignLessonToGroup(lesson.getId(), groupId);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign lesson to group.", e);
        }
        LOGGER.info("Lesson with id {} assigned to Group with id: {} successfully.", lesson.getId(), groupId);
    }

    @Override
    public void assignLessonToDateTimeForGroup(Lesson lesson, LocalDateTime lessonStart, LocalDateTime lessonEnd,
            Integer groupId) throws ServiceException {

        if (!timeIsFree(groupId, lessonStart, lessonEnd)) {
            throw new ServiceException("Failed to assign Lesson to date and time. Date and time are occupied.");
        }

        lesson.setLessonStart(lessonStart);
        lesson.setLessonEnd(lessonEnd);

        try {
            lessonDAO.update(lesson);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign Lesson to date/time for Group.", e);
        }
        LOGGER.info("Lesson with id {} assigned to date/time({},{}) for Group with id: {} successfully.",
                lesson.getId(), lessonStart, lessonEnd, groupId);
    }

    private boolean timeIsFree(Integer groupId, LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        List<Lesson> lessons = new ArrayList<>();
        try {
            lessons = lessonDAO.getByDateTimeIntervalAndGroupId(groupId, lessonStart, lessonEnd);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Lessons were not found by date/time interval({},{}) and Group id:{}.", lessonStart,
                    lessonEnd, groupId);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
        }

        return lessons.isEmpty();
    }

    @Override
    public Page<Lesson> findPaginated(Pageable pageable) throws ServiceException {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Lesson> allLessons = getAll();
        int lessonsSize = allLessons.size();

        List<Lesson> list;

        if (lessonsSize < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, lessonsSize);
            list = allLessons.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), lessonsSize);
    }
}
