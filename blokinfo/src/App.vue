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
                    <div class="rt" style="min-width: 500px;">
                        <ul class="nav">
                            <li :class="index==='index'?'active':''"><a @click="toIndex">首页</a></li>
                            <li :class="index==='data'?'active':''"><a @click="toData">平台数据</a></li>
                            <li :class="index==='chart'?'active':''"><a @click="toChart">区块统计</a></li>
                            <li :class="index==='table'?'active':''"><a @click="toTable">最新区块</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </header>
        <img style="width: 100%;margin-top:93px;height: 360px;"
             src="http://seopic.699pic.com/photo/50080/0504.jpg_wh1200.jpg">
        <div class="briefly">
            <ul>
                <li class="post">
                    <div class="visual"><i class="fa fa-tachometer"></i></div>
                    <div class="number">{{count.orgCount}}</div>
                    <div class="more"><a href="#">平台组织总数</a></div>
                </li>
                <li style="background-color: #ea4c89">
                    <div class="visual"><i class="fa fa-comments"></i></div>
                    <div class="number">{{count.peerCount}}</div>
                    <div class="more"><a href="?user&amp;comments">平台节点总数</a></div>
                </li>
                <li style="background-color: #ffb848">
                    <div class="visual"><i class="fa fa-cog"></i></div>
                    <div class="number">{{count.platform.blockCount/3}}</div>
                    <div class="more"><a href="?user&amp;info">平台区块总数</a></div>
                </li>
                <li style="background-color: #5db8f8">
                    <div class="visual"><i class="fa fa-sign-in"></i></div>
                    <div class="number">{{count.platform.rwSetCount/3}}</div>
                    <div class="more login_logout"><a href="?user&amp;logout">平台读写集总数</a></div>
                </li>
            </ul>
        </div>
        <div>
            <div style="padding-top: 100px;position: relative">
                <div style="width:50%;height:300px;" id="chart-div"></div>
                <div style="width:50%;height:300px; position: absolute;right: 0;top: 100px" id="pie-chart"></div>
            </div>
            <div>
                <Index/>
            </div>
        </div>
        <div class="footer">
            <div>
                <ul class="list-unstyled ">
                    <li>
                        <a href="https://github.com/aberic/fabric-net-server" target="_blank">Fabric Net
                            Server</a>
                    </li>
                    <li>
                        <a href="http://www.cnblogs.com/aberic/" target="_blank">Aberic Blog</a>
                    </li>
                </ul>
                <ul class="list-unstyled ">
                    <li>
                        <a href="https://item.jd.com/12381034.html?dist=jd" target="_blank">Book Link</a>
                    </li>
                    <li>
                        <a href="https://hub.docker.com/u/aberic/" target="_blank">Docker Image</a>
                    </li>
                </ul>
                <ul class="list-unstyled ">
                    <li>
                        <a href="https://github.com/hyperledger" target="_blank">HyperLedger</a>
                    </li>
                    <li>
                        <a href="https://jira.hyperledger.org/secure/Dashboard.jspa" target="_blank">Jira</a>
                    </li>
                </ul>
                <ul class="list-unstyled ">
                    <li>
                        <a href="https://jira.hyperledger.org/secure/Dashboard.jspa" target="_blank">Fabric
                            Doc</a>
                    </li>
                    <li>
                        <a href="http://hyperledger-fabric-ca.readthedocs.io/en/latest/" target="_blank">Fabric
                            CA Doc</a>
                    </li>
                </ul>
            </div>
            <div style="margin-top: 80px">
                <span>基于HyperLedger Fabric的imp溯源系统区块链浏览器</span>
            </div>
        </div>
    </div>
</template>

