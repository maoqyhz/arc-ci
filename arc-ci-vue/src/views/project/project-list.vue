<template>
  <div class="app-container">
    <div v-if="showTable" id="data-grid">
      <el-row>
        <el-button type="primary" icon="el-icon-edit" @click="createNew">
          新建项目
        </el-button>
      </el-row>
      <el-table
        v-loading="listLoading"
        :data="list"
        element-loading-text="Loading"
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
            {{ scope.row.projectName }}
          </template>
        </el-table-column>
        <el-table-column label="语言" align="center" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.lang }}</span>
          </template>
        </el-table-column>
        <el-table-column label="编译环境" align="center" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.env }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" width="200">
          <template slot-scope="scope">
            {{ scope.row.remark }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="created_at" label="创建时间" width="200">
          <template slot-scope="scope">
            <i class="el-icon-time" />
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="420">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleView(scope.$index, scope.row)"
            >查看
            </el-button>
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)"
            >编辑
            </el-button>
            <el-button
              size="mini"
              @click="handleDeploy(scope.$index, scope.row)"
            >部署
            </el-button>
            <el-button
              size="mini"
              @click="showDeployList(scope.$index, scope.row)"
            >部署列表
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
    <div v-if="showForm" id="project-form">
      <el-form ref="project-form" :disabled="!isEdit" :model="form" label-width="100px" style="width: 800px;">
        <el-form-item label="项目名" prop="projectName" style="width: 400px">
          <el-input v-model="form.projectName" />
        </el-form-item>
        <el-form-item label="Git地址" prop="gitRepo">
          <el-input v-model="form.gitRepo" />
        </el-form-item>
        <el-form-item label="语言" prop="lang" style="width: 360px">
          <el-input v-model="form.lang" />
        </el-form-item>
        <el-form-item label="编译环境" prop="env" style="width: 360px">
          <el-input v-model="form.env" />
        </el-form-item>
        <el-form-item label="服务器类型" prop="serverType" style="width: 300px">
          <el-select v-model="form.serverType" placeholder="请选择">
            <el-option
              v-for="item in serverTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="服务器IP" prop="serverIp" style="width: 360px">
          <el-input v-model="form.serverIp" />
        </el-form-item>
        <el-form-item label="端口" prop="serverPort" style="width: 200px">
          <el-input v-model="form.serverPort" />
        </el-form-item>
        <el-form-item label="用户名" prop="serverUsername" style="width: 200px">
          <el-input v-model="form.serverUsername" />
        </el-form-item>
        <el-form-item label="服务器备注" prop="serverRemark" style="width: 360px">
          <el-input v-model="form.serverRemark" />
        </el-form-item>
        <el-form-item label="部署路径" prop="deployPath">
          <el-input v-model="form.deployPath" />
        </el-form-item>
        <el-form-item label="项目备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
      </el-form>
      <div style="width: 800px;margin-left: 100px">
        <el-button v-if="isEdit" type="primary" style="margin-right: 20px" @click="onSubmit(form)">完成</el-button>
        <el-button @click="onCancel">取消</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import * as project from '@/api/project'

export default {
  name: 'Proj',
  data() {
    return {
      list: null,
      listLoading: true,
      showTable: true,
      showForm: false,
      showDeployForm: false,
      showId: false,
      isEdit: true,
      form: {
        id: null,
        projectName: '',
        gitRepo: '',
        lang: '',
        env: '',
        deployPath: '',
        remark: '',
        serverIp: '',
        serverType: '',
        serverPort: '',
        serverUsername: '',
        serverRemark: ''
      },
      deployForm: {
        id: null,
        deployProject: '',
        projectId: '',
        gitRepo: '',
        deployType: '',
        typeRef: '',
        evnVariable: '',
        deployRemark: '',
        createTime: ''
      },
      envVariable: {
        ip: '',
        port: '',
        user: ''
      },
      typeRefOptions: [],
      pipelineInfo: {
        id: '',
        sha: '',
        web_url: ''
      },
      serverTypeOptions: [{
        value: '阿里云'
      }, {
        value: '本地'
      }, {
        value: '集群'
      }]
    }
  },
  mounted() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      project.getProjectList().then(response => {
        this.list = response.data
        this.listLoading = false
      })
    },
    handleView(index, row) {
      console.log(index, row)
      this.showTable = false
      this.showForm = true
      project.detail(row.id).then(response => {
        this.form = response.data
      })
      this.isEdit = false
    },
    handleEdit(index, row) {
      console.log(index, row)
      this.showTable = false
      this.showForm = true
      project.detail(row.id).then(response => {
        this.form = response.data
      })
      this.isEdit = true
    },
    handleDeploy(index, row) {
      this.deployForm.deployProject = row.projectName
      this.deployForm.gitRepo = row.gitRepo
      this.envVariable.ip = row.serverIp
      this.envVariable.port = row.serverPort
      this.envVariable.user = row.serverUsername
      this.deployForm.projectId = row.id
      this.showTable = false
      this.$router.push({
        path: '/manage/deploy-form',
        query: {
          projectId: row.id,
          isEdit: true
        }
      })
    },
    showDeployList(index, row) {
      this.$router.push({
        path: '/manage/deploy',
        query: {
          projectName: row.projectName
        }
      })
    },
    handleDelete(index, row) {
      console.log(index, row)
      project.deleteProject(row.id).then(response => {
        this.$message({
          message: '删除成功',
          type: 'success'
        })
        this.fetchData()
      })
    },
    onSubmit: function(form) {
      console.log('submit!')
      project.createOrUpdate(form).then(response => {
        this.$message({
          message: '操作成功',
          type: 'success'
        })
        this.$refs['project-form'].resetFields()
        this.showForm = false
        this.showTable = true
        this.fetchData()
      })
    },
    onCancel() {
      this.$refs['project-form'].resetFields()
      this.showForm = false
      this.showTable = true
      this.fetchData()
    },
    createNew() {
      this.showTable = false
      this.showForm = !this.showForm
      this.isEdit = true
    }
  }
}
</script>

<style scoped>
</style>
