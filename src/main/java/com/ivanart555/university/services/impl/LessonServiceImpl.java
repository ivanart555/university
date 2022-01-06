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
import com.ivanart555.university.dto.LessonDto;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.LessonService;

@Component
public class LessonServiceImpl implements LessonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonServiceImpl.class);
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
            LOGGER.info("There are no Lessons in database");
            return lessons;
        }
        LOGGER.info("All Lessons were received successfully.");

        return lessons;
    }

    public List<LessonDto> getAllDto() throws ServiceException {
        return convertLessonsToDto(getAll());
    }

    @Override
    public Lesson getById(Integer id) throws ServiceException {
        Lesson lesson = null;
        try {
            lesson = lessonDAO.getById(id);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Lesson with id {} not found!", id);
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

    public void create(LessonDto lessonDto) throws ServiceException {
        Lesson lesson = convertDtoToLesson(lessonDto);
        create(lesson);
    }
    
    public void update(LessonDto lessonDto) throws ServiceException {
        Lesson lesson = convertDtoToLesson(lessonDto);
        update(lesson);
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
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
        }

        return lessons.isEmpty();
    }

    @Override
    public Page<LessonDto> findPaginated(Pageable pageable) throws ServiceException {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<LessonDto> allLessons = getAllDto();
        int lessonsSize = allLessons.size();

        List<LessonDto> list;

        if (lessonsSize < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, lessonsSize);
            list = allLessons.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), lessonsSize);
    }

    private Lesson convertDtoToLesson(LessonDto lessonDto) throws ServiceException {
        Lesson lesson = new Lesson();
        lesson.setId(lessonDto.getId());

        Classroom classroom;
        try {
            classroom = lessonDto.getRoomId() == null ? null : classroomDAO.getById(lessonDto.getRoomId());
            lesson.setClassroom(classroom);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Classroom with id '%s' not found");
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
        }

        Course course;
        try {
            course = lessonDto.getCourseId() == null ? null : courseDAO.getById(lessonDto.getCourseId());
            lesson.setCourse(course);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Course with id '%s' not found");
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
        }

        Lecturer lecturer;
        try {
            lecturer = lessonDto.getLecturerId() == null ? null : lecturerDAO.getById(lessonDto.getLecturerId());
            lesson.setLecturer(lecturer);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Lecturer with id '%s' not found");
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
        }
        
        Group group;
        try {
            group = lessonDto.getGroupId() == null ? null : groupDAO.getById(lessonDto.getGroupId());
            lesson.setGroup(group);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Group with id '%s' not found");
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
        }
        
        lesson.setLessonStart(lessonDto.getLessonStart());
        lesson.setLessonEnd(lessonDto.getLessonEnd());

        return lesson;
    }

    private List<LessonDto> convertLessonsToDto(List<Lesson> lessons) {
        List<LessonDto> lessonsDto = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonsDto.add(convertLessonToDto(lesson));
        }
        return lessonsDto;
    }

    private LessonDto convertLessonToDto(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setCourseId(lesson.getCourse() == null ? null : lesson.getCourse().getId());
        lessonDto.setCourseName(lesson.getCourse() == null ? null : lesson.getCourse().getName());
        lessonDto.setGroupId(lesson.getGroup() == null ? null : lesson.getGroup().getId());
        lessonDto.setGroupName(lesson.getGroup() == null ? null : lesson.getGroup().getName());
        lessonDto.setRoomId(lesson.getClassroom() == null ? null : lesson.getClassroom().getId());
        lessonDto.setRoomName(lesson.getClassroom() == null ? null : lesson.getClassroom().getName());
        lessonDto.setLecturerId(lesson.getLecturer() == null ? null : lesson.getLecturer().getId());
        lessonDto.setLecturerFirstName(lesson.getLecturer() == null ? null : lesson.getLecturer().getFirstName());
        lessonDto.setLecturerLastName(lesson.getLecturer() == null ? null : lesson.getLecturer().getLastName());
        lessonDto.setLessonStart(lesson.getLessonStart());
        lessonDto.setLessonEnd(lesson.getLessonEnd());
        return lessonDto;
    }
}
