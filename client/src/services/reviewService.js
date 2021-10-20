import axios from "axios";

const BASE_URL = '/codereviewtool/suggestions'

const reviews = [
    {
        "type": "UI_CONTROL",
        "comments": [
            {
                "comment": "Please use Set instead of arraylist",
                "author": "reviewer"
            },
            {
                "comment": "Done",
                "author": "owner"
            }
        ],
        "reviewer": 'amit.a.singh@ukg.com',
        "pullRequestLink": 'https://wfrbitbucket.int.kronos.com/projects/WFR/repos/zeyt/pull-requests/60982/overview%22',
        "date": "1634198878790",
        "props": {}
    },
    {
        "type": "UI_FORM",
        "comments": [
            {
                "comment": "Please make fornmane correct",
                "author": "reviewer"
            },
            {
                "comment": "Done",
                "author": "owner"
            }
        ],
        "reviewer": 'amit.a.singh@ukg.com',
        "pullRequestLink": 'https://wfrbitbucket.int.kronos.com/projects/WFR/repos/zeyt/pull-requests/60982/overview%22',
        "date": "1634198878790",
        "props": {}
    },
    {
        "type": "UI_SCREEN",
        "comments": [
            {
                "comment": "Please use screen",
                "author": "reviewer"
            },
            {
                "comment": "Done",
                "author": "owner"
            }
        ],
        "reviewer": 'amit.a.singh@ukg.com',
        "pullRequestLink": 'https://wfrbitbucket.int.kronos.com/projects/WFR/repos/zeyt/pull-requests/60982/overview%22',
        "date": "1634198878790",
        "props": {}
    },
    {
        "type": "END_POINT",
        "comments": [
            {
                "comment": "Please use Set instead of EndPoint",
                "author": "reviewer"
            },
            {
                "comment": "Done",
                "author": "owner"
            }
        ],
        "reviewer": 'amit.a.singh@ukg.com',
        "pullRequestLink": 'https://wfrbitbucket.int.kronos.com/projects/WFR/repos/zeyt/pull-requests/60982/overview%22',
        "date": "1634198878790",
        "props": {}
    }
]

const fileTypes = {'UI_CONTROL': 10000, 'UI_FORM': 153000, 'UI_SCREEN': 122343, 'END_POINT': 12345}

const reviewsCopy = [...reviews, ...reviews]

export const reviewService = () => {
    const getAllReviews = (prLink, { page, size }) => {
        return axios.get(`${BASE_URL}?url=${prLink}`, { params: { page, size } })
        // return Promise.resolve({data: reviewsCopy})
    }

    const getFileTypes = () => {
        return axios.get(`${BASE_URL}/allTypes`)
        // return Promise.resolve({data: fileTypes})
    }

    const getReviews = (fileType, { page, size }) => {
        return axios.get(`${BASE_URL}/${fileType}`, { params: { page, size } })
        // const reviews = reviewsCopy.filter(({ type }) => fileType === type)
        // return Promise.resolve({data: reviews})
    }

    return {
        getReviews,
        getFileTypes,
        getAllReviews,
    }
}