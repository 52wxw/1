import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path'; // 新增：引入path模块

export default defineConfig({
  // 基础配置
  base: '/', 
  root: '.', 

  // 插件配置
  plugins: [vue()],

  // 路径别名配置
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src') // 此时path已正确引入
    }
  },

  // 开发服务器配置
  server: {
    host: '0.0.0.0', 
    port: 3000, 
    proxy: {
      '/api': {
	      target: 'http://192.168.112.155:8080', 
        changeOrigin: true, 
      }
    }
  },

  // 构建配置
  build: {
    outDir: 'dist', 
    assetsDir: 'static', 
    rollupOptions: {
      input: './public/index.html',
      output: {
        chunkFileNames: 'static/js/[name]-[hash].js',
        assetFileNames: 'static/[ext]/[name]-[hash].[ext]',
        entryFileNames: 'static/js/[name]-[hash].js'
      }
    }
  }
});
