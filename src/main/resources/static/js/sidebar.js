var grade = document.getElementsByClassName("grade")[0];
var gradeShow = document.getElementsByClassName("grade-show")[0];
grade.addEventListener("click", function (event) {
    grade.classList.toggle("collapsed");
    gradeShow.classList.toggle("show");
});