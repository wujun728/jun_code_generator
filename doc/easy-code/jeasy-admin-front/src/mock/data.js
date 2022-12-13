import Mock from 'mockjs'
import { doCustomTimes } from '@/libs/util'
import orgData from './data/org-data'
import { treeData } from './data/tree-select'

const Random = Mock.Random

export const getTableData = req => {
  let tableData = []
  doCustomTimes(5, () => {
    tableData.push(Mock.mock({
      name: '@name',
      email: '@email',
      createTime: '@date'
    }))
  })

  return {
    code: 200,
    data: {
      message: 'success',
      list: tableData
    }
  }
}

export const getDragList = req => {
  let dragList = []
  doCustomTimes(5, () => {
    dragList.push(Mock.mock({
      name: Random.csentence(10, 13),
      id: Random.increment(10)
    }))
  })
  return {
    code: 200,
    data: {
      message: 'success',
      list: dragList
    }
  }
}

export const uploadImage = req => {
  return Promise.resolve()
}

export const getOrgData = req => {
  return {
    code: 200,
    data: {
      message: 'success',
      list: orgData
    }
  }
}

export const getTreeSelectData = req => {
  return {
    code: 200,
    data: {
      message: 'success',
      list: treeData
    }
  }
}
