function App() {
  // state
  const [state, setState] = React.useState({
    lectureHalls: null,
    selectedLectureHall: null,
    classRoomNameInput: '',
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

  // request
  const addClassroomRequest = async () => {
    try {
      const url = `/api/admin/classrooms`
      const body = {
        name: state.classRoomNameInput,
        lectureHallId: state.selectedLectureHall,
      }
      await axios({
        url,
        data: body,
        method: 'POST',
        headers: {'Content-Type': 'application/json'}
      })
      alert('Thêm Thành Công')
      location.reload()
    } catch (e) {
      console.log(e)
      alert('Thêm Thất Bại')
      location.reload()
    }
  }
  const getAllLecturerHalls = () => {
    return axios.get(`/api/lecturerHalls`)
  }

  // on
  const onSubmit = (e) => {
    e.preventDefault()
    addClassroomRequest()
  }

  // handle
  const handleClassroomNameInputChange = (e) => {
    setState({...state, classRoomNameInput: e.target.value})
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
      <form onSubmit={onSubmit}>
        <div className="form-group">
          <div>Chọn Giảng Đường</div>
          <select className="form-control"
                  value={state.selectedLectureHall || ''}
                  onChange={handleSelectLectureHall}
          >
            {state.lectureHalls && lectureHallOptionList(state.lectureHalls)}
          </select>
        </div>
        <div>
          <div>Tên Phòng Học</div>
          <input type="text" className="form-control" value={state.classRoomNameInput}
                 onChange={handleClassroomNameInputChange}/>
        </div>
        <button className="btn btn-primary mt-2" type="submit">Thêm</button>
      </form>
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
