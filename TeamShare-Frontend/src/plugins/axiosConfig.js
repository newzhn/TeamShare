// 配置axios
import axios from "axios";
import QueryString from "qs";

// 创建配置axios
const newAxios = axios.create({
    baseURL: 'http://localhost:8088/api',
    timeout: 10000,
    // withCredentials: false
})

// 添加请求拦截器
newAxios.interceptors.request.use(function (config) {
    if(config.method === 'get'){
        //如果是get请求，且params是数组类型如arr=[1,2]，则转换成arr=1&arr=2
        config.paramsSerializer = function(params) {
            return QueryString.stringify(params, {arrayFormat: 'repeat'})
        }
    }
    // 在发送请求之前做些什么
    // 每次请求都会带上本地存储的authorization
    const token = localStorage.getItem('authorization');
    if (token) {
        config.headers.Authorization = token;
    }
    return config;
}, function (error) {
    // 对请求错误做些什么
    console.log('请求发送失败');
    return Promise.reject(error);
});

// 添加响应拦截器
newAxios.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    return response;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
});

export default newAxios