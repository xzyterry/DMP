import request from '@/utils/request'

// 查询合表列表
export function fetchList() {
  return request({
    url: '/hive/queryTbList',
    method: 'post'
  })
}

// 查询单表
export function fetchTbDetail(param) {
  return request({
    url: '/hive/queryTb/' + param.tb_id,
    method: 'post'
  })
}

// 执行单条语句
export function testSql(data) {
  return request({
    url: '/hive/testSql/',
    method: 'post',
    data
  })
}

// 保存语句
export function saveSql(data) {
  return request({
    url: '/hive/saveSql/',
    method: 'post',
    data
  })
}
