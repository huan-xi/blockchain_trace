<template>
    <div class="page">
        <div class="block-table">
            <Table border :columns="columns" :data="data">
                <div slot="header" class="table-header">
                    最新出块
                </div>
                <div slot="footer" class="table-footer">
                    <Page :total="count" @on-change="pageChange"/>
                </div>
            </Table>
        </div>
    </div>
</template>

<style>
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
                        title: '高度',
                        key: 'blockNumber',
                        width: '100'
                    },
                    {
                        title: '大小(b)',
                        key: 'size',
                        width: '100'
                    },
                    {
                        title: '播报方',
                        key: 'creator',
                        width: '150'
                    },
                    {
                        title: '时间',
                        key: 'timestamp',
                        width: '200'
                    },
                    {
                        title: '数据hash',
                        key: 'dataHash'
                    }
                ],
                data: [],
                count: 0
            }
        },

        created() {
            axios.get('/api/blockchain/info').then(e => {
                this.count = e.data.height
            })
            this.refresh()
        },
        methods: {
            refresh() {
                axios.get(`/api/block/blocks?page=${this.page}&size=${this.size}`).then(e => {
                    console.log(e.data)
                    let data = e.data.msg.rows
                    for (let i = 0; i < data.length; i++) {
                        data[i].creator = data[i].envelopes[0].creator.mspid
                        data[i].timestamp = data[i].envelopes[0].timestamp
                    }
                    this.data = data
                    console.log(this.data)
                })
            },
            pageChange(e) {
                this.page = e
                this.refresh()
            }
        }
    }
</script>

