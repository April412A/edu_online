<script>
import teacher from '@/api/edu/teacher'

export default {
    data() {
        return {
            list:null,
            current:1,
            limit:10,
            total:0,
            teacherQuery:{}
        }
    },
    created() {
        this.getTeacherList()
    },
    methods: {
        getTeacherList(){
            teacher.getTeacherListPage(this.current,this.limit,this.teacherQuery)
            .then(response=>{
                console.log(response)
            })
        }
    },
}
</script>