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

const reviewsCopy = [...reviews, ...reviews]

export const reviewService = () => {
    const getReviews = () => {
        return Promise.resolve(reviewsCopy)
    }

    return {
        getReviews
    }
}