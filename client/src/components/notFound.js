import styled from "styled-components"
import { useHistory } from 'react-router-dom'

const Div = styled.div`
    position: relative;
    height: 100%;
    background-image: url("${process.env.PUBLIC_URL}/assets/404.jpg");
    background-size: cover;
`

const InfoDiv = styled.div`
    position: absolute;
    right: 15%;
    top: 20%;
    width: 350px;
    color: #caf0f8;

    span {
        display: inline-block;
        padding: 10px;
        border: 1px solid #00b4d8;
        cursor: pointer;
        transition: box-shadow 0.2s linear;

        &:hover {
            box-shadow: 1px 1px 6px #00b4d8
        }
    }

    * {
        margin-bottom: 25px;
        margin-top: 0;
    }
`

export const NotFound = () => {
    const history = useHistory()

    const handleDashboardNavigate = () => {
        history.push('/dashboard')
    }

    return <Div>
        <InfoDiv>
            <h2>Don't be so sad.</h2>
            <span title="Go to dashboard" onClick={handleDashboardNavigate}>It's just a 404 error</span>
            <p>What you are looking for may have been misplaced in Long Term Memory</p>
        </InfoDiv>
    </Div>
}