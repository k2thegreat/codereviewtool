import axios from "axios";

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

const fileTypes = [{type: 'UI_CONTROL', count: 10000}, {type: 'UI_FORM', count: 153000}, {type: 'UI_SCREEN', count: 122343}, {type: 'END_POINT', count: 12345}]

const reviewsCopy = [...reviews, ...reviews]

export const reviewService = () => {
    const getAllReviews = () => {
        return Promise.resolve(reviewsCopy)
    }

    const getFileTypes = () => {
        return Promise.resolve(fileTypes)
    }

    const getReviews = fileType => {
        const reviews = reviewsCopy.filter(({ type }) => fileType === type)
        return Promise.resolve(reviews)
    }

    return {
        getReviews,
        getFileTypes,
        getAllReviews,
    }
}