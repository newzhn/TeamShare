// 首页API请求
import axios from "@/plugins/axiosConfig"

export function createTeam(form) {
    return axios({
        url:'team/create',
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