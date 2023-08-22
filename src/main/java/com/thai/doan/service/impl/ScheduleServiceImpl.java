package com.thai.doan.service.impl;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.thai.doan.dao.model.*;
import com.thai.doan.dao.repository.*;
import com.thai.doan.dto.request.NewScheduleRequest;
import com.thai.doan.dto.request.ScheduleUpdatingRequest;
import com.thai.doan.exception.ErrorCode;
import com.thai.doan.security.CustomUserDetails;
import com.thai.doan.service.ScheduleService;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Data
public class ScheduleServiceImpl implements ScheduleService {
  private final ScheduleRepository scheduleRepo;
  private final StudentRepository studentRepo;
  private final SemesterRepository semesterRepo;
  private final SubjectRepository subjectRepo;
  private final DepartmentRepository departmentRepo;
  private final LecturerRepository lecturerRepo;
  private final SessionRepository sessionRepo;
  private final ClassesRepository classesRepo;
  private final ClassroomRepository classroomRepo;
  @PersistenceContext
  private EntityManager em;
  private Logger logger = LogManager.getLogger(ScheduleServiceImpl.class);

  @Override
  public List<Schedule> getSchedule(int subjectType) {
    logger.debug("start getSchedule");
    CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();
    User user = customUserDetails.getUser();
    Student std = user.getStudent();
    logger.debug("getMaxTermOfStudent: " + semesterRepo.getMaxTermOfStudent(std));
    if (semesterRepo.getMaxTermOfStudent(std) > 1) {
      // after semester 1, the general subject will not appear
      return scheduleRepo.getCurrentSchedules(std, subjectType, std.getSession().getId(), false);
    } else
      return scheduleRepo.getCurrentSchedules(std, subjectType, std.getSession().getId(), true);
  }

