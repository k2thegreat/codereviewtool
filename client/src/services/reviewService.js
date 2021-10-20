import axios from "axios";
import { BASE_URL, fileTypes, reviewsCopy } from '../constants'

export const reviewService = () => {
    const getAllReviews = (prLink, { page, size }) => {
        return axios.get(BASE_URL, { params: { url: prLink, page, size } })
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