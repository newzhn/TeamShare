import { fileURLToPath, URL } from 'node:url'

import { defineConfig,loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default ({ mode }) => {
  //参数mode为开放模式或生产模式
  //console.log(mode);  // development or product
  const env=loadEnv(mode, process.cwd());   // 获取.env文件里定义的环境变量
  
  return defineConfig({
    plugins: [vue()],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url))
      }
    },
    server: {
      host: env.VITE_FRONTEND_HOST, //ip地址
      port: env.VITE_FRONTEND_PORT, //端口号
      open: true //启动后是否自动打开浏览器
    }
    })
}