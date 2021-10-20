import styled from 'styled-components'
import { gradientPalette } from '../constants'

const Card = ({ fileType, count, className, onSelect }) => {
    return <div className={className}>
        <div onClick={() => { onSelect(fileType) }}>
            <h3>{fileType}</h3>
            <h4>{count.toLocaleString()}</h4>
        </div>
    </div>
}

export const StyledCard = styled(Card)`
    padding: 10px;
    width: 100%;
    box-sizing: border-box;

    > div {
        padding: 10px 20px;
        border-radius: 8px;
        box-shadow: 2px 2px 4px 2px rgba(0, 119, 182, 0.4);
        color: white;
        background-image: ${({ gradientNum }) => gradientPalette[gradientNum]};
        border: 1px solid #0077b6;
        cursor: pointer;
        transition: border-color 0.2s linear;

        h3, h4 {
            margin-top: 0;
        }

        &:hover {
            border-color: #caf0f8;
        }
    }
`