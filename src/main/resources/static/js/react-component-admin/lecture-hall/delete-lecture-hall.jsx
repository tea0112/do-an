function App() {
  // state
  const [state, setState] = React.useState({
    lectureHalls: null,
    selectedLectureHall: null,
    lectureHallNameInput: '',
    lectureHallAddressInput: ''
  })

  // effect
  React.useEffect(async () => {
    const lectureHalls = await getAllLecturerHalls()
    setState({...state, lectureHalls: lectureHalls})
  }, [])
  React.useEffect(async () => {
    if (state.lectureHalls) {
      setState({...state, selectedLectureHall: state.lectureHalls[0].id})
    }
  }, [state.lectureHalls])
  React.useEffect(() => {
    if (state.selectedLectureHall) {
      const selectedLectureHall = state.lectureHalls.find((lH => lH.id === parseInt(state.selectedLectureHall)))
      setState({
        ...state,
        lectureHallNameInput: selectedLectureHall.name,
        lectureHallAddressInput: selectedLectureHall.address,
      })
    }
  }, [state.selectedLectureHall])

  // request
  const deleteLectureHallRequest = async () => {
    try {
      const url = `/api/admin/lectureHalls/${state.selectedLectureHall}`
      await axios({
        url,
        method: 'DELETE',
      })
      alert('Xoá Thành Công')
      location.reload()
    } catch (e) {
      console.log(e)
      alert('Xoá Thất Bại')
      location.reload()
    }
  }
  const getAllLecturerHalls = () => {
    return axios.get(`/api/lecturerHalls`)
  }

  // on
  const onSubmit = (e) => {
    e.preventDefault()
    deleteLectureHallRequest()
  }

  // handle
  const handleLectureHallAddressInputChange = (e) => {
    setState({...state, lectureHallAddressInput: e.target.value})
  }
  const handleLectureHallNameInputChange = (e) => {
    setState({...state, lectureHallNameInput: e.target.value})
  }
  const handleSelectLectureHall = (e) => {
    setState({...state, selectedLectureHall: e.target.value})
  }

  // component
  const lectureHallOptionList = (lectureHalls) => lectureHalls.map((lectureHall) => {
    return (
      <option value={lectureHall.id} key={lectureHall.id}>{lectureHall.name} - {lectureHall.address}</option>
    )
  })

  return (
    <div>
      <div className="form-group">
        <div>Chọn Giảng Đường</div>
        <select className="form-control"
                value={state.selectedLectureHall || ''}
                onChange={handleSelectLectureHall}
        >
          {state.lectureHalls && lectureHallOptionList(state.lectureHalls)}
        </select>
      </div>

      <form onSubmit={onSubmit}>
        <div>
          <div>Tên Giảng Đường</div>
          <input type="text" className="form-control" value={state.lectureHallNameInput}
                 onChange={handleLectureHallNameInputChange} disabled={true}/>
        </div>
        <div>
          <div>Địa Chỉ</div>
          <input type="text" className="form-control" value={state.lectureHallAddressInput}
                 onChange={handleLectureHallAddressInputChange} disabled={true}/>
        </div>
        <button className="btn btn-primary mt-2" type="submit">Xoá</button>
      </form>
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
