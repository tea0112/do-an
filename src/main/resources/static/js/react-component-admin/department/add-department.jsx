function App() {
  const generalCheckRef = React.useRef(null);
  const nameRef = React.useRef(null);

  // submit
  const onSubmit = async (e) => {
    e.preventDefault();
    if (generalCheckRef) {
      if (generalCheckRef.current.value !== '') {
        try {
          const response = await axios.post(`/api/admin/departments`, {
            name: nameRef.current.value,
            isGeneral: generalCheckRef.current.checked
          })
          alert('Thêm Khoa thành công')
        } catch (e) {
          console.log(e)
          alert('Thêm Khoa thất bại')
        }
      }
    }
  }
  return (
    <div>
      <h1>Thêm Khoa</h1>
      <form onSubmit={onSubmit}>
        <div className="form-group">
          <label>Nhập Tên Khoa</label>
          <input type="text" className="form-control" id={name} ref={nameRef}/>
        </div>
        <div className="form-group form-check">
          <input
            type="checkbox"
            className="form-check-input"
            id="generalCheck"
            ref={generalCheckRef}
          />
          <label>
            Là Khoa Chung
          </label>
        </div>
        <button className="btn btn-primary">Thêm</button>
      </form>
    </div>
  );
}

ReactDOM.render(React.createElement(App), document.getElementById('app'));

const deepFreeze = (object) => {
  const propNames = Object.getOwnPropertyNames(object);
  for (const name of propNames) {
    const value = object[name];
    if (value && typeof value === 'object') {
      deepFreeze(value);
    }
  }
  return Object.freeze(object);
};