<script>

    import Index from "@/pages/Index"
    import echarts from 'echarts';
    import axios from 'axios'
    export default {
        name: 'app',
        components: {
            Index
        },
        data() {
            return {
                index:'index',
                count:{
                    platform:{}
                },
                exampleChart: null // 统计图对象
            }
        },
        created() {
            axios.get('/api/info/count').then(e => {
                this.count = e.data
            })
            // this.refresh()
        },
        methods: {
            refresh() {
                axios.get(`/api/info/blocks`).then(e => {
                    console.log(e.data)
                   /* let data = e.data.msg.rows
                    for (let i = 0; i < data.length; i++) {
                        data[i].creator = data[i].envelopes[0].creator.mspid
                        data[i].timestamp = data[i].envelopes[0].timestamp
                    }
                    this.data = data
                    console.log(this.data)*/
                })
            },
            toData(){
                this.index='data';
                document.documentElement.scrollTop=380;
            },
            toIndex(){
                this.index='index';
                document.documentElement.scrollTop=0;
            },
            toChart(){
                this.index='chart';
                document.documentElement.scrollTop=500;
            },
            toTable(){
                this.index='table';
                document.documentElement.scrollTop=650;
            },
            drawExampleChart: function () {
                this.exampleChart = echarts.init(document.getElementById('chart-div'));
                this.exampleChart.setOption(this.exampleChartOption);
            },
            drawPieChart: function () {
                this.exampleChart = echarts.init(document.getElementById('pie-chart'));
                this.exampleChart.setOption(this.pieChartOption);
            }

        },
        computed: {
            exampleChartOption() {
                let xAxisData = [];
                let seriesData = [];
                // 动态数据的获取及修改
                let option = {
                    title: {
                        text: '平台日总区块曲线',
                        left: 'center',
                        textStyle: {
                            //文字颜色
                            color: '#6e6e6e',
                            //字体风格,'normal','italic','oblique'
                            fontStyle: 'normal',
                            //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
                            fontWeight: 'bold',
                            //字体系列
                            fontFamily: 'sans-serif',
                            //字体大小
                            fontSize: 12
                        }
                    },
                    xAxis: {
                        // show:false,
                        type: 'category',
                        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                        textStyle: {
                            color: "#a7aab3"
                        }
                    },
                    axisLine: {//x轴的横坐标边框线
                        show: true
                    },
                    lineStyle: {
                        color: '#6ab2ec',
                    },
                    yAxis: {
                        show:false,
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
                            }, itemStyle: {
                                normal: {
                                    color: "#e4e4e4",
                                    barBorderColor: "#e4e4e4",
                                }
                            },
                        }
                    }]
                };
                return option;
            },
            pieChartOption() {
                let xAxisData = [];
                let seriesData = [];
                // 动态数据的获取及修改
                let option = {
                    title: {
                        text: '平台区块记录比',
                        left: 'center',
                        textStyle: {
                            //文字颜色
                            color: '#6e6e6e',
                            //字体风格,'normal','italic','oblique'
                            fontStyle: 'normal',
                            //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
                            fontWeight: 'bold',
                            //字体系列
                            fontFamily: 'sans-serif',
                            //字体大小
                            fontSize: 12
                        }
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b}: {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'left',
                        data:['奶源组织','加工厂商','检测机构']
                    },
                    series: [
                        {
                            name:'区块来源',
                            type:'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                normal: {
                                    show: false,
                                    position: 'center'
                                },
                                emphasis: {
                                    show: true,
                                    textStyle: {
                                        fontSize: '30',
                                        fontWeight: 'bold'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: false
                                }
                            },
                            data:[
                                {value:33, name:'奶源组织'},
                                {value:30, name:'加工厂商'},
                                {value:24, name:'检测机构'},
                            ]
                        }
                    ]
                };
                return option;
            }
        },
        mounted() {
            this.$nextTick(() => {
                this.drawExampleChart();
                this.drawPieChart();
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
    .briefly ul li .visual {
        float: left;
        font-size: 40px;
        margin: 5px 15px;
    }

    .briefly ul li .more a {
        display: block;
        color: rgba(255, 255, 255, .85);
        background-color: rgba(0, 0, 0, .15);
        padding: 5px 15px;
    }

    .briefly ul li .more {
        clear: both;
    }

    .briefly ul li .number {
        float: right;
        margin: 15px;
        font-size: 30px;
        text-align: right;
    }

   .list-unstyled{
       float: left;
       list-style: none;
       display: block;
       width: 220px;
   }

    .footer {
        background: #fff;
        border-top: 1px solid rgba(0, 40, 100, 0.12);
        font-size: 0.875rem;
        padding: 1.25rem 0;
        color: #9aa0ac;
    }

    .briefly ul li {
        width: 23.65%;
        float: left;
        margin-left: 1.35%;
        background-color: #0bacd3;
        color: #FFF;
        box-sizing: border-box;
    }

    .briefly {
        height: auto;
        overflow: hidden;
        margin-left: -1.35%;
    }

    ul .active {
        color: deepskyblue;
        border-bottom: 2px solid deepskyblue;
    }
    ul li a{
        color: black;
    }
    .rt {
        position: relative;
    }

    .nav {
        position: absolute;
        top: 50px;
        list-style: none;
    }

    .nav li {
        text-align: center;
        display: block;
        float: left;
        width: 93px;
        height: 38px;
        font-size: 16px;
    }

    .header_wrap {
        position: fixed;
        font-family: Roboto, Helvetica Neue, Helvetica, Hiragino Sans GB, STHeitiSC-Light, Microsoft YaHei, \\5FAE\8F6F\96C5\9ED1, Arial, sans-serif;
        width: 980px;
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
        width: 980px;
    }
</style>
