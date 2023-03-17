<!-- 组队页 -->
<template>
    <el-row :gutter="20">
        <el-col :xs="0" :sm="1" :md="1" :lg="4" :xl="4">
            <!-- 左侧间隙 -->
        </el-col>
        <el-col :xs="24" :sm="22" :md="22" :lg="16" :xl="16">
            <el-row :gutter="20">
                <!-- 左侧具体内容，占17列 -->
                <el-col :xs="24" :sm="24" :md="17" :lg="17" :xl="17">
                    <!-- 队伍标签页 -->
                    <el-tabs :tab-position="state.tabPosition" class="demo-tabs">
                        <el-tab-pane label="公开">
                            <!-- 队伍列表 -->
                            <TeamListPane ></TeamListPane>
                        </el-tab-pane>
                        <el-tab-pane label="加密">
                            <!-- 队伍列表 -->
                            <TeamListPane ></TeamListPane>
                        </el-tab-pane>
                    </el-tabs>
                </el-col>
                <!-- 右侧边栏，占7列 -->
                <el-col class="hidden-sm-and-down" :md="7" :lg="7" :xl="7">
                    <!-- 已加入队伍列表 -->
                    <JoinedTeamList></JoinedTeamList>
                </el-col>
            </el-row>
        </el-col>
        <el-col :xs="0" :sm="1" :md="1" :lg="4" :xl="4">
            <!-- 右侧间隙 -->
        </el-col>
    </el-row>
</template>

<script setup>
import { reactive,onMounted,onUnmounted } from 'vue';
import TeamListPane from './components/TeamListPane.vue'
import JoinedTeamList from './components/JoinedTeamList.vue';


const state = reactive({
    // 标签页展示位置控制，移动端顶端展示
    tabPosition:'left',
})

// 监听页面大小变化改变展示样式
const handleResize = () => {
    if (window.innerWidth <= 768) {
        state.tabPosition = 'top'
    } else {
        state.tabPosition = 'left'
    }
}
window.addEventListener('resize', handleResize);

// 在组件销毁时移除事件监听器
onUnmounted(() => {
    window.removeEventListener('resize', handleResize);
});
</script>

<style scoped>

</style>