import Slider from 'react-slick'
import { StyledCard } from './card'
import React from 'react'
import { reviewService } from '../services/reviewService'
import styled from 'styled-components'
import 'slick-carousel/slick/slick.css'
import 'slick-carousel/slick/slick-theme.css'
import { Styles, Table, columns, transformData } from './reviews'

const sliderSettings = {
    dots: true,
    infinite: true,
    speed: 600,
    slidesToShow: 4,
    slidesToScroll: 4,
}

const Wrapper = styled.div`
padding: 30px;
`

export const Dashboard = props => {
    const [fileTypes, setFileTypes] = React.useState([])
    const [selectedType, setSelectedType] = React.useState()
    const [data, setData] = React.useState()

    React.useEffect(() => {
        reviewService().getFileTypes().then(fileTypes => {
            setFileTypes(fileTypes)
            setSelectedType(fileTypes[0].type)
        })
    }, [])

    React.useEffect(() => {
        reviewService().getReviews(selectedType).then(data => {
            setData(transformData(data))
        })
    }, [selectedType])

    const handleCardSelect = fileType => {
        setSelectedType(fileType)
    }

    return <Wrapper>
        <Slider {...sliderSettings}>
            {fileTypes.map(({ type, count }) => {
                return <StyledCard key={type} fileType={type} count={count} onSelect={handleCardSelect} />
            })}
        </Slider>
        <Styles>
            {data && <Table columns={columns} data={data} />}
        </Styles>
    </Wrapper>
}