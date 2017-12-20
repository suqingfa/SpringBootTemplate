# 说明
    前端与后台使用Cookie保持会话，因此前端请求的时候需要携带相应Cookie, 请求响应时需要保存Cookie。
    前端需要自动处理301重定向，否则可能得不到正确响应。
    如无特殊说明，请求为参数格式json，请求时需要设置Content-Type:application/json;charset=UTF-8
    响应格式为json
        请求成功时，响应格式如下，在文档中简写为"info":"OK" 或 "data":{...}
            响应不包含数据时
            {
                 "code":0,
                 "info":"OK"
            }
            响应包含数据时
            {
                 "code":0,
                 "info":"OK",
                 "data":{...}
            }
        请求失败时，响应格式如下，在文档中简写为"info":"xxx"
            {
                "code":xxx,
                "info":"xxx"
            }
        响应可能分多种情况，例如:当参数错误时返回"info":"ParameterError",当未登录时返回"info":"NotLogin",当请求成功时返回"data"{...}
            在文档中简写格式
            Response
                "info":"xx"
                "info":"yy"
                "info":"zz"
                "data":{...}

# code与info的对应关系如下
        OK(0),                  // 请求成功
        NotLogin(100),          // 未登录
        UsernameExist(101),     // 用户名已被注册
        Error(200),             // 请求失败
        ParameterError(201),    // 请求参数不正确

# 全局状态码
    未登录状态请求
        Response
            "info":"NotLogin"
    其它错误        
        Response
            "info":"Error"

# 一、账号
    公共url /api/account

## 注册
    /register post
    Request
        {
            "username":String,
            "password":String,
        }
    Response
        "info":"OK"
        "info":"UsernameExist"
        "info":"ParameterError"

## form登录
    /login post
    Content-Type:application/x-www-form-urlencoded
    Request
        username=
        password=
        remember-me=true|false  // 可选
    Response
        "info":"OK"
        "info":"NotLogin"
        "info":"ParameterError"

## json登录
    /updatePassword post
    Request
        {
            "username":String,
            "password":String,
        }
    Response
        "info":"OK"
        "info":"NotLogin"
        "info":"ParameterError"

## 注销
    /logout post
    Response
        "info":"NotLogin"

## 修改密码
    /updatePassword post
    Request
        {
            "password":String,
        }
    Response
        "info":"OK"
        "info":"ParameterError"

## 设置用户头像
    /setUserAvatar post
    Request
        {
            "data":String,              // 图片base64编码
        }
    Response
        "info":"OK"
        "info":"ParameterError"

## 获取用户头像
    /getUserAvatar get
    /getUserAvatar/{id} get
    Response
        图片文件/默认头像

## 获取用户信息
    /getUserInfo get
    /getUserInfo/{id} get
    Response
        "info":"ParameterError"
        "data":{
            "id":String,
            "username":String,
        }