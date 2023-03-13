import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
//element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//导入element-plus图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//导入自定义全局样式
import '@/assets/css/index.css'

const app = createApp(App)

app.use(ElementPlus)
app.use(createPinia())
app.use(router)

// 注册图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.mount('#app')
