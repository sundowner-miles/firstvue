<template>
  <div class="home-container" :class="currentTheme">
    <el-container style="height: 100vh;">
      
      <el-aside width="200px" class="sidebar">
        <el-menu 
          default-active="1" 
          class="custom-menu"
          background-color="transparent"
          text-color="#333"
          active-text-color="#409EFF">
          
          <el-menu-item index="1">
            <i class="el-icon-menu"></i>
            <span slot="title">功能菜单</span>
          </el-menu-item>
          
          <el-menu-item index="2" @click="$router.push('/user')">
            <i class="el-icon-s-custom"></i>
            <span slot="title">用户管理</span>
          </el-menu-item>
          
          <el-menu-item index="3">
            <i class="el-icon-document"></i>
            <span slot="title">文章管理</span>
          </el-menu-item>
          
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span>主题切换</span>
            </template>
            <el-menu-item index="4-1" @click="changeTheme('blue-theme')">蓝色风格</el-menu-item>
            <el-menu-item index="4-2" @click="changeTheme('yellow-theme')">黄色风格</el-menu-item>
          </el-submenu>
          
          <el-submenu index="5">
             <template slot="title">导航三</template>
             <el-menu-item index="5-1">选项</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-main class="main-content">
        <div class="content-wrapper">
          
          <div class="profile-section">
            <h2 class="welcome-text">欢迎 张三</h2>
            <div class="avatar-card">
              <img src="https://picsum.photos/300/300" alt="张三的头像" class="avatar-img" />
              <p class="avatar-label">头像展示</p>
            </div>
          </div>
          
          <div class="calendar-section">
            <el-calendar v-model="calendarDate"></el-calendar>
          </div>
          
        </div>
      </el-main>
      
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      calendarDate: new Date(), // 绑定当前日期
      currentTheme: 'blue-theme' // 默认设置为蓝色主题
    };
  },
  methods: {
    // 切换主题方法，修改 currentTheme 的值
    changeTheme(theme) {
      this.currentTheme = theme;
    }
  }
};
</script>

<style scoped>
/* --- 全局与结构基础样式 --- */
.home-container {
  height: 100vh;
  transition: background-color 0.3s ease; /* 添加平滑过渡效果 */
}

/* 侧边栏样式 */
.sidebar {
  border-right: solid 1px #e6e6e6;
  background-color: rgba(255, 255, 255, 0.5); /* 半透明背景，透出底层颜色 */
}
.custom-menu {
  border-right: none;
}

/* 主体内容区容器布局 */
.main-content {
  padding: 20px;
}
.content-wrapper {
  display: flex;
  gap: 20px;
  height: 100%;
}

/* 左侧头像区域 */
.profile-section {
  flex: 1; /* 占据一定比例 */
  max-width: 350px;
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
}
.welcome-text {
  margin-bottom: 20px;
  color: #333;
}
.avatar-card {
  width: 100%;
  text-align: center;
}
.avatar-img {
  width: 100%;
  max-width: 250px;
  border-radius: 8px;
  border: 4px solid #fff;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}
.avatar-label {
  margin-top: 15px;
  color: #F56C6C; /* 对应实验图中红色的文字 */
  font-weight: bold;
}

/* 右侧日历区域 */
.calendar-section {
  flex: 2; /* 日历占据更多空间 */
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow: hidden; /* 防止日历内容溢出 */
}

/* --- 主题风格定义 --- */
/* 蓝色风格 */
.blue-theme {
  background-color: #E6F0FC; /* 浅蓝底色 */
}
.blue-theme .sidebar {
  background-color: #D3E0F1;
}

/* 黄色风格 */
.yellow-theme {
  background-color: #FDF6E3; /* 浅黄底色 */
}
.yellow-theme .sidebar {
  background-color: #F3E5AB;
}


/* --- 核心：响应式布局 --- */
@media (max-width: 800px) {
  /* 隐藏左侧边栏 */
  .sidebar {
    display: none;
  }
  
  /* 将主体内容由左右分列改为上下堆叠 */
  .content-wrapper {
    flex-direction: column;
  }
  
  /* 头像区域宽度撑满屏幕 */
  .profile-section {
    max-width: 100%;
  }
}
</style>