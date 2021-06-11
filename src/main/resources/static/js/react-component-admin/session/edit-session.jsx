function App() {
  // -state
  const [sessions, setSessions] = React.useState(null);
  const [sessionEditForm, setSessionEditForm] = React.useState(null);
  const [showSessionEditForm, setShowSessionEditForm] = React.useState(false);
  // -ref
  const sessionRef = React.useRef(null);
  const sessionEditInputRef = React.useRef(null);
  // -effect
  React.useEffect(() => {
    getSessions()
      .then((sessions) => {
        setSessions(sessions)
      })
      .catch((err) => {
        console.log(err)
      })
  }, [])
  React.useEffect(() => {
    if (sessionEditForm) {
      setShowSessionEditForm(true)
    }
  }, [sessionEditForm])
  // -fetch
  const getSessions = () => {
    return axios.get('/admin/sessions')
  }
  // -component
  const sessionOption = (sessionData) => sessionData.map((session) => {
    return (
      <option key={session.id} value={session.id}>{session.name}</option>
    )
  })
  const submitForm = () => {
    return (
      <div>
        <br/>
        <div className="form-group">
          <form onSubmit={onFinalSubmit}>
            <label htmlFor="sessionEditInput">Tên Khoá</label>
            <input type="text" id="sessionEditInput" ref={sessionEditInputRef}
                   onChange={onSessionEditInputChange}
                   defaultValue={sessionEditForm && sessionEditForm}
                   className="form-control"/>
            <br/>
            <button type="submit" className="btn btn-primary">
              Cập Nhật
            </button>
          </form>
        </div>
      </div>
    )
  }
  // -submit
  const onEditSubmit = (e) => {
    e.preventDefault()
    sessions.forEach(elm => {
      if (elm.id.toString() === sessionRef.current.value) {
        console.log("change")
        setShowSessionEditForm(false)
        setSessionEditForm(elm.name)
      }
    })
  }
  const onFinalSubmit = (e) => {
    e.preventDefault()
    axios({
      method: 'PATCH',
      url: `/admin/sessions/${sessionRef.current.value}`,
      headers: {
        'Content-Type': 'application/json'
      },
      data: {
        name: sessionEditForm
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
  // -change
  const onSessionChange = () => {
    sessions.forEach(elm => {
      if (elm.id.toString() === sessionRef.current.value) {
        console.log("change")
        setShowSessionEditForm(false)
        setSessionEditForm(elm.name)
      }
    })
  }
  const onSessionEditInputChange = () => {
    setSessionEditForm(sessionEditInputRef.current.value)
  }
  return (
    <div>
      <h6>Tất Cả Niên Khoá</h6>
      <form onSubmit={onEditSubmit}>
        <div className="form-group">
          <select className="form-control" id="sessionInput" ref={sessionRef} onChange={onSessionChange}>
            {sessions && sessionOption(sessions)}
          </select>
        </div>
        <button type="submit" className="btn btn-primary">Sửa</button>
      </form>
      {showSessionEditForm && sessionRef && submitForm()}
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))