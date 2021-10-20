import styled from 'styled-components'
import { useTable, useExpanded, usePagination } from 'react-table'
import { reviewService } from '../services/reviewService'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronRight } from '@fortawesome/free-solid-svg-icons'
import React from 'react'
import { toast } from 'react-toastify'
import { prUrl, syntax } from '../constants'
import { PrismAsyncLight as SyntaxHighlighter } from 'react-syntax-highlighter'
import prism from 'react-syntax-highlighter/dist/esm/styles/prism/prism'

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
        Header: 'Reviewer',
        accessor: 'reviewer',
    },
    {
        Header: 'Code Snippet',
        accessor: 'codeSnippet',
        Cell: ({ cell: { row: { values: { type } }, value } }) => value ? <SyntaxHighlighter language={syntax[type] ?? 'java'} style={prism}>{value}</SyntaxHighlighter> : '',
    },
    {
        Header: 'Comment',
        accessor: 'comment',
    },
    {
        Header: 'File Name',
        accessor: 'fileName',
    },
    {
        Header: 'PR Link',
        accessor: 'pullRequestLink',
        Cell: ({ cell: { value } }) => <a href={value} target="_blank">{value}</a>,
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

const getHeight = offset => `calc(100vh - ${offset}px)`

export const Styles = styled.div`
padding: 20px;

.input {
    margin-bottom: 15px;
}

.pr-link {
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
    color: white;
    border: 1px solid #0077b6;
    background-color: #0077b6;
    border-radius: 6px;
    outline: none;
    transition: all 0.2s linear;

    &:hover, &:focus {
        color: white;
        background-color: #03045e;
        border-color: #03045e;
    }
}

table {
    display: inline-block;
    max-width: 100%;
    max-height: ${({ offset }) => getHeight(offset)};
    border-spacing: 0;
    border: 1px solid #00b4d8;
    border-radius: 4px;
    box-shadow: 2px 2px 6px 2px rgba(0, 119, 182, 0.4);
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

.pagination {
    button {
        &:not(:disabled) {
            cursor: pointer;
        }
    }
    
    select {
        cursor: pointer;
    }
}
`

export function Table({ columns: userColumns, data, fetchReviews, pageCount: controlledPageCount }) {
    const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    page,
    prepareRow,
    canPreviousPage,
    canNextPage,
    pageOptions,
    pageCount,
    gotoPage,
    nextPage,
    previousPage,
    setPageSize,
    state: { pageIndex, pageSize },
    } = useTable(
    {
        columns: userColumns,
        data,
        initialState: { pageIndex: 0 },
        manualPagination: true,
        pageCount: controlledPageCount,
    },
    useExpanded,
    usePagination,
    )

    React.useEffect(() => {
        fetchReviews({ page: pageIndex, size: pageSize })
    }, [fetchReviews, pageIndex, pageSize])

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
                {page.map(row => {
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
        <div className="pagination">
            <button onClick={() => gotoPage(0)} disabled={!canPreviousPage}>
                {'<<'}
            </button>{' '}
            <button onClick={() => previousPage()} disabled={!canPreviousPage}>
                {'<'}
            </button>{' '}
            <button onClick={() => nextPage()} disabled={!canNextPage}>
                {'>'}
            </button>{' '}
            <button onClick={() => gotoPage(pageCount - 1)} disabled={!canNextPage}>
                {'>>'}
            </button>{' '}
            <span>
                Page{' '}
                <strong>
                    {pageIndex + 1} of {pageOptions.length}
                </strong>{' '}
            </span>
            <span>
                | Go to page:{' '}
                <input
                type="number"
                defaultValue={pageIndex + 1}
                onChange={e => {
                    const page = e.target.value ? Number(e.target.value) - 1 : 0
                    gotoPage(page)
                }}
                style={{ width: '100px' }}
                />
            </span>{' '}
            <select
                value={pageSize}
                onChange={e => {
                setPageSize(Number(e.target.value))
                }}
            >
                {[10, 20, 30, 40, 50].map(pageSize => (
                <option key={pageSize} value={pageSize}>
                    Show {pageSize}
                </option>
                ))}
            </select>
        </div>
    </>
    )
}

export const transformData = data => {
    return data.map(({ type, comments, fileName, reviewer, pullRequestLink, date, codeSnippet }) => {
        const data = { type, codeSnippet, fileName, comment: comments[0]?.comment ?? '', author: comments[0]?.author === 'reviewer' ? reviewer : comments[0]?.author ?? '', reviewer, pullRequestLink, date: new Date(+date).toLocaleDateString() }
        if (comments.length > 1) {
            const commentsCopy = [...comments]
            commentsCopy.shift()
            data.subRows = commentsCopy.map(({ comment, author }) => ({ comment, author: author === 'reviewer' ? reviewer : comments[0]?.author ?? '' }))
        } else {
            data.subRows = undefined
        }
        return data
    })
}

export const Reviews = () => {
    const [data, setData] = React.useState()
    const [prLink, setPRLink] = React.useState(prUrl)

    const fetchReviews = React.useCallback(({ page = 0, size = 10 }) => {
        toast.promise(reviewService().getAllReviews(prLink, { page, size }), {
            pending: 'Fetching review data',
            success: 'Fetched successfully 👌',
            error: 'Error while fetching 🤯'
        }).then(({ data }) => {
            setData(transformData(data))
        })
    }, [prLink])

    return <Styles offset={150}>
        <div className="input">
            <label className="pr-link">Enter PR link: <input value={prLink} onChange={e => setPRLink(e.target.value)} /></label>
            <button className="go" onClick={() => fetchReviews({})}>Go</button>
        </div>
        {data && <Table columns={columns} data={data} fetchReviews={fetchReviews} pageCount={10} />}
    </Styles>
}