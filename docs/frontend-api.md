# 前端联调接口文档

生成日期：2026-04-23  
扫描范围：`src/main/java/com/aiticket/server/**/controller`、DTO、VO、Entity、Service 调用、全局异常、分页与鉴权配置。

## 1. 接口总览

- 本地服务地址：`http://localhost:8080`
- API 前缀：`/api`
- OpenAPI JSON：`http://localhost:8080/api/v3/api-docs`
- Swagger UI：`http://localhost:8080/api/swagger-ui/index.html`
- 认证方式：登录后传 `Authorization: Bearer <tokenValue>`
- Content-Type：JSON 接口使用 `application/json`

当前扫描到的 Controller：

| 模块 | Controller | 接口数 | 说明 |
| --- | --- | ---: | --- |
| 认证模块 | `AuthController` | 3 | 登录、登出、当前用户 |
| 权限与菜单模块 | `SysMenuController` | 5 | 菜单/路由/按钮权限的平铺数据维护 |
| 用户模块 | `SysUserController` | 5 | 用户分页、CRUD |
| 角色模块 | `SysRoleController` | 5 | 角色分页、CRUD |
| 部门模块 | `SysDeptController` | 5 | 部门平铺列表、CRUD |
| 工单模块 | `TicketOrderController` | 7 | 工单分页、详情、新增、修改、删除、回收站、恢复 |

接口清单：

| 模块 | 方法 | 完整路径 | 接口名称 |
| --- | --- | --- | --- |
| 认证 | POST | `/api/auth/login` | 登录 |
| 认证 | POST | `/api/auth/logout` | 登出 |
| 认证 | GET | `/api/auth/me` | 获取当前用户 |
| 权限与菜单 | GET | `/api/system/menus` | 菜单列表 |
| 权限与菜单 | POST | `/api/system/menus` | 新增菜单 |
| 权限与菜单 | GET | `/api/system/menus/{id}` | 菜单详情 |
| 权限与菜单 | PUT | `/api/system/menus/{id}` | 修改菜单 |
| 权限与菜单 | DELETE | `/api/system/menus/{id}` | 删除菜单 |
| 用户 | GET | `/api/system/users` | 用户分页列表 |
| 用户 | POST | `/api/system/users` | 新增用户 |
| 用户 | GET | `/api/system/users/{id}` | 用户详情 |
| 用户 | PUT | `/api/system/users/{id}` | 修改用户 |
| 用户 | DELETE | `/api/system/users/{id}` | 删除用户 |
| 角色 | GET | `/api/system/roles` | 角色分页列表 |
| 角色 | POST | `/api/system/roles` | 新增角色 |
| 角色 | GET | `/api/system/roles/{id}` | 角色详情 |
| 角色 | PUT | `/api/system/roles/{id}` | 修改角色 |
| 角色 | DELETE | `/api/system/roles/{id}` | 删除角色 |
| 部门 | GET | `/api/system/depts` | 部门列表 |
| 部门 | POST | `/api/system/depts` | 新增部门 |
| 部门 | GET | `/api/system/depts/{id}` | 部门详情 |
| 部门 | PUT | `/api/system/depts/{id}` | 修改部门 |
| 部门 | DELETE | `/api/system/depts/{id}` | 删除部门 |
| 工单 | GET | `/api/tickets` | 工单分页列表 |
| 工单 | POST | `/api/tickets` | 新增工单 |
| 工单 | GET | `/api/tickets/recycle-bin` | 工单回收站 |
| 工单 | GET | `/api/tickets/{id}` | 工单详情 |
| 工单 | PUT | `/api/tickets/{id}` | 修改工单 |
| 工单 | DELETE | `/api/tickets/{id}` | 逻辑删除工单 |
| 工单 | PATCH | `/api/tickets/{id}/restore` | 恢复已删除工单 |

未扫描到 Controller 的模块：

| 模块 | 当前状态 | 前端影响 |
| --- | --- | --- |
| 字典模块 | 未实现 Controller/Service | 字典下拉需前端写死或等待后端补接口 |
| 文件模块 | 仅有 `TicketAttachment` Entity/Mapper，无上传下载接口 | 不能直接上传/下载/预览 |
| 统计模块 | `report` 包为预留，无 Controller | 看板图表暂无后端数据源 |
| 思维导图模块 | 未发现相关包或 Controller | 暂无接口 |
| 工单评论/附件/流转 | 有 Entity/Mapper，暂无 Controller | 工单详情不会返回评论、附件、流转记录 |

## 2. 全局返回结构

所有 Controller 当前都返回 `ApiResponse<T>`：

```json
{
  "code": 200,
  "message": "success",
  "data": {},
  "timestamp": 1713830400000
}
```

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| code | number | 业务状态码。`200` 成功，非 `200` 业务失败 |
| message | string | 成功为 `success`，失败为错误原因 |
| data | any | 业务数据。无返回数据时为 `null` |
| timestamp | number | 服务端响应时间戳，毫秒 |

常见错误：

```json
{
  "code": 401,
  "message": "未登录或登录已过期",
  "data": null,
  "timestamp": 1713830400000
}
```

| 场景 | HTTP 状态 | code | data | 说明 |
| --- | ---: | ---: | --- | --- |
| 参数校验失败 | 400 | 400 | null | message 包含字段名和校验原因 |
| 未登录/token 失效 | 401 | 401 | null | 需要重新登录 |
| 无权限 | 403 | 403 | null | 需要检查角色/权限码 |
| 资源不存在 | 200 | 404 | null | `BusinessException` 当前用 HTTP 200 包装业务错误 |
| 系统异常 | 500 | 500 | null | 后端未处理异常 |

前端统一判断建议：

```ts
if (response.data.code !== 200) {
  throw new Error(response.data.message || '请求失败')
}
return response.data.data
```

