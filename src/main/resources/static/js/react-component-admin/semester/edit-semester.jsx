function App() {
  // state
  const [sessions, setSessions] = React.useState(null)
  const [semesters, setSemesters] = React.useState(null)
  const [semester, setSemester] = React.useState(null)
  // ref
  const sessionsRef = React.useRef(null)
  const semestersRef = React.useRef(null)
  const termNumberChangeInputRef = React.useRef(null)
  const startDayChangeInputRef = React.useRef(null)
  const endDayChangeInputRef = React.useRef(null)
  const sessionChangeRef = React.useRef(null)

  // effect
  React.useEffect(() => {
    getSessions()
      .then((sessions) => {
        setSessions(deepFreeze(sessions))
      })
      .catch(err => console.log(err))
  }, [])
  React.useEffect(() => {
    if (sessions)
      getSemesters(sessionsRef.current.value)
        .then((semesters) => {
          setSemesters(deepFreeze(semesters))
        })
        .catch(err => console.log(err))
  }, [sessions])
  React.useEffect(() => {
    if (semesters) {
      semesters.filter(semester => parseInt(semestersRef.current.value) === semester.id &&
        setSemester(deepFreeze(semester)))
    }
  }, [semesters])

  // fetch
  const getSessions = () => {
    return axios.get('/admin/sessions')
  }
  const getSemesters = (sessionId) => {
    return axios.get(`/api/semesters?sessionId=${sessionId}`)
  }

  // change
  const sessionsInputChange = () => {
    getSemesters(sessionsRef.current.value)
      .then((semesters) => {
        setSemesters(deepFreeze(semesters))
      })
      .catch(err => console.log(err))
  }
  const semestersInputChange = () => {
    if (semesters) {
      semesters.filter(semester => parseInt(semestersRef.current.value) === semester.id &&
        setSemester(deepFreeze(semester)))
    }
  }

  //submit
  const onUpdate = (e) => {
    e.preventDefault()
    axios({
      method: 'PATCH',
      url: `/api/admin/semesters/${semester.id}`,
      headers: {
        'Content-Type': 'application/json'
      },
      data: {
        termNumber: termNumberChangeInputRef.current.value,
        sessionId: sessionChangeRef.current.value,
        startDay: startDayChangeInputRef.current.value,
        endDay: endDayChangeInputRef.current.value,
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

  // component
  const sessionOption = (sessionData) => sessionData.map((session) => {
    return (
      <option key={session.id} value={session.id}>{session.name}</option>
    )
  })
  const semesterOption = (semesterData) => semesterData.map((semester) => {
    return (
      <option key={semester.id} value={semester.id}>{semester.termNumber}</option>
    )
  })
  return (
    <div>
      <h1 className="h3 mb-4 text-gray-800">Sửa Học Kỳ</h1>
      <form>
        <div className="form-group">
          <label>Khoá</label>
          <select className="form-control" id="sessionsInput" ref={sessionsRef}
                  onChange={sessionsInputChange}>
            {sessions && sessionOption(sessions)}
          </select>
        </div>
        <div className="form-group">
          <label>Học Kỳ</label>
          <select className="form-control" id="semestersInput" ref={semestersRef}
                  onChange={semestersInputChange}>
            {semesters && semesterOption(semesters)}
          </select>
        </div>
      </form>
      <hr/>
      <h1 className="h3 mb-4 text-gray-800">
        Nhập Thay Đổi Cho:&nbsp;
        <i>
          <u>
            {semester && <span>ID {semester.id} - Học Kỳ Số {semester.termNumber} - Khoá&nbsp;
              {semester.session.name}</span>}
          </u>
        </i>
      </h1>
      <hr/>
      {semester && (
        <div key={semester.id}>
          <form onSubmit={onUpdate}>
            <div className="form-group">
              <label>Học Kỳ Số</label>
              <select className="form-control"
                      id="termNumberChangeInput" defaultValue={semester && semester.termNumber}
                      ref={termNumberChangeInputRef}>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
              </select>
            </div>
            <div className="form-group">
              <label htmlFor="startDayChangeInput">Ngày Bắt Đầu</label>
              <input className="form-control"
                     defaultValue={semester.startDay}
                     ref={startDayChangeInputRef}
                     type="date" id="startDayChangeInput"/>
            </div>
            <div className="form-group">
              <label htmlFor="endDayChangeInput">Ngày Kết Thúc</label>
              <input className="form-control" type="date"
                     defaultValue={semester.endDay}
                     ref={endDayChangeInputRef}
                     id="endDayChangeInput"/>
            </div>
            <div className="form-group">
              <label>Khoá</label>
              <select className="form-control" id="sessionsChangeInput" ref={sessionChangeRef}
                      defaultValue={semester.session.id}
              >
                {sessions && sessionOption(sessions)}
              </select>
            </div>
            <button type="submit" className="btn btn-primary">Cập Nhật</button>
          </form>
        </div>
      )}
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
