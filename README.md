# Bami WMS + BMS 完整系统

> 八米仓储管理系统 + 计费管理系统完整源码与数据库  
> 版本: v2.0 (2026-04-14更新)  
> 来源: 八米新加坡生产环境

---

## 📦 项目结构

```
bami-wms-complete/
├── database/                    # 完整数据库结构与初始化数据
│   ├── zzz_base_data.sql       # 基础数据 (~11.7MB)
│   ├── zzz_org_permission.sql  # 组织架构权限 (~4.4MB)
│   ├── zzz_wms.sql             # WMS业务表 (~397KB)
│   ├── zzz_wms_base.sql        # WMS基础表 (~80KB)
│   ├── zzz_bms_base.sql        # BMS计费基础表 (~51KB)
│   ├── zzz_bms_bill.sql        # BMS账单表 (~28KB)
│   ├── zzz_bms_data.sql        # BMS初始化数据 (~444B)
│   └── zzz_open_external.sql   # 开放平台表 (~7KB)
├── source/                      # 完整源代码
│   ├── wms-parent-language/            # WMS核心模块
│   ├── base-data-parent-language/      # 基础数据模块
│   ├── boss-crm-parent-language/       # CRM客户管理
│   ├── org-permission-parent-language/ # 组织架构权限
│   ├── open-external-parent-language/  # 开放平台
│   ├── usercenter-parent-language/     # 用户中心
│   ├── common-parent-language/         # 公共组件
│   └── web-gateway-language/           # 网关服务
├── docs/                        # 文档
│   ├── DATABASE_SCHEMA_COMPLETE.md     # 完整数据库结构文档
│   ├── DEPLOYMENT.md                   # 部署文档
│   └── API.md                          # API接口文档
└── README.md                    # 项目说明
```

---

## 🏗️ 系统架构

### 技术栈
- **后端**: Spring Boot 2.0.4 + Spring Cloud Finchley.SR1 + Java 8
- **前端**: Vue.js 2.7.15 + Ant Design Vue 1.7.8 + Vue i18n
- **数据库**: MySQL 5.7+ / MariaDB 10.6+
- **缓存**: Redis 5.x
- **消息队列**: RabbitMQ / ActiveMQ
- **微前端**: Qiankun 2.10.16
- **ORM**: MyBatis-Plus

### 模块划分

| 模块 | 说明 | 代码量 |
|------|------|--------|
| wms-parent-language | WMS核心（入库/出库/库内/报表） | ~2,684 files |
| base-data-parent-language | 基础数据（商品/货位/仓库） | ~380 files |
| boss-crm-parent-language | CRM客户管理 | ~394 files |
| org-permission-parent-language | 组织架构权限 | ~1,013 files |
| open-external-parent-language | 开放平台（奇门/聚石塔） | ~352 files |
| usercenter-parent-language | 用户中心 | ~252 files |
| common-parent-language | 公共组件工具 | ~275 files |
| web-gateway-language | API网关 | ~44 files |

**总计**: ~4,051个Java文件, ~308个XML文件, ~4,467个总文件

---

## 🗄️ 数据库结构

### 统计概览
| 分类 | 表数量 |
|------|--------|
| **总计** | **142张表** |
| WMS业务表 | ~70张 |
| BMS计费表 | ~34张 |
| 组织架构权限 | ~24张 |
| 开放平台 | ~6张 |
| 基础数据 | ~36张 |

### 核心模块

#### 1. WMS 仓储管理
- **入库管理**: 入库通知、收货、上架、销退
- **出库管理**: 波次、拣货、复核、封箱、交接
- **库内管理**: 盘点、移库、补货、库存账本
- **基础数据**: 商品、货位、容器、供应商

#### 2. BMS 计费管理
- **计费策略**: 多维度计费规则配置
- **价格管理**: 价格表、阶梯价格、客户协议价
- **账单管理**: 费用账单、结算账单、调整单
- **人工计费**: 手工录入费用、异常处理

详细结构见: [DATABASE_SCHEMA_COMPLETE.md](./DATABASE_SCHEMA_COMPLETE.md)

---

## 🚀 快速开始

### 环境要求
- JDK 1.8+
- MySQL 5.7+ / MariaDB 10.6+
- Redis 5.x
- Maven 3.6+
- Node.js 14+

### 数据库初始化
```bash
# 按顺序执行SQL文件
mysql -u root -p < database/zzz_base_data.sql
mysql -u root -p < database/zzz_org_permission.sql
mysql -u root -p < database/zzz_wms_base.sql
mysql -u root -p < database/zzz_wms.sql
mysql -u root -p < database/zzz_bms_base.sql
mysql -u root -p < database/zzz_bms_bill.sql
mysql -u root -p < database/zzz_bms_data.sql
mysql -u root -p < database/zzz_open_external.sql
```

### 后端启动
```bash
cd source/web-gateway-language
mvn spring-boot:run

cd source/wms-parent-language/wms-server
mvn spring-boot:run
```

### 前端启动
```bash
cd source/wms-parent-language/wms-web
npm install
npm run serve
```

---

## 📋 功能清单

### WMS 核心功能
- [x] 多仓库多货主管理
- [x] 入库管理（ASN、收货、质检、上架）
- [x] 出库管理（波次、拣货、复核、打包、交接）
- [x] 库内作业（盘点、移库、补货、调拨）
- [x] 库存管理（实时库存、库存预警、效期管理）
- [x] 批次/序列号管理
- [x] RF手持终端作业
- [x] 报表与数据分析
- [x] 开放平台接口（奇门、聚石塔）

### BMS 计费功能
- [x] 多维度计费策略（按件、按重、按体积、按订单）
- [x] 阶梯价格配置
- [x] 客户协议价格
- [x] 自动计费引擎
- [x] 费用账单生成
- [x] 结算对账
- [x] 费用调整与审核

---

## 🔐 系统账号

### 测试环境
- **地址**: http://asc.8bami.com/
- **账号**: ysadmin
- **密码**: zzz123456

### 数据库连接
- **主机**: rm-7xv8s1v6lw2j22ho2eo.mysql.rds.aliyuncs.com:3306
- **账号**: 根据具体数据库配置

---

## 📚 文档

- [数据库结构文档](./DATABASE_SCHEMA_COMPLETE.md)
- [部署文档](./docs/DEPLOYMENT.md)
- [API接口文档](./docs/API.md)

---

## 📝 更新记录

| 日期 | 版本 | 更新内容 |
|------|------|----------|
| 2026-04-14 | v2.0 | 新增BMS计费系统，完整数据库导出 |
| 2026-04-13 | v1.0 | 初始版本，WMS核心功能 |

---

## 📄 许可证

Copyright © 2026 八米科技 (Bami Technology). All rights reserved.

---

**注意**: 本系统为八米科技商业产品，仅供授权客户使用。

*最后更新: 2026-04-14*
