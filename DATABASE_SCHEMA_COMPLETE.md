# WMS + BMS 完整数据库结构文档

> 数据来源: 八米新加坡服务器 (47.236.202.11)  
> 导出时间: 2026-04-14 09:16:22  
> 数据库版本: MySQL 10.6.24 (MariaDB)

---

## 📊 数据库文件清单

| 文件名 | 大小 | 行数 | 说明 |
|--------|------|------|------|
| `zzz_base_data.sql` | 11.7 MB | 58,074 | 基础数据（地区、字典、初始化数据） |
| `zzz_org_permission.sql` | 4.4 MB | 27,854 | 组织架构与权限系统 |
| `zzz_wms.sql` | 397 KB | 4,748 | WMS核心业务表 |
| `zzz_wms_base.sql` | 80 KB | 942 | WMS基础数据表 |
| `zzz_bms_base.sql` | 51 KB | 729 | BMS计费系统基础表 |
| `zzz_bms_bill.sql` | 28 KB | 389 | BMS账单业务表 |
| `zzz_bms_data.sql` | 444 B | 20 | BMS初始化数据 |
| `zzz_open_external.sql` | 7 KB | 96 | 开放平台接口表 |

**总计**: 8个文件，~16.7 MB，~92,852 行SQL，**142张表**

---

## 🏗️ 模块结构

### 1. WMS 仓储管理系统 (~70张表)

#### 1.1 基础数据模块 (base_*) - 20张表
| 表名 | 说明 |
|------|------|
| `base_area` | 库区信息 |
| `base_box_type` | 箱型配置 |
| `base_container` | 容器管理 |
| `base_container_type` | 容器类型 |
| `base_item` | 商品主数据 |
| `base_item_image` | 商品图片 |
| `base_item_keep_rule` | 商品养护规则 |
| `base_item_unit` | 商品单位 |
| `base_item_warehouse_info` | 商品仓库信息 |
| `base_location` | 货位管理 |
| `base_location_rule` | 货位规则 |
| `base_location_rule_detail` | 货位规则明细 |
| `base_owner_area` | 货主库区 |
| `base_platform` | 电商平台 |
| `base_store_print` | 店铺打印配置 |
| `base_store_print_detail` | 店铺打印明细 |
| `base_supplier` | 供应商管理 |
| `base_workbench` | 工作台配置 |

#### 1.2 入库管理模块 (inbound_*) - 12张表
| 表名 | 说明 |
|------|------|
| `inbound_notify` | 入库通知单 |
| `inbound_notify_detail` | 入库通知明细 |
| `inbound_notify_detail_pool` | 入库通知明细池 |
| `inbound_notify_pool` | 入库通知池 |
| `inbound_rejection` | 入库拒收 |
| `inbound_rejection_detail` | 入库拒收明细 |
| `inbound_sale_return_batch` | 销退批次 |
| `inbound_sale_return_batch_detail` | 销退批次明细 |
| `inbound_sale_return_order` | 销退单 |
| `inbound_sale_return_order_detail` | 销退单明细 |
| `receiving_inbound` | 收货单 |
| `receiving_inbound_detail` | 收货明细 |
| `receiving_work` | 收货作业 |
| `receiving_work_detail` | 收货作业明细 |
| `putaway` | 上架单 |
| `putaway_detail` | 上架明细 |

#### 1.3 库内管理模块 (inside_*) - 16张表
| 表名 | 说明 |
|------|------|
| `inside_difference` | 差异单 |
| `inside_difference_detail` | 差异单明细 |
| `inside_inventory_check` | 盘点单 |
| `inside_inventory_check_detail` | 盘点明细 |
| `inside_inventory_check_difference_rel` | 盘点差异关联 |
| `inside_move_area_task` | 移库任务(区域) |
| `inside_move_area_task_detail` | 移库任务明细 |
| `inside_move_location_task` | 移库任务(货位) |
| `inside_move_location_task_detail` | 移库任务明细 |
| `inside_replenish_plan` | 补货计划 |
| `inside_replenish_plan_detail` | 补货计划明细 |
| `inside_rf_task` | RF任务 |
| `inside_rf_work` | RF作业 |
| `inside_rf_work_detail` | RF作业明细 |
| `inside_take_down` | 下架单 |
| `inside_take_down_detail` | 下架明细 |

