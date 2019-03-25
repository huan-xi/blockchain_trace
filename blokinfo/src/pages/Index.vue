<template>
    <div class="page">
        <div class="block-table">
            <Table border :columns="columns" :data="data">
                <div slot="header" class="table-header">最新出块</div>
                <div slot="footer" class="table-footer">
                    <Page :total="count"/>
                </div>
            </Table>
        </div>
    </div>
</template>

<style scoped>
    .page {

    }

    .block-table {
        margin: 30px;
    }

    .table-header {
        padding-left: 20px;
        background-color: #ddd;
        color: #000;
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
                columns: [
                    {
                        title: '高度',
                        key: '',
                        width: '100'
                    },
                    {
                        title: '大小',
                        key: 'age'
                    },
                    {
                        title: '播报方',
                        key: 'address'
                    },
                    {
                        title: '时间',
                        key: 'address'
                    },
                    {
                        title: '数据hash',
                        key: 'address'
                    }
                ],
                data: [
                    {
                        name: '',
                        age: 18,
                        address: 'New York No. 1 Lake Park',
                        date: '2016-10-03'
                    },
                    {
                        name: 'Jim Green',
                        age: 24,
                        address: 'London No. 1 Lake Park',
                        date: '2016-10-01'
                    },
                    {
                        name: 'Joe Black',
                        age: 30,
                        address: 'Sydney No. 1 Lake Park',
                        date: '2016-10-02'
                    },
                    {
                        name: 'Jon Snow',
                        age: 26,
                        address: 'Ottawa No. 2 Lake Park',
                        date: '2016-10-04'
                    }
                ],
                count: 0
            }
        },
        created() {
            axios.get('/api/blockchain/info').then(e => {
                console.log(e.data)
                this.count = e.data.height
            })
        },
        methods: {}
    }
</script>

