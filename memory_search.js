#!/usr/bin/env node
/**
 * 大地哥的记忆检索系统
 * 用法: node memory_search.js [关键词]
 */

const fs = require('fs');
const path = require('path');

const MEMORY_DIR = path.join(__dirname, 'memory');
const MEMORY_FILE = path.join(__dirname, 'MEMORY.md');

function searchInFile(filePath, keyword) {
    try {
        const content = fs.readFileSync(filePath, 'utf8');
        const lines = content.split('\n');
        const matches = [];
        
        lines.forEach((line, index) => {
            if (line.toLowerCase().includes(keyword.toLowerCase())) {
                matches.push({
                    line: index + 1,
                    text: line.trim()
                });
            }
        });
        
        return matches;
    } catch (e) {
        return [];
    }
}

function getAllMemoryFiles() {
    try {
        return fs.readdirSync(MEMORY_DIR)
            .filter(f => f.endsWith('.md'))
            .map(f => path.join(MEMORY_DIR, f));
    } catch (e) {
        return [];
    }
}

function searchMemory(keyword) {
    console.log(`🔍 搜索记忆: "${keyword}"\n`);
    
    const results = {
        longTerm: [],
        daily: []
    };
    
    // 搜索长期记忆
    results.longTerm = searchInFile(MEMORY_FILE, keyword);
    
    // 搜索日常记忆
    const dailyFiles = getAllMemoryFiles();
    dailyFiles.forEach(file => {
        const matches = searchInFile(file, keyword);
        if (matches.length > 0) {
            results.daily.push({
                file: path.basename(file),
                matches: matches
            });
        }
    });
    
    // 输出结果
    console.log('📚 长期记忆 (MEMORY.md):');
    if (results.longTerm.length === 0) {
        console.log('   无匹配');
    } else {
        results.longTerm.forEach(m => {
            console.log(`   第${m.line}行: ${m.text.substring(0, 80)}`);
        });
    }
    
    console.log('\n📝 日常记忆:');
    if (results.daily.length === 0) {
        console.log('   无匹配');
    } else {
        results.daily.forEach(d => {
            console.log(`\n   📄 ${d.file}:`);
            d.matches.forEach(m => {
                console.log(`      第${m.line}行: ${m.text.substring(0, 80)}`);
            });
        });
    }
    
    return results;
}

// 列出所有记忆
function listAllMemories() {
    console.log('📂 记忆文件列表:\n');
    
    // 长期记忆
    console.log('长期记忆:');
    console.log(`  📄 MEMORY.md - 核心知识、项目信息`);
    
    // 日常记忆
    console.log('\n日常记录:');
    const dailyFiles = getAllMemoryFiles();
    if (dailyFiles.length === 0) {
        console.log('  无记录');
    } else {
        dailyFiles.forEach(f => {
            const stats = fs.statSync(f);
            console.log(`  📄 ${path.basename(f)} - ${stats.size} bytes`);
        });
    }
}

// 主函数
const command = process.argv[2];
const keyword = process.argv[3];

if (!command || command === 'list') {
    listAllMemories();
} else if (command === 'search' && keyword) {
    searchMemory(keyword);
} else {
    console.log('用法:');
    console.log('  node memory_search.js list           - 列出所有记忆');
    console.log('  node memory_search.js search [关键词] - 搜索记忆');
    console.log('');
    console.log('示例:');
    console.log('  node memory_search.js search 菲律宾');
    console.log('  node memory_search.js search WMS');
}
