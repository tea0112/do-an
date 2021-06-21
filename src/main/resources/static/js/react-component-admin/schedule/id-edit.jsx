function App() {
  const [schedule, setSchedule] = React.useState(null)
  const [id, setId] = React.useState(null)
  const [departments, setDepartments] = React.useState(null)
  const [subjects, setSubjects] = React.useState(null)
  const [lecturers, setLecturers] = React.useState(null)
  const [sessions, setSessions] = React.useState(null)
  const [classes, setClasses] = React.useState(null)
  const [semesters, setSemesters] = React.useState(null)
  const [startDayInput, setStartDayInput] = React.useState(null)
  const [endDayInput, setEndDayInput] = React.useState(null)
  const [periodType, setPeriodType] = React.useState(null)
  const [subjectType, setSubjectType] = React.useState(null)
  const [subjectName, setSubjectName] = React.useState(null)
  const [lectureHall, setLectureHall] = React.useState(null)
  const [classroom, setClassroom] = React.useState(null)
  const [lectureHalls, setLectureHalls] = React.useState(null)
  const [classrooms, setClassrooms] = React.useState(null)

  const departmentRef = React.useRef();
  const subjectTypeRef = React.useRef();
  const subjectRef = React.useRef();
  const sessionRef = React.useRef();
  const periodTypeRef = React.useRef();
  const endDayRef = React.useRef();
  const startDayRef = React.useRef();
  const weekDayRef = React.useRef();
  const endPeriodRef = React.useRef()
  const startPeriodRef = React.useRef()
  const semesterRef = React.useRef()
  const classRef = React.useRef()
  const lecturerRef = React.useRef()

  React.useEffect(async () => {
    getSchedule()
      .then(response => {
        setSchedule(response)
        setStartDayInput(response.startDay)
        setEndDayInput(response.endDay)
        setId(response.id)
        setSubjectType(response.subject.subjectType)
        setSubjectName(response.subject.id)
        setPeriodType(response.periodType)
      })
      .catch((err) => {
        console.log(err)
      })
    getAllDepartment()
      .then(response => {
        setDepartments(response)
      })
      .catch((err) => {
        console.log(err)
      })
    const lectureHalls = await getLectureHall()
    setLectureHalls(lectureHalls)
  }, [])
  React.useEffect(() => {
    getSession()
      .then((sessions) => setSessions(sessions))
      .catch((err) => console.log(err))
  }, [])
  React.useEffect(() => {
    if (schedule) {
      getSemesters(schedule.semester.session.id)
        .then((semesters) => setSemesters(semesters))
        .catch((err) => console.log(err))
    }
  }, [schedule])
  React.useEffect(() => {
    if (schedule) {
      getSubject(subjectTypeRef.current.value, schedule.subject.department.id)
        .then((subjects) => setSubjects(subjects))
        .catch((err) => console.log(err))
    }
  }, [schedule])
  React.useEffect(() => {
    if (schedule) {
      getLecturer(schedule.subject.department.id)
        .then((lecturers) => setLecturers(lecturers))
        .catch((err) => console.log(err))
    }
  }, [schedule])
  React.useEffect(() => {
    if (schedule) {
      setLectureHall(schedule.classroom.lectureHall.id)
      setClassroom(schedule.classroom.id)
      getClasses(schedule.subject.department.id, schedule.semester.session.id)
        .then((classes) => setClasses(classes))
        .catch((err) => console.log(err))
    }
  }, [schedule])
  React.useEffect(() => {
    if (subjectType) {
      getSubject(subjectTypeRef.current.value, departmentRef.current.value)
        .then((subjects) => setSubjects(subjects))
        .catch((err) => console.log(err))
    }
  }, [subjectType])
  React.useEffect(async () => {
    if (lectureHalls && lectureHalls.length > 0) {
    }
  }, [lectureHalls])
  React.useEffect(async () => {
    if (lectureHall) {
      const classrooms = await getClassroomWithLectureHallId(lectureHall)
      setClassrooms(classrooms)
    }
  }, [lectureHall])
  React.useEffect(async () => {
    if (classrooms && classrooms.length > 0) {
    }
  }, [classrooms])

  // fetch
  const getSchedule = () => {
    return axios.get(`/api/admin/schedules/${getParamValue('scheduleId')}`)
  }
  const getAllDepartment = () => {
    return axios.get(`/api/departments`)
  }
  const getSubject = (subjectType, departmentId) => {
    return axios.get(
      `/admin/subject?subjectType=${subjectType}&departmentId=${departmentId}`
    )
  }
  const getLecturer = (departmentId) => {
    return axios.get(
      `/api/admin/lecturers?departmentId=${departmentId}`
    )
  }
  const getSession = () => {
    return axios.get(
      `/admin/session`
    )
  }
  const getClasses = (departmentId, sessionId) => {
    return axios.get(
      `/api/classes?department=${departmentId}&session=${sessionId}`
    )
  }
  const getSemesters = (sessionId) => {
    return axios.get(
      `/api/semesters?sessionId=${sessionId}`
    )
  }
  const getLectureHall = () => {
    return axios.get(`/api/lecturerHalls`)
  }

  const getClassroomWithLectureHallId = (lectureHallId) => {
    return axios.get(`/api/admin/classrooms?lectureHallId=${lectureHallId}`)
  }
  // component
  const departmentOption = (departmentData, currentDepartment) => departmentData.map((department) => {
    if (department.id === currentDepartment.id) {
      return (
        <option key={department.id} value={department.id} selected={true}>{department.name}</option>
      )
    } else
      return (
        <option key={department.id} value={department.id}>{department.name}</option>
      )
  })
  const subjectOption = (subjectData, currentSubject) => subjectData.map((subject) => {
    if (subject.id === currentSubject.id) {
      return (
        <option key={subject.id} value={subject.id} selected={true}>{subject.name}</option>
      )
    } else
      return (
        <option key={subject.id} value={subject.id}>{subject.name}</option>
      )
  })
  const lecturerOption = (lecturerData, currentLecturer) => lecturerData.map((lecturer) => {
    if (lecturer.id === currentLecturer.id) {
      return (
        <option key={lecturer.id} value={lecturer.id} selected={true}>{lecturer.name}</option>
      )
    } else
      return (
        <option key={lecturer.id} value={lecturer.id}>{lecturer.name}</option>
      )
  })
  const sessionOption = (sessionData, currentSession) => sessionData.map((session) => {
    if (session.id === currentSession.id) {
      return (
        <option key={session.id} value={session.id} selected={true}>{session.name}</option>
      )
    } else
      return (
        <option key={session.id} value={session.id}>{session.name}</option>
      )
  })
  const classOption = (classData, currentClass) => classData.map((clazz) => {
    if (clazz.id === currentClass.id) {
      return (
        <option key={clazz.id} value={clazz.id} selected={true}>{clazz.name}</option>
      )
    } else
      return (
        <option key={clazz.id} value={clazz.id}>{clazz.name}</option>
      )
  })
  const semesterOption = (semesterData, currentSemester) => semesterData.map((semester) => {
    if (semester.id === currentSemester.id) {
      return (
        <option key={semester.id} value={semester.id} selected={true}>{semester.termNumber}</option>
      )
    } else
      return (
        <option key={semester.id} value={semester.id}>{semester.termNumber}</option>
      )
  })
  const startPeriodOption = (currentStartPeriod) => {
    return Array(6).fill(null).map((e, i) => {
      const periodNumber = i + 1;
      if (periodNumber === currentStartPeriod) {
        return (
          <option key={periodNumber} value={periodNumber} selected={true}>{periodNumber}</option>
        )
      }
      return (
        <option key={periodNumber} value={periodNumber}>{periodNumber}</option>
      )
    })
  }
  const endPeriodOption = (currentEndPeriod) => {
    return Array(6).fill(null).map((e, i) => {
      const periodNumber = i + 1;
      if (periodNumber === currentEndPeriod) {
        return (
          <option key={periodNumber} value={periodNumber} selected={true}>{periodNumber}</option>
        )
      }
      return (
        <option key={periodNumber} value={periodNumber}>{periodNumber}</option>
      )
    })
  }
  const weekDayOption = (currentWeekDayData) => {
    return Array(7).fill(null).map((e, i) => {
      const periodNumber = i + 2;
      if (currentWeekDayData === periodNumber) {
        return (
          <option key={periodNumber} value={periodNumber} selected={true}>{periodNumber}</option>
        )
      }
      return (
        <option key={periodNumber} value={periodNumber}>{periodNumber}</option>
      )
    })
  }
  const periodTypeOption = (current) => {
    return Array(3).fill(null).map((e, i) => {
      if (current === i) {
        return <option key={i} value={i}>{i === 0 && 'Sáng'}{i === 1 && 'chiều'}{i === 2 && 'tối'}</option>
      }
      return <option key={i} value={i}>{i === 0 && 'Sáng'}{i === 1 && 'chiều'}{i === 2 && 'tối'}</option>
    })
  }
  const lectureHallOption = (lectureHall) => lectureHall.map((lectureHall) => {
    return (
      <option key={lectureHall.id} value={lectureHall.id}>{lectureHall.name} - {lectureHall.address}</option>
    )
  })
  const classroomOption = (classroom) => classroom.map((classroom) => {
    return (
      <option key={classroom.id} value={classroom.id}>{classroom.name}</option>
    )
  })
  // onChange
  const subjectNameChange = (e) => {
    setSubjectName(e.target.value)
  }
  const handleChangeDepartment = (e) => {
    getSubject(subjectTypeRef.current.value, e.target.value)
      .then(subjects => setSubjects(subjects))
      .catch(err => console.log(err))
    getLecturer(e.target.value)
      .then(lecturers => setLecturers(lecturers))
      .catch(err => console.log(err))
    getClasses(e.target.value, sessionRef.current.value)
      .then(classes => setClasses(classes))
  }
  const onSubjectTypeChange = (e) => {
    setSubjectType(e.target.value)
  }
  const handlePeriodTypeChange = (e) => {
    setPeriodType(e.target.value)
  }
  const handleClassChange = (e) => {

  }
  const handleSessionChange = (e) => {
    getClasses(departmentRef.current.value, e.target.value)
      .then((classes) => setClasses(classes))
      .catch((err) => console.log(err))
    getSemesters(e.target.value)
      .then((semesters) => setSemesters(semesters))
      .catch((err) => console.log(err))
  }
  const handleSelectLectureHallChange = (e) => {
    setLectureHall(e.target.value)
  }
  const handleClassroomChange = (e) => {
    setClassroom(e.target.value)
  }

  // onSubmit
  const onSubmit = (e) => {
    e.preventDefault()
    axios({
      method: 'PATCH',
      url: `/api/admin/schedules/${getParamValue('scheduleId')}`,
      headers: {
        'Content-Type': 'application/json'
      },
      data: {
        startDay: startDayRef.current.value,
        endDay: endDayRef.current.value,
        weekDay: weekDayRef.current.value,
        periodType: periodTypeRef.current.value,
        startPeriod: startPeriodRef.current.value,
        endPeriod: endPeriodRef.current.value,
        semester: semesterRef.current.value,
        lecturer: lecturerRef.current.value,
        subject: subjectRef.current.value,
        classes: classRef.current.value,
        classroomId: parseInt(classroom)
      }
    })
      .then(() => {
        alert('Cập Nhật Thành Công')
        location.reload()
      })
      .catch(err => {
        alert('Cập Nhật Thất Bại ' + err)
        location.reload()
      })
  }
  // utils
  const getParamValue = (name) => {
    const urlString = window.location.href
    const url = new URL(urlString)
    const value = url.searchParams.get(name)
    if (value) {
      return value
    }
    return null
  }
  return (
    <div>
      <div className="font-weight-bold">Chỉnh sửa cho: {schedule && <span>{schedule.id} - {schedule.classes.name} -
        &nbsp;{schedule.subject.name} - {schedule.subject.subjectType ? 'thực hành' : 'lý thuyết'} -
        &nbsp;{schedule.lecturer.name}
      </span>}</div>
      <form onSubmit={onSubmit}>
        <div className="form-group">
          {schedule && <input type="text" id="idInput" value={id} style={{display: 'none'}}/>}
        </div>
        <div className="form-group">
          <label htmlFor="departmentInput">Khoa</label>
          <select className="form-control" ref={departmentRef} id="departmentInput" onChange={handleChangeDepartment}>
            {schedule && departments && departmentOption(departments, schedule.classes.department)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="subjectTypeInput">Kiểu Môn</label>
          <select className="form-control" ref={subjectTypeRef} id="subjectTypeInput"
                  value={subjectType}
                  onChange={onSubjectTypeChange}>
            <option value="0">lý thuyết</option>
            <option value="1">thực hành</option>
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="subjectInput">Tên Môn</label>
          <select className="form-control" id="subjectInput" value={subjectName} onChange={subjectNameChange}
                  ref={subjectRef}>
            {schedule && subjects && subjectOption(subjects, schedule.subject)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="lecturerInput">Giảng Viên</label>
          <div>
            <select className="form-control" id="lecturerInput" ref={lecturerRef}>
              {schedule && departments && lecturers && lecturerOption(lecturers, schedule.lecturer)}
            </select>
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="sessionInput">Khoá</label>
          <select className="form-control" id="sessionInput" ref={sessionRef} onChange={handleSessionChange}>
            {schedule && sessions && sessionOption(sessions, schedule.classes.session)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="classInput">Lớp</label>
          <select className="form-control" id="classInput" ref={classRef} onChange={handleClassChange}>
            {schedule && departments && sessions && classes && classOption(classes, schedule.classes)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="semesterInput">Học Kỳ</label>
          <select className="form-control" ref={semesterRef} id="semesterInput">
            {schedule && semesters && sessions && semesterOption(semesters, schedule.semester)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="startPeriodInput">Tiết Bắt Đầu</label>
          <select className="form-control" ref={startPeriodRef} id="startPeriodInput">
            {schedule && startPeriodOption(schedule.startPeriod)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="endPeriodInput">Tiết Kết Thúc</label>
          <select className="form-control" ref={endPeriodRef} id="endPeriodInput">
            {schedule && endPeriodOption(schedule.endPeriod)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="weekDayInput">Thứ</label>
          <select className="form-control form-control-sm" ref={weekDayRef} id="weekDayInput">
            {schedule && weekDayOption(schedule.weekDay)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="startDayInput">Ngày Bắt Đầu</label>
          <input className="form-control" value={startDayInput}
                 onChange={(e) => setStartDayInput(e.target.value)}
                 ref={startDayRef}
                 type="date" id="startDayInput"/>
        </div>
        <div className="form-group">
          <label htmlFor="endDayInput">Ngày Kết Thúc</label>
          <input className="form-control" type="date" value={endDayInput}
                 ref={endDayRef}
                 onChange={e => setEndDayInput(e.target.value)}
                 id="endDayInput"/>
        </div>
        <div className="form-group">
          <label htmlFor="periodType">Buổi</label>
          <select className="form-control" id="periodType"
                  ref={periodTypeRef}
                  value={periodType && periodType}
                  onChange={handlePeriodTypeChange}>
            {schedule && periodTypeOption(schedule.periodType)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="lectureHall">Chọn Giảng Đường</label>
          <select className="form-control"
                  id="lectureHall"
                  value={lectureHall}
                  onChange={handleSelectLectureHallChange}>
            {lectureHalls && lectureHallOption(lectureHalls)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="classroom">Chọn Phòng Học</label>
          <select className="form-control"
                  value={classroom}
                  onChange={handleClassroomChange}
                  id="classroom">
            {classrooms && classroomOption(classrooms)}
          </select>
        </div>
        <button type="submit" className="btn btn-primary">Cập Nhật</button>
      </form>
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
