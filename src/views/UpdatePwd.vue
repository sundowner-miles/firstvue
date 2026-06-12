<template>
  <div class="page-container">
    <div class="auth-box">
      <h2 class="auth-title">修改密码</h2>
      <div class="pwd-content">
        <div class="pwd-left">
          <el-form :model="pwdForm" :rules="rules" ref="pwdForm" label-width="0">
            <el-form-item prop="username">
              <el-input v-model="pwdForm.username" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="oldPwd">
              <el-input type="password" v-model="pwdForm.oldPwd" placeholder="旧密码"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="divider"></div>
        <div class="pwd-right">
          <el-form :model="pwdForm" :rules="rules" ref="pwdFormRight" label-width="0">
            <el-form-item prop="newPwd">
              <el-input type="password" v-model="pwdForm.newPwd" placeholder="新密码"></el-input>
            </el-form-item>
            <el-form-item prop="confirmPwd">
              <el-input type="password" v-model="pwdForm.confirmPwd" placeholder="确认新密码"></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div class="auth-footer">
        <a href="#" @click.prevent="submitPwd">修改密码</a>
        <router-link to="/">返回登录</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { updatePassword } from '@/api/auth'

export default {
  data() {
    // 自定义验证规则：校验两次密码是否一致
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.pwdForm.newPwd) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      pwdForm: { username: '', oldPwd: '', newPwd: '', confirmPwd: '' },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        oldPwd: [{ required: true, message: '请输入旧密码', trigger: 'blur' }],
        newPwd: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
        confirmPwd: [{ required: true, validator: validatePass2, trigger: 'blur' }]
      }
    };
  },
  methods: {
    async submitPwd() {
      try {
        await Promise.all([
          this.$refs.pwdForm.validate(),
          this.$refs.pwdFormRight.validate()
        ])
        await updatePassword({
          username: this.pwdForm.username,
          oldPwd: this.pwdForm.oldPwd,
          newPwd: this.pwdForm.newPwd
        })
        this.$message.success('修改成功！')
        this.$router.push('/login')
      } catch (e) {
        if (e && e.message) {
          this.$message.error(e.message)
        }
      }
    }
  }
};
</script>

<style scoped>
/* 结构和之前一样，直接复用刚才的 CSS 逻辑 */
.page-container { height: 100vh; display: flex; justify-content: center; align-items: center; background: url('https://picsum.photos/1920/1080') no-repeat center center; background-size: cover; }
.auth-box { background: #f5f5f5; width: 60%; max-width: 800px; border-radius: 4px; box-shadow: 0 4px 12px rgba(0,0,0,0.15); overflow: hidden; }
.auth-title { text-align: center; margin: 20px 0; }
.pwd-content { display: flex; padding: 20px 40px; }
.pwd-left, .pwd-right { flex: 1; }
.divider { width: 1px; background: #ccc; margin: 0 40px; }
.auth-footer { background: #555; display: flex; justify-content: space-around; padding: 15px; }
.auth-footer a { color: white; text-decoration: none; }

/* 响应式：小于等于 800px 时 */
@media (max-width: 800px) {
  .auth-box { width: 90%; }
  .pwd-content { flex-direction: column; padding: 20px; }
  .divider { display: none; }
}
</style>