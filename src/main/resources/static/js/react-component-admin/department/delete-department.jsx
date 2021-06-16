function App() {
  // state
  const [departments, setDepartments] = React.useState(null)
  const [choseDepartment, setChoseDepartment] = React.useState(null)

  // ref
  const departmentNameSelectRef = React.useRef(null)
  const changedNameRef = React.useRef(null)
  const changedGeneralCheckRef = React.useRef(null)

  // effect
  React.useEffect(async () => {
    try {
      const response = await axios.get('/api/departments')
      setDepartments(response)
    } catch (e) {
      console.log(e)
    }
  }, [])
  React.useEffect(() => {
    if (departments) {
      setChoseDepartment(departments.find(d => d.id === parseInt(departmentNameSelectRef.current.value)))
    }
  }, [departments])

  // change
  const changingDepartmentNameSelect = () => {
    if (departments) {
      setChoseDepartment(departments.find(d => d.id === parseInt(departmentNameSelectRef.current.value)))
    }
  }

  // component
  const departmentOptions = (departments) => departments.map(e => {
    return (
      <option value={e.id} key={e.id}>{e.name}</option>
    )
  })
  const choseDepartmentFormChanging = (choseDepartment) => {
    return (
      <div>
        <label>ID</label>
        <input className="form-control" type="text" value={choseDepartment.id} disabled/>
        <label>Tên Khoa</label>
        <input type="text" className="form-control" key={choseDepartment.id} ref={changedNameRef}
               defaultValue={choseDepartment.name} disabled={true}/>
        <div className="form-group form-check">
          <input
            key={choseDepartment.id}
            type="checkbox"
            className="form-check-input"
            id="changedGeneralCheck"
            ref={changedGeneralCheckRef}
            defaultChecked={choseDepartment.isGeneral}
            disabled={true}
          />
          <label>
            Là Khoa Chung
          </label>
        </div>
      </div>
    )
  }

  // submit
  const onSubmit = async (e) => {
    e.preventDefault();
    if (choseDepartment) {
      try {
        await axios({
          url: `/api/admin/departments/${choseDepartment.id}`,
          method: 'DELETE'
        })
        alert('Xoá Thành Công')
        location.reload()
      } catch (e) {
        console.log(e)
        alert('Xoá Thất Bại')
        location.reload()
      }
    }
  }
  const preDeleteClick = () => {
    $("#deleteModal").modal()
  }
  return (
    <div>
      <h1>Xoá Khoa</h1>
      <form onSubmit={onSubmit}>
        <div className="form-group">
          <select
            ref={departmentNameSelectRef}
            className="form-control"
            id="departmentNameSelect"
            onChange={changingDepartmentNameSelect}>
            {departments && departmentOptions(departments)}
          </select>
        </div>
        {choseDepartment && choseDepartmentFormChanging(choseDepartment)}
        <button className="btn btn-primary" type="button" onClick={preDeleteClick}>Xoá</button>
        {/*modal*/}
        <div className="modal fade" id="deleteModal" tabIndex="-1" role="dialog"
             aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="exampleModalLabel">
                  Bạn chắc chắn muốn xoá khoa "{choseDepartment && <b>{choseDepartment.name}</b>}":
                </h5>
                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div className="modal-body">
                {choseDepartment && <span>{choseDepartment.id} - {choseDepartment.name}</span>}
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal">Đóng</button>
                <button type="submit" className="btn btn-primary">Xoá</button>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
