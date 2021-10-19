import styled from 'styled-components'
import { useTable, useExpanded } from 'react-table'
import { reviewService } from '../services/reviewService'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronRight } from '@fortawesome/free-solid-svg-icons'
import React from 'react'

const columns = [
    {
    id: 'expander',
    Cell: ({ row }) =>
        row.canExpand ? (
        <span
            {...row.getToggleRowExpandedProps({
            style: {
                paddingLeft: `${row.depth * 2}px`,
            },
            })}
        >
            <FontAwesomeIcon className={row.isExpanded ? "m-expanded" : ""} color="black" icon={faChevronRight} />
        </span>
        ) : null,
    },
    {
        Header: 'File Type',
        accessor: 'type',
    },
    {
        Header: 'PR Link',
        accessor: 'pullRequestLink',
    },
    {
        Header: 'Reviewer',
        accessor: 'reviewer',
    },
    {
        Header: 'Comment',
        accessor: 'comment',
    },
    {
        Header: 'Author',
        accessor: 'author',
    },
    {
        Header: 'Date',
        accessor: 'date',
    },
]

const Styles = styled.div`
padding: 20px;

table {
    display: inline-block;
    max-width: 100%;
    max-height: calc(100vh - 60px);
    border-spacing: 0;
    border: 1px solid black;
    overflow: auto;

    tr {
        &:last-child {
            td {
                border-bottom: 0;
            }
        }
    }

    th,
    td {
        margin: 0;
        padding: 10px;
        border-bottom: 1px solid black;
        border-right: 1px solid black;

        :last-child {
            border-right: 0;
        }
    }

    td:first-child {
        svg {
            transition: transform 0.2s linear;

            &.m-expanded {
                transform: rotate(90deg)
            }
        }
    }

    td:first-child, th:first-child {
        position: sticky;
        left: 0;
        z-index: 1;
        background-color: #90e0ef;
    }

    thead tr th {
        position: sticky;
        top: 0;
        background-color: #90e0ef;
    }

    th:first-child {
        z-index: 2;
    }
}
`

function Table({ columns: userColumns, data }) {
    const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow,
    } = useTable(
    {
        columns: userColumns,
        data,
    },
    useExpanded
    )

    return (
    <>
        <table {...getTableProps()}>
            <thead>
                {headerGroups.map(headerGroup => (
                <tr {...headerGroup.getHeaderGroupProps()}>
                    {headerGroup.headers.map(column => (
                        <th {...column.getHeaderProps()}>{column.render('Header')}</th>
                    ))}
                </tr>
                ))}
            </thead>
            <tbody {...getTableBodyProps()}>
                {rows.map((row, i) => {
                    prepareRow(row)
                    return (
                        <tr {...row.getRowProps()}>
                            {row.cells.map(cell => {
                                return <td {...cell.getCellProps()}>{cell.render('Cell')}</td>
                            })}
                        </tr>
                    )
                })}
            </tbody>
        </table>
    </>
    )
}

const transformData = data => {
    return data.map(({ type, comments, reviewer, pullRequestLink, date }) => {
        const data = { type, comment: comments[0].comment, author: comments[0].author === 'reviewer' ? reviewer : 'author', reviewer, pullRequestLink, date: new Date(+date).toLocaleDateString() }
        if (comments.length > 1) {
            const commentsCopy = [...comments]
            commentsCopy.shift()
            data.subRows = commentsCopy.map(({ comment, author }) => ({ comment, author: author === 'reviewer' ? reviewer : 'author' }))
        } else {
            data.subRows = undefined
        }
        return data
    })
}

export const Reviews = props => {
    const [data, setData] = React.useState()

    React.useEffect(() => {
        reviewService().getReviews().then(data => {
            setData(transformData(data))
        })
    }, [])

    return <Styles>
        {data && <Table columns={columns} data={data} />}
    </Styles>
}