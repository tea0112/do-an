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
               defaultValue={choseDepartment.name}/>
        <div className="form-group form-check">
          <input
            key={choseDepartment.id}
            type="checkbox"
            className="form-check-input"
            id="changedGeneralCheck"
            ref={changedGeneralCheckRef}
            defaultChecked={choseDepartment.isGeneral}
          />
          <label>
            Là Khoa Cơ Bản
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
          method: 'PATCH',
          headers: {
            'content-type': 'application/json'
          },
          data: {
            name: changedNameRef.current.value,
            isGeneral: changedGeneralCheckRef.current.checked,
          }
        })
        alert('Cập Nhật Thành Công')
        location.reload()
      } catch (e) {
        console.log(e)
        alert('Cập Nhật Thất Bại')
        location.reload()
      }
    }
  }
  return (
    <div>
      <h1>Sửa Khoa</h1>
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
        <button className="btn btn-primary">Cập Nhật</button>
      </form>
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
