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
    return new Tabulator("#schedule-table", {
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
  }
  // -component
  const departmentOption = (departmentData) => departmentData.map((department) => {
    return (
      <option key={department.id} value={department.id}>{department.name}</option>
    )
  })
  const subjectUpdatingField = (subject) => {
    const changeSubjectType = (name) => {
      if (name === 'lý thuyết') {
        return 0
      } else {
        return 1
      }
    }
    return (
      <form onSubmit={onSubmit}>
        <hr/>
        <div className="form-group">
          <label htmlFor="departmentChangeInput">Khoa</label>
          <select className="form-control" id="departmentChangeInput"
                  key={subject.id}
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
                 className="form-control"/>
        </div>
        <div className="form-group">
          <label htmlFor="subjectTypeChangeInput">Kiểu Môn</label>
          <select className="form-control" key={subject.id}
                  defaultValue={subject.subjectType}
                  ref={subjectTypeChangeRef}
                  id="subjectTypeChangeInput">
            <option value="0">lý thuyết</option>
            <option value="1">thực hành</option>
          </select>
        </div>
        <button type="submit" className="btn btn-primary">Cập Nhật</button>
      </form>
    )
  }
  // -submit
  const onSubmit = (e) => {
    e.preventDefault()
    axios({
      method: 'PATCH',
      url: `/admin/subjects/${subject.id}`,
      headers: {
        'Content-Type': 'application/json'
      },
      data: {
        name: subjectNameChangeInputRef.current.value,
        subjectType: subjectTypeChangeRef.current.value,
        departmentId: departmentChangeInputRef.current.value
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
      <h1>Sửa Môn</h1>
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
      <div id="schedule-table"/>
      {subject && subjectUpdatingField(subject)}
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
