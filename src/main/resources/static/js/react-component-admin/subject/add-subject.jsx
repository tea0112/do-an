function App() {
  // -state
  const [departments, setDepartments] = React.useState(null)
  const [subjectType, setSubjectType] = React.useState(null)
  // -ref
  const departmentInputRef = React.useRef();
  const subjectTypeRef = React.useRef();
  const subjectNameInputRef = React.useRef();
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
  // -fetch
  const getDepartment = () => axios.get('/api/departments')
  // -component
  const departmentOption = (departmentData) => departmentData.map((department) => {
      return (
        <option key={department.id} value={department.id}>{department.name}</option>
      )
  })
  // -submit
  const onSubmit = (e) => {
    e.preventDefault()
    axios({
      method: 'POST',
      url: `/admin/subjects`,
      headers: {
        'Content-Type': 'application/json'
      },
      data: {
        name: subjectNameInputRef.current.value,
        subjectType: subjectTypeRef.current.value,
        departmentId: departmentInputRef.current.value
      }
    })
      .then(() => {
        alert('Thêm Thành Công')
        location.reload()
      })
      .catch(err => {
        console.log(err)
        alert('Thêm Thất Bại')
        location.reload()
      })
  }
  // -change
  const onSubjectTypeChange = () => {

  }
  const onDepartmentChange = () => {

  }
  const onSubjectNameChange = () => {

  }
  return (
    <div>
      <h1>Thêm Môn</h1>
      <form onSubmit={onSubmit}>
        <div className="form-group">
          <label>Khoa</label>
          <select className="form-control" id="departmentInput" onChange={onDepartmentChange}
                  ref={departmentInputRef}>
            {departments && departmentOption(departments)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="subjectNameInput">Tên Môn</label>
          <input type="text" id="subjectNameInput" ref={subjectNameInputRef} onChange={onSubjectNameChange}
                 className="form-control"/>
        </div>
        <div className="form-group">
          <label htmlFor="subjectTypeInput">Kiểu Môn</label>
          <select className="form-control" ref={subjectTypeRef} id="subjectTypeInput" onChange={onSubjectTypeChange}>
            <option value="0">lý thuyết</option>
            <option value="1">thực hành</option>
          </select>
        </div>
        <button type="submit" className="btn btn-primary">Thêm</button>
      </form>
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))