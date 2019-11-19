<template>
  <div class="app-container">
    <div v-show="showTable" id="data-grid">
      <el-row>
        <el-autocomplete
          v-model="state"
          :fetch-suggestions="querySearchAsync"
          placeholder="项目名称"
          @select="handleSelect"
        />
        <el-button type="primary" @click="clear">清空</el-button>
      </el-row>

      <el-table
        v-loading="listLoading"
        :data="list"
        element-loading-text="Loading"
        :default-sort="{prop: 'createTime', order: 'descending'}"
        highlight-current-row
      >
        <el-table-column align="center" label="ID" width="55">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column v-if="showId" label="iprod" align="center" width="200">
          <template slot-scope="scope">
            {{ scope.row.id }}
          </template>
        </el-table-column>
        <el-table-column label="项目" align="center" width="200">
          <template slot-scope="scope">
            {{ scope.row.deployProject }}
          </template>
        </el-table-column>
        <el-table-column label="发布类别" align="center" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.deployType }}</span>
          </template>
        </el-table-column>
        <el-table-column label="发布版本" align="center" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.typeRef }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" width="600">
          <template slot-scope="scope">
            {{ scope.row.deployRemark }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="createTime" label="创建时间" width="200">
          <template slot-scope="scope">
            <i class="el-icon-time" />
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="360">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleView(scope.$index, scope.row)"
            >查看
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import * as deploy from '@/api/deploy'

export default {
  name: 'Deploy',
  data() {
    return {
      list: null,
      listLoading: true,
      showTable: true,
      showDeployForm: false,
      showId: false,
      isEdit: true,
      deployForm: {
        id: null,
        deployProject: '',
        projectId: '',
        gitRepo: '',
        type: '',
        typeRef: '',
        evnVariable: '',
        deployRemark: '',
        createTime: ''
      },
      typeRefOptions: [],
      projectSearch: [],
      state: '',
      timeout: null
    }
  },
  mounted() {
    const projectName = this.$route.query.projectName
    if (projectName !== null && projectName !== undefined && projectName !== '') { this.fetchData(projectName) } else { this.fetchData(null) }
    this.loadProjectList()
  },
  methods: {
    fetchData(projectName) {
      this.listLoading = true
      deploy.getDeployList(projectName).then(response => {
        this.list = response.data
        this.listLoading = false
      })
    },
    handleView(index, row) {
      // this.showTable = false
      // this.showDeployForm = true
      this.$router.push({
        path: '/manage/deploy-form',
        query: {
          deployId: row.id,
          isEdit: false
        }
      })
    },
    handleDelete(index, row) {
      console.log(index, row)
      deploy.deleteDeploy(row.id).then(response => {
        this.$message({
          message: '删除成功',
          type: 'success'
        })
        this.fetchData()
      })
    },
    onCancel() {
      this.showDeployForm = false
      this.showTable = true
      this.fetchData()
    },
    querySearchAsync(queryString, cb) {
      const projectSearch = this.projectSearch
      const results = queryString ? projectSearch.filter(this.createStateFilter(queryString)) : projectSearch
      clearTimeout(this.timeout)
      this.timeout = setTimeout(() => {
        cb(results)
      }, 1000 * Math.random())
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    handleSelect(item) {
      this.fetchData(item.value)
    },
    clear() {
      this.state = ''
      this.fetchData()
    },
    loadProjectList() {
      deploy.projectList().then(response => {
        this.projectSearch = response.data
      })
    }
  }
}
</script>

<style scoped>

</style>
