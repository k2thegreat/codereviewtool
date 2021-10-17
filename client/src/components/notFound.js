import styled from "styled-components"

const Div = styled.div`
    height: 100%;
    background-image: url("${process.env.PUBLIC_URL}/assets/404.jpg");
    background-size: cover;
`

export const NotFound = () => {
    return <Div>
        Not Found
    </Div>
}