## 3. 全局分页结构

分页接口统一返回：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [],
    "total": 0,
    "pageNum": 1,
    "pageSize": 10
  },
  "timestamp": 1713830400000
}
```

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| records | array | 当前页列表。无数据返回 `[]` |
| total | number | 总条数 |
| pageNum | number | 当前页，从 1 开始 |
| pageSize | number | 每页条数，最大 500 |

分页请求统一使用 Query 参数：

| 字段 | 类型 | 必填 | 默认值 | 说明 |
| --- | --- | --- | --- | --- |
| pageNum | number | 否 | 1 | 页码，最小 1 |
| pageSize | number | 否 | 10 | 每页条数，1-500 |

## 4. 字段规范与前端约定

- 字段命名：接口层均为 camelCase，数据库 snake_case 已由 MyBatis-Plus 映射。
- ID 类型：Java 为 `Long`，JSON 当前会输出 number。由于 `IdType.ASSIGN_ID` 可能生成 19 位雪花 ID，前端存在 JS 精度风险。建议后端统一将 Long 序列化为 string，或前端在 axios 层用大整数解析方案。
- 时间格式：全局配置为 `yyyy-MM-dd HH:mm:ss`，时区 `Asia/Shanghai`。请求 body 中的 `LocalDateTime` 也按该格式传。
- 状态字段：用户/角色/部门/菜单为 `ENABLED` / `DISABLED`；工单为 `NEW` / `PROCESSING` / `PENDING` / `RESOLVED` / `CLOSED`；优先级为 `LOW` / `NORMAL` / `HIGH` / `URGENT`。
- Boolean：当前仅菜单 `visible` 是 boolean，传 `true` / `false`，不要传字符串 `"true"`。
- 空值：可选字段可能为 `null`，包括头像、邮箱、手机号、时间字段、AI 摘要、AI 风险等级、工单处理人等。

## 5. 认证模块

DTO/VO/Service：

- DTO：`LoginRequest`
- VO：`LoginVO`
- Service：`AuthService`
- Entity：`SysUser`、`SysLoginLog`

### 5.1 登录

| 项 | 内容 |
| --- | --- |
| 接口名称 | 登录 |
| 接口描述 | 用户名密码登录，返回 Sa-Token token |
| 请求方式 | POST |
| 完整路径 | `/api/auth/login` |
| Service 调用 | `authService.login(request, servletRequest)` |
| 是否需要 token | 否 |

请求头：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| Content-Type | string | 是 | `application/json` |

Body 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| username | string | 是 | 用户名，示例 `admin` |
| password | string | 是 | 密码，示例 `admin123` |

返回 data：

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| userId | number | 用户 ID |
| username | string | 用户名 |
| nickname | string | 昵称 |
| tokenName | string | token 名，当前为 `Authorization` |
| tokenValue | string | token 值 |
| tokenPrefix | string | token 前缀，当前为 `Bearer` |
| roles | string[] | 角色编码 |
| permissions | string[] | 权限编码 |

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "userId": 1,
    "username": "admin",
    "nickname": "系统管理员",
    "tokenName": "Authorization",
    "tokenValue": "xxxx",
    "tokenPrefix": "Bearer",
    "roles": ["admin"],
    "permissions": ["*", "*:*:*"]
  },
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 401,
  "message": "用户名或密码错误",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 登录按钮建议防重复提交。
- 后续请求头拼接为 `Authorization: Bearer ${tokenValue}`。
- 当前 token 不是 JWT，不要尝试前端解析 payload。

### 5.2 登出

| 项 | 内容 |
| --- | --- |
| 接口名称 | 退出登录 |
| 接口描述 | 注销当前 token |
| 请求方式 | POST |
| 完整路径 | `/api/auth/logout` |
| Service 调用 | `authService.logout()` |
| 是否需要 token | 是 |

请求头：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| Authorization | string | 是 | `Bearer <tokenValue>` |

Body 参数：无。

返回 data：`null`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": null,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 401,
  "message": "未登录或登录已过期",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 无论接口是否成功，前端通常都应清理本地 token 并跳转登录页。
- 不分页，不需要 body。

### 5.3 获取当前用户

| 项 | 内容 |
| --- | --- |
| 接口名称 | 当前登录用户 |
| 接口描述 | 获取当前用户、角色和权限 |
| 请求方式 | GET |
| 完整路径 | `/api/auth/me` |
| Service 调用 | `authService.currentUser()` |
| 是否需要 token | 是 |

请求头：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| Authorization | string | 是 | `Bearer <tokenValue>` |

Query/Path/Body 参数：无。

返回 data：同登录接口 `LoginVO`。

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "userId": 1,
    "username": "admin",
    "nickname": "系统管理员",
    "tokenName": "Authorization",
    "tokenValue": "xxxx",
    "tokenPrefix": "Bearer",
    "roles": ["admin"],
    "permissions": ["*", "*:*:*"]
  },
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 401,
  "message": "未登录或登录已过期",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 刷新页面后可用此接口恢复用户信息和按钮权限。
- `permissions` 可能为空数组，按钮权限判断要兼容。
- 当前没有刷新 token、修改密码接口。

## 6. 权限与菜单模块

DTO/VO/Service：

- DTO：`MenuQueryRequest`、`MenuCreateRequest`、`MenuUpdateRequest`
- VO：`MenuVO`
- Entity：`SysMenu`
- Service：`SysMenuService`

权限说明：

- 登录用户 ID 为 1 时，后端返回 `["*", "*:*:*"]` 和角色 `admin`。
- 菜单接口需要对应权限码，例如 `system:menu:list`。
- 当前没有独立“路由树/按钮权限”接口。前端可使用 `/api/system/menus` 的平铺列表按 `parentId` 组装树，或直接使用 `/api/auth/me` 的 `permissions` 做按钮权限。

### 6.1 菜单列表

| 项 | 内容 |
| --- | --- |
| 接口名称 | 菜单列表 |
| 接口描述 | 查询目录、菜单、按钮权限平铺列表 |
| 请求方式 | GET |
| 完整路径 | `/api/system/menus` |
| Service 调用 | `menuService.listMenus(request)` |
| 是否需要 token | 是，权限 `system:menu:list` |

请求头：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| Authorization | string | 是 | `Bearer <tokenValue>` |

Query 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| menuName | string | 否 | 菜单名称，模糊匹配 |
| menuType | string | 否 | `DIR` / `MENU` / `BUTTON` |
| status | string | 否 | `ENABLED` / `DISABLED` |

Path/Body 参数：无。

返回 data：`MenuVO[]`

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| id | number | 菜单 ID |
| parentId | number | 父级 ID，根节点为 0 |
| menuName | string | 菜单名称 |
| menuType | string | `DIR` / `MENU` / `BUTTON` |
| path | string \| null | 路由地址 |
| component | string \| null | 前端组件路径 |
| perms | string \| null | 权限标识 |
| icon | string \| null | 图标 |
| sortOrder | number | 排序 |
| visible | boolean | 是否显示 |
| status | string | 状态 |
| createTime/updateTime | string | 时间 |

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 110,
      "parentId": 100,
      "menuName": "用户管理",
      "menuType": "MENU",
      "path": "/system/users",
      "component": "system/user/index",
      "perms": "system:user:list",
      "icon": "user",
      "sortOrder": 1,
      "visible": true,
      "status": "ENABLED",
      "createTime": "2026-04-23 09:00:00",
      "updateTime": "2026-04-23 09:00:00"
    }
  ],
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 403,
  "message": "没有访问权限",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 非分页，空数据返回 `[]`。
- 返回是平铺结构，不是树；前端需要按 `parentId` 构建树。
- `path/component/perms/icon` 对 `DIR` 或 `BUTTON` 可能为空。

### 6.2 新增菜单

| 项 | 内容 |
| --- | --- |
| 接口名称 | 新增菜单 |
| 请求方式 | POST |
| 完整路径 | `/api/system/menus` |
| Service 调用 | `menuService.createMenu(request)` |
| 是否需要 token | 是，权限 `system:menu:add` |

请求头：`Authorization`、`Content-Type: application/json`

Body 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| parentId | number | 否 | 父级菜单 ID，空时默认 0 |
| menuName | string | 是 | 菜单名称 |
| menuType | string | 是 | `DIR` / `MENU` / `BUTTON` |
| path | string | 否 | 路由地址 |
| component | string | 否 | 组件路径 |
| perms | string | 否 | 权限标识 |
| icon | string | 否 | 图标 |
| sortOrder | number | 否 | 排序 |
| visible | boolean | 否 | 空时默认 true |
| status | string | 否 | 空时默认 `ENABLED` |

返回 data：新菜单 ID，number。

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": 10001,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 400,
  "message": "menuName: 菜单名称不能为空",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- `visible` 必须传 boolean，不要传字符串。
- 新增后需要刷新菜单列表或本地插入节点。

### 6.3 菜单详情

| 项 | 内容 |
| --- | --- |
| 接口名称 | 菜单详情 |
| 请求方式 | GET |
| 完整路径 | `/api/system/menus/{id}` |
| Service 调用 | `menuService.getMenu(id)` |
| 是否需要 token | 是，权限 `system:menu:query` |

Path 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| id | number | 是 | 菜单 ID |

返回 data：`MenuVO`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 110,
    "parentId": 100,
    "menuName": "用户管理",
    "menuType": "MENU",
    "path": "/system/users",
    "component": "system/user/index",
    "perms": "system:user:list",
    "icon": "user",
    "sortOrder": 1,
    "visible": true,
    "status": "ENABLED",
    "createTime": "2026-04-23 09:00:00",
    "updateTime": "2026-04-23 09:00:00"
  },
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 详情不存在时 HTTP 可能是 200，但 `code` 是 404。
- ID 建议在前端按 string 存储，避免大整数精度风险。

### 6.4 修改菜单

| 项 | 内容 |
| --- | --- |
| 接口名称 | 修改菜单 |
| 请求方式 | PUT |
| 完整路径 | `/api/system/menus/{id}` |
| Service 调用 | `menuService.updateMenu(id, request)` |
| 是否需要 token | 是，权限 `system:menu:edit` |

Path 参数：`id` number，必填。  
Body 参数：同新增菜单，`menuName`、`menuType` 必填。

返回 data：`null`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": null,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 当前是全量更新语义，表单需带上不想清空的字段。
- 修改菜单后需要刷新路由/权限缓存。

### 6.5 删除菜单

| 项 | 内容 |
| --- | --- |
| 接口名称 | 删除菜单 |
| 请求方式 | DELETE |
| 完整路径 | `/api/system/menus/{id}` |
| Service 调用 | `menuService.deleteMenu(id)` |
| 是否需要 token | 是，权限 `system:menu:delete` |

Path 参数：`id` number，必填。  
返回 data：`null`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": null,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 逻辑删除。
- 当前未校验是否存在子菜单，前端删除父节点前建议二次确认。

## 7. 用户模块

DTO/VO/Service：

- DTO：`UserQueryRequest`、`UserCreateRequest`、`UserUpdateRequest`
- VO：`UserVO`
- Entity：`SysUser`
- Service：`SysUserService`

### 7.1 用户分页列表

| 项 | 内容 |
| --- | --- |
| 接口名称 | 用户分页列表 |
| 请求方式 | GET |
| 完整路径 | `/api/system/users` |
| Service 调用 | `userService.pageUsers(request)` |
| 是否需要 token | 是，权限 `system:user:list` |

Query 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| pageNum | number | 否 | 默认 1 |
| pageSize | number | 否 | 默认 10，最大 500 |
| username | string | 否 | 用户名模糊匹配 |
| nickname | string | 否 | 昵称模糊匹配 |
| deptId | number | 否 | 部门 ID |
| status | string | 否 | `ENABLED` / `DISABLED` |

返回 data：`PageResult<UserVO>`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "username": "admin",
        "nickname": "系统管理员",
        "email": "admin@example.com",
        "mobile": null,
        "avatar": null,
        "deptId": 1,
        "status": "ENABLED",
        "lastLoginTime": "2026-04-23 09:00:00",
        "createTime": "2026-04-23 09:00:00",
        "updateTime": "2026-04-23 09:00:00"
      }
    ],
    "total": 1,
    "pageNum": 1,
    "pageSize": 10
  },
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 403,
  "message": "没有访问权限",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 分页接口，表格使用 `records` 和 `total`。
- 搜索框建议防抖。
- `mobile/avatar/lastLoginTime` 可能为空。

### 7.2 新增用户

| 项 | 内容 |
| --- | --- |
| 接口名称 | 新增用户 |
| 请求方式 | POST |
| 完整路径 | `/api/system/users` |
| Service 调用 | `userService.createUser(request)` |
| 是否需要 token | 是，权限 `system:user:add` |

Body 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| username | string | 是 | 用户名，唯一 |
| password | string | 是 | 6-64 位 |
| nickname | string | 是 | 昵称 |
| email | string | 否 | 邮箱 |
| mobile | string | 否 | 手机号 |
| avatar | string | 否 | 头像 URL |
| deptId | number | 否 | 部门 ID |
| status | string | 否 | 默认 `ENABLED` |

返回 data：新用户 ID，number。

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": 10001,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 400,
  "message": "用户名已存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- `username` 重复会失败；当前返回 code 400，建议后端进一步细化为 409。
- 密码只在新增时设置，当前没有改密接口。

### 7.3 用户详情

| 项 | 内容 |
| --- | --- |
| 接口名称 | 用户详情 |
| 请求方式 | GET |
| 完整路径 | `/api/system/users/{id}` |
| Service 调用 | `userService.getUser(id)` |
| 是否需要 token | 是，权限 `system:user:query` |

Path 参数：`id` number，必填。  
返回 data：`UserVO`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "admin",
    "nickname": "系统管理员",
    "email": "admin@example.com",
    "mobile": null,
    "avatar": null,
    "deptId": 1,
    "status": "ENABLED",
    "lastLoginTime": "2026-04-23 09:00:00",
    "createTime": "2026-04-23 09:00:00",
    "updateTime": "2026-04-23 09:00:00"
  },
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 不返回密码。
- 不存在时看 `code`，不要只看 HTTP 状态。

### 7.4 修改用户

| 项 | 内容 |
| --- | --- |
| 接口名称 | 修改用户 |
| 请求方式 | PUT |
| 完整路径 | `/api/system/users/{id}` |
| Service 调用 | `userService.updateUser(id, request)` |
| 是否需要 token | 是，权限 `system:user:edit` |

Path 参数：`id` number，必填。

Body 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| nickname | string | 是 | 昵称 |
| email | string | 否 | 邮箱 |
| mobile | string | 否 | 手机号 |
| avatar | string | 否 | 头像 URL |
| deptId | number | 否 | 部门 ID |
| status | string | 否 | `ENABLED` / `DISABLED` |

返回 data：`null`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": null,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 400,
  "message": "nickname: 昵称不能为空",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 当前接口不能修改 `username/password`。
- 表单提交前清理不需要的空字符串，避免覆盖为 `""`。

### 7.5 删除用户

| 项 | 内容 |
| --- | --- |
| 接口名称 | 删除用户 |
| 请求方式 | DELETE |
| 完整路径 | `/api/system/users/{id}` |
| Service 调用 | `userService.deleteUser(id)` |
| 是否需要 token | 是，权限 `system:user:delete` |

Path 参数：`id` number，必填。  
返回 data：`null`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": null,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 逻辑删除。
- 当前未限制删除当前登录用户，前端建议隐藏或二次确认管理员账号删除操作。

## 8. 角色模块

DTO/VO/Service：

- DTO：`RoleQueryRequest`、`RoleCreateRequest`、`RoleUpdateRequest`
- VO：`RoleVO`
- Entity：`SysRole`、`SysUserRole`
- Service：`SysRoleService`

### 8.1 角色分页列表

| 项 | 内容 |
| --- | --- |
| 接口名称 | 角色分页列表 |
| 请求方式 | GET |
| 完整路径 | `/api/system/roles` |
| Service 调用 | `roleService.pageRoles(request)` |
| 是否需要 token | 是，权限 `system:role:list` |

Query 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| pageNum | number | 否 | 默认 1 |
| pageSize | number | 否 | 默认 10，最大 500 |
| roleName | string | 否 | 角色名称模糊匹配 |
| roleCode | string | 否 | 角色编码模糊匹配 |
| status | string | 否 | `ENABLED` / `DISABLED` |

返回 data：`PageResult<RoleVO>`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "roleName": "超级管理员",
        "roleCode": "admin",
        "sortOrder": 0,
        "status": "ENABLED",
        "remark": "系统内置管理员角色",
        "createTime": "2026-04-23 09:00:00",
        "updateTime": "2026-04-23 09:00:00"
      }
    ],
    "total": 1,
    "pageNum": 1,
    "pageSize": 10
  },
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 403,
  "message": "没有访问权限",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 分页接口。
- 搜索框建议防抖。

### 8.2 新增角色

| 项 | 内容 |
| --- | --- |
| 接口名称 | 新增角色 |
| 请求方式 | POST |
| 完整路径 | `/api/system/roles` |
| Service 调用 | `roleService.createRole(request)` |
| 是否需要 token | 是，权限 `system:role:add` |

Body 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| roleName | string | 是 | 角色名称 |
| roleCode | string | 是 | 角色编码，唯一 |
| sortOrder | number | 否 | 排序 |
| status | string | 否 | 默认 `ENABLED` |
| remark | string | 否 | 备注 |

返回 data：新角色 ID，number。

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": 10001,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 400,
  "message": "角色编码已存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 当前未实现角色授权菜单接口，仅能维护角色基础信息。
- 重复编码当前返回 code 400，建议后端进一步细化为 409。

### 8.3 角色详情

| 项 | 内容 |
| --- | --- |
| 接口名称 | 角色详情 |
| 请求方式 | GET |
| 完整路径 | `/api/system/roles/{id}` |
| Service 调用 | `roleService.getRole(id)` |
| 是否需要 token | 是，权限 `system:role:query` |

Path 参数：`id` number，必填。  
返回 data：`RoleVO`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "roleName": "超级管理员",
    "roleCode": "admin",
    "sortOrder": 0,
    "status": "ENABLED",
    "remark": "系统内置管理员角色",
    "createTime": "2026-04-23 09:00:00",
    "updateTime": "2026-04-23 09:00:00"
  },
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 详情不包含已绑定用户和菜单权限。

### 8.4 修改角色

| 项 | 内容 |
| --- | --- |
| 接口名称 | 修改角色 |
| 请求方式 | PUT |
| 完整路径 | `/api/system/roles/{id}` |
| Service 调用 | `roleService.updateRole(id, request)` |
| 是否需要 token | 是，权限 `system:role:edit` |

Path 参数：`id` number，必填。  
Body 参数：同新增角色，`roleName`、`roleCode` 必填。  
返回 data：`null`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": null,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 当前没有检查修改后的 `roleCode` 是否和其他角色重复，建议后端补唯一性校验。

### 8.5 删除角色

| 项 | 内容 |
| --- | --- |
| 接口名称 | 删除角色 |
| 请求方式 | DELETE |
| 完整路径 | `/api/system/roles/{id}` |
| Service 调用 | `roleService.deleteRole(id)` |
| 是否需要 token | 是，权限 `system:role:delete` |

Path 参数：`id` number，必填。  
返回 data：`null`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": null,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 逻辑删除。
- 当前未检查角色是否已分配给用户，删除前建议二次确认。

## 9. 部门模块

DTO/VO/Service：

- DTO：`DeptQueryRequest`、`DeptCreateRequest`、`DeptUpdateRequest`
- VO：`DeptVO`
- Entity：`SysDept`
- Service：`SysDeptService`

### 9.1 部门列表

| 项 | 内容 |
| --- | --- |
| 接口名称 | 部门列表 |
| 请求方式 | GET |
| 完整路径 | `/api/system/depts` |
| Service 调用 | `deptService.listDepts(request)` |
| 是否需要 token | 是，权限 `system:dept:list` |

Query 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| deptName | string | 否 | 部门名称模糊匹配 |
| deptCode | string | 否 | 部门编码模糊匹配 |
| status | string | 否 | `ENABLED` / `DISABLED` |

返回 data：`DeptVO[]`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "parentId": 0,
      "deptName": "总部",
      "deptCode": "HQ",
      "leader": "admin",
      "phone": null,
      "email": null,
      "sortOrder": 0,
      "status": "ENABLED",
      "createTime": "2026-04-23 09:00:00",
      "updateTime": "2026-04-23 09:00:00"
    }
  ],
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 403,
  "message": "没有访问权限",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 非分页，空数据返回 `[]`。
- 返回平铺列表，部门树需前端按 `parentId` 组装。

### 9.2 新增部门

| 项 | 内容 |
| --- | --- |
| 接口名称 | 新增部门 |
| 请求方式 | POST |
| 完整路径 | `/api/system/depts` |
| Service 调用 | `deptService.createDept(request)` |
| 是否需要 token | 是，权限 `system:dept:add` |

Body 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| parentId | number | 否 | 父级部门 ID，空时默认 0 |
| deptName | string | 是 | 部门名称 |
| deptCode | string | 是 | 部门编码，唯一 |
| leader | string | 否 | 负责人 |
| phone | string | 否 | 联系电话 |
| email | string | 否 | 邮箱 |
| sortOrder | number | 否 | 排序 |
| status | string | 否 | 默认 `ENABLED` |

返回 data：新部门 ID，number。

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": 10001,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 400,
  "message": "部门编码已存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 重复编码当前返回 code 400，建议后端进一步细化为 409。
- 新增后刷新部门列表并重建树。

### 9.3 部门详情

| 项 | 内容 |
| --- | --- |
| 接口名称 | 部门详情 |
| 请求方式 | GET |
| 完整路径 | `/api/system/depts/{id}` |
| Service 调用 | `deptService.getDept(id)` |
| 是否需要 token | 是，权限 `system:dept:query` |

Path 参数：`id` number，必填。  
返回 data：`DeptVO`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "parentId": 0,
    "deptName": "总部",
    "deptCode": "HQ",
    "leader": "admin",
    "phone": null,
    "email": null,
    "sortOrder": 0,
    "status": "ENABLED",
    "createTime": "2026-04-23 09:00:00",
    "updateTime": "2026-04-23 09:00:00"
  },
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- `phone/email` 可为空。

### 9.4 修改部门

| 项 | 内容 |
| --- | --- |
| 接口名称 | 修改部门 |
| 请求方式 | PUT |
| 完整路径 | `/api/system/depts/{id}` |
| Service 调用 | `deptService.updateDept(id, request)` |
| 是否需要 token | 是，权限 `system:dept:edit` |

Path 参数：`id` number，必填。  
Body 参数：同新增部门，`deptName`、`deptCode` 必填。  
返回 data：`null`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": null,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 400,
  "message": "deptName: 部门名称不能为空",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 当前未校验父级是否形成循环。
- 修改后需要刷新部门树。

### 9.5 删除部门

| 项 | 内容 |
| --- | --- |
| 接口名称 | 删除部门 |
| 请求方式 | DELETE |
| 完整路径 | `/api/system/depts/{id}` |
| Service 调用 | `deptService.deleteDept(id)` |
| 是否需要 token | 是，权限 `system:dept:delete` |

Path 参数：`id` number，必填。  
返回 data：`null`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": null,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 逻辑删除。
- 当前未检查子部门或用户引用，前端删除前建议二次确认。

## 10. 工单模块

DTO/VO/Service：

- DTO：`TicketQueryRequest`、`TicketCreateRequest`、`TicketUpdateRequest`
- VO：`TicketOrderVO`
- Entity：`TicketOrder`、`TicketFlowRecord`、`TicketComment`、`TicketAttachment`
- Service：`TicketOrderService`

### 10.1 工单分页列表

| 项 | 内容 |
| --- | --- |
| 接口名称 | 工单分页列表 |
| 请求方式 | GET |
| 完整路径 | `/api/tickets` |
| Service 调用 | `ticketOrderService.pageTickets(request)` |
| 是否需要 token | 是，权限 `ticket:order:list` |

Query 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| pageNum | number | 否 | 默认 1 |
| pageSize | number | 否 | 默认 10，最大 500 |
| keyword | string | 否 | 匹配标题或工单编号 |
| status | string | 否 | `NEW` / `PROCESSING` / `PENDING` / `RESOLVED` / `CLOSED` |
| priority | string | 否 | `LOW` / `NORMAL` / `HIGH` / `URGENT` |
| category | string | 否 | 分类 |
| assigneeId | number | 否 | 处理人 ID |
| applicantId | number | 否 | 申请人 ID |

返回 data：`PageResult<TicketOrderVO>`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 10001,
        "ticketNo": "TK202604230900001234",
        "title": "无法登录系统",
        "description": "用户反馈输入正确密码后仍提示登录失败",
        "priority": "NORMAL",
        "status": "NEW",
        "source": "WEB",
        "category": "IT",
        "applicantId": 1,
        "applicantName": "系统管理员",
        "assigneeId": 1,
        "assigneeName": "系统管理员",
        "dueTime": "2026-04-30 18:00:00",
        "resolvedTime": null,
        "closedTime": null,
        "aiSummary": null,
        "aiRiskLevel": null,
        "createTime": "2026-04-23 09:00:00",
        "updateTime": "2026-04-23 09:00:00"
      }
    ],
    "total": 1,
    "pageNum": 1,
    "pageSize": 10
  },
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 403,
  "message": "没有访问权限",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 分页接口。
- 搜索 `keyword` 建议防抖。
- `resolvedTime/closedTime/aiSummary/aiRiskLevel` 常为空。

### 10.2 新增工单

| 项 | 内容 |
| --- | --- |
| 接口名称 | 新增工单 |
| 请求方式 | POST |
| 完整路径 | `/api/tickets` |
| Service 调用 | `ticketOrderService.createTicket(request)` |
| 是否需要 token | 是，权限 `ticket:order:add` |

Body 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| title | string | 是 | 工单标题 |
| description | string | 是 | 工单描述 |
| priority | string | 否 | 默认 `NORMAL` |
| source | string | 否 | 来源 |
| category | string | 否 | 分类 |
| applicantId | number | 否 | 申请人 ID |
| applicantName | string | 否 | 申请人姓名 |
| assigneeId | number | 否 | 处理人 ID |
| assigneeName | string | 否 | 处理人姓名 |
| dueTime | string | 否 | `yyyy-MM-dd HH:mm:ss` |

返回 data：新工单 ID，number。`ticketNo/status` 服务端生成或默认。

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": 10001,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 400,
  "message": "title: 工单标题不能为空",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 时间字段按字符串传：`2026-04-30 18:00:00`。
- `priority` 不传默认为 `NORMAL`，`status` 默认为 `NEW`。
- 当前不支持附件随工单一起上传。

### 10.3 工单回收站

| 项 | 内容 |
| --- | --- |
| 接口名称 | 工单回收站 |
| 请求方式 | GET |
| 完整路径 | `/api/tickets/recycle-bin` |
| Service 调用 | `ticketOrderService.pageRecycleTickets(request)` |
| 是否需要 token | 是，权限 `ticket:order:recycle` |

Query 参数：同工单分页列表。  
返回 data：`PageResult<TicketOrderVO>`。

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [],
    "total": 0,
    "pageNum": 1,
    "pageSize": 10
  },
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 401,
  "message": "未登录或登录已过期",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 仅查询 `deleted = 1` 的工单。
- 恢复后需要刷新回收站和普通工单列表。

### 10.4 工单详情

| 项 | 内容 |
| --- | --- |
| 接口名称 | 工单详情 |
| 请求方式 | GET |
| 完整路径 | `/api/tickets/{id}` |
| Service 调用 | `ticketOrderService.getTicket(id)` |
| 是否需要 token | 是，权限 `ticket:order:query` |

Path 参数：`id` number，必填。  
返回 data：`TicketOrderVO`。

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 10001,
    "ticketNo": "TK202604230900001234",
    "title": "无法登录系统",
    "description": "用户反馈输入正确密码后仍提示登录失败",
    "priority": "NORMAL",
    "status": "NEW",
    "source": "WEB",
    "category": "IT",
    "applicantId": 1,
    "applicantName": "系统管理员",
    "assigneeId": 1,
    "assigneeName": "系统管理员",
    "dueTime": "2026-04-30 18:00:00",
    "resolvedTime": null,
    "closedTime": null,
    "aiSummary": null,
    "aiRiskLevel": null,
    "createTime": "2026-04-23 09:00:00",
    "updateTime": "2026-04-23 09:00:00"
  },
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 当前详情不包含评论、附件、流转记录，需要后端补接口。
- 已删除工单详情不会通过此接口返回。

### 10.5 修改工单

| 项 | 内容 |
| --- | --- |
| 接口名称 | 修改工单 |
| 请求方式 | PUT |
| 完整路径 | `/api/tickets/{id}` |
| Service 调用 | `ticketOrderService.updateTicket(id, request)` |
| 是否需要 token | 是，权限 `ticket:order:edit` |

Path 参数：`id` number，必填。

Body 参数：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| title | string | 是 | 工单标题 |
| description | string | 是 | 工单描述 |
| priority | string | 否 | `LOW` / `NORMAL` / `HIGH` / `URGENT` |
| status | string | 否 | 工单状态 |
| source | string | 否 | 来源 |
| category | string | 否 | 分类 |
| assigneeId | number | 否 | 处理人 ID |
| assigneeName | string | 否 | 处理人姓名 |
| dueTime | string | 否 | `yyyy-MM-dd HH:mm:ss` |
| resolvedTime | string | 否 | `yyyy-MM-dd HH:mm:ss` |
| closedTime | string | 否 | `yyyy-MM-dd HH:mm:ss` |
| aiSummary | string | 否 | AI 摘要 |
| aiRiskLevel | string | 否 | AI 风险等级 |

返回 data：`null`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": null,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 400,
  "message": "description: 工单描述不能为空",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 当前状态操作没有专用接口，`status` 通过修改接口更新；建议后端后续拆分状态流转接口。
- 表单需保留标题和描述，否则校验失败。

### 10.6 逻辑删除工单

| 项 | 内容 |
| --- | --- |
| 接口名称 | 逻辑删除工单 |
| 请求方式 | DELETE |
| 完整路径 | `/api/tickets/{id}` |
| Service 调用 | `ticketOrderService.deleteTicket(id)` |
| 是否需要 token | 是，权限 `ticket:order:delete` |

Path 参数：`id` number，必填。  
返回 data：`null`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": null,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 删除后进入回收站。
- 删除失败通常是 ID 不存在或已删除。

### 10.7 恢复已删除工单

| 项 | 内容 |
| --- | --- |
| 接口名称 | 恢复已删除工单 |
| 请求方式 | PATCH |
| 完整路径 | `/api/tickets/{id}/restore` |
| Service 调用 | `ticketOrderService.restoreTicket(id)` |
| 是否需要 token | 是，权限 `ticket:order:restore` |

Path 参数：`id` number，必填。  
返回 data：`null`

成功示例：

```json
{
  "code": 200,
  "message": "success",
  "data": null,
  "timestamp": 1713830400000
}
```

失败示例：

```json
{
  "code": 404,
  "message": "资源不存在",
  "data": null,
  "timestamp": 1713830400000
}
```

前端调用注意事项：

- 仅能恢复已逻辑删除的数据。
- 恢复失败一般表示工单不存在或不在回收站。

## 11. 前端 API 封装建议

建议目录：

```text
src/api/request.ts
src/api/auth.ts
src/api/user.ts
src/api/role.ts
src/api/dept.ts
src/api/menu.ts
src/api/workOrder.ts
src/api/file.ts
```

`src/api/request.ts`：

```ts
import axios from 'axios'

