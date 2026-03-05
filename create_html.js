const fs = require('fs');
const path = require('path');

// 创建输出目录
const outputDir = path.join(require('os').homedir(), 'Desktop', 'ppt_slides');
if (!fs.existsSync(outputDir)) {
  fs.mkdirSync(outputDir, { recursive: true });
}

// 创建 HTML 预览文件，然后可以用 canvas 截图
const slides = [
  {
    title: "八米科技",
    subtitle: "跨境供应链数字化解决方案领导者",
    type: "cover"
  },
  {
    title: "公司简介",
    items: [
      "公司全称：广州八米网络科技有限公司",
      "品牌名称：八米科技（BAMIKEJI）",
      "官方网站：www.bamitms.com",
      "企业定位：中国领先的跨境供应链数字化解决方案服务商",
      "行业经验：15年国际物流行业深耕，服务1200+成功客户",
      "企业荣誉：2025年入选广东省科技型中小企业库"
    ],
    type: "content"
  },
  {
    title: "核心业务定位",
    items: [
      "专注为国际物流、集运、海外仓企业提供软件系统支撑",
      "通过数字化手段帮助传统跨境物流企业实现转型升级",
      "构建「获客+集运+代购+商城」一体化管理生态",
      "赋能中国品牌出海，打通端到端供应链业务体系"
    ],
    type: "content"
  },
  {
    title: "四大核心产品",
    products: [
      {name: "代购集运系统", desc: "独家研发的代购商城与集运一体化系统，支持纯集运业务场景"},
      {name: "跨境TMS系统", desc: "适合海陆空铁运输、集装转运、自营国际专线"},
      {name: "海外仓WMS系统", desc: "助力海外仓企业搭建全球化仓库布局"},
      {name: "集成供应链", desc: "建设端到端业务体系，提供数字化协同"}
    ],
    type: "product"
  },
  {
    title: "核心竞争优势",
    advantages: [
      {num: "15年", desc: "行业沉淀经验"},
      {num: "80%", desc: "技术+服务人员占比"},
      {num: "1200+", desc: "成功客户案例"},
      {num: "7×24", desc: "小时客服在线"},
      {num: "首创", desc: "多组织+多仓+联盟模式"},
      {num: "百万票次", desc: "年订单系统考验"},
      {num: "定制开发", desc: "灵活可拓展"},
      {num: "1亿+", desc: "商品池资源"}
    ],
    type: "advantage"
  },
  {
    title: "服务模式",
    items: [
      "标准化SaaS产品：开箱即用，按年/月付费，适合中小型企业",
      "定制化开发服务：根据企业特殊需求深度定制，适合大型客户",
      "7×24小时客服支持：强大客服团队全天候在线",
      "持续升级迭代：紧跟行业发展，持续产品升级"
    ],
    type: "content"
  },
  {
    title: "目标客户群体",
    items: [
      "国际货运代理企业",
      "华人集运公司（反向海淘服务商）",
      "自营国际专线物流企业",
      "海外仓运营商（一件代发、FBA中转）",
      "跨境电商平台",
      "品牌出海企业",
      "代购平台及个人代购服务商"
    ],
    type: "content"
  },
  {
    title: "代表客户",
    clients: ["中欧联运", "恒发国际", "广州海淘集运", "俄罗斯涅瓦物流", "四季安集团", "弘海铁盛", "泛昇国际", "嘉拓智能", "广东晟晖再生资源集团"],
    type: "client"
  },
  {
    title: "最新动态",
    items: [
      "2026年3月：与菲律宾最大零售IT服务商ASC签署长期战略合作",
      "2025年12月：入选广东省科技型中小企业库",
      "2025年：与嘉拓智能达成战略合作，进军新能源智能制造物流",
      "2025年6月：与广东晟晖再生资源集团合作建设供应链数智化平台"
    ],
    type: "content"
  },
  {
    title: "广州八米网络科技有限公司",
    subtitle: "八米科技 BAMIKEJI",
    website: "www.bamitms.com",
    tagline: "15年行业经验 · 1200+成功客户 · 让跨境物流更智能",
    type: "end"
  }
];

