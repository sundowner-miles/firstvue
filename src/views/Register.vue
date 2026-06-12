<template>
  <div class="page-container">
    <div class="auth-box">
      <h2 class="auth-title">注册新用户</h2>
      <div class="reg-content">
        <div class="reg-form">
          <el-form :model="regForm" :rules="rules" ref="regForm" label-width="0">
            <el-form-item prop="username">
              <el-input v-model="regForm.username" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input type="password" v-model="regForm.password" placeholder="密码"></el-input>
            </el-form-item>
            <el-form-item prop="email">
              <el-input v-model="regForm.email" placeholder="邮箱"></el-input>
            </el-form-item>
            <el-form-item prop="birthday">
              <el-date-picker v-model="regForm.birthday" type="date" placeholder="年/月/日" style="width: 100%"></el-date-picker>
            </el-form-item>
          </el-form>
        </div>
        <div class="divider"></div>
        <div class="avatar-upload">
          <p>请上传头像</p>
          <el-upload
            class="avatar-uploader"
            action="#"
            :show-file-list="false"
            accept="image/jpeg,image/png,image/gif,image/webp,image/bmp,image/svg+xml,image/tiff,image/x-icon,image/avif,image/heic,image/heif,.jpg,.jpeg,.png,.gif,.webp,.bmp,.svg,.tif,.tiff,.ico,.avif,.heic,.heif"
            :before-upload="beforeAvatarUpload"
            :http-request="handleAvatarUpload"
          >
            <img v-if="regForm.avatar" :src="regForm.avatar" class="avatar-preview" alt="头像预览" />
            <div v-else class="upload-box">
              <span class="upload-plus">+</span>
              <span class="upload-tip">点击上传</span>
            </div>
          </el-upload>
          <p v-if="regForm.avatar" class="avatar-tip">头像已上传，点击可重新选择</p>
        </div>
      </div>
      <div class="auth-footer">
        <a href="#" @click.prevent="submitReg">注册用户</a>
        <router-link to="/">返回登录</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { register } from '@/api/auth'
import { uploadAvatar } from '@/api/upload'

export default {
  data() {
    return {
      regForm: { username: '', password: '', email: '', birthday: '', avatar: '' },
      uploading: false,
      rules: {
        username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
        password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change'] }
        ],
        birthday: [{ required: true, message: '请选择出生年月', trigger: 'change' }]
      }
    };
  },
  methods: {
    formatBirthday(value) {
      if (!value) return ''
      const date = value instanceof Date ? value : new Date(value)
      const y = date.getFullYear()
      const m = String(date.getMonth() + 1).padStart(2, '0')
      const d = String(date.getDate()).padStart(2, '0')
      return `${y}-${m}-${d}`
    },
    beforeAvatarUpload(file) {
      const allowedTypes = [
        'image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp',
        'image/bmp', 'image/svg+xml', 'image/tiff', 'image/x-icon',
        'image/vnd.microsoft.icon', 'image/avif', 'image/heic', 'image/heif'
      ]
      const allowedExtensions = [
        '.jpg', '.jpeg', '.png', '.gif', '.webp', '.bmp', '.svg',
        '.tif', '.tiff', '.ico', '.avif', '.heic', '.heif'
      ]
      const fileName = file.name ? file.name.toLowerCase() : ''
      const extension = fileName.includes('.') ? fileName.slice(fileName.lastIndexOf('.')) : ''
      const isImage = allowedTypes.includes(file.type) || allowedExtensions.includes(extension)
      const isLt150M = file.size / 1024 / 1024 <= 150

      if (!isImage) {
        this.$message.error('仅支持 JPG、PNG、GIF、WEBP、BMP、SVG、TIFF、ICO、AVIF、HEIC 等常见图片格式')
        return false
      }
      if (!isLt150M) {
        this.$message.error('头像大小不能超过 150MB')
        return false
      }
      return true
    },
    async handleAvatarUpload({ file }) {
      this.uploading = true
      try {
        const res = await uploadAvatar(file)
        this.regForm.avatar = res.data
        this.$message.success('头像上传成功')
      } catch (e) {
        this.$message.error(e.message || '头像上传失败')
      } finally {
        this.uploading = false
      }
    },
    submitReg() {
      if (!this.regForm.avatar) {
        this.$message.warning('请先上传头像')
        return
      }
      if (this.uploading) {
        this.$message.warning('头像正在上传，请稍候')
        return
      }
      this.$refs.regForm.validate(async (valid) => {
        if (!valid) return
        try {
          await register({
            username: this.regForm.username,
            password: this.regForm.password,
            email: this.regForm.email,
            birthday: this.formatBirthday(this.regForm.birthday),
            avatar: this.regForm.avatar
          })
          this.$message.success('注册成功！')
          this.$router.push('/login')
        } catch (e) {
          this.$message.error(e.message)
        }
      })
    }
  }
};
</script>

<style scoped>
.page-container { height: 100vh; display: flex; justify-content: center; align-items: center; background: url('https://picsum.photos/1920/1080') no-repeat center center; background-size: cover; }
.auth-box { background: #f5f5f5; width: 60%; max-width: 800px; border-radius: 4px; box-shadow: 0 4px 12px rgba(0,0,0,0.15); overflow: hidden; }
.auth-title { text-align: center; margin: 20px 0; }
.reg-content { display: flex; padding: 20px 40px; }
.reg-form, .avatar-upload { flex: 1; }
.avatar-upload { display: flex; flex-direction: column; align-items: center; }
.avatar-uploader { display: flex; justify-content: center; }
.upload-box {
  width: 150px;
  height: 150px;
  border: 1px dashed #ccc;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  background: #fff;
  transition: border-color 0.2s;
}
.upload-box:hover { border-color: #409EFF; }
.upload-plus { font-size: 32px; color: #ccc; line-height: 1; }
.upload-tip { margin-top: 8px; font-size: 13px; color: #999; }
.avatar-preview {
  width: 150px;
  height: 150px;
  object-fit: cover;
  border-radius: 8px;
  border: 1px solid #ddd;
  cursor: pointer;
}
.avatar-tip { margin-top: 10px; font-size: 12px; color: #666; }
.divider { width: 1px; background: #ccc; margin: 0 40px; }
.auth-footer { background: #555; display: flex; justify-content: space-around; padding: 15px; }
.auth-footer a { color: white; text-decoration: none; }

@media (max-width: 800px) {
  .auth-box { width: 90%; }
  .reg-content { flex-direction: column; padding: 20px; gap: 20px;}
  .divider { display: none; }
}
</style>