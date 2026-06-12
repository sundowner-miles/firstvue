import request from './request'

export function uploadAvatar(file) {
  const formData = new FormData()
  formData.append('file', file)
  // 不要手动设置 Content-Type，axios 会自动带上 boundary
  return request.post('/upload/avatar', formData, {
    timeout: 600000
  })
}
