<template>
  <div class="user-list-container">
    <h2 class="page-title">用户管理</h2>
    
    <div class="table-wrapper">
      
      <div class="toolbar">
        <el-button type="primary" plain>新增</el-button>
        <div class="search-box">
          <el-input 
            v-model="searchQuery" 
            placeholder="请输入关键字" 
            style="width: 200px; margin-right: 10px;">
          </el-input>
          <el-button type="primary">搜索</el-button>
        </div>
      </div>

      <el-table :data="tableData" stripe style="width: 100%">
        <el-table-column type="index" label="序号" width="60"></el-table-column>
        <el-table-column prop="date" label="日期" width="180"></el-table-column>
        <el-table-column prop="name" label="姓名" width="100"></el-table-column>
        <el-table-column prop="province" label="省份" width="100"></el-table-column>
        <el-table-column prop="city" label="市区" width="100"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="zip" label="邮编" width="100"></el-table-column>
        
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="scope">
            <el-button size="small" type="primary" link @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" link @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrapper">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 30, 40]"
          background
          layout="total, sizes, prev, pager, next, jumper"
          :total="100"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: 'UserList',
  data() {
    return {
      searchQuery: '',
      currentPage: 1,
      pageSize: 10,
      // 静态模拟的用户数据
      tableData: [
        { date: '2024-04-12 00:00:00', name: '王小虎', province: '上海', city: '普陀区', address: '上海市普陀区金沙江路 1518 弄', zip: '200333' },
        { date: '2024-04-12 00:00:00', name: '李小虎', province: '上海', city: '普陀区', address: '上海市普陀区金沙江路 1517 弄', zip: '200333' },
        { date: '2024-04-12 00:00:00', name: '张小虎', province: '上海', city: '普陀区', address: '上海市普陀区金沙江路 1519 弄', zip: '200333' },
        { date: '2024-04-12 00:00:00', name: '赵小虎', province: '上海', city: '普陀区', address: '上海市普陀区金沙江路 1516 弄', zip: '200333' },
        { date: '2024-04-12 00:00:00', name: '孙小虎', province: '上海', city: '普陀区', address: '上海市普陀区金沙江路 1515 弄', zip: '200333' }
      ]
    };
  },
  methods: {
    handleEdit(row) {
      this.$message.info(`正在编辑: ${row.name}`);
    },
    handleDelete(row) {
      this.$message.warning(`已删除: ${row.name}`);
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    }
  }
};
</script>

<style scoped>
.user-list-container {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  min-height: 100%;
}

.page-title {
  text-align: center;
  margin-top: 0;
  margin-bottom: 20px;
  color: #333;
}


.toolbar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.search-box {
  display: flex;
}

.pagination-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: center; /* 分页居中 */
}
</style>