function App() {
  // -state
  const [departments, setDepartments] = React.useState(null)
  const [subjectType, setSubjectType] = React.useState(null)
  const [subjects, setSubjects] = React.useState(null)
  const [subject, setSubject] = React.useState(null)

  // -ref
  const departmentInputRef = React.useRef(null);
  const subjectTypeRef = React.useRef(null);
  const subjectNameInputRef = React.useRef(null);
  const departmentChangeInputRef = React.useRef(null);
  const subjectNameChangeInputRef = React.useRef(null);
  const subjectTypeChangeRef = React.useRef(null);

  // -effect
  React.useEffect(() => {
    getDepartment()
      .then((departments) => {
        setDepartments(departments)
      })
      .catch((err) => {
        console.log(err)
      })
  }, [])
  React.useEffect(() => {
    if (subjects) {
      console.log('subject after get', subjects)
      getTabulator()
    }
  }, [subjects])
  React.useEffect(() => {
    if (subject) {
      console.log("subject after click", subject)
    }
  }, [subject])

  // -fetch
  const getDepartment = () => axios.get('/api/departments')
  const getSubjects = (subjectType, departmentId) => {
    return axios.get(`/admin/subjects?subjectType=${subjectType}&departmentId=${departmentId}`)
  }
  const getTabulator = () => {
    const newSubjects = subjects.toJS().map(sj => {
      if (sj.subjectType === 0) {
        sj.subjectType = 'lý thuyết'
      }
      if (sj.subjectType === 1) {
        sj.subjectType = 'thực hành'
      }
      return sj
    })
    const table = new Tabulator("#schedule-table", {
      data: newSubjects,
      layout: "fitColumns",
      columns: [
        {title: "Tên Môn", field: "name"},
        {title: "Kiểu Môn", field: "subjectType"},
        {title: "Thuộc Khoa", field: "department.name"},
      ],
      rowClick: function (e, row) {
        newSubjects.forEach(e => {
          if (e.id === row.getData().id) {
            setSubject(subjects.toJS().filter(subject => subject.id === e.id)[0])
          }
        })
      },
    })
    if (newSubjects.length > 0) {
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
  // -component
  const departmentOption = (departmentData) => departmentData.map((department) => {
    return (
      <option key={department.id} value={department.id}>{department.name}</option>
    )
  })
  const subjectUpdatingField = (subject) => {
    return (
      <form onSubmit={onSubmit}>
        <hr/>
        <div className="form-group">
          <label htmlFor="departmentChangeInput">Khoa</label>
          <select className="form-control" id="departmentChangeInput"
                  key={subject.id}
                  disabled={true}
                  defaultValue={subject.department.id}
                  ref={departmentChangeInputRef}>
            {departments && departmentOption(departments)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="subjectNameChangeInput">Tên Môn</label>
          <input type="text" id="subjectNameChangeInput" ref={subjectNameChangeInputRef}
                 key={subject.id}
                 defaultValue={subject.name}
                 disabled={true}
                 className="form-control"/>
        </div>
        <div className="form-group">
          <label htmlFor="subjectTypeChangeInput">Kiểu Môn</label>
          <select className="form-control" key={subject.id}
                  defaultValue={subject.subjectType}
                  disabled={true}
                  ref={subjectTypeChangeRef}
                  id="subjectTypeChangeInput">
            <option value="0">lý thuyết</option>
            <option value="1">thực hành</option>
          </select>
        </div>
        <button type="button" onClick={preDeleteClick} className="btn btn-primary">Xoá</button>
        {/*start modal*/}
        <div className="modal fade" id="deleteModal" tabIndex="-1" role="dialog"
             aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="exampleModalLabel">
                  Bạn chắc chắn muốn xoá môn "{subject && <b>{subject.name}</b>}":
                </h5>
                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div className="modal-body">
                {subject && <span>{subject.id} - {subject.name}</span>}
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal">Đóng</button>
                <button type="submit" className="btn btn-primary">Xoá</button>
              </div>
            </div>
          </div>
        </div>
        {/*end modal*/}
      </form>
    )
  }
  // -submit
  const preDeleteClick = () => {
    $("#deleteModal").modal()
  }

  const onSubmit = (e) => {
    e.preventDefault()
    axios({
      method: 'DELETE',
      url: `/admin/subjects/${subject.id}`,
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
  const onFind = (e) => {
    e.preventDefault()
    setSubject(null)
    getSubjects(subjectTypeRef.current.value, departmentInputRef.current.value)
      .then((newSubjects) => {
        setSubjects(Immutable.fromJS(newSubjects))
      })
      .catch(err => console.log(err))
  }
  // -change
  return (
    <div>
      <h1>Xoá Môn</h1>
      <form onSubmit={onFind}>
        <div className="form-group">
          <label>Khoa</label>
          <select className="form-control" id="departmentInput"
                  ref={departmentInputRef}>
            {departments && departmentOption(departments)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="subjectTypeInput">Kiểu Môn</label>
          <select className="form-control" ref={subjectTypeRef} id="subjectTypeInput">
            <option value="0">lý thuyết</option>
            <option value="1">thực hành</option>
          </select>
        </div>
        <button type="submit" className="btn btn-primary">Tìm</button>
      </form>
      <hr/>
      {subjects &&
      <div>
        <button id="download-csv" className="btn btn-success mr-2 mb-2">Tải CSV(Table)</button>
        <button id="download-xlsx" className="btn btn-success mb-2">Tải XLSX(Excel)</button>
      </div>
      }
      <div id="schedule-table"/>
      {subject && subjectUpdatingField(subject)}
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
