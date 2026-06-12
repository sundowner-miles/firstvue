import axios from 'axios'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

request.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res.code === 200) {
      return res
    }
    return Promise.reject(new Error(res.message || '请求失败'))
  },
  (error) => {
    const data = error.response?.data
    const message = data?.message || data?.error || error.message || '网络错误'
    return Promise.reject(new Error(message))
  }
)

export default request