export const request = axios.create({
  baseURL: '/api',
  timeout: 15000
})

request.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

request.interceptors.response.use((response) => {
  const body = response.data
  if (body?.code !== 200) {
    if (body?.code === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    }
    return Promise.reject(body)
  }
  return body.data
})
```

`src/api/auth.ts`：

```ts
import { request } from './request'

export function loginApi(data: { username: string; password: string }) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function logoutApi() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

export function getCurrentUserApi() {
  return request({
    url: '/auth/me',
    method: 'get'
  })
}
```

`src/api/user.ts`：

```ts
import { request } from './request'

export function getUserListApi(params: any) {
  return request({ url: '/system/users', method: 'get', params })
}

export function createUserApi(data: any) {
  return request({ url: '/system/users', method: 'post', data })
}

export function getUserDetailApi(id: string | number) {
  return request({ url: `/system/users/${id}`, method: 'get' })
}

export function updateUserApi(id: string | number, data: any) {
  return request({ url: `/system/users/${id}`, method: 'put', data })
}

export function deleteUserApi(id: string | number) {
  return request({ url: `/system/users/${id}`, method: 'delete' })
}
```

`src/api/workOrder.ts`：

```ts
import { request } from './request'

export function getWorkOrderListApi(params: any) {
  return request({ url: '/tickets', method: 'get', params })
}

