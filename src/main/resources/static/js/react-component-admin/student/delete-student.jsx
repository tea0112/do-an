function App() {
  const [students, setStudents] = React.useState(null)
  const [student, setStudent] = React.useState(null)
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
      getTabulator()
    }
  }, [students]);
  React.useEffect(() => {
    if (student) {
      preDeleteClick()
      console.log(student)
    }
  }, [student])

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
  const preDeleteClick = () => {
    $('#deleteModal').modal()
  }

  // onSubmit
  const onDelete = async () => {
    try {
      await axios({
        method: 'DELETE',
        url: `/api/students/${student.id}`
      })
      alert('Xoá Thành Công')
      location.reload()
    } catch (e) {
      alert('Xoá Thất Bại')
      location.reload()
    }
  }
  const handleSubmit = (e) => {
    e.preventDefault()
    if (classInputRef.current.value !== "") {
      getStudents(classInputRef.current.value)
        .then((students) => {
          setStudents(students)
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
  function comparePreviousStateAndSetStudent(cr) {
    if (student) {
      if (!_.isEqual(student, cr)) {
        setStudent(cr)
      } else {
        preDeleteClick()
      }
    } else {
      setStudent(cr)
    }
  }

  // function setStudentOutside = ()
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
        const current = _.cloneDeep(row.getData())
        comparePreviousStateAndSetStudent(current)
      },
      downloadReady: function (fileContents, blob) {
        //fileContents - the unencoded contents of the file
        //blob - the blob object for the download

        //custom action to send blob to server could be included here
        return blob; //must return a blob to proceed with the download, return false to abort download
      },
      downloadComplete: function () {
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
  }
  return (
    <div>
      <h1>Xoá Sinh Viên</h1>
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
          <select className="form-control" id="classInput" ref={classInputRef} onChange={handleClassChange}>
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
      <div id="schedule-table" ref={scheduleTableRef}/>
      {/*start modal*/}
      {student && (
        <div className="modal fade" id="deleteModal" tabIndex="-1" role="dialog"
             aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="exampleModalLabel">
                  Bạn chắc chắn muốn xoá <b>{student && <span>{student.firstName} {student.lastName}<span> </span>
                  {student.phoneNumber}</span>}</b>?
                </h5>
                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal">Đóng</button>
                <button type="button" onClick={onDelete} className="btn btn-primary">Xoá</button>
              </div>
            </div>
          </div>
        </div>
      )}
      {/*end modal*/}
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
