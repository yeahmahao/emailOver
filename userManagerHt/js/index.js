layui.use('carousel', function(){
    var carousel = layui.carousel;
    //建造实例
    carousel.render({
        elem: '#test1'
        ,width: '100%' //设置容器宽度
        ,arrow: 'always' //始终显示箭头
        //,anim: 'updown' //切换动画方式
        ,autoplay: true
        ,interval: 2500
        ,height: '600px'
    });
});
layui.use(['form', 'layedit', 'laydate'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,laydate = layui.laydate;

    //日期
    laydate.render({
        elem: '#date'
    });
    laydate.render({
        elem: '#date1'
    });

    //创建一个编辑器
    var editIndex = layedit.build('LAY_demo_editor');

    //自定义验证规则
    form.verify({
        username: function(value){
            if(value.length < 1){
                return '不能位空';
            }
            if(value.length > 6){
                return '长度不能大于6个字符';
            }
        },
        mimayanz: function(value){
            var ps1 = document.getElementById("pwd1").value;
            if(value.length < 1){
                return '不能位空';
            }
            if(value.length < 6 || value.length > 12){
                return '密码必须6到12位，且不能出现空格';
            }
            if( ps1 !== value){
                return '两次密码不一致,请重新输入';
            }
        },
        userim: function(value){
            if (value.length < 1){
                return '头像未上传成功';
            }

        },
        password: function(value){
            if(value.length < 6 || value.length > 12){
                return '密码必须6到12位，且不能出现空格';
            }
        }
        ,pass: [
            /^[\S]{6,12}$/
            ,'密码必须6到12位，且不能出现空格'
        ]
        ,content: function(value){
            layedit.sync(editIndex);

        }
    });
    form.on('submit(lo)', function (data1) {
        login(data1);
        layer.alert(JSON.stringify(data1.field), {
            title: '最终的提交信息'
        })
        return false;
    });
    //设定文件大小限制
    layui.use('upload', function() {
        var $ = layui.jquery
            , upload = layui.upload;
        var file = document.getElementById("userImage_file");
        upload.render({
            elem: '#userImage'
            , url: 'https://httpbin.org/post' //改成您自己的上传接口
            , size: 60 //限制文件大小，单位 KB
            , done: function (res) {
                layer.msg('上传成功');
                console.log(res.files.file)
                file.value = res.files.file;
            }
        });
    })

    //监听提交
    form.on('submit(register)', function(data){
        layer.alert(JSON.stringify(data.field), {
            title: '最终的提交信息'
        })
        return false;
    });


});