export function createWorkOrderApi(data: any) {
  return request({ url: '/tickets', method: 'post', data })
}

export function getWorkOrderDetailApi(id: string | number) {
  return request({ url: `/tickets/${id}`, method: 'get' })
}

export function updateWorkOrderApi(id: string | number, data: any) {
  return request({ url: `/tickets/${id}`, method: 'put', data })
}

export function deleteWorkOrderApi(id: string | number) {
  return request({ url: `/tickets/${id}`, method: 'delete' })
}

export function getRecycleWorkOrderListApi(params: any) {
  return request({ url: '/tickets/recycle-bin', method: 'get', params })
}

export function restoreWorkOrderApi(id: string | number) {
  return request({ url: `/tickets/${id}/restore`, method: 'patch' })
}
```

角色、部门、菜单建议按同样规则命名：

| 文件 | 方法 |
| --- | --- |
| `src/api/role.ts` | `getRoleListApi`、`createRoleApi`、`getRoleDetailApi`、`updateRoleApi`、`deleteRoleApi` |
| `src/api/dept.ts` | `getDeptListApi`、`createDeptApi`、`getDeptDetailApi`、`updateDeptApi`、`deleteDeptApi` |
| `src/api/menu.ts` | `getMenuListApi`、`createMenuApi`、`getMenuDetailApi`、`updateMenuApi`、`deleteMenuApi` |

文件上传示例，当前后端未提供文件接口，以下为后续新增 `/files/upload` 后的建议封装：

```ts
export function uploadFileApi(file: File) {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/files/upload',
    method: 'post',
    data: formData,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}
