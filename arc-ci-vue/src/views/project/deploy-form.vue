<template>
  <div class="app-container">
    <el-form ref="deploy-form" :disabled="!isEdit" :model="deployForm" label-width="100px" style="width: 800px;">
      <el-form-item label="项目名" prop="deployProject" style="width: 400px">
        <el-input v-model="deployForm.deployProject" readonly />
      </el-form-item>
      <el-form-item label="选择类型" prop="deployType">
        <el-select v-model="deployForm.deployType" placeholder="发布类型" @change="handleTypeChange">
          <el-option label="分支" value="branch" />
          <el-option label="标签" value="tag" />
          <el-option label="提交" value="commit" />
        </el-select>
      </el-form-item>
      <el-form-item label="选择版本" prop="typeRef">
        <el-select v-model="deployForm.typeRef" placeholder="选择版本" style="width: 400px">
          <el-option
            v-for="item in typeRefOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
            <span style="font-weight: bold">{{ item.value }}</span><br>
            <span style="color: #8492a6; font-size: 13px">{{ item.label }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="环境变量" prop="evnVariable">
        <el-input
          v-model="deployForm.evnVariable"
          type="textarea"
          :rows="5"
          placeholder="gitlab-ci环境变量，一行一组 key:value"
        />
      </el-form-item>
      <el-form-item label="备注" prop="deployRemark">
        <el-input v-model="deployForm.deployRemark" type="textarea" />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-input v-model="deployForm.createTime" disabled />
      </el-form-item>
    </el-form>
    <div style="width: 800px;margin-left: 100px">
      <el-button type="primary" style="margin-right: 20px" @click="handleNewDeploy(deployForm)">完成</el-button>
      <el-button @click="onDeployCancel">取消</el-button>
    </div>
  </div></template>

<script>
import * as deploy from '@/api/deploy'
import * as project from '@/api/project'

/**
 * 部署表单Component
*/
export default {
  name: 'DeployForm',
  data() {
    return {
      isEdit: true,
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
      }
    }
  },
  mounted() {
    const projectId = this.$route.query.projectId
    const deployId = this.$route.query.deployId
    this.isEdit = this.$route.query.isEdit
    if (projectId !== null && projectId !== undefined && projectId !== '') { this.fetchData(projectId) }
    if (deployId !== null && deployId !== undefined && deployId !== '') { this.loadDeployData(deployId) }
  },
  methods: {
    loadDeployData(deployId) {
      this.handleView(deployId)
    },
    fetchData(projectId) {
      project.detail(projectId).then(response => {
        const project = response.data
        this.deployForm.projectId = project.id
        this.deployForm.deployProject = project.projectName
        this.deployForm.gitRepo = project.gitRepo
        this.envVariable.ip = project.serverIp
        this.envVariable.port = project.serverPort
        this.envVariable.user = project.serverUsername
      })
    },
    handleTypeChange() {
      this.deployForm.typeRef = ''
      if (this.deployForm.deployType === 'branch') {
        project.branch(this.deployForm.gitRepo).then(response => {
          this.typeRefOptions = response.data
        })
      } else if (this.deployForm.deployType === 'tag') {
        project.tag(this.deployForm.gitRepo).then(response => {
          this.typeRefOptions = response.data
        })
      } else {
        this.typeRefOptions = []
      }
    },
    handleView(deployId) {
      deploy.detail(deployId).then(response => {
        this.deployForm = response.data
      })
    },
    handleNewDeploy() {
      this.$confirm('此操作将创建gitlab中创建一次发布，是否继续？<br/><strong>请确保发布信息的正确性！</strong>', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        dangerouslyUseHTMLString: true,
        type: 'info'
      }).then(() => {
        deploy.createOrUpdate(this.deployForm).then(() => {
          // this.$refs['deploy-form'].resetFields()
        }).catch((e) => {
          console.log(e)
          this.$message({
            type: 'error',
            message: '发布记录保存失败!'
          })
        })
        deploy.createPipeline(this.deployForm.gitRepo, this.deployForm.typeRef, this.envVariable).then(response => {
          this.pipelineInfo.id = response.data.id
          this.pipelineInfo.sha = response.data.sha
          this.pipelineInfo.web_url = response.data.web_url
          this.$msgbox({
            title: '创建成功',
            message: `<div>Gitlab流水线创建成功！<br/><strong>流水线id：</strong>${this.pipelineInfo.id}<br/><strong>commit sha：</strong>${this.pipelineInfo.sha}<br/><a target="_blank" href="${this.pipelineInfo.web_url}" style="text-decoration:underline">点击此处查看流水线运行状态</a></div>`,
            confirmButtonText: '确定',
            dangerouslyUseHTMLString: true
          })
        }).catch(() => {
          this.$message({
            type: 'error',
            message: '创建Gitlab流水线失败!'
          })
        })
        this.$router.push({ path: '/manage/project' })
      }).catch(() => {
        this.$message({
          type: 'error',
          message: '未知异常！'
        })
      })
    },
    onDeployCancel() {
      this.$router.go(-1)
    }
  }
}
</script>

<style scoped>
  .el-select-dropdown__item{
    height: auto;
  }
</style>