  @Override
  public Schedule createNewSchedule(NewScheduleRequest newSchlReq) {
    Optional<Department> department = departmentRepo.findById(newSchlReq.getDepartment());
    Optional<Subject> subject = subjectRepo.findById(newSchlReq.getSubject());
    Optional<Lecturer> lecturer = lecturerRepo.findByIdAndDepartment(newSchlReq.getLecturer(), department.get());
    Optional<Classes> studentsClass = classesRepo.findById(newSchlReq.getClassId());
    Optional<Semester> semester = semesterRepo.findById(newSchlReq.getSemester());
    Optional<Classroom> classroom = classroomRepo.findById(newSchlReq.getClassroomId());

    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Schedule> query = builder.createQuery(Schedule.class);
    Root<Schedule> root = query.from(Schedule.class);

    Predicate isSameSemester = builder.equal(root.get("semester").get("id"), newSchlReq.getSemester());
    Predicate isSameSubject = builder.equal(root.get("subject").get("id"), newSchlReq.getSubject());
    Predicate isSameWeekDay = builder.equal(root.get("weekDay"), newSchlReq.getWeek());
    Predicate isSamePeriodType = builder.equal(root.get("periodType"), newSchlReq.getPeriodType());
    Predicate isSameClass = builder.equal(root.get("classes").<Integer>get("id"), newSchlReq.getClassId());
    Predicate isSameClassroom = builder.equal(root.get("classroom").<Integer>get("id"), newSchlReq.getClassroomId());
    Predicate isSameLecturer = builder.equal(root.get("lecturer").<Integer>get("id"), newSchlReq.getLecturer());
    Predicate isSameStartPeriod = builder.equal(root.get("startPeriod"), newSchlReq.getStartPeriod());
    Predicate isSameEndPeriod = builder.equal(root.get("endPeriod"), newSchlReq.getEndPeriod());

    Predicate isStartEqualStartPeriod = builder.equal(root.get("startPeriod"), newSchlReq.getStartPeriod());
    Predicate isEndEqualStartPeriod = builder.equal(root.get("startPeriod"), newSchlReq.getEndPeriod());

    Predicate isStartEqualEndPeriod = builder.equal(root.get("endPeriod"), newSchlReq.getEndPeriod());
    Predicate isEndEqualEndPeriod = builder.equal(root.get("endPeriod"), newSchlReq.getStartPeriod());

    Predicate hasStartPeriod = builder.greaterThanOrEqualTo(root.get("startPeriod"), newSchlReq.getStartPeriod());
    Predicate hasEndPeriod = builder.lessThanOrEqualTo(root.get("endPeriod"), newSchlReq.getEndPeriod());

    // trùng môn
    query.where(builder.and(isSameSemester, isSameSubject, isSameWeekDay, isSamePeriodType, isSameClass));
    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() > 0) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng môn học trong ngày");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng môn học trong ngày");
    }

    // trùng phòng học
    query.where(builder.and(isSameSemester, isSameWeekDay, isSamePeriodType,
        isSameStartPeriod, isSameEndPeriod, isSameClassroom));
    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() > 0) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng phòng học trong ngày");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng phòng học trong ngày");
    }

    // bằng bắt đầu
    query.where(builder.and(isSameSemester, isSameWeekDay, isSamePeriodType, isStartEqualStartPeriod, isSameClass));
    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() > 0) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
    }
    query.where(builder.and(isSameSemester, isSameWeekDay, isSamePeriodType, isEndEqualStartPeriod, isSameClass));
    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() > 0) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
    }

    // bằng kết thúc
    query.where(builder.and(isSameSemester, isSameWeekDay, isSamePeriodType, isStartEqualEndPeriod, isSameClass));
    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() > 0) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
    }
    query.where(builder.and(isSameSemester, isSameWeekDay, isSamePeriodType, isEndEqualEndPeriod, isSameClass));
    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() > 0) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
    }

    // cùng lúc lớn hơn bằng bắt đầu và nhỏ hơn bằng kết thúc
    query.where(builder.and(isSameSemester, isSameWeekDay, isSamePeriodType, hasStartPeriod, hasEndPeriod, isSameClass));
    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() > 0) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
    }

    if (newSchlReq.getStartPeriod() > newSchlReq.getEndPeriod()) {
      throw new ResponseStatusException(HttpStatus.OK, "Tiết bắt đầu phải nhỏ hơn Tiết kết thúc. ");
    }
    if (newSchlReq.getStartDay().isAfter(newSchlReq.getEndDay())) {
      throw new ResponseStatusException(HttpStatus.OK, "Ngày bắt đầu phải nhỏ hơn Ngày kết thúc. ");
    }

    Schedule schedule = Schedule.builder()
        .startPeriod(newSchlReq.getStartPeriod())
        .endPeriod(newSchlReq.getEndPeriod())
        .weekDay(newSchlReq.getWeek())
        .startDay(newSchlReq.getStartDay())
        .endDay(newSchlReq.getEndDay())
        .periodType(newSchlReq.getPeriodType())
        .semester(semester.get())
        .lecturer(lecturer.get())
        .subject(subject.get())
        .classes(studentsClass.get())
        .classroom(classroom.get())
        .build();

    try {
      return scheduleRepo.save(schedule);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, ErrorCode.SAVE_ERROR);
    }
  }

  @Override
  public List<Schedule> getWithClassIdAndSemesterId(int classId, int semesterId) {
    Classes clazz = classesRepo.findById(classId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
    );
    Semester semester = semesterRepo.findById(semesterId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
    );
    return scheduleRepo.findByClassesAndSemester(clazz, semester);
  }

  @Override
  public Schedule updateSchedule(ScheduleUpdatingRequest scheduleUpdatingReq, String id) {
    Optional<Schedule> scheduleOpl = scheduleRepo.findById(Integer.parseInt(id));
    if (!scheduleOpl.isPresent()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    Semester semester = semesterRepo.findById(scheduleUpdatingReq.getSemester()).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
    );
    Lecturer lecturer = lecturerRepo.findById(scheduleUpdatingReq.getLecturer()).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
    );
    Subject subject = subjectRepo.findById(scheduleUpdatingReq.getSubject()).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
    );
    Classes clazz = classesRepo.findById(scheduleUpdatingReq.getClasses()).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
    );
    Classroom classroom = classroomRepo.findById(scheduleUpdatingReq.getClassroomId()).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.FORBIDDEN)
    );

    Schedule schedule = scheduleOpl.get();
    schedule.setStartDay(scheduleUpdatingReq.getStartDay());
    schedule.setEndDay(scheduleUpdatingReq.getEndDay());
    schedule.setWeekDay(scheduleUpdatingReq.getWeekDay());
    schedule.setPeriodType(scheduleUpdatingReq.getPeriodType());
    schedule.setStartPeriod(scheduleUpdatingReq.getStartPeriod());
    schedule.setEndPeriod(scheduleUpdatingReq.getEndPeriod());
    schedule.setSemester(semester);
    schedule.setLecturer(lecturer);
    schedule.setSubject(subject);
    schedule.setClasses(clazz);
    schedule.setClassroom(classroom);

    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Schedule> query = builder.createQuery(Schedule.class);
    Root<Schedule> root = query.from(Schedule.class);

    Predicate isSameSemester = builder.equal(root.get("semester").get("id"), scheduleUpdatingReq.getSemester());
    Predicate isSameSubject = builder.equal(root.get("subject").get("id"), scheduleUpdatingReq.getSubject());
    Predicate isSameWeekDay = builder.equal(root.get("weekDay"), schedule.getWeekDay());
    Predicate isSamePeriodType = builder.equal(root.get("periodType"), schedule.getPeriodType());

    Predicate isStartEqualStartPeriod = builder.equal(root.get("startPeriod"), schedule.getStartPeriod());
    Predicate isEndEqualStartPeriod = builder.equal(root.get("startPeriod"), schedule.getEndPeriod());

    Predicate isStartEqualEndPeriod = builder.equal(root.get("endPeriod"), schedule.getEndPeriod());
    Predicate isEndEqualEndPeriod = builder.equal(root.get("endPeriod"), schedule.getStartPeriod());

    Predicate hasStartPeriod = builder.greaterThanOrEqualTo(root.get("startPeriod"), schedule.getStartPeriod());
    Predicate hasEndPeriod = builder.lessThanOrEqualTo(root.get("endPeriod"), schedule.getEndPeriod());

    query.where(builder.and(isSameSemester, isSameSubject, isSameWeekDay, isSamePeriodType));

    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() == 1) {
        AtomicBoolean isEqual = new AtomicBoolean(false);
        existedSchedule.forEach(s -> {
          if (schedule.equals(s)) {
            isEqual.set(true);
          }
        });
        if (!isEqual.get()) {
          throw new ResponseStatusException(HttpStatus.OK, "Trùng buổi học trong ngày");
        }
      }
      if (existedSchedule.size() > 1) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng buổi học trong ngày");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng buổi học trong ngày");
    }

    // bằng bắt đầu
    query.where(builder.and(isSameSemester, isSameWeekDay, isSamePeriodType, isStartEqualStartPeriod));
    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() == 1) {
        AtomicBoolean isEqual = new AtomicBoolean(false);
        existedSchedule.forEach(s -> {
          if (schedule.equals(s)) {
            isEqual.set(true);
          }
        });
        if (!isEqual.get()) {
          throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
        }
      }
      if (existedSchedule.size() > 1) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
    }
    query.where(builder.and(isSameSemester, isSameWeekDay, isSamePeriodType, isEndEqualStartPeriod));
    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() == 1) {
        AtomicBoolean isEqual = new AtomicBoolean(false);
        existedSchedule.forEach(s -> {
          if (schedule.equals(s)) {
            isEqual.set(true);
          }
        });
        if (!isEqual.get()) {
          throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
        }
      }
      if (existedSchedule.size() > 1) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
    }

    // bằng kết thúc
    query.where(builder.and(isSameSemester, isSameWeekDay, isSamePeriodType, isStartEqualEndPeriod));
    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() == 1) {
        AtomicBoolean isEqual = new AtomicBoolean(false);
        existedSchedule.forEach(s -> {
          if (schedule.equals(s)) {
            isEqual.set(true);
          }
        });
        if (!isEqual.get()) {
          throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
        }
      }
      if (existedSchedule.size() > 1) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
    }
    query.where(builder.and(isSameSemester, isSameWeekDay, isSamePeriodType, isEndEqualEndPeriod));
    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() == 1) {
        AtomicBoolean isEqual = new AtomicBoolean(false);
        existedSchedule.forEach(s -> {
          if (schedule.equals(s)) {
            isEqual.set(true);
          }
        });
        if (!isEqual.get()) {
          throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
        }
      }
      if (existedSchedule.size() > 1) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
    }

    // cùng lúc lớn hơn bằng bắt đầu và nhỏ hơn bằng kết thúc
    query.where(builder.and(isSameSemester, isSameWeekDay, isSamePeriodType, hasStartPeriod, hasEndPeriod));
    try {
      List<Schedule> existedSchedule = em.createQuery(query.select(root)).getResultList();
      if (existedSchedule.size() == 1) {
        AtomicBoolean isEqual = new AtomicBoolean(false);
        existedSchedule.forEach(s -> {
          if (schedule.equals(s)) {
            isEqual.set(true);
          }
        });
        if (!isEqual.get()) {
          throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
        }
      }
      if (existedSchedule.size() > 1) {
        throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.OK, "Trùng tiết trong một buổi");
    }

    if (schedule.getStartPeriod() > schedule.getEndPeriod()) {
      throw new ResponseStatusException(HttpStatus.OK, "Tiết bắt đầu phải nhỏ hơn Tiết kết thúc. ");
    }
    if (schedule.getStartDay().isAfter(schedule.getEndDay())) {
      throw new ResponseStatusException(HttpStatus.OK, "Ngày bắt đầu phải nhỏ hơn Ngày kết thúc. ");
    }
    try {
      return scheduleRepo.save(schedule);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getCause().toString());
    }
  }

  @Override
  public Schedule getOneSchedule(String id) {
    try {
      Optional<Schedule> schedule = scheduleRepo.findById(Integer.parseInt(id));
      if (!schedule.isPresent()) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
      }
      return schedule.get();
    } catch (NumberFormatException e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
  }

  @Override
  public void delete(String id) {
    try {
      Optional<Schedule> schedule = scheduleRepo.findById(Integer.parseInt(id));
      if (!schedule.isPresent()) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
      }
      scheduleRepo.delete(schedule.get());
    } catch (NumberFormatException e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
    }
  }

  //test specification
  @Override
  public List<Schedule> lecturerNameLike(String name) {
    if (name == null) {
      return scheduleRepo.findAll();
    }
    Specification<Schedule> scheduleSpecifications = ((root, criteriaQuery, criteriaBuilder)
        -> criteriaBuilder.like(root.get("lecturer").get("name"), "%" + name + "%"));
    return scheduleRepo.findAll(scheduleSpecifications);
  }

  @Override
  public List<Schedule> retrieveSchedules(Integer weekDay,
                                          Integer periodType,
                                          Integer semesterId) {
    if (weekDay == null && periodType == null && semesterId == null) {
      return new ArrayList<>();
    }

    Specification<Schedule> weekDayEqualTo = (root, query, builder) -> Optional.ofNullable(weekDay).map(
        wkDay -> builder.equal(root.get("weekDay"), wkDay)).orElse(null);

    Specification<Schedule> periodTypeEqualTo = (root, query, builder) -> Optional.ofNullable(periodType).map(
        pdType -> builder.equal(root.get("periodType"), pdType)).orElse(null);

    Specification<Schedule> semesterIdEqualTo = (root, query, builder) -> Optional.ofNullable(semesterId).map(
        smesterId -> builder.equal(root.get("semester").<Integer>get("id"), smesterId)).orElse(null);

    Specification<Schedule> scheduleSpecifications = weekDayEqualTo.and(periodTypeEqualTo).and(semesterIdEqualTo);

    return scheduleRepo.findAll(scheduleSpecifications);
  }
}