```

## 12. 测试数据

内置账号：

| 用户名 | 密码 | 用户 ID | 角色 | 说明 |
| --- | --- | ---: | --- | --- |
| admin | admin123 | 1 | admin | 超级管理员，拥有全部权限 |

内置数据 ID：

| 类型 | ID | 说明 |
| --- | ---: | --- |
| 部门 | 1 | 总部 |
| 角色 | 1 | 超级管理员 |
| 用户 | 1 | admin |
| 菜单 | 100 | 系统管理 |
| 菜单 | 110 | 用户管理 |
| 菜单 | 200 | 工单管理 |
| 菜单 | 210 | 工单列表 |

示例分页参数：

```json
{
  "pageNum": 1,
  "pageSize": 10
}
```

示例新增工单 body：

```json
{
  "title": "无法登录系统",
  "description": "用户反馈输入正确密码后仍提示登录失败",
  "priority": "HIGH",
  "source": "WEB",
  "category": "IT",
  "applicantId": 1,
  "applicantName": "系统管理员",
  "assigneeId": 1,
  "assigneeName": "系统管理员",
  "dueTime": "2026-04-30 18:00:00"
}
```

示例新增用户 body：

```json
{
  "username": "zhangsan",
  "password": "admin123",
  "nickname": "张三",
  "email": "zhangsan@example.com",
  "mobile": "13800000000",
  "deptId": 1,
  "status": "ENABLED"
}
```

## 13. 跨域与代理

当前后端 `SaTokenConfig` 已配置 CORS：

- `allowedOriginPatterns("*")`
- `allowedMethods("*")`
- `allowedHeaders("*")`
- `allowCredentials(true)`

本地前端代理建议：

```ts
// vite.config.ts
export default {
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
}
```

联调注意：

- 使用 token header，不依赖 cookie。
- 生产环境建议把 CORS Origin 收窄到具体域名。
- nginx 转发时保留 `Authorization` 请求头。

## 14. 前端联调注意事项

1. 返回结构统一，但实际多了 `timestamp`。前端类型定义应包含该字段或忽略未知字段。
2. `BusinessException` 当前 HTTP 状态多数为 200，但 `code` 可能是 404/500；前端必须以业务 `code` 判断成功失败。
3. 分页结构已统一为 `records/total/pageNum/pageSize`，未发现 `list/count` 混用。
4. 用户、角色、部门新增的重复编码/用户名错误当前返回 `code=400`，建议进一步细化为 409，方便前端做冲突提示。
5. ID 当前以 number 输出，雪花 ID 存在 JS 精度风险。
6. 菜单和部门返回平铺列表，不是树。
7. 工单详情当前不含附件、评论、流转记录。
8. 文件、字典、统计、思维导图接口未实现。
9. 工单状态更新共用 `PUT /tickets/{id}`，没有独立状态流转接口，前端要避免误覆盖其他字段。
10. `visible` 是 boolean，状态字段是 string，不要混用 `"true"`、`1`、`0`。

## 15. 规范检查结果

返回结构：

- 已统一使用 `{ code, message, data, timestamp }`。
- 未发现 `{ success, msg, result }`。
- 建议统一文档和前端类型时保留 `timestamp`，或后端如不需要可去掉。

分页结构：

- 已统一使用 `PageResult<T>`：`records`、`total`、`pageNum`、`pageSize`。
- 未发现 `list/count` 或其他分页结构。

字段规范：

- Java/JSON 层为 camelCase。
- ID 为 Long number，建议转 string。
- 时间格式建议强制文档化为 `yyyy-MM-dd HH:mm:ss`。
- 状态字段建议集中为 enum 常量并在 Swagger 中展示。

空值规范：

| 场景 | 当前返回 |
| --- | --- |
| 空列表 | `data: []` 或 `data.records: []` |
| 详情不存在 | `code: 404, message: "资源不存在", data: null` |
| 删除失败 | `code: 404, message: "资源不存在", data: null` |
| 未登录 | HTTP 401，`code: 401` |
| 无权限 | HTTP 403，`code: 403` |
| 修改/删除成功 | `data: null` |

## 16. 后端优化建议

优先级 P0：

- 为文件上传/下载、字典、统计、工单附件/评论/流转补 Controller，否则前端对应页面无法联调。
- 明确 Long ID 序列化策略，建议统一输出 string。
- 重复数据建议使用 409，便于前端区分普通参数错误和唯一键冲突。

优先级 P1：

- 增加部门树、菜单树、前端路由树、按钮权限专用接口，减少前端重复组装。
- 工单状态流转拆成专用接口，例如 `PATCH /api/tickets/{id}/status`，并写入 `ticket_flow_record`。
- 修改接口补唯一性校验，例如角色编码、部门编码。
- 删除前校验引用关系，例如角色是否绑定用户、部门是否有子部门/用户。

优先级 P2：

- 为分页和响应包装增加 OpenAPI 泛型示例或统一响应注解。
- 为状态/优先级建立 enum，减少字符串拼写错误。
- 为 Swagger 增加按模块分组和统一错误响应示例。
