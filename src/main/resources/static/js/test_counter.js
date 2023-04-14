function getRowsColumn() {
    let table = document.getElementById('1')
    let tBody = table.querySelector('tbody')
    const count = tBody.querySelectorAll('tr').length;
    document.write('Количество тестов в системе: ' + count)
}

getRowsColumn()