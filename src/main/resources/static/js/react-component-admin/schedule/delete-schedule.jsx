// var Tabulator = require('tabulator-tables');

function App() {
  const [sessions, setSessions] = React.useState(null);
  const [departments, setDepartments] = React.useState(null);
  const [classes, setClasses] = React.useState(null);
  const [semesters, setSemesters] = React.useState(null);
  const [schedules, setSchedules] = React.useState(null);
  const [scheduleId, setScheduleId] = React.useState(null);
  const [tabulator, setTabulator] = React.useState(null);
  const sessionInputRef = React.useRef();
  const departmentInputRef = React.useRef();
  const classInputRef = React.useRef();
  const semesterInputRef = React.useRef();
  const scheduleTableRef = React.useRef();

  React.useEffect(() => {
    Promise.all([getSessionPromise(), getDepartmentPromise()]).then(
      ([sessionArr, departmentArr]) => {
        setSessions(sessionArr);
        setDepartments(departmentArr);
      }
    );
  }, []);
  React.useEffect(() => {
    while (scheduleTableRef.current.firstElementChild) {
      scheduleTableRef.current.removeChild(scheduleTableRef.current.firstChild);
    }
    if (schedules && schedules.length !== 0) {
      setTabulator(getTabulator());
    }
  }, [schedules]);
  React.useEffect(() => {
    if (departments) {
      getClasses();
    }
  }, [departments]);
  React.useEffect(() => {
    if (classes) {
      getSemester();
    }
  }, [classes]);
  React.useEffect(() => {
    if (scheduleId) {
    }
  }, [scheduleId]);


  const getSessionPromise = () => axios.get('/admin/session');
  const getDepartmentPromise = () => axios.get('/api/departments');
  const getSemester = () => {
    const sessionId = sessionInputRef.current.value;
    axios
      .get(`/api/semesters?sessionId=${sessionId}`)
      .then((semesters) => setSemesters(semesters));
  };
  const getClasses = () => {
    axios
      .get(
        `/api/classes?session=${sessionInputRef.current.value}&department=${departmentInputRef.current.value}`
      )
      .then((classes) => setClasses(classes));
  };
  const getSchedule = () => {
    axios
      .get(
        `/api/schedules?classId=${classInputRef.current.value}&semesterId=${semesterInputRef.current.value}`
      )
      .then((schedules) => setSchedules(deepFreeze(schedules)));
  };
  const handleSessionChange = (e) => {
    getClasses();
    getSemester();
  };
  const handleDepartmentChange = (e) => {
    getClasses();
  };
  const handleClassChange = () => {
    getSemester();
  };
  const handleSemesterChange = () => {
  };
  //click
  const handleSubmit = (e) => {
    e.preventDefault();
    if (semesterInputRef.current.value) {
      getSchedule();
      if (scheduleTableRef.current.hasAttribute('class')) {
        scheduleTableRef.current.removeAttribute('class');
      }
    } else {
      alert('Không có dữ liệu')
    }
  };
  // option
  const selectSession = (sessionOpt, listOpt) => {
    return (
      <select
        className="form-control"
        id="sessionInput"
        onChange={handleSessionChange}
        ref={sessionInputRef}
      >
        {sessionOpt(listOpt)}
      </select>
    );
  };
  const sessionOption = (sessionData) => {
    return sessionData.map((session) => (
      <option key={session.id} value={session.id}>
        {session.name}
      </option>
    ));
  };
  const departmentOption = (departmentData) =>
    departmentData.map((department) => {
      return (
        <option key={department.id} value={department.id}>
          {department.name}
        </option>
      );
    });
  const classOption = (classData) =>
    classData.map((clazz) => {
      return (
        <option key={clazz.id} value={clazz.id}>
          {clazz.name}
        </option>
      );
    });
  const semesterOption = (semesterData) =>
    semesterData.map((semester) => {
      return (
        <option key={semester.id} value={semester.id}>
          {semester.termNumber}
        </option>
      );
    });
  // request
  const onDelete = () => {
    axios({
      method: 'DELETE',
      url: `/api/admin/schedules/${scheduleId}`,
    })
      .then(() => {
        alert('Xoá Thành Công')
        location.reload()
      })
      .catch(err => {
        alert('Xoá Thất Bại ' + err)
        location.reload()
      })
  }
  const preDeleteClick = (setSchId) => {
    console.log(setSchId)
    setScheduleId(setSchId)
    $("#deleteModal").modal()
  }

  // utils
  const getSubjectName = (schedules, id) => {
    if (schedules) {
      if (id) {
        const schedule = schedules.find(s => s.id === id)
        if (schedule) {
          if (schedule.subject !== undefined) {
            return schedule.subject.name
          }
        }
      }
    }
  }

  const getTabulator = () => {
    const changedSchedules = Immutable.fromJS(schedules).toJS().map(s => {
      if (s.subject.subjectType === 0) {
        s.subject.subjectType = 'lý thuyết'
      }
      if (s.subject.subjectType === 1) {
        s.subject.subjectType = 'thực hành'
      }
      if (s.periodType === 0) {
        s.periodType = 'sáng'
      }
      if (s.periodType === 1) {
        s.periodType = 'chiều'
      }
      if (s.periodType === 2) {
        s.periodType = 'tối'
      }
      return s
    })

    const table = new Tabulator('#schedule-table', {
      data: JSON.parse(JSON.stringify(changedSchedules)),
      layout: 'fitColumns',
      columns: [
        {title: 'Môn', field: 'subject.name'},
        {title: 'Loại Môn', field: 'subject.subjectType'},
        {title: 'Giảng Viên', field: 'lecturer.name'},
        {title: 'Thứ', field: 'weekDay'},
        {title: 'Tiết Bắt Đầu', field: 'startPeriod'},
        {title: 'Tiết Kết Thúc', field: 'endPeriod'},
        {title: 'Buổi', field: 'periodType'},
      ],
      rowClick: function (e, row) {
        preDeleteClick(row.getData().id)
      },
    })

    if (changedSchedules.length > 0) {
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
      <h1 className="h3 mb-4 text-gray-800">Xoá Thời Khoá Biểu</h1>
      <form name="addStudent" onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Khoá</label>
          {sessions && selectSession(sessionOption, sessions)}
        </div>
        <div className="form-group">
          <label>Khoa</label>
          <select
            className="form-control"
            id="departmentInput"
            onChange={handleDepartmentChange}
            ref={departmentInputRef}
          >
            {departments && departmentOption(departments)}
          </select>
        </div>
        <div className="form-group">
          <label>Lớp</label>
          <select
            className="form-control"
            id="classInput"
            ref={classInputRef}
            onChange={handleClassChange}
          >
            {classes && classOption(classes)}
          </select>
        </div>
        <div className="form-group">
          <label>Học Kỳ</label>
          <select
            className="form-control"
            id="semesterInput"
            ref={semesterInputRef}
            onChange={handleSemesterChange}
          >
            {semesters && semesterOption(semesters)}
          </select>
        </div>
        <button
          type="submit"
          className="btn btn-primary"
          disabled={!classes || !semesters}
        >
          Tìm
        </button>
      </form>
      <br/>
      {schedules && schedules.length > 0 &&
      <div>
        <button id="download-csv" className="btn btn-success mr-2 mb-2">Tải CSV(Table)</button>
        <button id="download-xlsx" className="btn btn-success mb-2">Tải XLSX(Excel)</button>
      </div>
      }
      <div id="schedule-table" ref={scheduleTableRef}/>
      {/*start modal*/}
      {scheduleId && schedules && (
        <div className="modal fade" id="deleteModal" tabIndex="-1" role="dialog"
             aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="exampleModalLabel">
                  Bạn chắc chắn muốn xoá
                  môn <b>{scheduleId && schedules && getSubjectName(schedules, scheduleId)}</b>?
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
  );
}

ReactDOM.render(React.createElement(App), document.getElementById('app'));

function deepFreeze(object) {
  const propNames = Object.getOwnPropertyNames(object);
  for (const name of propNames) {
    const value = object[name];
    if (value && typeof value === "object") {
      deepFreeze(value);
    }
  }
  return Object.freeze(object);
}
