import Vue from 'vue'
import Router from 'vue-router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import GetJson from '@/components/GetJson'


const qs = require('querystring');
Vue.use(Router,VueAxios,axios);
Vue.prototype.$axios = axios.create({
	timeout:10000,
	headers:{
		//'Content-Type':'text/html;charset=UTF-8'
		'Accept':'application/json'
	},
	/*transformRequest:[function(data){
		data = qs.stringify(data);
		return data;
	}]*/
});
export default new Router({
  routes: [
    {
      path: '/',
      name: 'GetJson',
      component: GetJson
    },
    
  ]
})
