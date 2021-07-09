function App() {
  const [student, setStudent] = React.useState(null);
  const [firstName, setFirstName] = React.useState(null);
  const [lastName, setLastName] = React.useState(null);
  const [birth, setBirth] = React.useState(null);
  const [phoneNumber, setPhoneNumber] = React.useState(null);
  const [place, setPlace] = React.useState(null);
  const [gender, setGender] = React.useState(null);

  const birthRef = React.useRef()
  const genderInputRef = React.useRef()
  const firstNameRef = React.useRef()
  const lastNameRef = React.useRef()
  const phoneNumberRef = React.useRef()
  const placeRef = React.useRef()
  // -useEffect
  React.useEffect(() => {
    getStudent(getParamValue("studentId"))
      .then((student) => {
        setStudent(student)
      })
      .catch(err => console.log(err))
  }, [])
  React.useEffect(() => {
    if (student) {
      setFirstName(student.firstName)
      setLastName(student.lastName)
      setBirth(student.birth)
      setPlace(student.place)
      setPhoneNumber(student.phoneNumber)
      setGender(student.gender)
    }
  }, [student])

  // fetch
  const getStudent = () => {
    return axios.get(
      `/api/students/current`
    )
  }

  // -component
  const sessionOption = (sessionData) => sessionData.map((session) => {
    return (
      <option key={session.id} value={session.id}>{session.name}</option>
    )
  })

  // -onChange
  const onFirstNameChange = (e) => {
    setFirstName(e.target.value)
  }
  const onLastNameChange = (e) => {
    setLastName(e.target.value)
  }
  const onBirthChange = (e) => {
    setBirth(e.target.value)
  }
  const onPhoneNumberChange = (e) => {
    setPhoneNumber(e.target.value)
  }
  const onPlaceChange = (e) => {
    setPlace(e.target.value)
  }
  const onGenderChange = (e) => {
    setGender(e.target.value)
  }
  // -onSubmit
  const onSubmit = (e) => {
    e.preventDefault()
    axios({
      method: 'PATCH',
      url: `/api/students/${getParamValue('studentId')}`,
      headers: {
        'Content-Type': 'application/json'
      },
      data: {
        lastName: lastNameRef.current.value,
        firstName: firstNameRef.current.value,
        birth: birthRef.current.value,
        place: placeRef.current.value,
        phoneNumber: phoneNumberRef.current.value,
        gender: genderInputRef.current.value,
      }
    })
      .then(() => {
        alert('Cập Nhật Thành Công')
        window.location.replace('/admin/sinh-vien/sua')
      })
      .catch(err => {
        const errors = []
        err.errors.forEach(e => errors.push(e.defaultMessage))
        const violations = errors.reduce(
          (acc, crv) => '[' + acc.defaultMessage + ']' + '[' + crv.defaultMessage + ']'
        )
        alert('Cập Nhật Thất Bại: ' + violations)
      })
  }
  // -utils
  const getParamValue = (name) => {
    const urlString = window.location.href
    const url = new URL(urlString)
    const value = url.searchParams.get(name)
    if (value) {
      return value
    }
    return null
  }
  function removeAscent (str) {
    if (str === null || str === undefined) return str;
    str = str.toLowerCase();
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
    str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
    str = str.replace(/đ/g, "d");
    return str;
  }
  // -return
  return (
    <div>
      <h1>Thông Tin Sinh Viên</h1>
      <form onSubmit={onSubmit}>
        <div className="form-group">
          <label htmlFor="phoneNumberInput">Mã Số Sinh Viên</label>
          <input type="text" id="phoneNumberInput" value={student && student.user.username}
                 disabled={true}
                 className="form-control"/>
        </div>
        <div className="form-group">
          <label htmlFor="firstNameInput">Họ</label>
          <input type="text" id="firstNameInput" value={student && firstName} ref={firstNameRef}
                 disabled={true}
                 className="form-control"
                 onChange={onFirstNameChange}/>
        </div>
        <div className="form-group">
          <label htmlFor="lastNameInput">Tên</label>
          <input type="text" id="lastNameInput" onChange={onLastNameChange} value={student && lastName}
                 disabled={true}
                 ref={lastNameRef} className="form-control"/>
        </div>
        <div className="form-group">
          <label htmlFor="birthInput">Ngày Sinh</label>
          <input className="form-control" value={student && birth} onChange={onBirthChange}
                 disabled={true}
                 ref={birthRef}
                 type="date" id="birthInput"/>
        </div>
        <div className="form-group">
          <label htmlFor="placeInput">Nơi Ở</label>
          <input type="text" id="placeInput" value={student && place} ref={placeRef} onChange={onPlaceChange}
                 disabled={true}
                 className="form-control"/>
        </div>
        <div className="form-group">
          <label htmlFor="phoneNumberInput">Số Điện Thoại</label>
          <input type="text" id="phoneNumberInput" ref={phoneNumberRef} value={student && phoneNumber}
                 disabled={true}
                 onChange={onPhoneNumberChange}
                 className="form-control"/>
        </div>
        <div className="form-group">
          <label htmlFor="classInput">Giới Tính:</label>
          <div>
            <select className="form-control"
                    id="genderInput"
                    ref={genderInputRef}
                    onChange={onGenderChange}
                    disabled={true}
                    value={student && gender}>
              <option value="false">Nam</option>
              <option value="true">Nữ</option>
            </select>

          </div>
        </div>
        {/*<button type="submit" className="btn btn-primary">Cập Nhật</button>*/}
      </form>
    </div>
  )
}

ReactDOM.render(React.createElement(App), document.getElementById('app'))
