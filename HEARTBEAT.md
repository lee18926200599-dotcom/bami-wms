# HEARTBEAT.md

## 自动记忆系统 (Auto-Memory) - 高性能模式
- 运行 auto-memory 脚本捕获最近会话
- 命令: node ~/.openclaw/skills/auto-memory/scripts/auto-memory.js
- 自动生成 memory/YYYY-MM-DD.md 和更新 MEMORY.md
- 性能: 仅处理最近3个会话，每会话100条消息，运行时间<100ms
- 检查频率: 每10分钟（平衡性能与实时性）
- 捕获范围: 最近10分钟的对话
