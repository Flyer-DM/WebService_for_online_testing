function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}

labels = []
data = []
let tr = document.getElementsByClassName('table-tr');
for (let i = 0; i < tr.length; i++) {
    let t = tr[i].getElementsByClassName('table-th')[4].textContent;
    let test_name = tr[i].getElementsByClassName('table-th')[1].textContent;

    if (!labels.includes(t)) {
        let ind = Math.max(labels.length, 0);
        labels[ind] = test_name;
        data[ind] = Number(t);
    } else {
        let ind = labels.indexOf(t);
        data[ind] += t;
    }
}

let colors = [];
for (let i = 0; i < labels.length; i++) {
    let temp = 'rgba(' + getRandomInt(0, 256) + ', ' + getRandomInt(0, 256) + ', ' + getRandomInt(0, 256) + ', 1)';
    while (colors.includes(temp)) {
        temp = 'rgba(' + getRandomInt(0, 256) + ', ' + getRandomInt(0, 256) + ', ' + getRandomInt(0, 256) + ', 1)';
    }

    colors[i] = temp;
}

let dataset = {
    label: 'Количество прохождений',
    data: data,
    backgroundColor: colors,
    borderWidth: 1,
}

Chart.defaults.global.defaultFontColor='black';

let ctx = document.getElementById('hist').getContext('2d');
let myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: labels,
        datasets: [dataset],
    },
    options: {
        legend: {
            display: false,
        },
        title: {
            display: true,
            text: 'Статистика по тестам',
            position: 'top',
            fontSize: 24,
            padding: 20,
            fontColor: "black",
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true,
                    stepSize: 1,
                },
                scaleLabel: {
                    display: true,
                    labelString: 'Количество прохождений',
                    fontSize: 18,
                    fontColor: "black"
                },
            }],
            xAxes: [{
                scaleLabel: {
                    display: true,
                    labelString: 'Тест',
                    fontSize: 18,
                    fontColor: "black"
                },
            }],
        },
    },
});