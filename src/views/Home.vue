<template>
  <div class="home-container" :class="currentTheme">
    <el-container style="height: 100vh;">
      
      <el-aside width="200px" class="sidebar">
        <el-menu 
          default-active="0" 
          :default-openeds="['1', '2']"
          class="custom-menu"
          background-color="transparent"
          text-color="#333"
          active-text-color="#409EFF">
          
          <el-menu-item index="0" @click="$router.push('/home')">
            <template #title>
              <i class="el-icon-document"></i>
              <span>首页</span>
            </template>
          </el-menu-item>

          <el-sub-menu index="1">
            <template #title>
              <i class="el-icon-menu"></i>
              <span>功能菜单</span>
            </template>
            <el-menu-item index="1-1" @click="$router.push('/home/user')">用户管理</el-menu-item>
            <el-menu-item index="1-2">文章管理</el-menu-item>
          </el-sub-menu>
          
          <el-sub-menu index="2">
            <template #title>
              <i class="el-icon-setting"></i>
              <span>主题切换</span>
            </template>
            <el-menu-item index="2-1" @click="changeTheme('blue-theme')">蓝色风格</el-menu-item>
            <el-menu-item index="2-2" @click="changeTheme('yellow-theme')">黄色风格</el-menu-item>
          </el-sub-menu>
          
          <el-menu-item index="3">
             <template #title>
               <i class="el-icon-location"></i>
               <span>导航三</span>
             </template>
          </el-menu-item>

          <el-menu-item index="4">
             <template #title>
               <i class="el-icon-setting"></i>
               <span>导航四</span>
             </template>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-container class="right-container">
        
        <el-header class="top-header">
          <div class="header-right">
            <el-dropdown @command="handleCommand">
              <span class="el-dropdown-link user-dropdown">
                {{ currentUser }} <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="张三">切换为 张三</el-dropdown-item>
                  <el-dropdown-item command="李四">切换为 李四</el-dropdown-item>
                  <el-dropdown-item command="王五">切换为 王五</el-dropdown-item>
                  <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>

        <el-main class="main-content">
          <router-view v-if="$route.path === '/home/user'"></router-view>
          
          <div v-else class="content-wrapper">
            <div class="profile-section">
              <h2 class="welcome-text">欢迎 {{ currentUserName }}</h2>
              <div class="avatar-card">
                <img src="https://picsum.photos/300/300" alt="用户头像" class="avatar-img" />
              </div>
            </div>
            
            <div class="calendar-section">
              <el-calendar v-model="calendarDate"></el-calendar>
            </div>
          </div>
        </el-main>
        
      </el-container> </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      calendarDate: new Date(),
      currentTheme: 'yellow-theme', // 默认设为黄色风格
      currentUser: '登录用户',
      currentUserName: '张三' // 控制主页显示的欢迎名字
    };
  },
  methods: {
    changeTheme(theme) {
      this.currentTheme = theme;
    },
    // 处理下拉菜单点击事件
    handleCommand(command) {
      if (command === 'logout') {
        this.$router.push('/login');
      } else {
        this.currentUser = command;
        this.currentUserName = command;
        this.$message.success(`已切换登录用户为：${command}`);
      }
    }
  }
};
</script>

<style scoped>
.home-container {
  height: 100vh;
  transition: background-color 0.3s ease; 
}

/* 侧边栏样式 */
.sidebar {
  border-right: solid 1px rgba(0,0,0,0.05);
  background-color: rgba(255, 255, 255, 0.4); 
}
.custom-menu { border-right: none; }

/* --- 新增：顶部导航栏样式 --- */
.top-header {
  display: flex;
  justify-content: flex-end; /* 内容靠右对齐 */
  align-items: center;
  padding-right: 30px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
  transition: background-color 0.3s ease;
}
.user-dropdown {
  cursor: pointer;
  color: #333;
  font-weight: bold;
  font-size: 15px;
}

/* 主体内容区容器布局 */
.right-container {
  display: flex;
  flex-direction: column;
}
.main-content { padding: 20px; }
.content-wrapper { display: flex; gap: 20px; height: 100%; }

.profile-section {
  flex: 1; max-width: 350px; background: #fff; padding: 20px;
  border-radius: 8px; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  display: flex; flex-direction: column; align-items: center;
}
.welcome-text { margin-bottom: 20px; color: #333; }
.avatar-card { width: 100%; text-align: center; }
.avatar-img { width: 100%; max-width: 250px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }

.calendar-section {
  flex: 2; background: #fff; border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); overflow: hidden; 
}

/* --- 主题风格定义 --- */

/* 蓝色风格 */
.blue-theme { background-color: #E6F0FC; }
.blue-theme .sidebar { background-color: #D3E0F1; }
.blue-theme .top-header { background-color: #A0CFFF; } /* 蓝色顶部栏 */

/* 黄色风格 */
.yellow-theme { background-color: #FDF6E3; }
.yellow-theme .sidebar { background-color: #e4c597; } /* 侧边栏黄色 */
.yellow-theme .top-header { background-color: #c99a5b; } /* 顶部栏深黄色 */

/* 响应式 */
@media (max-width: 1000px) {
  /* 1. 依旧隐藏侧边栏 */
  .sidebar {
    display: none !important;
  }
  
  /* 2. 将主体内容由垂直堆叠恢复为水平排列 */
  .content-wrapper {
    flex-direction: row; /* 恢复为水平方向 */
    gap: 15px;           /* 适当减小间距，以适应较小的屏幕 */
  }
  
  /* 3. 确保头像区域和日历区域各占空间 */
  .profile-section {
    max-width: 300px;    /* 设置一个固定宽度或比例 */
    margin-bottom: 0;
  }
  
  .calendar-section {
    flex: 1;             /* 让日历填满剩余空间 */
    width: auto;
  }
}
</style>