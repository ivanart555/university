package com.ivanart555.university.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dto.LessonDto;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.ClassroomRepository;
import com.ivanart555.university.repository.CourseRepository;
import com.ivanart555.university.repository.GroupRepository;
import com.ivanart555.university.repository.LecturerRepository;
import com.ivanart555.university.repository.LessonRepository;
import com.ivanart555.university.services.LessonService;

@Component
public class LessonServiceImpl implements LessonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonServiceImpl.class);
    private LessonRepository lessonRepository;
    private CourseRepository courseRepository;
    private ClassroomRepository classroomRepository;
    private LecturerRepository lecturerRepository;
    private GroupRepository groupRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, CourseRepository courseRepository, ClassroomRepository classroomRepository,
            LecturerRepository lecturerRepository, GroupRepository groupRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.classroomRepository = classroomRepository;
        this.lecturerRepository = lecturerRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Lesson> findAll() throws ServiceException {
        List<Lesson> lessons = lessonRepository.findAll();
        LOGGER.info("All Lessons were received successfully.");
        return lessons;
    }

    @Override
    public Page<LessonDto> findAll(Pageable pageable) throws ServiceException {
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

    public List<LessonDto> getAllDto() throws ServiceException {
        return convertLessonsToDto(findAll());
    }

    @Override
    public Lesson findById(Integer id) throws ServiceException {
        Lesson lesson = null;
        try {
            lesson = lessonRepository.findById(id)
                    .orElseThrow(() -> new ServiceException(String.format("Lesson with id %d not found!", id)));
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Lesson with id {} not found!", id);
        }
        LOGGER.info("Lesson with id {} received successfully.", id);
        return lesson;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        lessonRepository.deleteById(id);
        LOGGER.info("Lesson with id {} deleted successfully.", id);
    }

    @Override
    public void save(Lesson lesson) throws ServiceException {
        lessonRepository.save(lesson);
        LOGGER.info("Lesson with id {} updated successfully.", lesson.getId());
    }

    public void save(LessonDto lessonDto) throws ServiceException {
        Lesson lesson = convertDtoToLesson(lessonDto);
        save(lesson);
    }

    @Override
    public void assignLessonToDateTimeForGroup(Lesson lesson, LocalDateTime lessonStart, LocalDateTime lessonEnd,
            Integer groupId) throws ServiceException {

        if (!timeIsFree(groupId, lessonStart, lessonEnd)) {
            throw new ServiceException("Failed to assign Lesson to date and time. Date and time are occupied.");
        }
        lesson.setLessonStart(lessonStart);
        lesson.setLessonEnd(lessonEnd);

        lessonRepository.save(lesson);

        LOGGER.info("Lesson with id {} assigned to date/time({},{}) for Group with id: {} successfully.",
                lesson.getId(), lessonStart, lessonEnd, groupId);
    }

    private boolean timeIsFree(Integer groupId, LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        List<Lesson> lessons = new ArrayList<>();
        try {
            lessons = lessonRepository.getByDateTimeIntervalAndGroupId(groupId, lessonStart, lessonEnd);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Lessons were not found by date/time interval({},{}) and Group id:{}.", lessonStart,
                    lessonEnd, groupId);
        }
        return lessons.isEmpty();
    }

    private Lesson convertDtoToLesson(LessonDto lessonDto) throws ServiceException {
        Lesson lesson = new Lesson();
        lesson.setId(lessonDto.getId());

        Classroom classroom;
        try {
            classroom = lessonDto.getRoomId() == null ? null : classroomRepository.getById(lessonDto.getRoomId());
            lesson.setClassroom(classroom);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Classroom with id '%s' not found");
        }

        Course course;
        try {
            course = lessonDto.getCourseId() == null ? null : courseRepository.getById(lessonDto.getCourseId());
            lesson.setCourse(course);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Course with id '%s' not found");
        }

        Lecturer lecturer;
        try {
            lecturer = lessonDto.getLecturerId() == null ? null : lecturerRepository.getById(lessonDto.getLecturerId());
            lesson.setLecturer(lecturer);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Lecturer with id '%s' not found");
        }

        Group group;
        try {
            group = lessonDto.getGroupId() == null ? null : groupRepository.getById(lessonDto.getGroupId());
            lesson.setGroup(group);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Group with id '%s' not found");
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
