import Slider from 'react-slick'
import { StyledCard } from './card'
import React from 'react'
import { reviewService } from '../services/reviewService'
import styled from 'styled-components'
import 'slick-carousel/slick/slick.css'
import 'slick-carousel/slick/slick-theme.css'
import { Styles, Table, columns, transformData } from './reviews'
import { TypesChart } from './typesChart'

const sliderSettings = {
    dots: true,
    infinite: true,
    speed: 600,
    slidesToShow: 4,
    slidesToScroll: 4,
}

const Wrapper = styled.div`
padding: 30px;

.slick-arrow {
    &::before {
        color: #0077b6;
        transition: opacity 0.2s linear;
    }
}

.content {
    display: flex;
    margin-top: 15px;

    > div:first-child {
        max-width: 60%;
    }

    > div:last-child {
        margin-top: 20px;
        > div {
            border-radius: 4px;
            border: 1px solid #00b4d8;
            box-shadow: 2px 2px 6px 2px rgba(0, 119, 182, 0.4);
        }
    }
}
`

export const Dashboard = props => {
    const [fileTypes, setFileTypes] = React.useState([])
    const [data, setData] = React.useState()

    React.useEffect(() => {
        reviewService().getFileTypes().then(({ data }) => {
            const fileTypes = Object.keys(data).reduce((acc, type) => {
                if (data[type] < 1) {
                    return acc
                }
                acc.push({ type, count: data[type] })
                return acc
            }, [])
            setFileTypes(fileTypes)
            setReviewData(fileTypes[0].type)
        })
    }, [])

    const setReviewData = fileType => {
        reviewService().getReviews(fileType).then(({ data }) => {
            setData(transformData(data))
        })
    }

    const handleCardSelect = fileType => {
        setReviewData(fileType)
    }

    return <Wrapper>
        {fileTypes.length > 0 && <Slider {...sliderSettings}>
            {fileTypes.map(({ type, count }) => {
                return <StyledCard key={type} fileType={type} count={count} onSelect={handleCardSelect} />
            })}
        </Slider>}
        <div className="content">
            {data && <Styles offset={400}>
                <Table columns={columns} data={data} />
            </Styles>}
            {fileTypes.length > 0 && <TypesChart data={fileTypes.reduce((acc, { type, count }) => {
                const data = [type, count]
                acc.push(data)
                return acc
            }, [])} />}
        </div>
    </Wrapper>
}