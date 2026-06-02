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
          <div class="upload-box"><i class="el-icon-plus"></i></div>
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
export default {
  data() {
    return {
      regForm: { username: '', password: '', email: '', birthday: '' },
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
    submitReg() {
      this.$refs.regForm.validate((valid) => {
        if (valid) { this.$message.success('注册成功！'); this.$router.push('/'); }
      });
    }
  }
};
</script>

<style scoped>
/* 复用登录页的基础 CSS */
.page-container { height: 100vh; display: flex; justify-content: center; align-items: center; background: url('https://picsum.photos/1920/1080') no-repeat center center; background-size: cover; }
.auth-box { background: #f5f5f5; width: 60%; max-width: 800px; border-radius: 4px; box-shadow: 0 4px 12px rgba(0,0,0,0.15); overflow: hidden; }
.auth-title { text-align: center; margin: 20px 0; }
.reg-content { display: flex; padding: 20px 40px; }
.reg-form, .avatar-upload { flex: 1; }
.avatar-upload { display: flex; flex-direction: column; align-items: center; }
.upload-box { width: 150px; height: 100px; border: 1px dashed #ccc; display: flex; justify-content: center; align-items: center; font-size: 24px; color: #ccc; cursor: pointer; }
.divider { width: 1px; background: #ccc; margin: 0 40px; }
.auth-footer { background: #555; display: flex; justify-content: space-around; padding: 15px; }
.auth-footer a { color: white; text-decoration: none; }

/* 响应式：小于等于 800px 时 */
@media (max-width: 800px) {
  .auth-box { width: 90%; }
  .reg-content { flex-direction: column; padding: 20px; gap: 20px;}
  .divider { display: none; }
}
</style>