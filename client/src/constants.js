export const prUrl = 'https://wfrbitbucket.int.kronos.com/projects/WFR/repos/zeyt/pull-requests/60982/overview'

export const BASE_URL = '/codereviewtool/suggestions'

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

export const fileTypes = {'UI_CONTROL': 10000, 'UI_FORM': 153000, 'UI_SCREEN': 122343, 'END_POINT': 12345}

export const reviewsCopy = [...reviews, ...reviews]