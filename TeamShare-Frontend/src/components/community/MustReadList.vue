<!-- 必读榜卡片 -->
<template>
    <el-card class="resource" v-loading="state.loading" element-loading-text="玩命加载中..." shadow="hover">
        <div class="resource_title">
            <h3 class="title"><b>必读榜</b></h3>
        </div>
        <div v-for="(item, index) in state.mustReadList" :key="item.articleId">
            <el-row>
                <span :style="{color: index < 3 ? 'orange' : 'gray', marginRight: '0.7em'}">{{ index + 1 }}</span>
                <el-avatar :size="20" :src="item.author.avatar" style="margin-right: 0.5em;"/>
                <el-link :underline="false" class="title-link">面试题 | {{ item.title }}</el-link>
            </el-row>
            <p class="content">{{ item.content }}</p>
        </div>
    </el-card>
</template>

<script setup>
import { reactive } from 'vue';

const state = reactive({
    loading:false,
    mustReadList:[
        {
            articleId:1,
            title:"力扣刷题攻略",
            categary:"面试题",
            content:'在什么圈子就会看到什么样的内容，几个月没登录leetcode之后，再一看当初做的题目，都只记得一些方法了，再写感觉应该很难写出来了。我当时就感觉，做leetcode和刷高考数学题真的很像，我只需要去理解他的思想，实在理解不了的就背下来就完事了。刚入学的时候还是每天坚持刷leetcode，感觉就是一个很长的主线任务，每天做一点，每天变强一点，但是看着就业环境一点点变差，加上自己对计算机好像确实不感兴趣。 然后就直接放弃转码了。并不是劝退，现在在我看来，学了计算机的总会比不学计算机要好找工作，但是只是自己真的背负不了这么大的压力，花这么多时间，去找一个不确定的未来工作。我知道劝退永远比劝进方便，劝退的人不需要承担任何责任，你做好了不会怪他，你做的不好，他会说，我不是早就劝退你了？我写这些东西的原因是，我希望大家在转码之前好好想一想，是不是真的只有转码这条路了。希望大家能找到自己感兴趣的路.最后感谢代码随想录，宫水三叶的题解。',
            author:{
                userId:2,
                nickname:'zhn',
                avatar:'https://assets.leetcode.cn/aliyun-lc-upload/users/juzi-in/avatar_1650793887.png'
            }
        },
        {
            articleId:2,
            title:"力扣刷题攻略",
            categary:"面试题",
            content:'在什么圈子就会看到什么样的内容，几个月没登录leetcode之后，再一看当初做的题目，都只记得一些方法了，再写感觉应该很难写出来了。我当时就感觉，做leetcode和刷高考数学题真的很像，我只需要去理解他的思想，实在理解不了的就背下来就完事了。刚入学的时候还是每天坚持刷leetcode，感觉就是一个很长的主线任务，每天做一点，每天变强一点，但是看着就业环境一点点变差，加上自己对计算机好像确实不感兴趣。 然后就直接放弃转码了。并不是劝退，现在在我看来，学了计算机的总会比不学计算机要好找工作，但是只是自己真的背负不了这么大的压力，花这么多时间，去找一个不确定的未来工作。我知道劝退永远比劝进方便，劝退的人不需要承担任何责任，你做好了不会怪他，你做的不好，他会说，我不是早就劝退你了？我写这些东西的原因是，我希望大家在转码之前好好想一想，是不是真的只有转码这条路了。希望大家能找到自己感兴趣的路.最后感谢代码随想录，宫水三叶的题解。',
            author:{
                userId:2,
                nickname:'zhn',
                avatar:'https://assets.leetcode.cn/aliyun-lc-upload/users/juzi-in/avatar_1650793887.png'
            }
        },
        {
            articleId:3,
            title:"力扣刷题攻略",
            categary:"面试题",
            content:'在什么圈子就会看到什么样的内容，几个月没登录leetcode之后，再一看当初做的题目，都只记得一些方法了，再写感觉应该很难写出来了。我当时就感觉，做leetcode和刷高考数学题真的很像，我只需要去理解他的思想，实在理解不了的就背下来就完事了。刚入学的时候还是每天坚持刷leetcode，感觉就是一个很长的主线任务，每天做一点，每天变强一点，但是看着就业环境一点点变差，加上自己对计算机好像确实不感兴趣。 然后就直接放弃转码了。并不是劝退，现在在我看来，学了计算机的总会比不学计算机要好找工作，但是只是自己真的背负不了这么大的压力，花这么多时间，去找一个不确定的未来工作。我知道劝退永远比劝进方便，劝退的人不需要承担任何责任，你做好了不会怪他，你做的不好，他会说，我不是早就劝退你了？我写这些东西的原因是，我希望大家在转码之前好好想一想，是不是真的只有转码这条路了。希望大家能找到自己感兴趣的路.最后感谢代码随想录，宫水三叶的题解。',
            author:{
                userId:2,
                nickname:'zhn',
                avatar:'https://assets.leetcode.cn/aliyun-lc-upload/users/juzi-in/avatar_1650793887.png'
            }
        },
        {
            articleId:4,
            title:"力扣刷题攻略",
            categary:"面试题",
            content:'在什么圈子就会看到什么样的内容，几个月没登录leetcode之后，再一看当初做的题目，都只记得一些方法了，再写感觉应该很难写出来了。我当时就感觉，做leetcode和刷高考数学题真的很像，我只需要去理解他的思想，实在理解不了的就背下来就完事了。刚入学的时候还是每天坚持刷leetcode，感觉就是一个很长的主线任务，每天做一点，每天变强一点，但是看着就业环境一点点变差，加上自己对计算机好像确实不感兴趣。 然后就直接放弃转码了。并不是劝退，现在在我看来，学了计算机的总会比不学计算机要好找工作，但是只是自己真的背负不了这么大的压力，花这么多时间，去找一个不确定的未来工作。我知道劝退永远比劝进方便，劝退的人不需要承担任何责任，你做好了不会怪他，你做的不好，他会说，我不是早就劝退你了？我写这些东西的原因是，我希望大家在转码之前好好想一想，是不是真的只有转码这条路了。希望大家能找到自己感兴趣的路.最后感谢代码随想录，宫水三叶的题解。',
            author:{
                userId:2,
                nickname:'zhn',
                avatar:'https://assets.leetcode.cn/aliyun-lc-upload/users/juzi-in/avatar_1650793887.png'
            }
        },
        {
            articleId:5,
            title:"力扣刷题攻略",
            categary:"面试题",
            content:'在什么圈子就会看到什么样的内容，几个月没登录leetcode之后，再一看当初做的题目，都只记得一些方法了，再写感觉应该很难写出来了。我当时就感觉，做leetcode和刷高考数学题真的很像，我只需要去理解他的思想，实在理解不了的就背下来就完事了。刚入学的时候还是每天坚持刷leetcode，感觉就是一个很长的主线任务，每天做一点，每天变强一点，但是看着就业环境一点点变差，加上自己对计算机好像确实不感兴趣。 然后就直接放弃转码了。并不是劝退，现在在我看来，学了计算机的总会比不学计算机要好找工作，但是只是自己真的背负不了这么大的压力，花这么多时间，去找一个不确定的未来工作。我知道劝退永远比劝进方便，劝退的人不需要承担任何责任，你做好了不会怪他，你做的不好，他会说，我不是早就劝退你了？我写这些东西的原因是，我希望大家在转码之前好好想一想，是不是真的只有转码这条路了。希望大家能找到自己感兴趣的路.最后感谢代码随想录，宫水三叶的题解。',
            author:{
                userId:2,
                nickname:'zhn',
                avatar:'https://assets.leetcode.cn/aliyun-lc-upload/users/juzi-in/avatar_1650793887.png'
            }
        },
        {
            articleId:6,
            title:"力扣刷题攻略",
            categary:"面试题",
            content:'在什么圈子就会看到什么样的内容，几个月没登录leetcode之后，再一看当初做的题目，都只记得一些方法了，再写感觉应该很难写出来了。我当时就感觉，做leetcode和刷高考数学题真的很像，我只需要去理解他的思想，实在理解不了的就背下来就完事了。刚入学的时候还是每天坚持刷leetcode，感觉就是一个很长的主线任务，每天做一点，每天变强一点，但是看着就业环境一点点变差，加上自己对计算机好像确实不感兴趣。 然后就直接放弃转码了。并不是劝退，现在在我看来，学了计算机的总会比不学计算机要好找工作，但是只是自己真的背负不了这么大的压力，花这么多时间，去找一个不确定的未来工作。我知道劝退永远比劝进方便，劝退的人不需要承担任何责任，你做好了不会怪他，你做的不好，他会说，我不是早就劝退你了？我写这些东西的原因是，我希望大家在转码之前好好想一想，是不是真的只有转码这条路了。希望大家能找到自己感兴趣的路.最后感谢代码随想录，宫水三叶的题解。',
            author:{
                userId:2,
                nickname:'zhn',
                avatar:'https://assets.leetcode.cn/aliyun-lc-upload/users/juzi-in/avatar_1650793887.png'
            }
        },
        {
            articleId:7,
            title:"力扣刷题攻略",
            categary:"面试题",
            content:'在什么圈子就会看到什么样的内容，几个月没登录leetcode之后，再一看当初做的题目，都只记得一些方法了，再写感觉应该很难写出来了。我当时就感觉，做leetcode和刷高考数学题真的很像，我只需要去理解他的思想，实在理解不了的就背下来就完事了。刚入学的时候还是每天坚持刷leetcode，感觉就是一个很长的主线任务，每天做一点，每天变强一点，但是看着就业环境一点点变差，加上自己对计算机好像确实不感兴趣。 然后就直接放弃转码了。并不是劝退，现在在我看来，学了计算机的总会比不学计算机要好找工作，但是只是自己真的背负不了这么大的压力，花这么多时间，去找一个不确定的未来工作。我知道劝退永远比劝进方便，劝退的人不需要承担任何责任，你做好了不会怪他，你做的不好，他会说，我不是早就劝退你了？我写这些东西的原因是，我希望大家在转码之前好好想一想，是不是真的只有转码这条路了。希望大家能找到自己感兴趣的路.最后感谢代码随想录，宫水三叶的题解。',
            author:{
                userId:2,
                nickname:'zhn',
                avatar:'https://assets.leetcode.cn/aliyun-lc-upload/users/juzi-in/avatar_1650793887.png'
            }
        },
        {
            articleId:8,
            title:"力扣刷题攻略",
            categary:"面试题",
            content:'在什么圈子就会看到什么样的内容，几个月没登录leetcode之后，再一看当初做的题目，都只记得一些方法了，再写感觉应该很难写出来了。我当时就感觉，做leetcode和刷高考数学题真的很像，我只需要去理解他的思想，实在理解不了的就背下来就完事了。刚入学的时候还是每天坚持刷leetcode，感觉就是一个很长的主线任务，每天做一点，每天变强一点，但是看着就业环境一点点变差，加上自己对计算机好像确实不感兴趣。 然后就直接放弃转码了。并不是劝退，现在在我看来，学了计算机的总会比不学计算机要好找工作，但是只是自己真的背负不了这么大的压力，花这么多时间，去找一个不确定的未来工作。我知道劝退永远比劝进方便，劝退的人不需要承担任何责任，你做好了不会怪他，你做的不好，他会说，我不是早就劝退你了？我写这些东西的原因是，我希望大家在转码之前好好想一想，是不是真的只有转码这条路了。希望大家能找到自己感兴趣的路.最后感谢代码随想录，宫水三叶的题解。',
            author:{
                userId:2,
                nickname:'zhn',
                avatar:'https://assets.leetcode.cn/aliyun-lc-upload/users/juzi-in/avatar_1650793887.png'
            }
        },
        {
            articleId:9,
            title:"力扣刷题攻略",
            categary:"面试题",
            content:'在什么圈子就会看到什么样的内容，几个月没登录leetcode之后，再一看当初做的题目，都只记得一些方法了，再写感觉应该很难写出来了。我当时就感觉，做leetcode和刷高考数学题真的很像，我只需要去理解他的思想，实在理解不了的就背下来就完事了。刚入学的时候还是每天坚持刷leetcode，感觉就是一个很长的主线任务，每天做一点，每天变强一点，但是看着就业环境一点点变差，加上自己对计算机好像确实不感兴趣。 然后就直接放弃转码了。并不是劝退，现在在我看来，学了计算机的总会比不学计算机要好找工作，但是只是自己真的背负不了这么大的压力，花这么多时间，去找一个不确定的未来工作。我知道劝退永远比劝进方便，劝退的人不需要承担任何责任，你做好了不会怪他，你做的不好，他会说，我不是早就劝退你了？我写这些东西的原因是，我希望大家在转码之前好好想一想，是不是真的只有转码这条路了。希望大家能找到自己感兴趣的路.最后感谢代码随想录，宫水三叶的题解。',
            author:{
                userId:2,
                nickname:'zhn',
                avatar:'https://assets.leetcode.cn/aliyun-lc-upload/users/juzi-in/avatar_1650793887.png'
            }
        },
    ]
})
</script>

<style scoped>
    .resource {
        border-radius: 6px;
        margin-bottom: 20px;
    }

    .resource_title {
        margin-bottom: 15px;
        margin-top: -10px;
        display: flex;
        justify-content: space-between;
        border-bottom: 1px solid rgb(238, 238, 238);
        overflow: hidden;
    }

    .resource_title .title {
        line-height: 40px;
        color: #474749;
    }

    .resource_title .title b {
        display: inline-block;
        border-bottom: 4px solid #2fa7b9;
    }

    .title-link {
        font-size: 16px;
        line-height: 22px;
        font-weight: 540;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .content {
        font-size: 13px;
        color: #999;
        margin-left: 20px;
        margin-top: 5px;
        margin-bottom: 15px;
        -webkit-box-orient: vertical;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        text-align: justify;
        overflow: hidden;
    }
</style>