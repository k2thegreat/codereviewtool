import React from 'react'
import HighchartsReact from 'highcharts-react-official'
import Highcharts from 'highcharts'
import highcharts3d from 'highcharts/highcharts-3d'
highcharts3d(Highcharts)

export const TypesChart = ({ data }) => {
    const chartOptions = React.useRef({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45
            }
        },
        title: {
            text: 'Review Comments'
        },
        subtitle: {
            text: 'Share by file type'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                innerSize: 100,
                depth: 45
            }
        },
        series: [{
            name: 'Total share: ',
            data,
        }]
    })

    return <div>
        <HighchartsReact
            options = {chartOptions.current}
            highcharts={Highcharts}
        />
    </div>
}