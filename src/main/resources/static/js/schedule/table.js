//define data array
const theoryData = fetch('/thoi-khoa-bieu/ly-thuyet/theory-schedule')
    .then(
        function (response) {
            if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                return;
            }
        response.json().then(function (tableData) {
                const morningData = tableData.filter(schedule => schedule.periodType === 0);
                const morningTable = new Tabulator("#morning-table", {
                    data: morningData,           //load row data from array
                    layout: "fitColumns",      //fit columns to width of table
                    responsiveLayout: "hide",  //hide columns that dont fit on the table
                    tooltips: true,            //show tool tips on cells
                    addRowPos: "top",          //when adding a new row, add it to the top of the table
                    history: true,             //allow undo and redo actions on the table
                    pagination: "local",       //paginate the data
                    paginationSize: 100,         //allow 7 rows per page of data
                    movableColumns: true,      //allow column order to be changed
                    resizableRows: true,       //allow row order to be changed
                    initialSort: [             //set the initial sort order of the data
                        {column: "startPeriod", dir: "asc"},
                    ],
                    columns: [                 //define the table columns
                        {title: "Môn", field: "subject.name", headerSort:false},
                        {title: "Giảng Viên", field: "lecturer.name", headerSort:false},
                        {title: "Tiết Bắt Đầu", field: "startPeriod", headerSort:false},
                        {title: "Tiết Kết Thúc", field: "endPeriod", headerSort:false},
                    ],
                });
                const afternoonData = tableData.filter(schedule => schedule.periodType === 1);
                const afternoonTable = new Tabulator("#afternoon-table", {
                    data: afternoonData,           //load row data from array
                    layout: "fitColumns",      //fit columns to width of table
                    responsiveLayout: "hide",  //hide columns that dont fit on the table
                    tooltips: true,            //show tool tips on cells
                    addRowPos: "top",          //when adding a new row, add it to the top of the table
                    history: true,             //allow undo and redo actions on the table
                    pagination: "local",       //paginate the data
                    paginationSize: 100,         //allow 7 rows per page of data
                    movableColumns: true,      //allow column order to be changed
                    resizableRows: true,       //allow row order to be changed
                    initialSort: [             //set the initial sort order of the data
                        {column: "startPeriod", dir: "asc"},
                    ],
                    columns: [                 //define the table columns
                        {title: "Môn", field: "subject.name", headerSort:false},
                        {title: "Giảng Viên", field: "lecturer.name", headerSort:false},
                        {title: "Tiết Bắt Đầu", field: "startPeriod", headerSort:false},
                        {title: "Tiết Kết Thúc", field: "endPeriod", headerSort:false},
                    ],
                });
            });
        }
    )
    .catch(function (err) {
        console.log('Fetch THEORY Error :-S', err);
    });

//define data array
const practiceData = fetch('/thoi-khoa-bieu/thuc-hanh/practice-schedule')
    .then(
        function (response) {
            if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                return;
            }
            response.json().then(function (tableData) {
                const morningData = tableData.filter(schedule => schedule.periodType === 0);
                const morningTable = new Tabulator("#morning-practice-table", {
                    data: morningData,           //load row data from array
                    layout: "fitColumns",      //fit columns to width of table
                    responsiveLayout: "hide",  //hide columns that dont fit on the table
                    tooltips: true,            //show tool tips on cells
                    addRowPos: "top",          //when adding a new row, add it to the top of the table
                    history: true,             //allow undo and redo actions on the table
                    pagination: "local",       //paginate the data
                    paginationSize: 100,         //allow 7 rows per page of data
                    movableColumns: true,      //allow column order to be changed
                    resizableRows: true,       //allow row order to be changed
                    initialSort: [             //set the initial sort order of the data
                        {column: "startPeriod", dir: "asc"},
                    ],
                    columns: [                 //define the table columns
                        {title: "Môn", field: "subject.name", headerSort:false},
                        {title: "Giảng Viên", field: "lecturer.name", headerSort:false},
                        {title: "Tiết Bắt Đầu", field: "startPeriod", headerSort:false},
                        {title: "Tiết Kết Thúc", field: "endPeriod", headerSort:false},
                    ],
                });
                const afternoonData = tableData.filter(schedule => schedule.periodType === 1);
                const afternoonTable = new Tabulator("#afternoon-practice-table", {
                    data: afternoonData,           //load row data from array
                    layout: "fitColumns",      //fit columns to width of table
                    responsiveLayout: "hide",  //hide columns that dont fit on the table
                    tooltips: true,            //show tool tips on cells
                    addRowPos: "top",          //when adding a new row, add it to the top of the table
                    history: true,             //allow undo and redo actions on the table
                    pagination: "local",       //paginate the data
                    paginationSize: 100,         //allow 7 rows per page of data
                    movableColumns: true,      //allow column order to be changed
                    resizableRows: true,       //allow row order to be changed
                    initialSort: [             //set the initial sort order of the data
                        {column: "startPeriod", dir: "asc"},
                    ],
                    columns: [                 //define the table columns
                        {title: "Môn", field: "subject.name", headerSort:false},
                        {title: "Giảng Viên", field: "lecturer.name", headerSort:false},
                        {title: "Tiết Bắt Đầu", field: "startPeriod", headerSort:false},
                        {title: "Tiết Kết Thúc", field: "endPeriod", headerSort:false},
                    ],
                });
            });
        }
    )
    .catch(function (err) {
        console.log('Fetch THEORY Error :-S', err);
    });