#### 1.4 出库管理模块 (outbound_*) - 32张表
| 表名 | 说明 |
|------|------|
| `outbound_abnormal_order` | 异常订单 |
| `outbound_face_order` | 面单订单 |
| `outbound_handover` | 交接单 |
| `outbound_handover_detail` | 交接明细 |
| `outbound_intercept_order` | 拦截订单 |
| `outbound_intercepter_item_homing` | 拦截商品归位 |
| `outbound_lack_stock_order` | 缺货订单 |
| `outbound_lack_stock_order_detail` | 缺货明细 |
| `outbound_order` | 出库单 |
| `outbound_order_distribution_pool` | 分配池 |
| `outbound_order_goods_lack_item` | 缺货商品 |
| `outbound_order_goods_lack_manage` | 缺货管理 |
| `outbound_order_package` | 包裹信息 |
| `outbound_order_summary_pool` | 汇总池 |
| `outbound_original_order` | 原始订单 |
| `outbound_original_order_detail` | 原始订单明细 |
| `outbound_original_order_extend` | 原始订单扩展 |
| `outbound_out_order` | 外部出库单 |
| `outbound_out_order_detail` | 外部出库明细 |
| `outbound_seal_box` | 封箱记录 |
| `outbound_seal_box_detail` | 封箱明细 |
| `outbound_supplier_order` | 供应商订单 |
| `outbound_supplier_order_detail` | 供应商订单明细 |
| `outbound_supplier_order_extend` | 供应商订单扩展 |
| `outbound_sys_lack_info` | 系统缺货 |
| `outbound_task_detail` | 出库任务明细 |
| `outbound_task_head` | 出库任务头 |
| `outbound_wait_replenish_order` | 待补货订单 |
| `outbound_wait_replenish_order_detail` | 待补货明细 |
| `outbound_wave_task` | 波次任务 |
| `outbound_wide_order` | 大宗订单 |
| `outbound_wide_order_detail` | 大宗订单明细 |

#### 1.5 库存账本模块 (stockbook_*) - 14张表
| 表名 | 说明 |
|------|------|
| `stockbook_occupation_flow` | 占用流水 |
| `stockbook_owner_goods_sn` | 货主商品SN |
| `stockbook_owner_inout_location_flow` | 货位出入流水 |
| `stockbook_owner_inout_warehouse_flow` | 仓库出入流水 |
| `stockbook_owner_location` | 货主货位库存 |
| `stockbook_owner_location_day` | 货主货位日结 |
| `stockbook_owner_location_occupation` | 货主货位占用 |
| `stockbook_owner_location_occupation_day` | 货主货位占用日结 |
| `stockbook_owner_package_conversion_flow` | 包装转换流水 |
| `stockbook_owner_sale` | 货主销售 |
| `stockbook_owner_sale_day` | 货主销售日结 |
| `stockbook_owner_sale_flow` | 货主销售流水 |
| `stockbook_owner_warehouse` | 货主仓库库存 |
| `stockbook_owner_warehouse_day` | 货主仓库日结 |
| `stockbook_sn_inout_location_flow` | SN货位出入流水 |
| `stockbook_sn_inout_warehouse_flow` | SN仓库出入流水 |

#### 1.6 其他业务表
| 表名 | 说明 |
|------|------|
| `abnormal_inbound_notify` | 异常入库通知 |
| `batch_manage` | 批次管理 |
| `cancel_instruct` | 取消指令 |
| `disassembly_team` | 拆单组 |
| `excel_task` | Excel任务 |
| `item_pick_rule` | 商品拣货规则 |
| `occupation_location` | 货位占用 |
| `order_operate_log` | 订单操作日志 |
| `profit_loss_order` | 损益单 |
| `profit_loss_order_detail` | 损益单明细 |
| `video_info` | 视频信息 |
| `wms_rule` | WMS规则 |

---

### 2. BMS 计费管理系统 (~34张表)

#### 2.1 计费策略与规则 (bms_strategy_*, bms_rule_*)
| 表名 | 说明 |
|------|------|
| `bms_account_strategy` | 计费策略 |
| `bms_account_strategy_execute_record` | 策略执行记录 |
| `bms_account_strategy_expense_item` | 策略费用项 |
| `bms_billing_rule` | 计费规则 |
| `bms_billing_strategy` | 计费策略 |
| `bms_price_strategy` | 价格策略 |
| `bms_strategy_expense_dimension` | 费用维度 |
| `bms_strategy_price` | 策略价格 |
| `bms_strategy_screen_condition` | 筛选条件 |

#### 2.2 价格表管理 (bms_price_*)
| 表名 | 说明 |
|------|------|
| `bms_price_table` | 价格表 |
| `bms_price_table_config` | 价格表配置 |
| `bms_price_table_config_billing_item` | 价格表计费项 |
| `bms_price_table_config_cust_ref` | 价格表客户关联 |
| `bms_price_table_config_item` | 价格表商品关联 |
| `bms_price_table_detail` | 价格表明细 |

