export const prUrl = 'https://wfrbitbucket.int.kronos.com/projects/WFR/repos/zeyt/pull-requests/60982/overview'

export const BASE_URL = '/codereviewtool/suggestions'

export const gradientPalette = {
    0: 'radial-gradient( circle farthest-corner at 10% 20%,  rgba(14,174,87,1) 0%, rgba(12,116,117,1) 90% )',
    1: 'radial-gradient( circle farthest-corner at 10% 20%,  rgba(147,67,67,1) 0%, rgba(111,27,27,1) 90% )',
    2: 'radial-gradient( circle farthest-corner at -4.5% 34.3%,  rgba(13,20,174,1) 0%, rgba(243,165,140,1) 90% )',
    3: 'radial-gradient( circle 976px at 51.2% 51%,  rgba(11,27,103,1) 0%, rgba(16,66,157,1) 0%, rgba(11,27,103,1) 17.3%, rgba(11,27,103,1) 58.8%, rgba(11,27,103,1) 71.4%, rgba(16,66,157,1) 100.2%, rgba(187,187,187,1) 100.2% )',
    4: 'linear-gradient( 358.4deg,  rgba(249,151,119,1) -2.1%, rgba(98,58,162,1) 90% )',
    5: 'linear-gradient( 99deg,  rgba(115,18,81,1) 10.6%, rgba(28,28,28,1) 118% )',
    6: 'linear-gradient( 73.1deg,  rgba(34,126,34,1) 8%, rgba(99,162,17,1) 86.9% )',
    7: 'linear-gradient( 106.9deg,  rgba(148,14,60,1) 60.9%, rgba(3,22,27,1) 122.3% )',
    8: 'radial-gradient( circle farthest-corner at -4.5% 34.3%,  rgba(13,20,174,1) 0%, rgba(243,165,140,1) 90% )',
    9: 'radial-gradient( circle 941px at 2.6% 6.8%,  rgba(124,74,228,0.81) 15.9%, rgba(249,208,40,0.70) 88.6% )',
}

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