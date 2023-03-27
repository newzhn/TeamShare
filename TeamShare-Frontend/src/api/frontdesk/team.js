// 首页API请求
import axios from "@/plugins/axiosConfig"

// 创建队伍
export function createTeam(form) {
    return axios({
        url:'team/',
        method:'Post',
        data:{
            teamName:form.teamName,
            teamDescribe:form.teamDescribe,
            teamSize:form.teamSize,
            deadline:form.deadline,
            teamStatus:form.teamStatus,
            teamPassword:form.teamPassword
        }
    })
}

// 发送请求校验队伍名
export function checkTeamName(teamName) {
    return axios({
        url:'team/checkTeamName/' + teamName,
        method:'Get',
    })
}

// 发送请求获取最新队伍列表，参数是队伍状态
export function getTeamListForStatus(teamStatus) {
    return axios({
        url:'team/getList/' + teamStatus,
        method:'Get',
    })
}

// 获取已加入队伍列表
export function getJoinedTeamList() {
    return axios({
        url:'team/getJoinedList',
        method:'Get'
    })
}

// 加入队伍
export function joinInTeam(teamId,password) {
    return axios({
        url:'team/joinIn/' + teamId,
        method:'Post',
        params:{
            password
        }
    })
}

// 获取某个队伍详细信息
export function getTeamInfo(teamId) {
    return axios({
        url:'team/' + teamId,
        method:'Get'
    })
}

// 删除队伍
export function deleteTeam(teamId) {
    return axios({
        url:'team/' + teamId,
        method:'Get'
    })
}

// 搜索队伍
export function searchTeamList(searchText,teamStatus) {
    return axios({
        url:'team/search',
        method:'Get',
        params:{
            searchText,
            teamStatus
        }
    })
}