#### 2.3 计费来源与账单 (bms_billing_*, bms_account_*)
| 表名 | 说明 |
|------|------|
| `bms_account_bill` | 账户账单 |
| `bms_billing_source` | 计费来源 |
| `bms_billing_source_org_allocate` | 组织分摊 |
| `bms_billing_source_table_detail` | 来源表明细 |
| `bms_billing_source_table_ref` | 来源表关联 |
| `bms_biilling_strategy_exception` | 计费策略异常 |

#### 2.4 费用与结算 (bms_expense_*, bms_settlement_*)
| 表名 | 说明 |
|------|------|
| `bms_expense_adjust_bill` | 费用调整单 |
| `bms_expense_bill` | 费用账单 |
| `bms_expense_bill_ref` | 费用账单关联 |
| `bms_expense_item` | 费用项目 |
| `bms_expense_item_cust_ref` | 费用项客户关联 |
| `bms_settlement_bill` | 结算账单 |

#### 2.5 人工计费 (bms_artificial_*)
| 表名 | 说明 |
|------|------|
| `bms_artificial_account` | 人工计费 |
| `bms_artificial_account_config` | 人工计费配置 |
| `bms_artificial_account_config_cust_ref` | 配置客户关联 |
| `bms_artificial_account_config_item` | 配置商品关联 |
| `bms_keyboard_dict` | 键盘字典 |

---

### 3. 组织架构与权限系统 (~24张表)

#### 3.1 组织与用户
| 表名 | 说明 |
|------|------|
| `org_department` | 部门 |
| `org_enterprise` | 企业 |
| `org_organization` | 组织 |
| `org_position` | 职位 |
| `org_role` | 角色 |
| `org_user` | 用户 |
| `org_user_department` | 用户部门关联 |
| `org_user_role` | 用户角色关联 |

#### 3.2 权限与菜单
| 表名 | 说明 |
|------|------|
| `permission_menu` | 菜单 |
| `permission_resource` | 资源 |
| `permission_role` | 权限角色 |
| `permission_role_menu` | 角色菜单关联 |
| `permission_role_resource` | 角色资源关联 |
| `permission_user_data` | 用户数据权限 |

#### 3.3 数据权限与日志
| 表名 | 说明 |
|------|------|
| `data_permission_rule` | 数据权限规则 |
| `data_permission_rule_detail` | 规则明细 |
| `operate_log` | 操作日志 |
| `login_log` | 登录日志 |

---

### 4. 开放平台 (~6张表)
| 表名 | 说明 |
|------|------|
| `open_external_app` | 外部应用 |
| `open_external_callback` | 回调配置 |
| `open_external_interface` | 接口配置 |
| `open_external_log` | 接口日志 |

---

### 5. 基础数据 (~36张表)

#### 5.1 地区与字典
| 表名 | 说明 |
|------|------|
| `base_country` | 国家 |
| `base_province` | 省份 |
| `base_city` | 城市 |
| `base_district` | 区县 |
| `base_dictionary` | 数据字典 |
| `base_dictionary_detail` | 字典明细 |

#### 5.2 仓库与货主
| 表名 | 说明 |
|------|------|
| `base_warehouse` | 仓库 |
| `base_warehouse_area` | 仓库区域 |
| `base_warehouse_service` | 仓库服务 |
| `base_owner` | 货主 |
| `base_owner_warehouse` | 货主仓库 |

---

## 📈 核心统计

| 分类 | 数量 |
|------|------|
| **总表数** | 142 张 |
| **WMS业务表** | ~70 张 |
| **BMS计费表** | ~34 张 |
| **组织架构权限表** | ~24 张 |
| **开放平台表** | ~6 张 |
| **基础数据表** | ~36 张 |

---

## 🔗 关联关系

### 核心关联字段
- `warehouse_id` - 仓库ID（多租户隔离字段）
- `owner_id` / `owner_code` - 货主ID/编码
- `created_by` / `created_name` - 创建人
- `modified_by` / `modified_name` - 修改人
- `version` - 乐观锁版本号
- `deleted_flag` - 逻辑删除标记

### 业务单号规则
- 入库通知单: ASN + 日期 + 序号
- 出库单: SO + 日期 + 序号
- 盘点单: PD + 日期 + 序号
- 移库单: YK + 日期 + 序号

---

## 📝 更新记录

| 时间 | 更新内容 |
|------|----------|
| 2026-04-14 | 首次完整导出，包含WMS+BMS全部142张表 |

---

*文档生成时间: 2026-04-14*  
*数据来源: 八米新加坡生产环境*
