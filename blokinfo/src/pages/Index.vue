<template>
    <div class="page">
        <div class="block-table">
            <Table border :columns="columns" :data="data">
                <div slot="header" class="table-header">
                    <div class="icon"></div>
                      <span style="margin-left: 10px">最新出块</span>
                </div>
                <div slot="footer" class="table-footer">
                    <Page :total="count" @on-change="pageChange"/>
                </div>
            </Table>
        </div>
    </div>
</template>

<style>
    .icon {
        width: 26px;
        height: 25px;
        float: left;
        margin: 12px 4px 0 10px;
        background: url(https://static.tokenview.com/_nuxt/img/block_icon.059a312.png);
    }
    .ivu-table-header tr th{
        background-color: white;
        font-size: 16px;
    }

    .block-table {
        margin: 30px;
        box-shadow: 2px 2px 2px 0 #b8b8b8;
    }

    .table-header {
        padding-left: 20px;
        color: #000;
        border-bottom: 1px solid gray;
        font-size: 20px;
    }

    .table-footer {
        text-align: center;
    }
</style>
<script>
    import axios from 'axios'

    export default {
        name: "Index",
        data() {
            return {
                page: 1,
                size: 10,
                columns: [
                    {
                        title: '区块高度',
                        key: 'height',
                        width: '100'
                    },{
                        title: '读写集',
                        key: 'rwSetCount',
                        width: '100'
                    },
                    {
                        title: '节点名称',
                        key: 'peerChannelName',
                        width: '150'
                    },
                    {
                        title: '时间',
                        key: 'createDate',
                        width: '200'
                    },
                    {
                        title: '前一块hash',
                        key: 'previousHash'
                    }
                ],
                data: [],
                count: 0
            }
        },

        created() {
            axios.get('/api/info/count').then(e => {
                this.count = e.data.platform.blockCount/3
            })
            this.refresh()
        },
        methods: {
            refresh() {
                axios.get(`/api/info/blockDaos`).then(e => {
                    console.log(e.data)
                    this.data=e.data
                })
            },
            pageChange(e) {
                // this.page = e
                // this.refresh()
            }
        }
    }
</script>