function generateHTML() {
  let html = `<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
* { margin: 0; padding: 0; box-sizing: border-box; }
body { font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif; }
.slide {
  width: 1280px;
  height: 720px;
  position: relative;
  overflow: hidden;
  page-break-after: always;
}
.slide-cover {
  background: linear-gradient(135deg, #0A2342 0%, #005293 100%);
}
.slide-content { background: #fff; }
.slide-product { background: #E6F5FF; }
.slide-client {
  background: linear-gradient(135deg, #0A2342 0%, #005293 100%);
}
.slide-end {
  background: linear-gradient(135deg, #005293 0%, #0A2342 100%);
}
.header {
  position: absolute;
  top: 0; left: 0;
  width: 1280px; height: 80px;
  background: #005293;
  display: flex;
  align-items: center;
  padding-left: 30px;
}
.header h2 {
  color: #fff;
  font-size: 36px;
  font-weight: bold;
}
.page-num {
  position: absolute;
  bottom: 15px; right: 30px;
  font-size: 14px;
  color: #999;
}
</style>
</head>
<body>`;

  slides.forEach((slide, index) => {
    html += generateSlideHTML(slide, index + 1, slides.length);
  });

  html += `</body></html>`;
  return html;
}

function generateSlideHTML(slide, num, total) {
  let content = '';
  
  if (slide.type === 'cover') {
    content = `
      <div style="position:absolute;top:280px;left:50px;width:120px;height:4px;background:#0096C7;"></div>
      <div style="position:absolute;top:280px;right:50px;width:120px;height:4px;background:#0096C7;"></div>
      <div style="position:absolute;top:220px;left:0;width:1280px;text-align:center;font-size:72px;font-weight:bold;color:#fff;">${slide.title}</div>
      <div style="position:absolute;top:340px;left:0;width:1280px;text-align:center;font-size:32px;color:#0096C7;">${slide.subtitle}</div>
      <div style="position:absolute;bottom:0;left:0;width:1280px;height:50px;background:#005293;"></div>
    `;
  } else if (slide.type === 'end') {
    content = `
      <div style="position:absolute;top:200px;left:0;width:1280px;text-align:center;font-size:40px;font-weight:bold;color:#fff;">${slide.title}</div>
      <div style="position:absolute;top:300px;left:0;width:1280px;text-align:center;font-size:28px;color:#0096C7;">${slide.subtitle}</div>
      <div style="position:absolute;top:380px;left:0;width:1280px;text-align:center;font-size:24px;color:#fff;">${slide.website}</div>
      <div style="position:absolute;top:480px;left:0;width:1280px;text-align:center;font-size:18px;color:#fff;">${slide.tagline}</div>
      <div style="position:absolute;bottom:80px;left:480px;width:320px;height:3px;background:#0096C7;"></div>
    `;
  } else if (slide.type === 'content') {
    let itemsHtml = slide.items.map((item, i) => 
      `<div style="position:absolute;top:${120 + i * 70}px;left:50px;font-size:22px;color:#333;">• ${item}</div>`
    ).join('');
    content = `
      <div class="header"><h2>${slide.title}</h2></div>
      ${itemsHtml}
      <div class="page-num">${num} / ${total}</div>
    `;
  } else if (slide.type === 'product') {
    const positions = [
      {x: 40, y: 120}, {x: 660, y: 120},
      {x: 40, y: 390}, {x: 660, y: 390}
    ];
    let cardsHtml = slide.products.map((prod, i) => {
      const pos = positions[i];
      return `
        <div style="position:absolute;top:${pos.y}px;left:${pos.x}px;width:580px;height:240px;background:#fff;border-radius:10px;border:2px solid #0096C7;">
          <div style="position:absolute;top:0;left:0;width:580px;height:45px;background:#005293;border-radius:8px 8px 0 0;"></div>
          <div style="position:absolute;top:8px;left:15px;font-size:20px;font-weight:bold;color:#fff;">${prod.name}</div>
          <div style="position:absolute;top:60px;left:20px;width:540px;font-size:15px;color:#333;line-height:1.5;">${prod.desc}</div>
        </div>
      `;
    }).join('');
    content = `
      <div style="position:absolute;top:0;left:0;width:1280px;height:90px;background:#0A2342;display:flex;align-items:center;justify-content:center;">
        <h2 style="color:#fff;font-size:42px;font-weight:bold;">${slide.title}</h2>
      </div>
      ${cardsHtml}
      <div class="page-num">${num} / ${total}</div>
    `;
  } else if (slide.type === 'advantage') {
    const positions = [
      {x: 50, y: 150}, {x: 330, y: 150}, {x: 610, y: 150}, {x: 890, y: 150},
      {x: 50, y: 380}, {x: 330, y: 380}, {x: 610, y: 380}, {x: 890, y: 380}
    ];
    let advsHtml = slide.advantages.map((adv, i) => {
      const pos = positions[i];
      return `
        <div style="position:absolute;top:${pos.y}px;left:${pos.x + 40}px;width:120px;height:120px;background:#005293;border-radius:50%;display:flex;align-items:center;justify-content:center;">
          <span style="color:#fff;font-size:22px;font-weight:bold;">${adv.num}</span>
        </div>
        <div style="position:absolute;top:${pos.y + 130}px;left:${pos.x}px;width:200px;text-align:center;font-size:14px;color:#333;">${adv.desc}</div>
      `;
    }).join('');
    content = `
      <div style="position:absolute;top:30px;left:0;width:1280px;text-align:center;font-size:48px;font-weight:bold;color:#0A2342;">${slide.title}</div>
      <div style="position:absolute;top:100px;left:540px;width:200px;height:4px;background:#0096C7;"></div>
      ${advsHtml}
      <div class="page-num">${num} / ${total}</div>
    `;
  } else if (slide.type === 'client') {
    const positions = [
      {x: 60, y: 140}, {x: 450, y: 140}, {x: 840, y: 140},
      {x: 60, y: 240}, {x: 450, y: 240}, {x: 840, y: 240},
      {x: 60, y: 340}, {x: 450, y: 340}, {x: 840, y: 340}
    ];
    let clientsHtml = slide.clients.map((client, i) => {
      const pos = positions[i];
      return `
        <div style="position:absolute;top:${pos.y}px;left:${pos.x}px;width:380px;height:60px;background:#005293;border-radius:8px;display:flex;align-items:center;justify-content:center;">
          <span style="color:#fff;font-size:18px;font-weight:bold;">${client}</span>
        </div>
      `;
    }).join('');
    content = `
      <div style="position:absolute;top:50px;left:0;width:1280px;text-align:center;font-size:42px;font-weight:bold;color:#fff;">${slide.title}</div>
      ${clientsHtml}
      <div class="page-num" style="color:#fff;">${num} / ${total}</div>
    `;
  }
  
  return `<div class="slide slide-${slide.type}">${content}</div>`;
}

// 保存 HTML 文件
const html = generateHTML();
const htmlPath = path.join(outputDir, 'preview.html');
fs.writeFileSync(htmlPath, html);
console.log('HTML preview created:', htmlPath);

// 同时输出每个幻灯片的纯文本摘要
const summaryPath = path.join(outputDir, 'slides_summary.txt');
let summary = '八米科技企业介绍PPT - 内容摘要\n';
summary += '='.repeat(50) + '\n\n';

slides.forEach((slide, i) => {
  summary += `第${i + 1}页: ${slide.title}\n`;
  if (slide.items) {
    slide.items.forEach(item => summary += `  - ${item}\n`);
  }
  if (slide.products) {
    slide.products.forEach(p => summary += `  - ${p.name}: ${p.desc}\n`);
  }
  if (slide.advantages) {
    slide.advantages.forEach(a => summary += `  - ${a.num}: ${a.desc}\n`);
  }
  if (slide.clients) {
    summary += `  - 客户: ${slide.clients.join(', ')}\n`;
  }
  summary += '\n';
});

fs.writeFileSync(summaryPath, summary);
console.log('Summary created:', summaryPath);