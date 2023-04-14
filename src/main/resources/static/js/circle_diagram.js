let percentage = parseFloat(document.getElementById('percentage').textContent);
data = [percentage, 100 - percentage]
let ctx = document.getElementById('myChart').getContext('2d');
let myChart = new Chart(ctx, {
    type: 'doughnut',
    data: {
        labels: ['Правильные ответы', 'Ошибки'],
        datasets: [{
            data: data,
            backgroundColor: ['#00e676', '#e91e63'],
            borderWidth: 0.5 ,
            borderColor: '#ddd'
        }]
    },
    options: {
        title: {
            display: true,
            text: 'Статистика тестирования',
            position: 'top',
            fontSize: 16,
            fontColor: '#111',
            padding: 20
        },
        legend: {
            display: true,
            position: 'bottom',
            labels: {
                boxWidth: 20,
                fontColor: '#111',
                padding: 15
            }
        },
        plugins: {
            datalabels: {
                color: '#111',
                textAlign: 'center',
                font: {
                    lineHeight: 1.6
                },
                formatter: function(value, ctx) {
                    return ctx.chart.data.labels[ctx.dataIndex] + '\n' + value + '%';
                }
            }
        }
    }
});