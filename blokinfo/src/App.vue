<template>
    <div id="app">
        <header>
            <div class="header_wrap btc">
                <div class="inner_header">
                    <div class="logo_box">
                        <div class="coin_name">
                            IMP
                            <h1 style="display: none;">IMP 区块链浏览器</h1></div>
                        <div class="explorer"><p class="explorer_txt">区块浏览器</p>
                            <div class="tokenview"><span class="txt">powered by</span><a href="#" target="_blank"
                                                                                         class="logo"><img
                                    src="https://static.tokenview.com/_nuxt/img/colorLogo.86c1390.png" alt=""></a></div>
                        </div>
                    </div>
                    <!--  <div class="rt" style="min-width: 500px;">
                          <div class="top">
                              <div class="search_box">
                                  <div class="icon_box"><img
                                          src="https://static.tokenview.com/_nuxt/img/search_icon.bebd31e.png"></div>
                                  <input placeholder="请输入钱包地址,交易ID,区块哈希,区块高度等" type="text" class="search_input">
                                  <div class="jump-to">
                                      <div class="jump-to-all"><img
                                              src="https://static.tokenview.com/_nuxt/img/search_icon.bebd31e.png" alt=""
                                              class="jump-to-icon">
                                          <div class="jump-to-txt">1</div>
                                          <div class="jump-to-all-btn">全站搜索</div>
                                      </div>&lt;!&ndash;&ndash;&gt;&lt;!&ndash;&ndash;&gt;</div>
                              </div>
                              <div class="lang-box">
                                  <div class="lang-box1"><img src="/lang/cn.png" alt=""> 中文
                                  </div>&lt;!&ndash;&ndash;&gt;</div>
                          </div>
                          <ul class="bottom">
                              <li class="item uitrain"><a href="#"></a>
                                  <div class="triangle"></div>
                                  <div class="popover-box">
                                      <div tabindex="0" x-placement="bottom"
                                           class="el-popover el-popper el-popover&#45;&#45;plain">
                                          <div class="item-box"><a href="https://ugas.tokenview.com/cn/"
                                                                   class="popover-item">主链</a><a
                                                  href="https://ugasbmtpr.tokenview.com/cn/"
                                                  class="popover-item">开拓者侧链</a><a
                                                  href="https://ugasbmupc.tokenview.com/cn/" class="popover-item">电魂侧链</a><a
                                                  href="https://ugasbmnrl.tokenview.com/cn/"
                                                  class="popover-item">新零售侧链</a></div>
                                          <div x-arrow="" class="popper__arrow"></div>
                                      </div>
                                  </div>
                              </li>
                              <li class="item active"><a href="/cn/">首页</a></li>&lt;!&ndash;&ndash;&gt;
                              <li class="item"><a href="/cn/charts">图表</a></li>
                              <li class="item"><a href="/cn/coininfo">基本信息</a></li>
                              <li class="item"><a href="/cn/blocklist">爆块列表</a></li>
                              <li class="item"><a href="/cn/pending">待确认交易</a></li>&lt;!&ndash;&ndash;&gt;</ul>
                      </div>-->
                </div>
            </div>
        </header>
        <img style="width: 100%;height: 500px;" src="http://seopic.699pic.com/photo/50080/0504.jpg_wh1200.jpg">
        <div>
            <div style="padding-top: 100px">
                <div style="width:60%;height:300px;" id="chart-div"></div>
                <div>

                </div>
            </div>
            <div>
                <Index/>
            </div>
        </div>
    </div>
</template>

<script>

    import Index from "@/pages/Index"
    import echarts from 'echarts';
    export default {
        name: 'app',
        components: {
            Index
        },
        data(){
            return {
                exampleChart: null // 统计图对象
            }
        },
        methods: {
            drawExampleChart: function () {
                this.exampleChart = echarts.init(document.getElementById('chart-div'));
                this.exampleChart.setOption(this.exampleChartOption);
            }
        },
        computed: {
            exampleChartOption () {
                let xAxisData = [];
                let seriesData = [];
                // 动态数据的获取及修改
                let option = {
                    title:{
                        text:'平台日总区块曲线',
                        left:'center',
                        textStyle:{
                            //文字颜色
                            color:'#6e6e6e',
                            //字体风格,'normal','italic','oblique'
                            fontStyle:'normal',
                            //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
                            fontWeight:'bold',
                            //字体系列
                            fontFamily:'sans-serif',
                            //字体大小
                            fontSize:12
                        }
                    },
                    xAxis: {
                        type: 'category',
                        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                        textStyle:{
                            color:"#a7aab3"
                        }
                    },
                    axisLine:{//x轴的横坐标边框线
                        show: false
                    },
                    lineStyle:{
                        color:'#6ab2ec',
                    },
                    yAxis: {
                        type: 'value',
                        lineStyle: {
                            // 使用深浅的间隔色
                            color: ['#132a6e']
                        }
                    },
                    series: [{
                        data: [820, 932, 901, 934, 1290, 1330, 1320],
                        type: 'line',
                        smooth: true,
                        areaStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                    offset: 0,
                                    color: 'rgba(40, 182, 252, 0.85)'
                                }, {
                                    offset: 1,
                                    color: 'rgba(28, 159, 255, 0.2)'
                                }])
                            },itemStyle:{
                                normal:{
                                    color:"#e4e4e4",
                                    barBorderColor:"#e4e4e4",
                                }
                            },
                        }
                    }]
                };
                return option;
            }
        },
        mounted () {
            this.$nextTick(() => {
                this.drawExampleChart();

                let _self = this;
                window.addEventListener('resize', function () {
                    _self.exampleChart.resize();
                });
            });
        },
        watch: {
            exampleChartOption: {
                handler: function (newVal, oldVal) {
                    if (this.exampleChart) {
                        if (newVal) {
                            this.exampleChart.setOption(newVal);
                        } else {
                            this.exampleChart.setOption(oldVal);
                        }
                    } else {
                        this.drawExampleChart();
                    }
                },
                deep: true
            }
        }
    }

</script>

<style>
    .header_wrap {
        position: fixed;
        font-family: Roboto, Helvetica Neue, Helvetica, Hiragino Sans GB, STHeitiSC-Light, Microsoft YaHei, \\5FAE\8F6F\96C5\9ED1, Arial, sans-serif;
        width: 100%;
        height: 87px;
        z-index: 27;
        background-color: #fff;
        border-bottom: 1px solid #e5e5e5;
    }

    .header_wrap .inner_header .rt {
        position: relative;
        float: right;
        height: 100%;
    }

    .header_wrap .inner_header .logo_box {
        width: 40%;
        height: 100%;
        float: left;
        position: absolute;
    }

    .header_wrap .inner_header .logo_box .explorer .explorer_txt {
        font-size: 30px;
        color: #0d2986;
        margin-top: 9px;
    }

    .header_wrap .inner_header .logo_box .coin_name {
        float: left;
        margin-left: 13px;
        margin-right: 6px;
        width: unset;
        height: 100%;
        line-height: 86px;
        color: #0d2986;
        font-size: 50px;
    }

    #app {
    }
</style>
