function App() {
  // -state
  const [sessions, setSessions] = React.useState()
  // -ref
  const sessionRef = React.useRef();
  const startDayRef = React.useRef();
  const endDayRef = React.useRef();
  const termNumberInputRef = React.useRef();
  // -effect
  React.useEffect(() => {
    getSession()
      .then((sessions) => {
        setSessions(sessions)
      })
      .catch((err) => {
        console.log(err)
      })
  }, [])
  // -fetch
  const getSession = () => {
    return axios.get(
      `/admin/session`
    )
  }
  // -component
  const sessionOption = (sessionData) => sessionData.map((session) => {
    return (
      <option key={session.id} value={session.id}>{session.name}</option>
    )
  })
  // -submit
  const onSubmit = (e) => {
    e.preventDefault()
    axios({
      method: 'POST',
      url: `/api/admin/semesters`,
      headers: {
        'Content-Type': 'application/json'
      },
      data: {
        termNumber: termNumberInputRef.current.value,
        sessionId: sessionRef.current.value,
        startDay: startDayRef.current.value,
        endDay: endDayRef.current.value,
      }
    })
      .then(() => {
        alert('Thêm Thành Công')
        location.reload()
      })
      .catch(err => {
        alert('Thêm Thất Bại ' + err)
        location.reload()
      })
  }
  // -change
  return (
    <div>
      <h1 className="h3 mb-4 text-gray-800">Thêm Học Kỳ</h1>
      <form onSubmit={onSubmit}>
        <div className="form-group">
          <label>Khoá</label>
          <select className="form-control" id="sessionInput" ref={sessionRef}>
            {sessions && sessionOption(sessions)}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="startDayInput">Ngày Bắt Đầu</label>
          <input className="form-control"
                 ref={startDayRef}
                 type="date" id="startDayInput"/>
        </div>
        <div className="form-group">
          <label htmlFor="endDayInput">Ngày Kết Thúc</label>
          <input className="form-control" type="date"
                 ref={endDayRef}
                 id="endDayInput"/>
        </div>
        <div className="form-group">
          <label htmlFor="termNumberInput">Học Kỳ Số</label>
          <select className="form-control" id="termNumberInput" ref={termNumberInputRef}>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
          </select>
        </div>
        <button type="submit" className="btn btn-primary">Thêm</button>
      </form>
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
