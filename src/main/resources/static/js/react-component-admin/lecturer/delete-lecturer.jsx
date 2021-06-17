function App() {
  const [departments, setDepartments] = React.useState(null)
  const [lecturers, setLecturers] = React.useState(null)
  const [lecturer, setLecturer] = React.useState(null)
  const [departmentUpdateInput, setDepartmentUpdateInput] = React.useState(null)

  const departmentInputRef = React.useRef()
  const lecturerInputRef = React.useRef()
  const departmentUpdateInputRef = React.useRef()
  const lecturerUpdateInputRef = React.useRef()

  // effect
  React.useEffect(() => {
    getDepartment()
      .then((departments) => {
        setDepartments(deepFreeze(departments))
        setDepartmentUpdateInput(departmentInputRef.current.value)
      })
      .catch(err => console.log(err))
  }, [])
  React.useEffect(() => {
    if (departments) {
      getLecturers(departmentInputRef.current.value)
        .then((lecturers) => {
          setLecturers(deepFreeze(lecturers))
        })
        .catch(err => console.log(err))
    }
  }, [departments])
  React.useEffect(() => {
    if (lecturers) {
      getLecturer(lecturerInputRef.current.value)
        .then((lecturer) => {
          setLecturer(deepFreeze(lecturer))
        })
        .catch(err => console.log(err))
    }
  }, [lecturers])
  React.useEffect(() => {
    if (lecturer) {
      lecturerUpdateInputRef.current.value = lecturer.name
    }
  }, [lecturer])

  // change
  const departmentInputChange = () => {
    departmentUpdateInputRef.current.value = departmentInputRef.current.value
    setDepartmentUpdateInput(departmentInputRef.current.value)
    getLecturers(departmentInputRef.current.value)
      .then((lecturers) => {
        setLecturers(deepFreeze(lecturers))
      })
      .catch(err => console.log(err))
  }
  const lecturerInputChange = () => {
    getLecturer(lecturerInputRef.current.value)
      .then((lecturer) => {
        setLecturer(deepFreeze(lecturer))
      })
      .catch(err => console.log(err))
  }
  const departmentInputUpdateChange = () => {

  }

  // submit
  const onUpdate = (e) => {
    e.preventDefault()
    axios({
      method: 'DELETE',
      url: `/api/admin/lecturers/${lecturer.id}`,
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
  const preDeleteClick = () => {
    $("#deleteModal").modal()
  }

  // get
  const getDepartment = () => axios.get('/api/departments')
  const getLecturers = (departmentId) => {
    return axios.get(
      `/api/admin/lecturers?departmentId=${departmentId}`
    )
  }
  const getLecturer = (id) => {
    return axios.get(`/api/admin/lecturers/${id}`)
  }

  const departmentOption = (departmentData) => departmentData.map((department) => {
    return (
      <option key={department.id} value={department.id}>{department.name}</option>
    )
  })
  const lecturersOption = (lecturerData) => lecturerData.map((lecturer) => {
    return (
      <option key={lecturer.id} value={lecturer.id}>{lecturer.name}</option>
    )
  })
  return (
    <div>
      <h1 className="h3 mb-4 text-gray-800">Xoá Giảng Viên</h1>
      <form>
        <div className="form-group">
          <label htmlFor="departmentInput">Khoa</label>
          <select className="form-control" id="departmentInput"
                  ref={departmentInputRef} onChange={departmentInputChange}>
            {departments && departmentOption(departments)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="lecturerInput">Tên Giảng Viên</label>
          <select className="form-control"
                  ref={lecturerInputRef}
                  onChange={lecturerInputChange}
                  id="lecturerInput">
            {lecturers && lecturersOption(lecturers)}
          </select>
        </div>
      </form>
      <hr/>
      <div>
        <i>Id hiện tại</i>
        <input type="text" className="form-control" disabled={true} value={lecturer && lecturer.id}/>
        <i>Tên hiện tại</i>
        <input type="text" className="form-control" disabled={true} value={lecturer && lecturer.name}/>
        <i>Khoa hiện tại</i>
        <input type="text" className="form-control" disabled={true} value={lecturer && lecturer.department.name}/>
        <hr/>
        <h4>Thay đổi:</h4>
        <form onSubmit={onUpdate}>
          <div className="form-group">
            <label htmlFor="departmentUpdateInput">Khoa</label>
            <select className="form-control" id="departmentUpdateInput"
                    defaultValue={departmentUpdateInput && departmentUpdateInput}
                    ref={departmentUpdateInputRef} onChange={departmentInputUpdateChange}>
              {departments && departmentOption(departments)}
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="lecturerUpdateInput">Tên Giảng Viên</label>
            <input className="form-control" type="text" id="lecturerUpdateInput"
                   ref={lecturerUpdateInputRef}
                   defaultValue={lecturer && lecturer.name}/>
          </div>
          <button type="button" onClick={preDeleteClick} className="btn btn-primary">Xoá</button>
          {/*start modal*/}
          <div className="modal fade" id="deleteModal" tabIndex="-1" role="dialog"
               aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog" role="document">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title" id="exampleModalLabel">
                    Bạn chắc chắn muốn xoá giảng viên "{lecturer && <b>{lecturer.name}</b>}":
                  </h5>
                  <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div className="modal-body">
                  {lecturer && <span>{lecturer.id} - {lecturer.name}</span>}
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
      </div>
    </div>
  )
}

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

ReactDOM.render(React.createElement(App), document.getElementById('app'))
