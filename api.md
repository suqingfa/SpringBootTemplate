#说明
    前端与后台使用Cookie保持会话，因此前端请求的时候需要携带相应Cookie, 请求响应时需要保存Cookie。
    前端需要自动处理301重定向，否则可能得不到正确响应。
    如无特殊说明，请求为参数格式json，请求时需要设置Content-Type:application/json;charset=UTF-8
        响应格式为json
        请求成功时，响应格式如下，在文档中简写为"code":0 或 "data":{...}
            响应不包含数据时
            {
                 "code":0,
                 "codeInfo":"OK"
            }
            响应包含数据时
            {
                 "code":0,
                 "codeInfo":"OK",
                 "data":{...}
            }
        请求失败时，响应格式如下，在文档中简写为"code":xx
            {
                "code":xxx,
                "codeInfo":"xxx"
            }

# code与codeInfo的对应关系如下
        OK(0),                  // 请求成功
        NotLogin(100),          // 未登录
        UsernameExist(101),     // 用户名已被注册
        Error(200),             // 请求失败
        ParameterError(201),    // 请求参数不正确

# 全局状态码
    未登录状态请求
        Response
            "code":100
    其它错误        
        Response
            "code":200

# 账号
    公共url /api/account

## 注册
    /register post
    Request
        {
            "username":String,      //
            "password":String       //
        }
    Response
        成功
        "code":0
        用户名已存在
        "code":101
        参数错误
        "code":201

## 登录
    /login post
    Content-Type:application/x-www-form-urlencoded
    Request Body   username=**&password=**
    Response
        成功
        "code":0
        登录失败
        "code":100
        参数错误
        "code":201

## 修改密码
    /updatePassword post
    Request
        {
            "password":String       //
        }
    Response
        成功
        "code":0
        参数错误
        "code":201

## 设置用户头像
    /setUserAvatar post
    Request
            file
    Response
        成功时
        "code":0
        参数错误
        "code":203

## 获取用户头像
    /getUserAvatar get
    /getUserAvatar/{id} get
    Response
        图片文件/默认头像

## 获取用户信息
    /getUserInfo get
    /getUserInfo/{id} get
    Response
        参数错误
        "code":201
        成功时
        "data":{
            "id":String,            //
            "username":String,      //
        }