<template>
  <div class="page-container">
    <div class="auth-box">
      <h2 class="auth-title">登录</h2>
      <div class="login-content">
        <div class="social-login">
          <el-button type="primary" icon="el-icon-user">QQ登录</el-button>
          <el-button type="success" icon="el-icon-chat-dot-round">微信登录</el-button>
          <el-button type="info" icon="el-icon-wallet">支付宝登录</el-button>
        </div>
        
        <div class="divider"></div>

        <div class="account-login">
          <el-form :model="loginForm" :rules="rules" ref="loginForm">
            <el-form-item prop="username">
              <el-input v-model="loginForm.username" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input type="password" v-model="loginForm.password" placeholder="密码"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="success" style="width: 100%" @click="submitLogin">登录</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div class="auth-footer">
        <router-link to="/register">注册用户</router-link>
        <router-link to="/updatePwd">修改密码</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginForm: { username: '', password: '' },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    };
  },
  methods: {
    submitLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          // 模拟登录成功跳转
          this.$router.push('/home/front');
        }
      });
    }
  }
};
</script>

<style scoped>
/* 基础全屏背景设置 */
.page-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: url('https://picsum.photos/1920/1080') no-repeat center center; /* 请替换为你的海滩背景图 */
  background-size: cover;
}
.auth-box {
  background: #f5f5f5;
  width: 60%;
  max-width: 800px;
  border-radius: 4px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
  overflow: hidden;
}
.auth-title { text-align: center; margin: 20px 0; }
.login-content { display: flex; padding: 20px 40px; }
.social-login, .account-login { flex: 1; display: flex; flex-direction: column; gap: 15px; }
.social-login .el-button { margin-left: 0; margin-bottom: 10px; }
.divider { width: 1px; background: #ccc; margin: 0 40px; }
.auth-footer { background: #555; display: flex; justify-content: space-around; padding: 15px; }
.auth-footer a { color: white; text-decoration: none; }

/* 核心响应式代码：小于等于 800px 时 */
@media (max-width: 800px) {
  .auth-box { width: 90%; }
  .login-content { flex-direction: column; padding: 20px; }
  .divider { display: none; } /* 隐藏竖向分割线 */
  .social-login { margin-bottom: 20px; }
}
</style>