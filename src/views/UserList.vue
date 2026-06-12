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

      <el-table v-loading="loading" :data="pagedData" stripe style="width: 100%">
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
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>

    </div>
  </div>
</template>

<script>
import { getUserList } from '@/api/user'

export default {
  name: 'UserList',
  data() {
    return {
      loading: false,
      searchQuery: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: []
    };
  },
  computed: {
    filteredData() {
      if (!this.searchQuery) return this.tableData
      const keyword = this.searchQuery.trim().toLowerCase()
      return this.tableData.filter((row) =>
        Object.values(row).some((val) =>
          String(val).toLowerCase().includes(keyword)
        )
      )
    },
    pagedData() {
      const start = (this.currentPage - 1) * this.pageSize
      return this.filteredData.slice(start, start + this.pageSize)
    }
  },
  watch: {
    filteredData(list) {
      this.total = list.length
      if ((this.currentPage - 1) * this.pageSize >= list.length) {
        this.currentPage = 1
      }
    }
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    async fetchList() {
      this.loading = true
      try {
        const res = await getUserList()
        this.tableData = res.data || []
        this.total = this.tableData.length
      } catch (e) {
        this.$message.error(e.message || '加载列表失败')
      } finally {
        this.loading = false
      }
    },
    handleEdit(row) {
      this.$message.info(`正在编辑: ${row.name}`);
    },
    handleDelete(row) {
      this.$message.warning(`已删除: ${row.name}`);
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
    },
    handleCurrentChange(val) {
      this.currentPage = val
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