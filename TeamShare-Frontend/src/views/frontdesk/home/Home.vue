<!-- 首页 -->
<template>
    <el-row :gutter="20">
        <el-col :xs="0" :sm="1" :md="1" :lg="4" :xl="4">
            <!-- 左侧间隙 -->
        </el-col>
        <el-col :xs="24" :sm="22" :md="22" :lg="16" :xl="16">
            <el-row :gutter="20">
                <!-- 左侧具体内容 -->
                <el-col :xs="24" :sm="24" :md="17" :lg="17" :xl="17">
                    <el-row :gutter="20">
                        <el-col class="hidden-sm-and-down" :md="24" :lg="24" :xl="24">
                            <!-- 队伍推荐卡片 -->
                            <TeamRecommand></TeamRecommand>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20">
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                            <!-- 最新文章展示列表 -->
                            <RecentPosts></RecentPosts>
                        </el-col>
                    </el-row>
                    <el-row >
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" style="text-align:center">
                            <el-button color="#909399" :dark="isDark" plain>前往社区查看更多</el-button>
                        </el-col>
                    </el-row>
                </el-col>
                <!-- 右侧边栏 -->
                <el-col class="hidden-sm-and-down" :md="7" :lg="7" :xl="7">
                    <!-- 搜索卡片 -->
                    <SearchCard></SearchCard>
                    <!-- 好友推荐卡片 -->
                    <UserRecommand></UserRecommand>
                    <!-- 文章推荐卡片 -->
                    <ArticleRecommand></ArticleRecommand>
                    <!-- 公告栏卡片 -->
                    <Announcement></Announcement>
                </el-col>
            </el-row>
        </el-col>
        <el-col :xs="0" :sm="1" :md="1" :lg="4" :xl="4">
            <!-- 右侧间隙 -->
        </el-col>
    </el-row>
</template>

<script setup>
//引入队伍推荐功能
import TeamRecommand from './components/TeamRecommand.vue';
//引入最新文章功能
import RecentPosts from './components/RecentPosts.vue';
//引入搜索卡片功能
import SearchCard from '../../../components/frontdesk/common/Search.vue';
// 引入用户推荐功能
import UserRecommand from '../../../components/frontdesk/common/UserRecommand.vue';
// 引入热点文章功能
import ArticleRecommand from '../../../components/frontdesk/common/ArticleRecommand.vue';
// 引入公告栏
import Announcement from '../../../components/frontdesk/common/Announcement.vue';

</script>

<style scoped>
.el-row {
    margin-bottom: 20px;
}
.el-row:last-child {
    margin-bottom: 0;
}
.el-col {
    border-radius: 4px;
}
.grid-content {
    border-radius: 4px;
    min-height: 36px;
}
</style>