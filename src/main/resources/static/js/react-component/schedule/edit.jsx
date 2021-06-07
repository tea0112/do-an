// var Tabulator = require('tabulator-tables');

function App() {
  const [sessions, setSessions] = React.useState(null)
  const [departments, setDepartments] = React.useState(null)
  const [classes, setClasses] = React.useState(null)
  const [semesters, setSemesters] = React.useState(null)
  const [schedules, setSchedules] = React.useState(null)
  const [tabulator, setTabulator] = React.useState(null)
  const sessionInputRef = React.useRef()
  const departmentInputRef = React.useRef()
  const classInputRef = React.useRef()
  const semesterInputRef = React.useRef()
  const scheduleTableRef = React.useRef()

  React.useEffect(() => {
    Promise.all([getSessionPromise(), getDepartmentPromise()])
      .then(([sessionArr, departmentArr]) => {
        setSessions(sessionArr)
        setDepartments(departmentArr)
      })
  }, []);
  React.useEffect(() => {
    while(scheduleTableRef.current.firstElementChild) {
      scheduleTableRef.current.removeChild(scheduleTableRef.current.firstChild)
    }
    if (schedules && schedules.length !== 0) {
      setTabulator(getTabulator())
    }
  }, [schedules]);
  const getSessionPromise = () => axios.get('/admin/session')
  const getDepartmentPromise = () => axios.get('/api/departments')
  const getSemester = () => {
    const sessionId = sessionInputRef.current.value
    axios.get(`/api/semesters?sessionId=${sessionId}`)
      .then((semesters) => setSemesters(semesters))
  }
  const getClasses = () => {
    axios.get(`/api/classes?session=${sessionInputRef.current.value}&department=${departmentInputRef.current.value}`)
      .then((classes) => setClasses(classes))
  }
  const getSchedule = () => {
    axios.get(`/api/schedules?classId=${classInputRef.current.value}&semesterId=${semesterInputRef.current.value}`)
      .then((schedules) => setSchedules(schedules))
  }
  const handleSessionChange = (e) => {
    setClasses(null)
    setSemesters(null)
  }
  const handleDepartmentChange = (e) => {
    setClasses(null)
    setSemesters(null)
  }
  const handleClassChange = () => {

  }
  //click
  const handleClassClick = () => {
    getClasses()
  }
  const handleSemesterClick = () => {
    getSemester()
  }
  const handleSubmit = (e) => {
    e.preventDefault()
    getSchedule()
    if (scheduleTableRef.current.hasAttribute("class")) {
      scheduleTableRef.current.removeAttribute("class")
    }
  }
  // option
  const selectSession = (sessionOpt, listOpt) => {
    return (
      <select className="form-control" id="sessionInput" onChange={handleSessionChange} ref={sessionInputRef}>
        {sessionOpt(listOpt)}
      </select>
    )
  }
  const sessionOption = (sessionData) => {
    return sessionData.map((session) => <option key={session.id} value={session.id}>{session.name}</option>)
  }
  const departmentOption = (departmentData) => departmentData.map((department) => {
    return (
      <option key={department.id} value={department.id}>{department.name}</option>
    )
  })
  const classOption = (classData) => classData.map((clazz) => {
    return (
      <option key={clazz.id} value={clazz.id}>{clazz.name}</option>
    )
  })
  const semesterOption = (semesterData) => semesterData.map((semester) => {
    return (
      <option key={semester.id} value={semester.id}>{semester.termNumber}</option>
    )
  })
  // utils
  const getTabulator = () => new Tabulator("#schedule-table", {
    // height: 205, // set height of table (in CSS or here), this enables the Virtual DOM and improves render speed dramatically (can be any valid css height value)
    data: schedules, //assign data to table
    layout: "fitColumns", //fit columns to width of table (optional)
    columns: [ //Define Table Columns
      {title: "Môn", field: "subject.name"},
      {title: "Loại", field: "subject.subjectType"},
      {title: "Giảng Viên", field: "lecturer.name"},
      {title: "Thứ", field: "weekDay"},
      {title: "Tiết Bắt Đầu", field: "startPeriod"},
      {title: "Tiết Kết Thúc", field: "endPeriod"},
      {title: "Buổi", field: "periodType"},
    ],
    rowClick: function (e, row) { //trigger an alert message when the row is clicked
      alert("Row " + row.getData().id + " Clicked!!!!");
    },
  });
  return (
    <div>
      <h1 className="h3 mb-4 text-gray-800">Sửa Thời Khoá Biểu</h1>
      <form name="addStudent" onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Khoá</label>
          {sessions && selectSession(sessionOption, sessions)}
        </div>
        <div className="form-group">
          <label>Khoa</label>
          <select className="form-control" id="departmentInput" onChange={handleDepartmentChange}
                  ref={departmentInputRef}>
            {departments && departmentOption(departments)}
          </select>
        </div>
        <div className="form-group">
          <label>Lớp</label>
          <select className="form-control" id="classInput" ref={classInputRef} onChange={handleClassChange}
                  onClick={handleClassClick}>
            {classes && classOption(classes)}
          </select>
        </div>
        <div className="form-group">
          <label>Học Kỳ</label>
          <select className="form-control" id="semesterInput" ref={semesterInputRef} onClick={handleSemesterClick}>
            {semesters && semesterOption(semesters)}
          </select>
        </div>
        <button type="submit" className="btn btn-primary">Tìm</button>
      </form>
      <br/>
      <br/>
      <br/>
      <div id="schedule-table" ref={scheduleTableRef}/>
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))