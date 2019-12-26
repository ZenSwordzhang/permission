# vue 搭建
## nodejs
    * [下载地址](https://nodejs.org/zh-cn/download/)
    * 查看nodejs版本命令
        * node -v
## npm：nodejs默认集成了npm
### 命令
    * 查看npm版本命令
        * npm -v
    * 设置全局存放依赖包路径
        * npm config set prefix "E:\nodejs\node_global"
    * 设置缓存路径
        * npm config set cache "E:\nodejs\node_cache"
    * 设置vue环境变量
        * E:\nodejs\node_global
    * 查看npm源地址
        * npm config get registry
    * 查看所有配置信息
        * npm config list
    * 查看全局依赖信息
        * npm list -global
    * 清除缓存
        * npm cache clean --force
    * npm init | npm init --yes
        * 生成package.json 文件
### 镜像
#### 淘宝镜像
    * 1.临时使用
        * npm --registry https://registry.npm.taobao.org install express
    * 2.持久使用
        * npm config set registry https://registry.npm.taobao.org
    * 3.通过cnpm
        * npm install -g cnpm --registry=https://registry.npm.taobao.org
#### 官方镜像
    * npm config set registry https://registry.npmjs.org      
## webpack
    * 全局安装webpack（webpack v4+ 版本，需要安装 CLI）
        * npm install webpack -g
    * 全局安装webpack-cli
        * npm install webpack-cli -g
    * 查看webpack版本信息
        npm info webpack
    * 安装指定版本（建议指定目录下安装，不进行全局安装）
        * npm install --save-dev webpack@<version>
            * npm install --save-dev webpack@4.41.4 | npm install webpack@4.41.4 --save-dev
    * 局部（当前项目）安装webpack依赖
            * npm install --save-dev webpack
    * 局部（当前项目）安装webpack-cli
        * npm install --save-dev webpack-cli
    * 安装webpack开发工具（热更新需要搭建服务模块）
        * npm install --save-dev webpack-dev-server
    * path模块提供了一些工具函数，用于处理文件与目录的路径
        * npm install --save-dev path
    * 简化HTML文件的创建
        * npm install --save-dev html-webpack-plugin
    * 用于在构建之前删除/清理您的构建文件夹
        * npm install --save-dev clean-webpack-plugin
    * Vue.js组件加载器(插件)
        * npm install --save-dev vue-loader
    * 对于模板的函数编译 与vue-loader 配合使用
        * npm install --save-dev vue-template-compiler
    * css样式处理器
        * npm install --save-dev style-loader css-loader
## vue
    *安装最近版vue
        * npm install -g @vue/cli
    * 全局查看vue版本号
        * npm info vue
    * 局部（当前项目）查vue版本号
        * npm list vue version

#问题：
##问题1
    * error Unexpected token w in JSON at position 17549 while parsing near
        * 解决1：删除package-lock.json文件
##问题2
    * webpack-cli安装失败
        * 解决2：
            * 全局安装webpack、webpack-cli
            * 再局部（当前项目）安装webpack、webpack-cli

