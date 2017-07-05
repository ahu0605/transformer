<template>
  <div class="GetJson">
		<header>
			
		</header>
	
		<div class="loginToreg clearfix">
			<a href="javascript:;" class="btn" v-on:click="getJson" class="fl">获取数据</a>
		</div>	
  </div>
</template>

<script>
import md5 from 'js-md5';
export default {
  name: 'GetJson',
  data () {
    return {
      phone: '',
      pwd:''
    }
  },
  mounted:function(){
  	let _this = this;
  	this.$nextTick(function(){
  		//window.setTitle('data');
  	});	
  },
  methods:{
  	
  	getJson:function(){
  	
  		this.$axios.post('/public/user/getJson',{}).then((res)=>{
  			let [code,resData,msg]=[res.data.code,res.data.data,res.data.msg];
  			if(code === 0){
	  			console.log(es.data.data,res.data.msg);
  			}
  		}).catch((res)=>{
  			console.log(res);
  		});
  	}
  	
  	
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
	@import '../style/common';
	header{width:35%;margin:0 auto;margin-bottom:2rem;}
	.login-form{width:80%;margin:0 auto;}
	.bottom-line{background:$color-white;display: block;
		border-radius:0.4rem;
		padding-top:0.5rem;
		padding-bottom:0.5rem;
		letter-spacing:2px;
		font-weight: lighter;
	}
	.bottom-line+.bottom-line{margin-top:0.6rem;}
	.span-left{height:100%;margin-right:1rem;display: inline-block;}
	input{font-size:0.64rem;font-weight:100;color:$color-font;}
	.btn{
	    background:url('../images/btn.jpg') no-repeat;
	    background-size:100% 100%;
	    border-radius:1.4rem !important;
	    letter-spacing: 1em;
	    text-align:center;
	}
	.loginToreg{padding-top:0.75rem;width:80%;margin:0 auto;}
</style>
