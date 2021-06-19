function App() {
  // state
  const [state, setState] = React.useState({
    lectureHalls: null,
    classrooms: null,
    selectedLectureHall: null,
    selectedClassroom: null,
    lectureHallChangeInput: null,
    classroomChangeInput: null,
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
  React.useEffect(async () => {
    if (state.classrooms) {
      setState({...state, selectedClassroom: state.classrooms[0].id})
    }
  }, [state.classrooms])
  React.useEffect(async () => {
    if (state.selectedLectureHall) {
      const classrooms = await getClassroomWithLectureHallId(state.selectedLectureHall)
      setState({...state, classrooms, lectureHallChangeInput: state.selectedLectureHall})
    }
  }, [state.selectedLectureHall])
  React.useEffect(async () => {
    if (state.selectedClassroom) {
      setState({
        ...state,
        classroomChangeInput: state.classrooms.find(c => c.id === parseInt(state.selectedClassroom)).name
      })
    }
  }, [state.selectedClassroom])

  // request
  const updateLectureHallRequest = async () => {
    try {
      const url = `/api/admin/classrooms/${state.selectedClassroom}`
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
  const getClassroomWithLectureHallId = (lectureHallId) => {
    return axios.get(`/api/admin/classrooms?lectureHallId=${lectureHallId}`)
  }

  // on
  const onSubmit = (e) => {
    e.preventDefault()
    updateLectureHallRequest()
  }

  // handle
  const handleSelectLectureHall = (e) => {
    setState({...state, selectedLectureHall: e.target.value})
  }

  const handleSelectClassroom = (e) => {
    setState({...state, selectedClassroom: e.target.value})
  }
  const handleLectureHallChangeInput = (e) => {
    setState({...state, lectureHallChangeInput: e.target.value})
  }
  const handleClassroomChange = (e) => {
    setState({...state, classroomChangeInput: e.target.value})
  }

  // component
  const lectureHallOptionList = (lectureHalls) => lectureHalls.map((lectureHall) => {
    return (
      <option value={lectureHall.id} key={lectureHall.id}>{lectureHall.name} - {lectureHall.address}</option>
    )
  })
  const classroomOptionList = (lectureHalls) => lectureHalls.map((lectureHall) => {
    return (
      <option value={lectureHall.id} key={lectureHall.id}>{lectureHall.name}</option>
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
      <div className="form-group">
        <div>Chọn Phòng Học</div>
        <select className="form-control"
                value={state.selectedClassroom || ''}
                onChange={handleSelectClassroom}
        >
          {state.classrooms && classroomOptionList(state.classrooms)}
        </select>
      </div>

      <hr/>

      <form onSubmit={onSubmit}>
        <div className="form-group">
          <div>Giảng Đường</div>
          <select className="form-control"
                  disabled={true}
                  value={state.lectureHallChangeInput || ''}
                  onChange={handleLectureHallChangeInput}
          >
            {state.lectureHalls && lectureHallOptionList(state.lectureHalls)}
          </select>
        </div>
        <div>
          <div>Tên Phòng</div>
          <input type="text" className="form-control" value={state.classroomChangeInput || ''}
                 disabled={true}
                 onChange={handleClassroomChange}/>
        </div>
        <button className="btn btn-primary mt-2" type="submit">Xoá</button>
      </form>
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
