function App() {
  // state
  const [state, setState] = React.useState({
    lectureHallNameInput: '',
    lectureHallAddressInput: ''
  })

  // effect


  // request
  const createLectureHallRequest = async () => {
    try {
      const url = '/api/admin/lectureHalls'
      const body = {
        name: state.lectureHallNameInput,
        address: state.lectureHallAddressInput,
      }
      await axios.post(url, body)
      alert('Thêm Thành Công')
      location.reload()
    } catch (e) {
      console.log(e)
      alert('Thêm Thất Bại')
      location.reload()
    }
  }

  // on
  const onSubmit = (e) => {
    e.preventDefault()
    createLectureHallRequest()
  }

  // handle
  const handleLectureHallAddressInputChange = (e) => {
    setState({...state, lectureHallAddressInput: e.target.value})
  }
  const handleLectureHallNameInputChange = (e) => {
    setState({...state, lectureHallNameInput: e.target.value})
  }

  // component


  return (
    <div>
      <form onSubmit={onSubmit}>
        <div>
          <div>Tên Giảng Đường</div>
          <input type="text" className="form-control" value={state.lectureHallNameInput}
            onChange={handleLectureHallNameInputChange}/>
        </div>
        <div>
          <div>Địa Chỉ</div>
          <input type="text" className="form-control" value={state.lectureHallAddressInput}
            onChange={handleLectureHallAddressInputChange}/>
        </div>
        <button className="btn btn-primary mt-2" type="submit">Thêm</button>
      </form>
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
