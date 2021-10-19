import styled from 'styled-components'
import { useTable, useExpanded } from 'react-table'
import { reviewService } from '../services/reviewService'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronRight } from '@fortawesome/free-solid-svg-icons'
import React from 'react'

export const columns = [
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

export const Styles = styled.div`
padding: 20px;

.input {
    margin-bottom: 15px;
}

.prlink {
    input {
        width: 250px;
        padding: 5px;
        border: 1px solid grey;
        border-radius: 6px;
        outline: none;

        &:focus {
            border-color: #00b4d8;
        }
    }
}

.go {
    margin-left: 10px;
    padding: 5px;
    cursor: pointer;
    border: 1px solid grey;
    border-radius: 6px;
    outline: none;
    transition: all 0.2s linear;

    &:hover, &:focus {
        color: white;
        background-color: #0077b6;
        border-color: #0077b6;
    }
}

table {
    display: inline-block;
    max-width: 100%;
    max-height: calc(100vh - 60px);
    border-spacing: 0;
    border: 1px solid grey;
    border-radius: 4px;
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
        border-bottom: 1px solid grey;
        border-right: 1px solid grey;

        :last-child {
            border-right: 0;
        }
    }

    td {
        background-color: white;
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

export function Table({ columns: userColumns, data }) {
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

export const transformData = data => {
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

export const Reviews = () => {
    const [data, setData] = React.useState()

    React.useEffect(() => {
        reviewService().getAllReviews().then(data => {
            setData(transformData(data))
        })
    }, [])

    return <Styles>
        <div class="input">
            <label class="prlink">Enter PR link: <input value="https://wfrbitbucket.int.kronos.com/projects/WFR/repos/zeyt/pull-requests/60982/overview" /></label>
            <button class="go">Go</button>
        </div>
        {data && <Table columns={columns} data={data} />}
    </Styles>
}