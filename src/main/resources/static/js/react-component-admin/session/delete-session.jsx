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
  React.useEffect(() => {
    if (sessions) {
      sessions.forEach(elm => {
        if (elm.id.toString() === sessionRef.current.value) {
          setShowSessionEditForm(false)
          setSessionEditForm(elm)
        }
      })
    }
  }, [sessions])

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
        <div className="form-group">
          <form onSubmit={onFinalSubmit}>
            <label htmlFor="sessionEditInput">Tên Khoá</label>
            <input type="text" id="sessionEditInput" ref={sessionEditInputRef}
                   defaultValue={sessionEditForm && sessionEditForm.name}
                   disabled={true}
                   className="form-control"/>
            <br/>
            <button type="button" onClick={preDeleteClick} className="btn btn-primary">
              Xoá
            </button>
            {/*start modal*/}
              <div className="modal fade" id="deleteModal" tabIndex="-1" role="dialog"
                   aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog" role="document">
                  <div className="modal-content">
                    <div className="modal-header">
                      <h5 className="modal-title" id="exampleModalLabel">
                        Bạn chắc chắn muốn xoá <b>{sessionEditForm && <span>{sessionEditForm.id}<span> </span>
                        - {sessionEditForm.name}</span>}</b>?
                      </h5>
                      <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>
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
  // -submit
  const preDeleteClick = () => {
    $('#deleteModal').modal()
  }
  const onFinalSubmit = (e) => {
    e.preventDefault()
    axios({
      method: 'DELETE',
      url: `/api/admin/sessions/${sessionEditForm.id}`
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
  // -change
  const onSessionChange = () => {
    sessions.forEach(elm => {
      if (elm.id.toString() === sessionRef.current.value) {
        setShowSessionEditForm(false)
        setSessionEditForm(elm)
      }
    })
  }
  return (
    <div>
      <h6>Tất Cả Niên Khoá</h6>
      <form>
        <div className="form-group">
          <select className="form-control" id="sessionInput" ref={sessionRef} onChange={onSessionChange}>
            {sessions && sessionOption(sessions)}
          </select>
        </div>
      </form>
      <hr/>
      {showSessionEditForm && sessionRef && submitForm()}
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
