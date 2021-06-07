const lecturerInput = document.getElementById("lecturerInput");
const departmentInput = document.getElementById("departmentInput");
const subjectInput = document.getElementById("subjectInput");
const subjectTypeInput = document.getElementById("subjectTypeInput");
const sessionInput = document.getElementById("sessionInput");
const classInput = document.getElementById("classInput");
const semesterInput = document.getElementById("semesterInput");
const saveAlert = document.getElementById("alert");

if (saveAlert) {
  if (saveAlert.className === "error")
    alert("Không thể thêm thời khoá biểu");
  else
    alert("Thêm thành công thời khoá biểu")
}

commonGetFetchOpt("/admin/session", sessionInput, bindWithIdAndName)
  .then(() => commonGetFetchOpt(
    "/admin/classes?department=" + departmentInput.value + "&session=" + sessionInput.value,
    classInput, bindWithIdAndName))
  .then(() => commonGetFetchOpt("/api/semesters?sessionId=" + sessionInput.value,
    semesterInput, bindWithId))
commonGetFetchOpt("/admin/lecturers?department=" + departmentInput.value, lecturerInput, bindWithIdAndName);
commonGetFetchOpt("/admin/subject?subjectType=" + subjectTypeInput.value + "&department=" + departmentInput.value,
  subjectInput, bindWithIdAndName);

function changeDpm() {
  commonGetFetchOpt(
    "/admin/classes?department=" + departmentInput.value + "&session=" + sessionInput.value,
    classInput, bindWithIdAndName);
  commonGetFetchOpt("/admin/lecturers?department=" + departmentInput.value, lecturerInput, bindWithIdAndName);
  commonGetFetchOpt("/admin/subject?subjectType=" + subjectTypeInput.value
    + "&department=" + departmentInput.value, subjectInput, bindWithIdAndName);
}

function handleSubjectTypeChange() {
  commonGetFetchOpt("/admin/subject?subjectType=" + subjectTypeInput.value + "&department=" + departmentInput.value,
    subjectInput, bindWithIdAndName);
}

function handleSessionChange() {
  commonGetFetchOpt(
    "/admin/classes?department=" + departmentInput.value + "&session=" + sessionInput.value,
    classInput, bindWithIdAndName);
  commonGetFetchOpt("/api/semesters?sessionId=" + sessionInput.value, semesterInput, bindWithId)
}

function handleClassChange() {
  commonGetFetchOpt("/api/semesters?sessionId=" + sessionInput.value, semesterInput, bindWithId)
}

// Common
function commonGetFetchOpt(url, parentSlt, binding) {
  $("#" + parentSlt.id).empty();
  return fetch(url)
    .then(function (response) {
      return response.json()
    })
    .then(data => {
        return {
          data,
          parentSlt
        }
      }
    )
    .then((data) => binding(data))
    .catch(function (err) {
      console.log('Fetch (' + url + ') Error - ', err);
    })
}


function bindWithIdAndName(response = {}) {
  response.data.forEach(function (value) {
    const childSlt = document.createElement('option');
    childSlt.value = value.id;
    childSlt.innerHTML = value.name;
    response.parentSlt.appendChild(childSlt);
  })
}

function bindWithId(response = {}) {
  response.data.forEach(function (value) {
    const childSlt = document.createElement('option');
    childSlt.value = value.id;
    childSlt.innerHTML = value.id;
    response.parentSlt.appendChild(childSlt);
  })
}

