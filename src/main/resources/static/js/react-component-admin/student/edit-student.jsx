function App() {
  const [students, setStudent] = React.useState(null)
  const [sessions, setSessions] = React.useState(null)
  const [departments, setDepartments] = React.useState(null)
  const [classes, setClasses] = React.useState(null)
  const [tabulator, setTabulator] = React.useState(null)
  const sessionInputRef = React.useRef()
  const departmentInputRef = React.useRef()
  const classInputRef = React.useRef()
  const scheduleTableRef = React.useRef()
  React.useEffect(() => {
    Promise.all([getSessionPromise(), getDepartmentPromise()])
      .then(([sessionArr, departmentArr]) => {
        setSessions(sessionArr)
        setDepartments(departmentArr)
      })
  }, []);
  React.useEffect(() => {
    if (sessions && departments) {
      if (sessionInputRef && departmentInputRef) {
        getClasses(sessionInputRef.current.value, departmentInputRef.current.value)
          .then((classes) => {
            setClasses(classes)
          })
          .catch(err => console.log(err))
      }
    }
  }, [sessions, departments]);
  React.useEffect(() => {
    if (students) {
      while (scheduleTableRef.current.firstElementChild) {
        scheduleTableRef.current.removeChild(scheduleTableRef.current.firstChild)
      }
      setTabulator(getTabulator())
    }
  }, [students]);
  const getSessionPromise = () => axios.get('/admin/session')
  const getDepartmentPromise = () => axios.get('/api/departments')
  const getClasses = (sessionId, departmentId) => {
    return axios.get(`/api/classes?session=${sessionId}&department=${departmentId}`)
  }
  const getStudents = (classId) => {
    return axios.get(`/api/admin/students?classId=${classId}`)
  }
  // onChange
  const handleSessionChange = (e) => {
    getClasses(sessionInputRef.current.value, departmentInputRef.current.value)
      .then((classes) => {
        setClasses(classes)
      })
  }
  const handleDepartmentChange = (e) => {
    getClasses(sessionInputRef.current.value, departmentInputRef.current.value)
      .then((classes) => {
        setClasses(classes)
      })
  }
  const handleClassChange = () => {

  }
  // onClick
  const handleClassClick = () => {

  }
  // onSubmit
  const handleSubmit = (e) => {
    e.preventDefault()
    if (classInputRef.current.value !== "") {
      getStudents(classInputRef.current.value)
        .then((students) => {
          setStudent(students)
        })
        .catch((err) => {
          console.log(err)
        })
      if (scheduleTableRef.current.hasAttribute("class")) {
        scheduleTableRef.current.removeAttribute("class")
      }
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
  // utils
  const getTabulator = () => {
    const tableStudents = students.map(s => ({...s, gender: s.gender ? 'Nữ' : 'Nam'}))
    const table = new Tabulator("#schedule-table", {
      // height: 205
      data: tableStudents, //assign data to table
      layout: "fitColumns", //fit columns to width of table (optional)
      columns: [ //Define Table Columns
        {title: "Mã Số Sinh Viên", field: "user.username"},
        {title: "Họ", field: "firstName"},
        {title: "Tên", field: "lastName"},
        {title: "Ngày Sinh", field: "birth"},
        {title: "Nơi Sinh", field: "place"},
        {title: "Số Điện Thoại", field: "phoneNumber"},
        {title: "Giới Tính", field: "gender"},
      ],
      rowClick: function (e, row) {
        window.location.href = "/admin/sinh-vien/sua?studentId=" + row.getData().id
      },
    })
    if (students.length > 0) {
      //trigger download of data.csv file
      document.getElementById("download-csv").addEventListener("click",
        () => {
          table.download("csv", "data-" + Math.random().toString().replace('0\.', '') + ".csv")
        });
      //trigger download of data.xlsx file
      document.getElementById("download-xlsx").addEventListener("click", () =>
        table.download("xlsx", "data-" + Math.random().toString().replace('0\.', '') + ".xlsx",
          {sheetName: "Data"}));
    }
  };
  return (
    <div>
      <h1>Sửa Sinh Viên</h1>
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
        <button type="submit" className="btn btn-primary"
                disabled={!classes}>Tìm
        </button>
      </form>
      <br/>
      {students && students.length > 0 &&
      <div>
        <button id="download-csv" className="btn btn-success mr-2 mb-2">Tải CSV(Table)</button>
        <button id="download-xlsx" className="btn btn-success mb-2">Tải XLSX(Excel)</button>
      </div>
      }
      <div id="schedule-table" ref={scheduletableref}/>
    </div>
  )
}

reactdom.render(react.createelement(app), document.getelementbyid('app'))
