/**
 * Created by DELL on 2018/9/28.
 */
import ajax from '@/common/js/ajax'

export const sectionArchivesExtras = {
  /**
   * 单条查询
   * @param {*} query [查询信息]
   */
  getOne (sectionCode) {
    return ajax({
      url: '/section-archives-extras/' + sectionCode,
      method: 'get'
    })
  },
  /**
   * 新增接口
   * @param {*} data [主体数据]
   */
  save (data) {
    return ajax({
      url: '/section-archives-extras',
      method: 'post',
      data: data
    })
  }
}
