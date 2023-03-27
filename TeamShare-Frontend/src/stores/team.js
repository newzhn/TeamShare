import { ref, reactive } from 'vue'
import { defineStore } from 'pinia'

// 管理队伍相关全局状态
export const useTeamStore = defineStore('team', () => {
  // 标签页值，用户控制队伍展示列表状态
  const teamTab = ref(1)
  // 加入队伍监视属性，每次加入队伍成功，会改变值刷新加入队伍列表
  const joinFlag = ref(true)

  // 改变标签页值
  function changeTeamTab(value) {
    teamTab.value = value
  }
  // 改变加入队伍监视属性
  function changeJoinFlag() {
    joinFlag.value = !joinFlag.value
  }

  return { teamTab,joinFlag,changeTeamTab,changeJoinFlag}
})
