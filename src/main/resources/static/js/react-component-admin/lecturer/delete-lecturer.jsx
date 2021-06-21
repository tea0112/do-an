function App() {
  const [state, setState] = React.useState({
    departments: null,
    lecturers: null,
    lecturer: null,
    departmentUpdateInput: null,
  })

  const departmentInputRef = React.useRef()
  const lecturerInputRef = React.useRef()

  // effect
  React.useEffect(() => {
    getDepartment()
      .then((departments) => {
        setState(preState => deepFreeze(({
            ...preState,
            departments: deepFreeze(departments),
            departmentUpdateInput: departmentInputRef.current.value
          })
        ))
      })
      .catch(err => console.log(err))
  }, [])
  React.useEffect(() => {
    if (state.departments) {
      getLecturers(departmentInputRef.current.value)
        .then((lecturers) => {
          setState(prevState => deepFreeze({
            ...prevState,
            lecturers: deepFreeze(lecturers)
          }))
        })
        .catch(err => console.log(err))
    }
  }, [state.departments])
  React.useEffect(() => {
    if (state.lecturers) {
      getLecturer(lecturerInputRef.current.value)
        .then((lecturer) => {
          setState(prevState => deepFreeze({
            ...prevState,
            lecturer: deepFreeze(lecturer),
          }))
        })
        .catch(err => console.log(err))
    }
  }, [state.lecturers])

  // change
  const departmentInputChange = () => {
    setState(prevState => deepFreeze({
      ...prevState,
      departmentUpdateInput: departmentInputRef.current.value
    }))
    getLecturers(departmentInputRef.current.value)
      .then((lecturers) => {
        setState(prevState => deepFreeze({
          ...prevState,
          lecturers
        }))
      })
      .catch(err => console.log(err))
  }
  const lecturerInputChange = () => {
    getLecturer(lecturerInputRef.current.value)
      .then((lecturer) => {
        setState(prevState => deepFreeze({
          ...prevState,
          lecturer
        }))
      })
      .catch(err => console.log(err))
  }

  // submit
  const onUpdate = (e) => {
    e.preventDefault()
    axios({
      method: 'DELETE',
      url: `/api/admin/lecturers/${state.lecturer.id}`,
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
            {state.departments && departmentOption(state.departments)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="lecturerInput">Tên Giảng Viên</label>
          <select className="form-control"
                  ref={lecturerInputRef}
                  onChange={lecturerInputChange}
                  id="lecturerInput">
            {state.lecturers && lecturersOption(state.lecturers)}
          </select>
        </div>
      </form>
      <hr/>
      <div>
        {state.lecturers && state.lecturers.length > 0 && (
          <div>
            <i>Id hiện tại</i>
            <input type="text" className="form-control" disabled={true} value={state.lecturer && state.lecturer.id}/>
            <i>Tên hiện tại</i>
            <input type="text" className="form-control" disabled={true} value={state.lecturer && state.lecturer.name}/>
            <i>Khoa hiện tại</i>
            <input type="text" className="form-control" disabled={true}
                   value={state.lecturer && state.lecturer.department.name}/>
          </div>
        )}
        <hr/>
        <form onSubmit={onUpdate}>
          <button type="button" onClick={preDeleteClick} className="btn btn-primary">Xoá</button>
          {/*start modal*/}
          <div className="modal fade" id="deleteModal" tabIndex="-1" role="dialog"
               aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog" role="document">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title" id="exampleModalLabel">
                    Bạn chắc chắn muốn xoá giảng viên "{state.lecturer && <b>{state.lecturer.name}</b>}":
                  </h5>
                  <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div className="modal-body">
                  {state.lecturer && <span>{state.lecturer.id} - {state.lecturer.name}</span>}
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
